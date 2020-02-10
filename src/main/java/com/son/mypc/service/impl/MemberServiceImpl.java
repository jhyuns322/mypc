package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Member;
import com.son.mypc.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	/* MyBatis */
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 1) 회원 데이터 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 회원관리 페이지, 회원가입 컨트롤러, 비밀번호 찾기 컨트롤러
	 */
	@Override
	public Member getMemberItem(Member input) throws Exception {
		Member result = null;
		try {
			result = sqlSession.selectOne("MemberMapper.selectMemberItem", input);
			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("유저 정보가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("정보 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 2) 회원 아이디 데이터 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 비밀번호 찾기 컨트롤러, 사용자 로그인 컨트롤러, 관리자 로그인 컨트롤러
	 */
	@Override
	public Member getIdCheck(Member input) throws Exception {
		Member result = null;
		try {
			result = sqlSession.selectOne("MemberMapper.selectIdCheck", input);
			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("유저 정보가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("정보 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 3) 회원 이메일 데이터 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 아이디 찾기 컨트롤러
	 */
	@Override
	public Member getEmailCheck(Member input) throws Exception {
		Member result = null;
		try {
			result = sqlSession.selectOne("MemberMapper.selectEmailCheck", input);
			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("유저 정보가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("정보 조회에 실패했습니다.");
		}
		return result;
	}
	
	/**
	 * 4) 회원 데이터 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 회원 목록 페이지
	 */
	@Override
	public List<Member> getMemberList(Member input) throws Exception {
		List<Member> result = null;
		try {
			result = sqlSession.selectList("MemberMapper.selectMemberList", input);
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
	 * 5) 회원번호 데이터 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 쿠폰 지급 컨트롤러
	 */
	@Override
	public List<Member> getMembnoList(Member input) throws Exception {
		List<Member> result = null;
		try {
			result = sqlSession.selectList("MemberMapper.selectMembnoList", input);
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
	 * 6) 회원 데이터 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 회원관리 목록 페이지, 관리자 메인 페이지, 아이디 중복검사 컨트롤러, 이메일 중복검사 컨트롤러
	 */
	@Override
	public int getMemberCount(Member input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("MemberMapper.selectMemberCount", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 7) 회원 데이터 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 회원가입 컨트롤러
	 */
	@Override
	public int addMember(Member input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("MemberMapper.insertMembItem", input);
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
	 * 8) 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 회원 정보 수정 컨트롤러(Y)
	 */
	@Override
	public int editMember(Member input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("MemberMapper.updateMemberItem", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정될 값이 비어있습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 9) 관리자용 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 관리자용 회원 정보 수정 컨트롤러
	 */
	@Override
	public int editMemberForAdmin(Member input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("MemberMapper.updateMemberItemForAdmin", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정될 값이 비어있습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 10) 관리자용 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 비밀번호 찾기 컨트롤러
	 */
	@Override
	public int editUserPw(Member input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("MemberMapper.updateMemberPw", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정될 값이 비어있습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 11) 회원 데이터 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 관리자용 회원 삭제 컨트롤러
	 */
	@Override
	public int deleteMember(Member input) throws Exception {
		int result = 0;

		try {

			// 회원 탈퇴 전 참조하는 주문관리의 membno컬럼을 0으로 수정
			sqlSession.update("OrderMapper.unsetOrder", input);
			// 회원 탈퇴 전 참조하는 카트를 삭제
			sqlSession.delete("CartMapper.unsetCart",input);
			// 회원 탈퇴 전 참조하는 쿠폰의 membno컬럼을 0으로 수정
			sqlSession.update("CouponMapper.unsetCoupon", input);
			// 회원 탈퇴 전 참조하는 게시물의 membno컬럼을 0으로 수정
			sqlSession.update("DocumentMapper.unsetDocument", input);
			// 회원 탈퇴 전 참조하는 덧글의 membno컬럼을 0으로 수정
			sqlSession.update("CommentMapper.unsetComment", input);			
			
			result = sqlSession.delete("MemberMapper.deleteMemberItem", input);

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
