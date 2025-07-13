package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Enums.TypeAnnouce;
import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.Friend;
import Model.User;
import WebSocket.Notification;
import thread.SendNotification;

public class UserDAO implements InterfaceDAO<User> {
	private int result;
	private SessionFactory sessionFactory;
	private Session session;

	private void openSession() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

		}
		result = 0;
	}

	private void closeSession() {
		if (sessionFactory != null) {
			session.close();
			sessionFactory.close();
			sessionFactory = null;
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
//		removeFriend(t.getUserId());
		openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.remove(t);
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

	public void removeFriend(String id) {
		try {
			openSession();
			Transaction transaction = session.beginTransaction();
			session.createNativeQuery("delete from user_friend where user_id = :id").setParameter("id", id)
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}

	}

	@Override
	public int update(User t) {
		// TODO Auto-generated method stub
		try {
			openSession();
			Transaction transaction = session.beginTransaction();
			session.saveOrUpdate(t);
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
	public User selectById(String t) {
		// TODO Auto-generated method stub
		openSession();
		User user = new User();
		try {
			user = session.find(User.class, t);
			// Khi load object thì load luôn cả list (do eager không hỗ trợ nhiều list)
			Hibernate.initialize(user.getAnnounces());
			Hibernate.initialize(user.getListFriend());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			Hibernate.initialize(user.getListFriend());
			Hibernate.initialize(user.getAnnounces());
		} catch (NoResultException e) {
			// TODO: handle exception

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return user;

	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		List<User> users = null;
		try {
			openSession();
			TypedQuery<User> query = session.createQuery("from User", User.class);
			users = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return users;
	}

	public User confirmEmail(String idUser) {
		User user = selectById(idUser);
		if (user != null) {
			user.setIdentifyStatus(true);
			openSession();
			try {
				Transaction transaction = session.beginTransaction();
				session.update(user);
				transaction.commit();
				session.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				closeSession();
			}
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return users;
	}

	public User addingFriend(User user1, User user2, Announce announce) {
		try {
			List<User> friends = user1.getListFriend();
			List<Announce> announces = user1.getAnnounces();
			announces.add(announce);
			friends.add(user2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user1;
	}

	public List<User> selectFriendsByUserId(String userId) {
		List<User> users = new ArrayList<User>();

		openSession();

		Transaction transaction = session.beginTransaction();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(builder.equal(root.get("userId"), userId));
		session.createQuery(query).getSingleResult().getListFriend().forEach(o -> users.add(o));
		transaction.commit();

		closeSession();

		return users;
	}

	public void processAddingFriend(String friendId, User user) {
//	// TODO Auto-generated method stub
		User friend = selectById(friendId);

		String messageForReceiver = "Bạn và <strong>" + friend.getUserInformation().getFullName()
				+ "</strong> đã trở thành bạn bè!";
		String messageForSender = "Bạn và <strong>" + user.getUserInformation().getFullName()
				+ "</strong> đã trở thành bạn bè!";

		Announce announceForReceiver = Announce.createAnnouceObject(messageForReceiver, user, friend.getAvatar(),
				TypeAnnouce.FRIEND_ACCEPTED);
		Announce announceForSender = Announce.createAnnouceObject(messageForSender, friend, user.getAvatar(),
				TypeAnnouce.FRIEND_ACCEPTED);
		try {
			javax.websocket.Session session = Notification.getSession(friendId);
			SendNotification t1 = new SendNotification(session, messageForSender);
			t1.start();
			Thread.sleep(500);
			// Send notification for receiver
			session = Notification.getSession(user.getUserId());
			SendNotification t2 = new SendNotification(session, messageForReceiver);
			t2.start();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.getListFriend().add(friend);
		user.getAnnounces().add(announceForReceiver);
		friend.getListFriend().add(user);
		friend.getAnnounces().add(announceForSender);
		update(user);
		update(friend);
		AnnounceDAO announceDAO = new AnnounceDAO();
		announceDAO.removeFriendRequest(friend, user);
	}

}
