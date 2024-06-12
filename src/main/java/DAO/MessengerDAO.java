package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Messenger;

public class MessengerDAO implements InterfaceDAO<Messenger> {

	@Override
	public int add(Messenger t) {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.saveOrUpdate(t);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		session.close();
		sessionFactory.close();
		return 0;
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
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		messenger = session.find(Messenger.class, t.getUserNameGuestName());
		session.close();
		sessionFactory.close();
		return messenger;
	}

	@Override
	public List<Messenger> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
