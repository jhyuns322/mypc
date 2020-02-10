package com.son.mypc.helper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailHelper {

	JavaMailSender mailSender;

	public MailHelper(JavaMailSender sender) {
		this.mailSender = sender;
	}

	public String mailInquiryContent(String reg_date, String subject, String content, String reContent) {
		String result = String.format("%s" + "<h3> 문의 제목 </h3>" + "%s" + "<br/>" + "<h3> 문의 내용 </h3>" + "%s" + "<br/><hr/><br/>"
				+ "<h3> 관리자 답변 </h3>" + "%s", reg_date, subject, content, reContent);
		return result;
	}

	public String mailFindIdContent(String userId, String name) {
		String result = String
				.format("<strong>" + name + "</strong> 회원님의 아이디는 <strong>" + userId + "</strong> 입니다. <br/>" + "<a href='http://localhost:8080/mypc/login'>마이피씨(MYPC)로 이동</a>");
		return result;
	}
	
	public String mailFindPwContent(String userPw, String name) {
		String result = String
				.format("<strong>" + name + "</strong> 회원님의 임시 비밀번호는 <strong>" + userPw + "</strong> 입니다. <br/>" + "보안을 위해 로그인 후 비밀번호를 변경해주세요. <br/>" + "<a href='http://localhost:8080/mypc/login'>마이피씨(MYPC)로 이동</a>");
		return result;
	}	

	/**
	 * 메일을 발송한다.
	 * 
	 * @param receiver - 수신자 메일 주소
	 * @param subject  - 제목
	 * @param content  - 내용
	 * @throws MessagingException
	 */
	public void sendMail(String receiver, String subject, String content) throws Exception {

		log.debug("----------------------------------------------------");
		log.debug(String.format("To: %s", receiver));
		log.debug(String.format("Subject: %s", subject));
		log.debug(String.format("Content: %s", content));
		log.debug("----------------------------------------------------");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setSubject(subject);
		helper.setText(content, true);
		helper.setTo(new InternetAddress(receiver));
		mailSender.send(message);
	}
}
