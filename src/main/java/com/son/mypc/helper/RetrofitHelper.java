package com.son.mypc.helper;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
public class RetrofitHelper {
	/**
	 * Retrofit 객체를 생성하고 기본 설정들을 적용한 후 리턴한다.
	 *
	 * @param baseUrl - 접근하고자 하는 API의 기본 주소 (ex: http://도메인)
	 * @return Retrofit 객체
	 */
	// -> import retrofit2.Retrofit;
	public Retrofit getRetrofit(String baseUrl) {
		// 통신 객체를 생성하기 위한 Builder 객체 정의 -> import okhttp3.OkHttpClient;
		OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

		// 통신객체에 Log 기록 객체를 연결한다.
		// -> import okhttp3.logging.HttpLoggingInterceptor;
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLogger());
		// -> import okhttp3.logging.HttpLoggingInterceptor.Level;
		logging.level(Level.BODY);
		httpClientBuilder.addInterceptor(logging);

		// MyInterceptor 클래스의 객체를 통신객체에 연결한다.
		httpClientBuilder.addNetworkInterceptor(new MyInterceptor());

		// Builder를 통해 통신객체 생성 -> import okhttp3.OkHttpClient;
		OkHttpClient httpClient = httpClientBuilder.build();

		// Retrofit을 생성하는 기능을 수행하는 객체를 정의한다.
		Retrofit.Builder builder = new Retrofit.Builder();

		// 기본 주소를 지정한다.
		builder.baseUrl(baseUrl);

		// 통신 결과를 Gson 객체를 통해 처리하도록 Gson을 연결한다. --> JSON 파싱 자동화
		// -> import retrofit2.converter.gson.GsonConverterFactory;
		builder.addConverterFactory(GsonConverterFactory.create());

		// 통신객체를 연결한다.
		builder.client(httpClient);

		// 모든 설정이 반영된 Retrofit 객체 생성 -> import retrofit2.Retrofit;
		Retrofit retrofit = builder.build();

		// 결과 리턴
		return retrofit;
	}

	/**
	 * Log4j 객체를 통해 로그를 기록하도록 하는 메서드를 구현하는 클래스 정의. Retrofit이 이 클래스의 객체를 통해 통신 과정을
	 * 의미하는 문자열을 log() 메서드로 전달한다.
	 */
	// -> import okhttp3.logging.HttpLoggingInterceptor
	public class MyLogger implements HttpLoggingInterceptor.Logger {
		@Override
		public void log(String logMessage) {
			// 전달받은 문자열을 로그로 저장한다.
			// --> 통신 과정이 기록된다.
			log.debug(logMessage);
		}
	}

	/**
	 * 통신 절차를 가로채서 보조적인 처리를 수행하는 클래스 정의. 이 클래스가 통신 정보에 HTTP 헤더를 강제로 추가하는 기능을 수행한다. 이
	 * 클래스의 객체를 통신 객체에 연결해야 한다.
	 */
	// -> import okhttp3.Interceptor;
	public class MyInterceptor implements Interceptor {
		// -> import okhttp3.Response
		@Override
		public Response intercept(Chain chain) throws IOException {
			Request originalRequest = chain.request(); // 요청 정보 객체 추출 -> import okhttp3.Request
			Request.Builder builder = originalRequest.newBuilder(); // 요청 정보 객체를 복제해서 새로운 객체 생성
			builder.removeHeader("User-Agent"); // 기본으로 포함되어있는 브라우저 정보 삭제

			// 임의의 웹 브라우저 정보로 설정 (ex: 크롬)
			builder.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

			Request newRequest = builder.build(); // 구성된 정보로 요청정보 객체 새로 생성
			Headers headers = newRequest.headers(); // 설정된 헤더들을 추출 -> import okhttp3.Headers;

			for (int i = 0; i < headers.size(); i++) {
				String name = headers.name(i);
				String values = headers.get(name);
				log.debug("(H) -> " + name + ": " + values);
			}

			return chain.proceed(newRequest); // 요청 객체를 사용하여 응답객체 생성 후 리턴
		}
	}
}
