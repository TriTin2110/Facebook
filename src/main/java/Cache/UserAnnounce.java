package Cache;

import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.AnnounceDAO;
import Model.Announce;

public class UserAnnounce {
	private static AnnounceDAO announceDAO = new AnnounceDAO();
	private static LoadingCache<String, List<Announce>> announcesCache = Caffeine.newBuilder()
			.build(key -> announceDAO.selectSentAnnouncesByFromUserId(key));

	public static List<Announce> getAnnounces(String userId) {
		return announcesCache.get(userId);
	}
}
