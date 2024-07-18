package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.UserInformation;

public class UserInformationDAO implements InterfaceDAO<UserInformation> {

	@Override
	public int add(UserInformation t) {
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
	public int remove(UserInformation t) {
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
	public int update(UserInformation t) {
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
	public UserInformation selectById(UserInformation t) {
		// TODO Auto-generated method stub
		UserInformation userInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		userInformation = session.get(UserInformation.class, t.getUserId());
		session.close();
		return userInformation;
	}

	@Override
	public List<UserInformation> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
