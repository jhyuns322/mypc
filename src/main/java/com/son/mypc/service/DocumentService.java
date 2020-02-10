package com.son.mypc.service;

import java.util.List;

import com.son.mypc.model.Document;

public interface DocumentService {
	
	/** 자유게시판 (Board) */
	
	/**
	 * 1) 게시글 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 게시글 상세 페이지, 게시글 작성 페이지, 새 게시글 작성 컨트롤러, 게시글 수정 컨트롤러
	 */
	public Document getBoardView(Document input) throws Exception;

	/**
	 * 2) 게시글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 게시판 목록 페이지
	 */
	public List<Document> getBoardList(Document input) throws Exception;

	/**
	 * 3) 게시글 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 게시판 목록 페이지
	 */
	public int getBoardCount(Document input) throws Exception;

	/**
	 * 4) 새 게시글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 새 게시글 작성 컨트롤러
	 */
	public int addBoard(Document input) throws Exception;

	/**
	 * 5) 게시글 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 게시글 수정 컨트롤러
	 */
	public int editBoard(Document input) throws Exception;
	
	/**
	 * 6) 조회수 자동 누적
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	public int editBorardHits(Document input) throws Exception;

	/**
	 * 7) 게시글 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 게시글 삭제 컨트롤러
	 */
	public int deleteBoard(Document input) throws Exception;

	
	/** 문의 (Inquiry) */
	
	/**
	 * 8) 문의글 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 문의글 확인 페이지, 문의글 작성 컨트롤러
	 */
	public Document getInquiryView(Document input) throws Exception;

	/**
	 * 9) 문의글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 문의글 목록 페이지
	 */
	public List<Document> getInquiryList(Document input) throws Exception;

	/**
	 * 10) 문의글 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 관리자 메인 페이지, 문의글 목록 페이지
	 */
	public int getInquiryCount(Document input) throws Exception;

	/**
	 * 11) 새 문의글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 문의글 작성 컨트롤러
	 */
	public int addInquiry(Document input) throws Exception;

	/**
	 * 12) 이메일 발송 후 답변 확인 처리
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 문의글 답변 메일 전송 컨트롤러
	 */
	public int editInquiry(Document input) throws Exception;

}
