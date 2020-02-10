package com.son.mypc.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

@Slf4j
public class DownloadHelper {
	/** 업로드 된 결과물이 저장될 폴더 */
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	/**
	 * 지정된 경로의 파일을 읽어들인다. 그 내용을 응답객체(response)를 사용해서 출력한다.
	 *
	 * @param filePath   - 서버상의 파일 경로
	 * @param originName - 원본 파일 이름
	 * @throws Exception
	 */
    public void download(String filePath, String originName) throws Exception {

        ServletRequestAttributes requestAttr 
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        // JSP 내장객체 참조하기 --> getInstance()에 전달된 객체를 받는다.
        HttpServletResponse response = requestAttr.getResponse();
        
		/** 1) 다운로드 정보를 로그로 기록한다. */
		log.debug("----------------------------------------------------");
		log.debug(String.format("[Download] filePath: %s", filePath));
		log.debug(String.format("[Download] originName: %s", originName));
		log.debug("----------------------------------------------------");

		/** 2) 파일의 존재여부를 확인하고 파일의 정보 추출하기 */
		File f = new File(this.uploadDir, filePath);

		// 파일이 존재하지 않을 경우 에러 정보를 로그로 기록하고 예외를 발생시킨다.
		// -> 이 메서드를 호출한 JSP파일의 try~catch 블록 중에서 catch 블록으로 제어가 이동한다.
		if (!f.exists()) {
			log.error("[Download] FileNotFoundException");
			throw new FileNotFoundException(f.getAbsolutePath());
		}

		long size = f.length(); // 파일의 크기 추출하기
		String name = f.getName(); // 서버에 보관되어 있는 파일의 이름 추출하기

		// 원본 파일명이 전달되지 않은 경우 서버상의 파일이름으로 대체
		if (originName == null) {
			originName = name;
		}

		// 파일형식 얻기 (업로드 정보에서 추출했던 contentType과 같은 값)
		MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
		String fileType = typeMap.getContentType(f);

		/** 3) 브라우저에게 이 메서드를 호출하는 페이지의 형식을 일반 파일로 인식시키기 위한 처리 */
		// 다른 데이터와 섞이지 않도록 응답객체(response)를 리셋한다.
		response.reset();

		// 파일형식 정보 설정
		response.setHeader("Content-Type", fileType + "; charset=UTF-8");

		// 파일의 이름 설정 (인코딩 필요함)
		String encFileName = URLEncoder.encode(originName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + encFileName + ";");

		// 파일의 용량 설정
		response.setContentLength((int) size);

		/** 4) 스트림을 통한 파일의 바이너리를 읽어서 출력하기 */
		// 파일 읽기와 출력을 위한 스트림을 생성한다.
		InputStream is = new FileInputStream(f);

		// is는 한번에 내용을 읽어야 하지만 BufferedInputStream은 조금씩 나누어 읽어들일 수 있다.
		// 대용량 파일 처리를 위해서는 이 클래스를 통해 데이터를 조금씩 나누어 처리해야 한다.
		BufferedInputStream bis = new BufferedInputStream(is);

		// BufferedInputStream을 통해 읽어들인 데이터를 출력하기 위해 사용되는 클래스 
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[1024]; // 업로드 된 파일의 용량에 상관없이 1kbyte의 배열 생성한다.
		int length = 0; // 버퍼링되는 동안 읽어들인 데이터 크기를 체크하기 위한 변수

		// -> 버퍼링 처리
		while ((length = bis.read(buffer)) != -1) {
			// buffer의 내용을 0번째 위치부터 읽어들인 크기까지만 버퍼링용 스트림(bos)에 옮겨 담는다.
			bos.write(buffer, 0, length);
		}

		bos.flush(); // 옮겨담은 내용을 웹 브라우저에 전송한다.
		bos.close(); // 모든 스트림들을 닫는다.
		bis.close();
		is.close();
	}

	/**
	 * 리사이즈 된 썸네일 이미지를 생성하고 경로를 리턴한다.
	 *
	 * @param loadFile - 원본 파일의 경로
	 * @param width    - 최대 이미지 가로 크기
	 * @param height   - 최대 이미지 세로 크기
	 * @param crop     - 이미지 크롭 사용 여부
	 * @return 생성된 이미지의 절대 경로
	 * @throws Exception
	 */
	public String createThumbnail(String path, int width, int height, boolean crop) throws Exception {

		/** 1) 썸네일 생성 정보를 로그로 기록하기 */
		log.debug(String.format("[Thumbnail] path: %s, size: %dx%d, crop: %s", path, width, height,
				String.valueOf(crop)));

		/** 2) 저장될 썸네일 이미지의 경로 문자열 만들기 */
		path = path.substring(path.indexOf(":")+1, path.length());
		File loadFile = new File(this.uploadDir, path); // 원본파일의 전체경로 -> 업로드 폴더(상수값) + 파일명
		String dirPath = loadFile.getParent(); // 전체 경로에서 파일이 위치한 폴더 경로 분리
		String fileName = loadFile.getName(); // 전체 경로에서 파일 이름만 분리
		int p = fileName.lastIndexOf("."); // 파일이름에서 마지막 점(.)의 위치
		String name = fileName.substring(0, p); // 파일명 분리 -> 파일이름에서 마지막 점의 위치 전까지
		String ext = fileName.substring(p + 1); // 확장자 분리 -> 파일이름에서 마지막 점위 위치 다음부터 끝까지
		String prefix = crop ? "_crop_" : "_resize_"; // 크롭인지 리사이즈 인지에 대한 문자열

		// 최종 파일이름을 구성한다. -> 원본이름 + 크롭여부 + 요청된 사이즈
		// -> ex) myphoto.jpg -> myphoto_resize_320x240.jpg
		String thumbName = name + prefix + width + "x" + height + "." + ext;

		File f = new File(dirPath, thumbName); // 생성될 썸네일 파일 객체 --> 업로드폴더 + 썸네일이름
		String saveFile = f.getAbsolutePath(); // 생성될 썸네일 파일 객체로부터 절대경로 추출 (리턴할 값)

		// 생성될 썸네일 이미지의 경로를 로그로 기록
		log.debug(String.format("[Thumbnail] saveFile: %s", saveFile));

		/** 3) 썸네일 이미지 생성하고 최종 경로 리턴 */
		// 해당 경로에 이미지가 없는 경우만 수행
		if (!f.exists()) {
			// 원본 이미지 가져오기
			Builder<File> builder = Thumbnails.of(loadFile);
			// 이미지 크롭 여부 파라미터에 따라 크롭 옵션을 지정한다.
			if (crop == true) {
				builder.crop(Positions.CENTER);
			}

			builder.size(width, height); // 축소할 사이즈 지정
			builder.useExifOrientation(true); // 세로로 촬영된 사진을 회전시킴
			builder.outputFormat(ext); // 파일의 확장명 지정
			builder.toFile(saveFile); // 저장할 파일경로 지정
		}

		// 최종적으로 생성된 경로에서 업로드 폴더까지의 경로를 제거한다.
		saveFile = saveFile.replace("\\", "/").replace(this.uploadDir, "");
		return saveFile;
	}

	/**
	 * 원본파일의 경로와 함께 이미지의 가로,세로 크기가 전달될 경우 지정된 크기로 썸네일 이미지를 생성하고 생성된 썸네일을 출력시킨다.
	 *
	 * @param filePath - 원본 이미지 경로
	 * @param width    - 가로 크기
	 * @param height   - 세로 크기
	 * @throws IOException
	 */
	public void download(String filePath, int width, int height, boolean crop)
			throws Exception {

		// 썸네일을 생성하고 경로를 리턴받는다.
		String thumbPath = this.createThumbnail(filePath, width, height, crop);

		// 썸네일을 출력한다.
		// --> 이 메서드를 호출하기 위해서 try~catch가 요구되지만,
		// 현재 메서드 역시 throws를 명시했기 때문에
		// 예외처리가 현재 메서드를 호출하는 곳으로 이관된다.
		this.download(thumbPath, null);
	}
}
