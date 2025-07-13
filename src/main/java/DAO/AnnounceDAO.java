package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.User;

public class AnnounceDAO implements InterfaceDAO<Announce> {

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
	public int add(Announce t) {
		// TODO Auto-generated method stub
		openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(t);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			result = 0;
			transaction.rollback();
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int remove(Announce t) {
		// TODO Auto-generated method stub
		openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.remove(t);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int update(Announce t) {
		return 0;
	}

	@Override
	public Announce selectById(String t) {
		// TODO Auto-generated method stub
		Announce announce = new Announce();
		openSession();
		try {
			announce = session.find(Announce.class, t);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return announce;
	}

	@Override
	public List<Announce> selectAll() {
		// TODO Auto-generated method stub
		List<Announce> announces = new ArrayList<Announce>();
		openSession();
		try {
			announces = session.createQuery("from Announce", Announce.class).getResultList();
		} finally {
			// TODO: handle finally clause
			closeSession();
		}

		return announces;
	}

	public List<Announce> selectSentAnnouncesByFromUserId(String id) {
		List<Announce> announces = new ArrayList<Announce>();
		openSession();
		try {
			TypedQuery<Announce> query = session.createQuery("FROM Announce WHERE user_id=:from", Announce.class);
			query.setParameter("from", id);
			announces = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			closeSession();
		}

		return announces;
	}

	public void removeFriendRequest(User sender, User receiver) {
		openSession();
		try {
			String sql = "from Announce where sender_id=:sender and user_id=:receiver";
			TypedQuery<Announce> query = session.createQuery(sql, Announce.class);
			query.setParameter("sender", sender.getUserId());
			query.setParameter("receiver", receiver.getUserId());
			Announce announce = query.getSingleResult();
			remove(announce);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			closeSession();
		}
	}

}
