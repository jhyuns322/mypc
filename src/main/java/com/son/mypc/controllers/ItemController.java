package com.son.mypc.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.DownloadHelper;
import com.son.mypc.helper.PageData;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Item;
import com.son.mypc.model.UploadItem;
import com.son.mypc.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	WebHelper webHelper;
	@Autowired
	DownloadHelper downloadHelper;
	
	@Autowired
	ItemService itemService;

	/** 목록 페이지 */
	@RequestMapping(value = "/itemListGet", method = RequestMethod.GET)
	public Map<String, Object> itemListGet(HttpServletRequest request,
			@RequestParam(value = "checkArray[]", required = false) List<String> checkedList) {
		// @RequestParam(value="checkArray[]", required=false) required=false 속성을 부여하여
		// checkedList의 NULL 에러를 방지

		/** 1) 필요한 변수값 생성 */
		String keyword = request.getParameter("keyword"); // 카테고리
		String search = request.getParameter("search"); // 검색어
		String itemAlignment = request.getParameter("itemAlignment"); // 상품 정렬 옵션
		String minimumPrice = request.getParameter("minPri");
		String maximumPrice = request.getParameter("maxPri");
		int nowPage = Integer.parseInt(webHelper.getString("page", "1")); // 페이지 번호 (기본값 1)
		int totalCount = 0; // 전체 게시글 수
		int listCount = 10; // 한 페이지당 표시할 목록 수
		int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수
		/** 2) 데이터 조회하기 */
		// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
		Item input = new Item();
		if (search == "") { // 검색어가 없는 경우
			input.setCategory(keyword); // 카테고리에 keyword 적용
			input.setItem_chked(checkedList);
		} else { // 검색어가 존재하는 경우
			input.setCategory(search); // 카테고리에 search 적용
			input.setItem_name(search);
			input.setSpec(search);
		}
		if (minimumPrice != "" && maximumPrice != "" && minimumPrice != null && maximumPrice != null) { // 상품 가격 검색 옵션
																										// 값이 존재하는 경우
			input.setMinimumPrice(Integer.parseInt(minimumPrice));
			input.setMaximumPrice(Integer.parseInt(maximumPrice));
		}
		input.setItem_alignment(itemAlignment); // 상품 정렬 옵션 (기본값:0)

		List<Item> output = null; // 조회결과가 저장될 객체
		PageData pageData = null; // 페이지 번호를 계산한 결과가 저장될 객체

		try {
			// 전체 게시글 수 조회
			totalCount = itemService.getItemCount(input);
			// 페이지 번호 계산
			pageData = new PageData(nowPage, totalCount, listCount, pageCount);

			// SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
			Item.setOffset(pageData.getOffset());
			Item.setListCount(pageData.getListCount());

			// 데이터 조회하기
			output = itemService.getItemList(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 3) JSON 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("keyword", keyword);
		data.put("item", output);
		data.put("meta", pageData);
		return webHelper.getJsonData(data);
	}

	/** 상세 페이지 */
	@RequestMapping(value = "/itemListGet/{itemno}")
	public Map<String, Object> itemListGet(@PathVariable("itemno") int itemno) {

		/** 1) 필요한 변수값을 받아옴 -> "itemno" */

		// 이 값이 존재하지 않는다면 데이터 조회가 불가능하므로 반드시 필수값으로 처리해야 한다.
		if (itemno == 0) {
			return webHelper.getJsonWarning("상품번호가 없습니다.");
		}

		/** 2) 데이터 조회하기 */
		// 데이터 조회에 필요한 조건값을 Beans에 저장하기
		Item input = new Item();
		input.setItemno(itemno);

		// 조회결과를 저장할 객체 선언
		Item output = null;
		try {
			// 데이터 조회
			output = itemService.getItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 3) JSON 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("item", output);

		return webHelper.getJsonData(data);
	}

	/** 상품 등록 */
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public Map<String, Object> addItem() {

		/** 1) 상품 이미지 업로드를 수행 */
		try {
			webHelper.upload();
		} catch (Exception e) {
			e.printStackTrace();
			return webHelper.getJsonWarning("업로드에 실패했습니다.");
		}

		/** 2) 업로드 된 정보 추출 및 유효성 검사 */
		// 파일 정보 추출
		List<UploadItem> fileList = webHelper.getFileList();
		// 그 밖의 일반 데이터를 저장하기 위한 컬렉션
		Map<String, String> paramMap = webHelper.getParamMap();

		if (paramMap.get("category").length() < 1 || paramMap.get("category") == null
				|| paramMap.get("category").isEmpty()) {
			return webHelper.getJsonWarning("카테고리를 선택해주세요.");
		}
		if (paramMap.get("manufac").length() == 0) {
			return webHelper.getJsonWarning("제조사를 선택해주세요.");
		}
		if (paramMap.get("item_name").length() == 0) {
			return webHelper.getJsonWarning("상품이름을 선택해주세요.");
		}
		if (paramMap.get("spec").length() == 0) {
			return webHelper.getJsonWarning("스펙을 입력해주세요.");
		}
		if (paramMap.get("price").length() == 0) {
			return webHelper.getJsonWarning("가격을 입력해주세요.");
		}
		if (paramMap.get("stock").length() == 0) {
			return webHelper.getJsonWarning("재고 수량을 입력해주세요.");
		}
		if (paramMap.get("rel_date").length() == 0) {
			return webHelper.getJsonWarning("출시일을 입력해주세요.");
		}
		if (fileList.isEmpty()) {
			return webHelper.getJsonWarning("상품 이미지를 등록해주세요.");
		}
		if (fileList.size() == 1) {
			return webHelper.getJsonWarning("상품 이미지를 모두 등록해주세요.");
		}

		/** 3) 업로드 된 이미지주소1의 썸네일 이미지 생성 */
		// 썸네일을 추출하기 위한 변수 선언
		// 축소될 이미지 해상도 --> 480x320
		String size = "480x320";
		// 이미지 크롭 여부 --> 값이 없을 경우 기본값 false
		String crop = "false";
		// 저장되는 썸네일 이미지의 경로
		String thumbnailPath = null;

		if (fileList.get(0).getFilePath() != null) {
			try {
				// x를 기준으로 나눠서 숫자로 변환
				String[] temp = size.split("x");
				int width = Integer.parseInt(temp[0]);
				int height = Integer.parseInt(temp[1]);
				// 모든 파라미터는 문자열이므로 크롭 여부를 boolean으로 재설정
				boolean is_crop = false;
				if (crop.equals("true")) {
					is_crop = true;
				}
				// 썸네일 생성
				thumbnailPath = downloadHelper.createThumbnail(fileList.get(0).getFilePath(), width, height, is_crop);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		}

		/** 4) 사용자가 입력한 파라미터 수신 */
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String category = paramMap.get("category");
		String item_name = paramMap.get("item_name");
		String manufac = paramMap.get("manufac");
		String spec = paramMap.get("spec");
		int price = Integer.parseInt(paramMap.get("price").replaceAll(",", ""));
		int stock = Integer.parseInt(paramMap.get("stock"));
		String reg_date = format1.format(time); // 현재 날짜 저장
		String rel_date = paramMap.get("rel_date");
		String item_img1 = fileList.get(0).getFilePath();
		String item_img2 = fileList.get(1).getFilePath();

		/** 5) 데이터 저장하기 */
		// 저장할 값들을 Beans에 담는다.
		Item input = new Item();
		input.setCategory(category);
		input.setItem_name(item_name);
		input.setManufac(manufac);
		input.setSpec(spec);
		input.setPrice(price);
		input.setStock(stock);
		input.setReg_date(reg_date);
		input.setRel_date(rel_date);
		input.setItem_img1(item_img1);
		input.setItem_img2(item_img2);
		input.setItem_imgthumb(thumbnailPath);
		// 저장된 결과를 조회하기 위한 객체
		Item output = null;
		try {
			// 데이터 저장
			// --> 데이터 저장에 성공하면 파라미터로 전달하는 input 객체에 PK값이 저장된다.
			itemService.addItem(input);
			// 데이터 조회
			output = itemService.getItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 6) 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 상품 삭제 */
	@RequestMapping(value = "/removeItem/{itemno}", method = RequestMethod.DELETE)
	public Map<String, Object> removeItem(@PathVariable String[] itemno) {

		// 저장된 결과를 조회하기 위한 객체
		List<Item> output = new ArrayList<Item>();
		try {
			for (int i = 0; i < itemno.length; i++) {
				// 데이터 저장
				Item inputItem = new Item();
				inputItem.setItemno(Integer.parseInt(itemno[i]));
				itemService.deleteItem(inputItem);

				// 삭제 데이터를 리스트에 저장
				output.add(inputItem);
			}
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 상품 수정 */
	@RequestMapping(value = "/editItem/{itemno}/{editImg}", method = RequestMethod.PUT)
	public Map<String, Object> put(@PathVariable int itemno, @PathVariable String editImg) {
		// 데이터 조에 필요한 조건값을 Beans에 저장하기
		Item input = new Item();
		Item originInput = new Item();
		// 저장된 결과를 조회하기 위한 객체
		Item originOutput = new Item();

		/** 1) 상품 이미지 업로드를 수행 */
		try {
			webHelper.upload();
		} catch (Exception e) {
			e.printStackTrace();
			return webHelper.getJsonWarning("업로드에 실패했습니다.");
		}

		/** 2) 업로드 된 정보 추출 및 유효성 검사 */
		// 파일 정보 추출
		List<UploadItem> fileList = webHelper.getFileList();
		// 그 밖의 일반 데이터를 저장하기 위한 컬렉션
		Map<String, String> paramMap = webHelper.getParamMap();

		if (paramMap.get("category").length() < 1 || paramMap.get("category") == null
				|| paramMap.get("category").isEmpty()) {
			return webHelper.getJsonWarning("카테고리를 선택해주세요.");
		}
		if (paramMap.get("manufac").length() == 0) {
			return webHelper.getJsonWarning("제조사를 선택해주세요.");
		}
		if (paramMap.get("item_name").length() == 0) {
			return webHelper.getJsonWarning("상품이름을 선택해주세요.");
		}
		if (paramMap.get("spec").length() == 0) {
			return webHelper.getJsonWarning("스펙을 입력해주세요.");
		}
		if (paramMap.get("price").length() == 0) {
			return webHelper.getJsonWarning("가격을 입력해주세요.");
		}
		if (paramMap.get("stock").length() == 0) {
			return webHelper.getJsonWarning("재고 수량을 입력해주세요.");
		}
		if (paramMap.get("rel_date").length() == 0) {
			return webHelper.getJsonWarning("출시일을 입력해주세요.");
		}

		/** 3) 업로드 된 이미지주소1의 썸네일 이미지 생성 */
		// 썸네일을 추출하기 위한 변수 선언
		// 축소될 이미지 해상도 --> 480x320
		String size = "480x320";
		// 이미지 크롭 여부 --> 값이 없을 경우 기본값 false
		String crop = "false";
		// 저장되는 썸네일 이미지의 경로
		String thumbnailPath = null;

		// 이미지 수정사항이 없는 경우 기존의 이미지 경로를 가져온다.
		if (fileList.isEmpty() == true) {
			// 상품 수정을 요청한 itemno를 활용
			originInput.setItemno(itemno);

			try {
				originOutput = itemService.getItem(originInput);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			input.setItem_img1(originOutput.getItem_img1());
			input.setItem_img2(originOutput.getItem_img2());
			input.setItem_imgthumb(originOutput.getItem_imgthumb());
		}
		// 이미지 수정사항이 있는 경우에만 수행된다.
		else {
			if (fileList.get(0).getFilePath() != null) {
				try {
					// x를 기준으로 나눠서 숫자로 변환
					String[] temp = size.split("x");
					int width = Integer.parseInt(temp[0]);
					int height = Integer.parseInt(temp[1]);
					// 모든 파라미터는 문자열이므로 크롭 여부를 boolean으로 재설정
					boolean is_crop = false;
					if (crop.equals("true")) {
						is_crop = true;
					}
					// 썸네일 생성
					thumbnailPath = downloadHelper.createThumbnail(fileList.get(0).getFilePath(), width, height,
							is_crop);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
			}

			// 상품 수정을 요청한 itemno를 활용
			originInput.setItemno(itemno);
			try {
				// 수정 상품의 기존 데이터를 저장
				originOutput = itemService.getItem(originInput);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			String item_img1 = null;
			String item_img2 = null;
			String item_imgthumb = null;

			/** 4) 저장이 된 이미지의 경로를 브라우저에 출력하기 위해 파싱 처리 */
			switch (editImg) {
			// 첫번째 이미지만 변경한 경우
			case "imgFirst":
				item_img1 = fileList.get(0).getFilePath();
				item_img1 = "/assets/upload/" + item_img1.substring(item_img1.replace("\\", "/").lastIndexOf("/") + 1);
				input.setItem_img1(item_img1);
				input.setItem_img2(originOutput.getItem_img2());
				input.setItem_imgthumb(originOutput.getItem_imgthumb());
				break;

			// 두번째 이미지만 변경한 경우
			case "imgSecond":
				item_img2 = fileList.get(0).getFilePath();
				item_img2 = "/assets/upload/" + item_img2.substring(item_img2.replace("\\", "/").lastIndexOf("/") + 1);
				input.setItem_img1(originOutput.getItem_img1());
				input.setItem_img2(item_img2);
				input.setItem_imgthumb(originOutput.getItem_imgthumb());
				break;

			// 이미지를 모두 변경한 경우
			case "imgAll":
				item_img1 = fileList.get(0).getFilePath();
				item_img2 = fileList.get(1).getFilePath();
				item_imgthumb = thumbnailPath;

				item_img1 = "/assets/upload/" + item_img1.substring(item_img1.replace("\\", "/").lastIndexOf("/") + 1);
				item_img2 = "/assets/upload/" + item_img2.substring(item_img2.replace("\\", "/").lastIndexOf("/") + 1);
				item_imgthumb = "/assets/upload/"
						+ item_imgthumb.substring(item_imgthumb.replace("\\", "/").lastIndexOf("/") + 1);

				/* 데이터 저장하기 */
				// 저장할 값들을 Beans에 담는다.
				input.setItem_img1(item_img1);
				input.setItem_img2(item_img2);
				input.setItem_imgthumb(item_imgthumb);
				break;
			}
		}

		/** 5) 사용자가 입력한 파라미터 수신 */
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String category = paramMap.get("category");
		String item_name = paramMap.get("item_name");
		String manufac = paramMap.get("manufac");
		String spec = paramMap.get("spec");
		int price = Integer.parseInt(paramMap.get("price").replaceAll(",", ""));
		int stock = Integer.parseInt(paramMap.get("stock"));
		String reg_date = format1.format(time); // 현재 날짜 저장
		String rel_date = paramMap.get("rel_date");

		/** 6) 데이터 저장하기 */
		// 저장할 값들을 Beans에 담는다.
		input.setItemno(itemno);
		input.setCategory(category);
		input.setItem_name(item_name);
		input.setManufac(manufac);
		input.setSpec(spec);
		input.setPrice(price);
		input.setStock(stock);
		input.setReg_date(reg_date);
		input.setRel_date(rel_date);

		// 저장된 결과를 조회하기 위한 객체
		Item output = null;
		try {
			// 데이터 저장
			// --> 데이터 저장에 성공하면 파라미터로 전달하는 input 객체에 PK값이 저장된다.
			itemService.editItem(input);
			// 데이터 조회
			output = itemService.getItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 7) 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 최근 본 상품 저장 */
	@RequestMapping(value = "/cookieList", method = RequestMethod.POST)
	public Map<String, Object> cookieList(HttpServletRequest request,
			@RequestParam(value = "cookieValues[]", required = false) List<Integer> cookieValues) {
		Item input = new Item();
		List<Item> output = new ArrayList<Item>();

		// 상품 상세 페이지에서 본 상품이 존재할 경우 -> cookieItem번호 형식의 쿠키 값들이 존재
		if (cookieValues != null) {
			for (int i = 0; i < cookieValues.size(); i++) {
				input.setItemno(cookieValues.get(i));
				try {
					output.add(itemService.getItem(input));
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
				System.out.println(output.get(i).getItemno());
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cookie_item", output);
			return webHelper.getJsonData(data);

		} else {
			return webHelper.getJsonData();
		}
	}
	
	/** 최근 본 상품 삭제 */
	@RequestMapping(value = "/removeCookie/{key}", method = RequestMethod.DELETE)
	public Map<String, Object> removeCookie(@PathVariable String key) {
		webHelper.removeCookie(key);
		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", key+"제거");
		return webHelper.getJsonData(map);
	}
}
