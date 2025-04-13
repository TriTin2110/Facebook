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

import HibernateUtil.HibernateUtil;
import Model.Announce;
import Model.Friend;
import Model.FriendRequest2;
import Model.User;

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
	public User selectById(String t) {
		// TODO Auto-generated method stub
		openSession();
		User user = new User();
		try {
			user = session.find(User.class, t);
			Hibernate.initialize(user.getAnnounces()); // Khi load object thì load luôn cả list (do eager không hỗ trợ
														// nhiều list)
			Hibernate.initialize(user.getListFriend());
			Hibernate.initialize(user.getFriendReceives());
			Hibernate.initialize(user.getFriendRequests());
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
			Hibernate.initialize(user.getFriendReceives());
			Hibernate.initialize(user.getFriendRequests());
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
		return null;
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
		friendId = friendId.substring(0, friendId.indexOf("-"));

		friend.setUserId(friendId);
		friend = selectById(friend.getUserId());

		String messageForReceiver = "Bạn và " + friend.getUserInformation().getFullName() + " đã trở thành bạn bè!";
		String messageForSender = "Bạn và " + user.getUserInformation().getFullName() + " đã trở thành bạn bè!";

		List<Announce> announces = updateAnnouce(user, announceId, messageForReceiver, messageForSender);
		user.setAnnounces(announces);

		user = addingFriend(user, friend);
		update(user);
	}

	private List<Announce> updateAnnouce(User user, String annouceId, String messageForReceiver,
			String messageForSender) {
		List<Announce> announces = user.getAnnounces();

		Announce annouceReceiver = announces.stream().filter(a -> a.getId().equals(annouceId)).findFirst().get();
//		Announce annouceSender = annouceReceiver.getAnnouce();

		announces.remove(annouceReceiver);

		annouceReceiver.setMessage(messageForReceiver);
//		annouceSender.setMessage(messageForSender);
//		annouceReceiver.setTypeOfAnnouce("SN");

		announces.add(annouceReceiver);

		return announces;
	}

	public User addingFriend(User user1, User user2) {
		List<User> users = user1.getListFriend();
		users.add(user2);
		user1.setListFriend(users);
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

	public List<FriendRequest2> selectFriendRequestsByUserId(String userId) {
		List<FriendRequest2> users = new ArrayList<FriendRequest2>();

		openSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<FriendRequest2> query = builder.createQuery(FriendRequest2.class);
			Root<FriendRequest2> root = query.from(FriendRequest2.class);
			query.where(builder.like(root.get("userId"), "%" + userId + "%"));
			users = session.createQuery(query).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}

		return users;
	}

}
