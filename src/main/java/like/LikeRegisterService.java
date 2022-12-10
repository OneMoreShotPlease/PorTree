package like;

import authentication.SimpleUser;
import authentication.SimpleUserDao;

public class LikeRegisterService {
	private LikeDao likeDao;
	private SimpleUserDao simpleUserDao;
	
	public LikeRegisterService(LikeDao likeDao) {
		this.likeDao = likeDao;
	}
	
	public Long press(Long portfolio_id, SimpleUser user) {
		Like exist_like = likeDao.checkExist(portfolio_id, user);
		// 좋아요 누르기
		if (exist_like == null) {
			Like like = new Like(
					portfolio_id, user
			);
			likeDao.insert(like);
			return like.getLike_id();
		}
		
		// 좋아요 취소하기
		else {
			likeDao.delete(exist_like);
			return null;
		}
	}

}
