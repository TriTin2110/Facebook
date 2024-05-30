package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Comment;

public class CommentDAO implements InterfaceDAO<Comment> {
	@Override
	public int add(Comment t) {
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
	public int remove(Comment t) {
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
	public int update(Comment t) {
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

	@Override
	public Comment selectById(Comment t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
