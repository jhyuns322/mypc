package com.son.mypc.model;

import lombok.Data;

/**
 * 업로드 된 파일의 정보를 저장하기 위한 JavaBeans
 * - 이 클래스의 객체가 업로드 된 파일의 수 만큼 생성되어 List 형태로 보관된다.
 */
@Data
public class UploadItem {
	private String fieldName;    // <input type="file">의 name 속성
	private String originName;   // 원본 파일 이름
	private String filePath;     // 서버 상의 파일 경로
	private String contentType;  // 파일의 형식
	private long fileSize;       // 파일의 용량
}