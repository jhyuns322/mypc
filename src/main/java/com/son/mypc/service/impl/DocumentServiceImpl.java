package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Document;
import com.son.mypc.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

	/* MyBatis */
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 1) 게시글 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 게시글 상세 페이지, 게시글 작성 페이지, 새 게시글 작성 컨트롤러, 게시글 수정 컨트롤러
	 */
	@Override
	public Document getBoardView(Document input) throws Exception {
		Document result = null;

		try {
			result = sqlSession.selectOne("DocumentMapper.selectBoardItem", input);

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
	 * 2) 게시글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 게시판 목록 페이지
	 */
	@Override
	public List<Document> getBoardList(Document input) throws Exception {
		List<Document> result = null;

		try {
			result = sqlSession.selectList("DocumentMapper.selectBoardList", input);

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
	 * 3) 게시글 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 게시판 목록 페이지
	 */
	@Override
	public int getBoardCount(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("DocumentMapper.selectBoardCount", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 4) 새 게시글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 새 게시글 작성 컨트롤러
	 */
	@Override
	public int addBoard(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.insert("DocumentMapper.insertBoardItem", input);

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
	 * 5) 게시글 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 게시글 수정 컨트롤러
	 */
	@Override
	public int editBoard(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("DocumentMapper.updateBoardItem", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 6) 조회수 자동 누적
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 게시글 상세 페이지
	 */
	@Override
	public int editBorardHits(Document input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("DocumentMapper.updateBoardHits", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
		
		return result;
	}

	/**
	 * 7) 게시글 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 게시글 삭제 컨트롤러
	 */
	@Override
	public int deleteBoard(Document input) throws Exception {
		int result = 0;

		try {
			// 게시글 삭제 전 자신을 참조하는 회원의 membno컬럼을 0으로 수정
			sqlSession.update("DocumentMapper.unsetDocument", input);

			result = sqlSession.delete("DocumentMapper.deleteBoardItem", input);

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

	/**
	 * 8) 문의글 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 문의글 확인 페이지, 문의글 작성 컨트롤러
	 */
	@Override
	public Document getInquiryView(Document input) throws Exception {

		Document result = null;

		try {
			result = sqlSession.selectOne("DocumentMapper.selectInquiryItem", input);

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
	 * 9) 문의글 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 문의글 목록 페이지
	 */
	@Override
	public List<Document> getInquiryList(Document input) throws Exception {
		List<Document> result = null;

		try {
			result = sqlSession.selectList("DocumentMapper.selectInquiryList", input);

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
	 * 10) 문의글 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 관리자 메인 페이지, 문의글 목록 페이지
	 */
	@Override
	public int getInquiryCount(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("DocumentMapper.selectInquiryCount", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 11) 새 문의글 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 문의글 작성 컨트롤러
	 */
	@Override
	public int addInquiry(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.insert("DocumentMapper.insertInquiryItem", input);

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
	 * 12) 이메일 발송 후 답변 확인 처리
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 문의글 답변 메일 전송 컨트롤러
	 */
	public int editInquiry(Document input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("DocumentMapper.updateInquiryItem", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}
}
