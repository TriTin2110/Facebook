package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.Friend;
import Model.User;

public class UserDAO implements InterfaceDAO<User> {
	private int result;
	private SessionFactory sessionFactory;
	private Session session;

	private void openSession() {
		if (sessionFactory == null || sessionFactory.isClosed() && !session.isOpen()) {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
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
	public int add(User t) {
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
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int remove(User t) {
		// TODO Auto-generated method stub
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.remove(t);
			transaction.commit();
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public int update(User t) {
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
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public User selectById(User t) {
		// TODO Auto-generated method stub
		openSession();
		User user = new User();
		try {
			user = session.find(User.class, t.getUserId());
			Hibernate.initialize(user.getAnnounces());
		} finally {
			// TODO: handle finally clause
			closeSession();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public User selectByEmail(User t) {
		// TODO Auto-generated method stub
		openSession();
		User user = null;
		try {
			TypedQuery<User> query = session.createQuery("from User where email = :email");
			query.setParameter("email", t.getEmail());
			user = query.getSingleResult();
			if (user != null)
				Hibernate.initialize(user.getAnnounces());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}

		return user;

	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public User confirmEmail(String idUser) {
		openSession();
		User user = new User();
		try {
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
			closeSession();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<Friend> selectByFullName(String fullName) {
		List<Friend> users = new ArrayList<Friend>();

		openSession();
		try {
			String queryStatement = "select new Model.Friend(u.userId, ui.fullName, u.avatar)  from User u "
					+ "join UserInformation ui on u.userId = ui.userId " + "where ui.fullName like :fullName";

			Query query = session.createQuery(queryStatement, Friend.class);
			query.setParameter("fullName", "%" + fullName + "%");
			users = query.getResultList();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
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
