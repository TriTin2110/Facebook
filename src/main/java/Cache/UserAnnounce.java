package Cache;

import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.AnnounceDAO;
import Model.Announce;

public class UserAnnounce {
	private static AnnounceDAO announceDAO = new AnnounceDAO();
	private static LoadingCache<String, List<Announce>> sentAnnounces = Caffeine.newBuilder()
			.build(key -> announceDAO.selectSendedAnnouncesByFromUserId(key));
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
}
