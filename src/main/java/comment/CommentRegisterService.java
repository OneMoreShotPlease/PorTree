package comment;

import java.time.LocalDateTime;
import authentication.SimpleUser;

public class CommentRegisterService {
	private CommentDao commentDao;
	
	public CommentRegisterService(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	public Long regist(Long portfolio_id, SimpleUser user, String contents) {
		Comment comment = new Comment(
				portfolio_id, user, LocalDateTime.now(), contents
		);
		commentDao.insert(comment);
		return comment.getComment_id();
	}
	
	public Long update(Long comment_id, Long portfolio_id, SimpleUser user, String contents) {
		Comment comment = new Comment(
				portfolio_id, user, LocalDateTime.now(), contents
		);
		comment.setComment_id(comment_id);
		commentDao.update(comment);
		return comment.getComment_id();
	}
	
	public void delete(Long id) {
		commentDao.delete(id);
	}
}
