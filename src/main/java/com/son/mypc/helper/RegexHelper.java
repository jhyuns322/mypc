package com.son.mypc.helper;

import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexHelper {
	/**
	 * 주어진 문자열이 공백이거나 null인지를 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 공백,null이 아닐 경우 true 리턴
	 */
	public boolean isValue(String str) {
		boolean result = false;
		if (str != null) {
			result = !str.trim().equals("");
		}
		return result;
	}

	/**
	 * 숫자 모양에 대한 형식 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[0-9]*$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 숫자 형식이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 숫자 형식이 아닙니다. ", str));
		}

		return result;
	}

	/**
	 * 영문으로만 구성되었는지에 대한 형식 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isEng(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[a-zA-Z]*$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 영어로만 구성되어 있습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 영어로만 구성되어 있지 않습니다.", str));
		}

		return result;
	}

	/**
	 * 한글로만 구성되었는지에 대한 형식 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isKor(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 한글로만 구성되어 있습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 한글로만 구성되어 있지 않습니다.", str));
		}

		return result;
	}

	/**
	 * 영문과 숫자로만 구성되었는지에 대한 형식 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isEngNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[a-zA-Z0-9]*$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 영어+숫자 조합이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 영어+숫자 조합이 아닙니다.", str));
		}

		return result;
	}

	/**
	 * 한글과 숫자로만 구성되었는지에 대한 형식 검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isKorNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[ㄱ-ㅎ가-힣0-9]*$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> '%s'(은)는 한글+숫자 조합이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> '%s'(은)는 한글+숫자 조합이 아닙니다.", str));
		}

		return result;
	}

	/**
	 * 이메일 형식인지에 대한 검사.
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isEmail(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 이메일 형식이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 이메일 형식이 아닙니다.", str));
		}

		return result;
	}

	/**
	 * "-"없이 핸드폰번호인지에 대한 형식검사.
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isCellPhone(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 핸드폰 번호 형식이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 핸드폰 번호 형식이 아닙니다.", str));
		}

		return result;
	}

	/**
	 * "-"없이 전화번호인지에 대한 형식검사.
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isTel(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 전화번호 형식이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 전화번호 형식이 아닙니다.", str));
		}

		return result;
	}

	/**
	 * "-"없이 주민번호에 대한 형식검사
	 * 
	 * @param str - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isJumin(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^\\d{6}[1-4]\\d{6}", str);
		}

		if (result) {
			log.debug(String.format("(regex) -> `%s`(은)는 주민번호 형식이 맞습니다.", str));
		} else {
			log.debug(String.format("(regex) -> `%s`(은)는 주민번호 형식이 아닙니다.", str));
		}

		return result;
	}
}
