package Cache;

import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.AnnounceDAO;
import Model.Announce;

public class UserAnnounce {
	private static AnnounceDAO announceDAO = new AnnounceDAO();
	private static LoadingCache<String, List<Announce>> announces = Caffeine.newBuilder()
			.build(key -> announceDAO.selectAnnoucesByUserId(key));

	public static List<Announce> getAnnounces(String userId) {
		return announces.get(userId);
	}

	public static void updateAnnounces(String userId) {
		announces.refresh(userId);
	}
}
