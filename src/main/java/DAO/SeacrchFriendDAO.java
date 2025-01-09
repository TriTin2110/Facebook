package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import HibernateUtil.HibernateUtil;
import Model.SearchFriend;
import Model.User;

public class SeacrchFriendDAO {
	@SuppressWarnings("unchecked")
	public List<SearchFriend> selectByFullName(String fullName) {
		List<SearchFriend> SearchFriends = new ArrayList<SearchFriend>();
		try {
			String queryStatement = "u.userId, ui.fullName, u.avatar from User u "
					+ "join UserInformation ui on u.userId = ui.userId " + "where ui.fullName like :fullName";

			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(queryStatement, User.class);
			query.setParameter("fullName", "%" + fullName + "%");
			SearchFriends = query.getResultList();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SearchFriends;
	}
}
