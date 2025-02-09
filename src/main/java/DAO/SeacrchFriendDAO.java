package DAO;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import HibernateUtil.HibernateUtil;
import Model.Friend;
import Model.User;

public class SeacrchFriendDAO {
	@SuppressWarnings("unchecked")
	public List<Friend> selectByFullName(String fullName) {
		List<Friend> searchFriends = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String queryStatement = "select u.userId, ui.fullName, u.avatar from User u "
					+ "join UserInformation ui on u.userId = ui.userId " + "where ui.fullName like :fullName";

			Query query = session.createQuery(queryStatement, User.class);
			query.setParameter("fullName", "%" + fullName + "%");

			searchFriends = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
		}
		return searchFriends;
	}
}
