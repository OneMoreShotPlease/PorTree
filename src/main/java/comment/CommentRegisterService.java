package comment;

import java.time.LocalDateTime;

public class CommentRegisterService {
	private CommentDao commentDao;
	
	public CommentRegisterService(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	public Long regist(Long portfolio_id, Long user_id, String contents) {
		Comment comment = new Comment(
				portfolio_id, user_id, LocalDateTime.now(), contents
		);
		commentDao.insert(comment);
		return comment.getComment_id();
	}
	
	public Long update(Long comment_id, Long portfolio_id, Long user_id, String contents) {
		Comment comment = new Comment(
				portfolio_id, user_id, LocalDateTime.now(), contents
		);
		comment.setComment_id(comment_id);
		commentDao.update(comment);
		return comment.getComment_id();
	}
	
	public void delete(Long id) {
		commentDao.delete(id);
	}
}
