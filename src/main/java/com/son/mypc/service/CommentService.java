package com.son.mypc.service;

import java.util.List;

import com.son.mypc.model.Comment;
import com.son.mypc.model.Document;

public interface CommentService {

	/**
	 * 1) 덧글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	public List<Comment> getCommentList(Comment input) throws Exception;

	/**
	 * 2) 덧글 목록 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	public int getCommentCount(Comment input) throws Exception;

	/**
	 * 3) 덧글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	public int addComment(Comment inputComment, Document inputDocument) throws Exception;
	
	/**
	 * 4) 게시글 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	public int deleteComment(Comment inputComment, Document inputDocument) throws Exception;

}