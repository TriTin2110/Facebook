package DAO;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Post;
import Model.User;

public class PostDAO implements InterfaceDAO<Post> {
	@Override
	public int add(Post t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.save(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		session.close();
		sessionFactory.close();
		return result;
	}

	@Override
	public int remove(Post t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.remove(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		session.close();
		sessionFactory.close();
		return result;
	}

	@Override
	public int update(Post t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.update(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		session.close();
		sessionFactory.close();
		return result;
	}

	public List<Post> getPostsByUser(User user) {
		List<Post> posts = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			posts = session.createQuery("from Post where user = :user order by createdAt desc", Post.class)
					.setParameter("user", user).list();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
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

		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
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
		}
	}

	public List<Post> getPostByDateDesc() {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			List<Post> posts = session.createQuery("from Post order by createdAt desc", Post.class).list();
			return posts;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
