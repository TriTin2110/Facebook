package DAO;

import java.util.List;

import org.hibernate.Session;

import HibernateUtil.HibernateUtil;
import Model.FriendReceive;

public class FriendReceiveDAO implements InterfaceDAO<FriendReceive> {

	@Override
	public int add(FriendReceive t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int remove(FriendReceive t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FriendReceive t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FriendReceive selectById(String t) {
		// TODO Auto-generated method stub
		Session session = null;
		FriendReceive friendReceive = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			friendReceive = session.find(FriendReceive.class, t);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.close();
		}

		return friendReceive;
	}

	@Override
	public List<FriendReceive> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
