package DAO;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Post;
import Model.User;

public class PostDAO implements InterfaceDAO<Post> {
	private int result;
	private SessionFactory sessionFactory;
	private Session session;

	private void openSession() {
		if (sessionFactory == null || sessionFactory.isClosed() && !session.isOpen()) {
			this.sessionFactory = HibernateUtil.getSessionFactory();
			this.session = sessionFactory.openSession();
		}
		result = 0;
	}

	private void closeSession() {
		if (session.isOpen() && sessionFactory.isOpen()) {
			this.session.close();
			this.sessionFactory.close();
		}
	}

	@Override
	public int add(Post t) {
		// TODO Auto-generated method stub
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.save(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = 0;
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int remove(Post t) {
		// TODO Auto-generated method stub
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.remove(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = 0;
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int update(Post t) {
		// TODO Auto-generated method stub
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.update(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = 0;
		} finally {
			closeSession();
		}
		return result;
	}

	public List<Post> getPostsByUser(User user) {
		List<Post> posts = null;
		try {
			openSession();
			Transaction transaction = session.beginTransaction();
			posts = session.createQuery("from Post where user = :user order by createdAt desc", Post.class)
					.setParameter("user", user).list();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return posts;
	}

	@Override
	public Post selectById(Post t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void exportPostsToSQL(String filePath) {
		List<Post> posts = selectAll();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(filePath, true));
			writer.println("LOCK TABLES `post` WRITE;");
			writer.println("/*!40000 ALTER TABLE `post` DISABLE KEYS */;");
			writer.println("INSERT INTO `post` VALUES");

			for (int i = 0; i < posts.size(); i++) {
				Post post = posts.get(i);
				writer.printf("('%s','%s','%s','%s','%s')", post.getPostId(), post.getPostContent().replace("'", "\\'"),
						post.getPostImage(), sdf.format(post.getCreatedAt()), post.getUser().getUserId());

				if (i < posts.size() - 1) {
					writer.println(",");
				} else {
					writer.println(";");
				}
			}

			writer.println("/*!40000 ALTER TABLE `post` ENABLE KEYS */;");
			writer.println("UNLOCK TABLES;");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	public List<Post> getPostByDateDesc() {
		List<Post> posts = null;
		try {
			openSession();
			posts = session.createQuery("from Post order by createdAt desc", Post.class).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return posts;
	}

	public List<Post> selectAllPostByUserId(String id) {
		List<Post> posts = null;
		try {
			openSession();
			TypedQuery<Post> query = session.createQuery("from Post where user_id=:id", Post.class);
			query.setParameter("id", id);
			posts = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return posts;
	}
}
