package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Comment;
import com.son.mypc.model.Document;
import com.son.mypc.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	/* MyBatis */
	@Autowired
	SqlSession sqlSession;

	/**
	 * 1) 덧글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	@Override
	public List<Comment> getCommentList(Comment input) throws Exception {
		List<Comment> result = null;

		try {
			result = sqlSession.selectList("CommentMapper.selectCommentList", input);

			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 2) 덧글 목록 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	@Override
	public int getCommentCount(Comment input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("CommentMapper.selectCommentCount", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 3) 덧글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	@Override
	public int addComment(Comment inputComment, Document inputDocument) throws Exception {

		int result = 0;

		try {
			
			int docno = inputComment.getDocno();
			sqlSession.insert("CommentMapper.insertCommentItem", inputComment);
			int comment = sqlSession.selectOne("CommentMapper.selectCommentCount", docno);
			inputDocument.setComment(comment);
			result = sqlSession.update("DocumentMapper.updateBoardComment", inputDocument);
			
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 저장에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 4) 게시글 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	@Override
	public int deleteComment(Comment inputComment, Document inputDocument) throws Exception {
		int result = 0;

		try {
			
			result = sqlSession.delete("CommentMapper.deleteCommentItem", inputComment);
			int comment = sqlSession.selectOne("CommentMapper.selectCommentCount", inputDocument);
			inputDocument.setComment(comment);
			sqlSession.update("DocumentMapper.updateBoardComment", inputDocument);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("삭제된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 삭제에 실패했습니다.");
		}

		return result;
	}

}
