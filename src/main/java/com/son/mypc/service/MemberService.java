package com.son.mypc.service;

import java.util.List;

import com.son.mypc.model.Member;

public interface MemberService {
	
	/**
	 * 1) 회원 데이터 상세 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 회원관리 페이지, 회원가입 컨트롤러, 비밀번호 찾기 컨트롤러
	 */
	public Member getMemberItem(Member input) throws Exception;

	/**
	 * 2) 회원 아이디 데이터 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 비밀번호 찾기 컨트롤러, 사용자 로그인 컨트롤러, 관리자 로그인 컨트롤러
	 */
	public Member getIdCheck(Member input) throws Exception;
	
	/**
	 * 3) 회원 이메일 데이터 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 아이디 찾기 컨트롤러
	 */
	public Member getEmailCheck(Member input) throws Exception;
	
	/**
	 * 4) 회원 데이터 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 회원 목록 페이지
	 */
	public List<Member> getMemberList(Member input) throws Exception;

	/**
	 * 5) 회원번호 데이터 목록 조회
	 * @return 조회된 데이터 LIST
	 * @throws Exception
	 * @use 쿠폰 지급 컨트롤러
	 */
	public List<Member> getMembnoList(Member input) throws Exception;
	
	/**
	 * 6) 회원 데이터 수 조회
	 * @return 조회된 데이터 수
	 * @throws Exception
	 * @use 회원관리 목록 페이지, 관리자 메인 페이지, 아이디 중복검사 컨트롤러, 이메일 중복검사 컨트롤러
	 */
	public int getMemberCount(Member input) throws Exception;

	/**
	 * 7) 회원 데이터 등록
	 * @return 등록된 데이터 수
	 * @throws Exception
	 * @use 회원가입 컨트롤러
	 */
	public int addMember(Member input) throws Exception;

	/**
	 * 8) 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 회원 정보 수정 컨트롤러(Y)
	 */
	public int editMember(Member input) throws Exception;
	
	/**
	 * 9) 관리자용 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 관리자용 회원 정보 수정 컨트롤러
	 */
	public int editMemberForAdmin(Member input) throws Exception;
	
	/**
	 * 10) 관리자용 회원 데이터 수정
	 * @return 수정된 데이터 수
	 * @throws Exception
	 * @use 비밀번호 찾기 컨트롤러
	 */
	public int editUserPw(Member input) throws Exception;

	/**
	 * 11) 회원 데이터 삭제
	 * @return 삭제된 데이터 수
	 * @throws Exception
	 * @use 관리자용 회원 삭제 컨트롤러
	 */
	public int deleteMember(Member input) throws Exception;

}
