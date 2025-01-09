package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.SearchFriend;
import Model.User;

public class UserDAO implements InterfaceDAO<User> {

	@Override
	public int add(User t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.save(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.close();
			fac.close();
		}
		return result;
	}

	@Override
	public int remove(User t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.remove(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.close();
			fac.close();
		}
		return result;
	}

	@Override
	public int update(User t) {
		// TODO Auto-generated method stub
		int result = 0;
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.update(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
			fac.close();
		}
		return result;
	}

	@Override
	public User selectById(User t) {
		// TODO Auto-generated method stub
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		User user = new User();
		user = session.find(User.class, t.getUserId());
		Hibernate.initialize(user.getListFriendId());
		Hibernate.initialize(user.getAnnounces());
		session.close();
		fac.close();
		return user;
	}

	@SuppressWarnings("unchecked")
	public User selectByEmail(User t) {
		// TODO Auto-generated method stub
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		List<User> result = null;
		Query query = session.createQuery("from User where email = :email");
		query.setParameter("email", t.getEmail());
		result = query.getResultList();
		User user = (result.isEmpty()) ? null : result.get(0);
		if (user != null) {
			Hibernate.initialize(user.getListFriendId());
			Hibernate.initialize(user.getAnnounces());
		}
		session.close();
		fac.close();
		return user;

	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public User confirmEmail(String idUser) {
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			User user = new User();
			user.setUserId(idUser);
			user = selectById(user);
			user.setIdentifyStatus(true);
			Transaction transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			session.close();
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
			fac.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SearchFriend> selectByFullName(String fullName) {
		List<SearchFriend> users = new ArrayList<SearchFriend>();
		SessionFactory fac = HibernateUtil.getSessionFactory();
		Session session = fac.openSession();
		try {
			String queryStatement = "select new Model.SearchFriend(u.userId, ui.fullName, u.avatar)  from User u "
					+ "join UserInformation ui on u.userId = ui.userId " + "where ui.fullName like :fullName";

			Query query = session.createQuery(queryStatement, SearchFriend.class);
			query.setParameter("fullName", "%" + fullName + "%");
			users = query.getResultList();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
			fac.close();
		}
		return users;
	}

	public void processAddingFriend(String friendId, User user) {
		// TODO Auto-generated method stub
		User friend = new User();
		String announceId = friendId;
		friendId = friendId.substring(0, 64);

		friend.setUserId(friendId);
		friend = selectById(friend);
		updateFriend(user.getUserId(), friend);
		updateUser(friendId, user);
		removeAnnounce(announceId);
	}

	public void updateFriend(String userId, User friend) {
		addingFriend(userId, friend);
	}

	public void updateUser(String friendId, User user) {
		addingFriend(friendId, user);
	}

	public void removeAnnounce(String announceId) {
		AnnounceDAO announceDAO = new AnnounceDAO();
		System.out.println(announceId);
		Announce announce = announceDAO.selectById(new Announce(announceId));
		announceDAO.remove(announce);
	}

	public void addingFriend(String id, User user) {
		String listFriend = user.getListFriendId();
		listFriend += id + ";";
		user.setListFriend(listFriend);
		user.setFriendQuantity((user.getFriendQuantity() == null) ? 1 : user.getFriendQuantity() + 1);
		update(user);
		user = selectById(user);
	}
}
