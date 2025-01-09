package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.User;

public class AnnounceDAO implements InterfaceDAO<Announce> {

	@Override
	public int add(Announce t) {
		// TODO Auto-generated method stub
		int result = 1;
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
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
			fac.close();
			session.close();
		}
		return result;
	}

	@Override
	public int remove(Announce t) {
		// TODO Auto-generated method stub
		int result = 1;
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.remove(t);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			fac.close();
		}
		return result;
	}

	@Override
	public int update(Announce t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Announce selectById(Announce t) {
		// TODO Auto-generated method stub
		Announce announce = new Announce();
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			announce = session.find(Announce.class, t.getId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
			fac.close();
		}
		return announce;
	}

	@Override
	public List<Announce> selectAll() {
		// TODO Auto-generated method stub
		List<Announce> announces = new ArrayList<Announce>();
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		announces = session.createQuery("from Announce", Announce.class).getResultList();
		session.close();
		fac.close();
		return announces;
	}

	public void setUpAnnounce(String idFriend, String idUserSendRequest, String userSendRequestFullName,
			String userSendRequestAvatar) {
		User toUser = new User();
		UserDAO userDAO = new UserDAO();
		toUser.setUserId(idFriend);
		toUser = userDAO.selectById(toUser);
		List<Announce> announces = toUser.getAnnounces();
		long dateReceiveRequest = System.currentTimeMillis();

		Announce announce = new Announce(idUserSendRequest + dateReceiveRequest,
				userSendRequestFullName + " đã gửi lời mời kết bạn dành cho bạn!", userSendRequestFullName,
				userSendRequestAvatar, toUser, false, dateReceiveRequest);
		announces.add(announce);
		toUser.setAnnounces(announces);
		add(announce);
	}
}
