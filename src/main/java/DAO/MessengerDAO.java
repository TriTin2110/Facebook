package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Messenger;

public class MessengerDAO implements InterfaceDAO<Messenger> {
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
	public int add(Messenger t) {
		// TODO Auto-generated method stub
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.saveOrUpdate(t);
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
	public int remove(Messenger t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Messenger t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Messenger selectById(Messenger t) {
		Messenger messenger = new Messenger();
		openSession();
		try {
			messenger = session.find(Messenger.class, t.getUserNameGuestName());
		} finally {
			// TODO: handle finally clause
			closeSession();
		}
		return messenger;
	}

	@Override
	public List<Messenger> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
