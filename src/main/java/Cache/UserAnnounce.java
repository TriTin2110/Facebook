package Cache;

import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.AnnounceDAO;
import Model.Announce;

public class UserAnnounce {
	private static AnnounceDAO announceDAO = new AnnounceDAO();
	private static LoadingCache<String, List<Announce>> sentAnnounces = Caffeine.newBuilder()
			.build(key -> announceDAO.selectSentAnnouncesByFromUserId(key));
	private static LoadingCache<String, List<Announce>> receivedAnnounces = Caffeine.newBuilder()
			.build(key -> announceDAO.selectReceivedAnnouncesByToUserId(key));

	public static List<Announce> getSentAnnounces(String userId) {
		return sentAnnounces.get(userId);
	}

	public static void updateSentAnnounces(String userId) {
		sentAnnounces.refresh(userId);
	}

	public static List<Announce> getReceivedAnnounces(String userId) {
		return receivedAnnounces.get(userId);
	}

	public static void updateReceivedAnnounces(String userId) {
		receivedAnnounces.refresh(userId);
	}

	public static void insertSentAnnounce(String userId, Announce announce) {
		List<Announce> announces = sentAnnounces.get(userId);
		announces.add(announce);
		sentAnnounces.put(userId, announces);
	}

	public static void insertReceivedAnnounce(String userId, Announce announce) {
		List<Announce> announces = receivedAnnounces.get(userId);
		announces.add(announce);
		receivedAnnounces.put(userId, announces);
	}
}
