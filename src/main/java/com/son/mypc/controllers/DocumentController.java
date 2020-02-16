package com.son.mypc.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.MailHelper;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Comment;
import com.son.mypc.model.Document;
import com.son.mypc.service.CommentService;
import com.son.mypc.service.DocumentService;

@RestController
public class DocumentController {

	@Autowired
	WebHelper webHelper;
	@Autowired
	MailHelper mailHelper;

	@Autowired
	DocumentService documentService;
	@Autowired
	CommentService commentService;

	/** 새 게시글 작성 (사용자) */
	@RequestMapping(value = "boardPost", method = RequestMethod.POST)
	public Map<String, Object> boardPost() {

		/** session 조회 */
		HttpSession session = webHelper.getSession();
		int membno = (int) session.getAttribute("sessionMembno");

		/** 입력한 파라미터 수신 및 유효성 검사 */
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		if (subject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (content == null) {
			return webHelper.getJsonWarning("내용을 입력하세요.");
		}

		// 현재 날짜, 시간 >> 작성일
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		// 자유게시판 게시글의 doc_type = 'b'
		String doc_type = "b";

		// 조회 수
		int hits = 0;

		Document input = new Document();
		input.setDoc_type(doc_type);
		input.setSubject(subject);
		input.setContent(content);
		input.setReg_date(reg_date);
		input.setHits(hits);
		input.setMembno(membno);

		int output = 0;

		try {
			output = documentService.addBoard(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 게시글 수정 (사용자) */
	@RequestMapping(value = "boardUpdate", method = RequestMethod.PUT)
	public Map<String, Object> boardUpdate() {

		/** 사용자가 입려간 파라미터 수신 및 유효성 검사 */
		int docno = webHelper.getInt("docno");
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		if (subject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (content == null) {
			return webHelper.getJsonWarning("내용을 입력하세요.");
		}

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String edit_date = f.format(time.getTime());

		// 수정할 값들을 Beans에 담는다.
		Document input = new Document();
		input.setDocno(docno);
		input.setSubject(subject);
		input.setContent(content);
		input.setEdit_date(edit_date);

		Document output = null;

		try {
			// 데이터 수정
			documentService.editBoard(input);
			// 데이터 조회
			output = documentService.getBoardView(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 게시글 삭제 (사용자) */
	@RequestMapping(value = "boardDelete", method = RequestMethod.DELETE)
	public Map<String, Object> boardDelete() {

		HttpSession session = webHelper.getSession();
		String userId = (String) session.getAttribute("sessionUserId");

		int docno = webHelper.getInt("docno");
		String user_id = webHelper.getString("user_id");

		int output = 0;

		if (userId.equals(user_id)) {

			// 데이터 삭제에 필요한 조건값을 Beans에 저장하기
			Document input = new Document();
			input.setDocno(docno);

			try {
				// 데이터 삭제
				output = documentService.deleteBoard(input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 덧글 작성 (사용자) */
	@RequestMapping(value = "commentPost", method = RequestMethod.POST)
	public Map<String, Object> commentPost() {

		int membno = webHelper.getInt("membno");
		String comment = webHelper.getString("comment");
		int docno = webHelper.getInt("docno");

		if (membno == 0) {
			return webHelper.getJsonWarning("로그인 후 이용이 가능합니다.");
		}
		if (comment == null) {
			return webHelper.getJsonWarning("덧글을 입력하세요.");
		}

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		Comment inputComment = new Comment();
		inputComment.setComment(comment);
		inputComment.setMembno(membno);
		inputComment.setDocno(docno);
		inputComment.setReg_date(reg_date);

		int outputComm = 0;

		try {
			Document inputDocument = new Document();
			inputDocument.setDocno(docno);
			outputComm = commentService.addComment(inputComment, inputDocument);

		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemComm", outputComm);
		return webHelper.getJsonData(map);
	}

	/** 덧글 삭제 (사용자) */
	@RequestMapping(value = "commentDelete", method = RequestMethod.DELETE)
	public Map<String, Object> commentDelete() {

		int commno = webHelper.getInt("commno");
		int docno = webHelper.getInt("docno");

		Comment inputComment = new Comment();
		inputComment.setCommno(commno);

		int output = 0;

		try {
			Document inputDocument = new Document();
			inputDocument.setDocno(docno);
			output = commentService.deleteComment(inputComment, inputDocument);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 문의글 작성 (사용자) */
	@RequestMapping(value = "supportPost", method = RequestMethod.POST)
	public Map<String, Object> supportPost() {

		HttpSession session = webHelper.getSession();

		int membno = 0;

		if (session.getAttribute("sessionMembno") != null) {
			membno = (int) session.getAttribute("sessionMembno");
		} else {
			return webHelper.getJsonWarning("로그인 시 이용이 가능합니다.");
		}

		/** 입력한 파라미터 수신 및 유효성 검사 */
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		if (subject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (content == null) {
			return webHelper.getJsonWarning("내용을 입력하세요.");
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		// 미답변 = 'n', 답변 = 'y'
		String replied = "n";
		// 게시글 = 'b', 문의글= 'i'
		String doc_type = "i";

		Document input = new Document();
		input.setDoc_type(doc_type);
		input.setSubject(subject);
		input.setContent(content);
		input.setReg_date(reg_date);
		input.setReplied(replied);
		input.setMembno(membno);

		int output = 0;

		try {
			output = documentService.addInquiry(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 문의글 답변 메일 전송 (관리자) */
	@RequestMapping(value = "inquiryReply", method = RequestMethod.POST)
	public Map<String, Object> inquiryReply() {

		/** 사용자의 입력값 받기 */
		int docno = webHelper.getInt("docno");
		String email = webHelper.getString("email");
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");
		String reg_date = webHelper.getString("reg_date");
		String reSubject = webHelper.getString("mailSubject");
		String reContent = webHelper.getString("mailContent");

		/** 제목, 내용에 대한 입력 여부 검사 */
		if (reSubject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (reContent == null) {
			return webHelper.getJsonWarning("답변 내용을 입력하세요.");
		}

		// 기존 mailHelper 수정 >> content에 문의글 작성일, 문의 제목, 내용, 문의 답변을 포함하여 mailContent에 담음.
		String mailContent = mailHelper.mailInquiryContent(reg_date, subject, content, reContent);

		/** 메일 발송 처리 */
		try {
			// sendMail() 메서드 선언 시 throws를 정의했기 때문에 예외처리가 요구.
			mailHelper.sendMail(email, reSubject, mailContent);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		// 답변 여부에 대한 값을 n >> y로 변경 후 저장
		String replied = "y";

		/** 데이터 저장하기 */
		Document input = new Document();
		input.setDocno(docno);
		input.setReplied(replied);

		int output = 0;

		try {
			// 데이터 저장
			output = documentService.editInquiry(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 문의글 삭제 (관리자) */
	@RequestMapping(value = "deleteInquiry/{docno}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteInquiry(@PathVariable String[] docno) {

		// 저장된 결과를 조회하기 위한 객체
		List<Document> output = new ArrayList<Document>();
		try {
			for (int i = 0; i < docno.length; i++) {
				// 데이터 저장
				Document input = new Document();
				input.setDocno(Integer.parseInt(docno[i]));
				documentService.deleteBoard(input);
				// 삭제 데이터를 리스트에 저장
				output.add(input);
			}
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
}
