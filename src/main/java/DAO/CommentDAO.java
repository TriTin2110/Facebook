package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Comment;

public class CommentDAO implements InterfaceDAO<Comment> {
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
	public int add(Comment t) {
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
	public int remove(Comment t) {
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
	public int update(Comment t) {
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

	@Override
	public Comment selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
