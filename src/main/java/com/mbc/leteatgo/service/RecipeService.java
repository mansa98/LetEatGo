package com.mbc.leteatgo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeService {

	
	RecipeVO recipeVO = new RecipeVO();
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Transactional
	public List<RecipeVO> saveDatas(List<RecipeVO> foodDataList) {

		// return recipeRepository.saveAll(foodDataList);
		List<RecipeVO> foodList = new ArrayList<>();
		
		for (RecipeVO recipe : foodDataList) {
			
			recipeRepository.save(recipe);
			foodList.add(recipeRepository.save(recipe));
		}
		
		return foodList;

	}// saveDatas()
	
	@Transactional
	public RecipeVO saveData(RecipeVO foodData) {
		
		RecipeVO recipeVO = null;
		
		try {
			recipeVO = recipeRepository.save(foodData);
			
		} catch(Exception e) {
			
			log.error("saveDataError: " + e);
			e.printStackTrace();
		}
		
		return recipeVO;

	}// saveData()
	
	@Transactional(rollbackFor = Exception.class)
	public boolean increaseLike(int recipeNum) {
		
		// RecipeVO newRecipeVO = null;
		boolean result = false;
		
		try {
			recipeRepository.increaseRecipeLikeByRecipeNum(recipeNum);
			result = true;
			// newRecipeVO = recipeRepository.findById(recipeVO.getRecipeNum());
		} catch(Exception e) {
			
			log.error("increase like failed");
		}
		
		return result;
	}// increaseLike()
	
	@Transactional(rollbackFor = Exception.class)
	public boolean decreaseLike(int recipeNum) {
		
		// RecipeVO newRecipeVO = null;
		boolean result = false;
		
		try {
			recipeRepository.decreaseRecipeLikeByRecipeNum(recipeNum);
			result = true;
			// newRecipeVO = recipeRepository.findById(recipeVO.getRecipeNum());
		} catch(Exception e) {
			
			log.error("decrease like failed");
		}
		
		return result;
	}// decreaseLike()
	
	@Transactional(rollbackFor = Exception.class)
	public boolean updateCount(RecipeVO recipeVO) {
		
		// RecipeVO newRecipeVO = null;
		boolean result = false;
		
		log.info("updateCount의 getRecipeNum: {}", recipeVO.getRecipeNum());
		
		try {
			recipeRepository.updateRecipeCountByRecipeNum(recipeVO.getRecipeNum());
			result = true;
			// newRecipeVO = recipeRepository.findById(recipeVO.getRecipeNum());
		} catch(Exception e) {
			
			log.error("update count failed");
		}
		
		return result;
	}// updateCount()
	
	public long selectRecipesCount() {
		
		return recipeRepository.count();
		
	}
	
////퍼가요///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Transactional(readOnly = true)
	public List<RecipeVO> selectRecipesByPaging(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "recipeNum"));
		return recipeRepository.findAll(pageable).getContent();
	} //
	
	
	@Transactional(readOnly = true)
	public List<RecipeVO> selectRecipesByPagingOrderByRecipeLikeDesc(int currPage, int limit) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "recipeLike"));
		return recipeRepository.findAll(pageable).getContent();
	}
	
	

	@Transactional(readOnly = true)
	public RecipeVO selectRecipe(int recipeNum) {
		
		return recipeRepository.findById(recipeNum);
	}

	@Transactional(readOnly = true)
	public int selectRecipesCountBySearching(String searchKey, String searchWord) {

		// return searchKey.equals("recipe_subject") ? recipeRepository.countByRecipeSubjectLike("%"+searchWord+"%") : 
		return searchKey.equals("recipe_og_writer") ? recipeRepository.countByRecipeOgWriterContaining(searchWord) :
			   searchKey.equals("recipe_title") ? recipeRepository.countByRecipeTitleContaining(searchWord) : 
			   recipeRepository.countByRecipeIngrCombinedContaining(searchWord);	
		
	}

	@Transactional(readOnly = true)
	public List<RecipeVO> selectRecipesBySearching(int currPage, int limit, String searchKey, String searchWord) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "recipeNum"));
		
		// return searchKey.equals("recipe_subject") ? recipeRepository.findByRecipeSubjectLike("%"+searchWord+"%", pageable).getContent() : 
		return searchKey.equals("recipe_og_writer") ? recipeRepository.findByRecipeOgWriterContaining(searchWord, pageable).getContent() :
			   searchKey.equals("recipe_title") ? recipeRepository.findByRecipeTitleContaining(searchWord, pageable).getContent() : 
			   recipeRepository.findByRecipeIngrCombinedContaining(searchWord, pageable).getContent();
	}

////퍼가요///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public List<RecipeVO> getDatasFromDB() {
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		foodList = (List<RecipeVO>)recipeRepository.findAll();
		
		return foodList;
	}// getDatasFromDB()
								// 		페이지수	  재료이름
	public List<RecipeVO> getDatas(int pageNum, String ingr) throws IOException {
		
		List<RecipeVO> dataList = new ArrayList<>();
		Map<String, String> linksMap =  new HashMap<>();
		
		/** 재료별 크롤링 링크 */
		linksMap.put("소고기", "https://www.10000recipe.com/recipe/list.html?q=%EC%86%8C%EA%B3%A0%EA%B8%B0&order=date&page=");
		linksMap.put("돼지고기", "https://www.10000recipe.com/recipe/list.html?q=%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0&order=date&page=");
		linksMap.put("오리고기", "https://www.10000recipe.com/recipe/list.html?q=%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0&order=date&page=");
		linksMap.put("닭고기", "https://www.10000recipe.com/recipe/list.html?q=%EB%8B%AD%EA%B3%A0%EA%B8%B0&order=date&page=");
		linksMap.put("고등어", "https://www.10000recipe.com/recipe/list.html?q=%EA%B3%A0%EB%93%B1%EC%96%B4&order=date&page=");
		linksMap.put("오징어", "https://www.10000recipe.com/recipe/list.html?q=%EC%98%A4%EC%A7%95%EC%96%B4&order=date&page=");
		linksMap.put("미역", "https://www.10000recipe.com/recipe/list.html?q=%EB%AF%B8%EC%97%AD&order=date&page=");
		linksMap.put("갈치", "https://www.10000recipe.com/recipe/list.html?q=%EA%B0%88%EC%B9%98&order=date&page=");
		linksMap.put("쭈꾸미", "https://www.10000recipe.com/recipe/list.html?q=%EC%AD%88%EA%BE%B8%EB%AF%B8&order=date&page=");
		linksMap.put("두부", "https://www.10000recipe.com/recipe/list.html?q=%EB%91%90%EB%B6%80&order=date&page=");
		linksMap.put("콩나물", "https://www.10000recipe.com/recipe/list.html?q=%EC%BD%A9%EB%82%98%EB%AC%BC&order=date&page=");
		linksMap.put("낙지", "https://www.10000recipe.com/recipe/list.html?q=%EB%82%99%EC%A7%80&order=date&page=");
		linksMap.put("홍합", "https://www.10000recipe.com/recipe/list.html?q=%ED%99%8D%ED%95%A9&order=date&page=");
		linksMap.put("연어", "https://www.10000recipe.com/recipe/list.html?q=%EC%97%B0%EC%96%B4&order=date&page=");
		
		// Set linksMapKeys = linksMap.keySet();
		
		
		/** 검색창에 "소고기"를 검색했을때 나오는 갤러리 */
		// String beef_url = "https://www.10000recipe.com/recipe/list.html?q=%EC%86%8C%EA%B3%A0%EA%B8%B0&order=reco&page=";
		
		//String beef_url = "https://www.10000recipe.com/recipe/list.html?q=%EC%86%8C%EA%B3%A0%EA%B8%B0&order=date&page=";
		//String pork_url = "https://www.10000recipe.com/recipe/list.html?q=%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0&order=date&page=";
		// https://www.10000recipe.com/recipe/list.html?q=%EC%86%8C%EA%B3%A0%EA%B8%B0&order=date&page=1
		
		/** 갤러리내 레시피들의 ID(페이지당 40개)를 담을 리스트 */
		List<String> idList = new ArrayList<>();
		
		
		
		/** 1~n페이지 분량의 레시피 ID 획득 이후 idList에 삽입 */
		for (int page=1; page <= pageNum; page++) {
		//for (int page=3; page <= pageNum+2; page++) { //치킨(3~6)
		//for (int page=2; page <= pageNum+1; page++) { //오징어(2)
			
			Document doc = Jsoup.connect(linksMap.get(ingr) + page).get();
			
			Elements id_elems = doc.select("div[class=\"common_sp_thumb\"] a");
			
			for (Element elem: id_elems) {
				
				String id = elem.attr("href").replace("/recipe/", "");
				idList.add(id);
			}
			
		}// for
		
		log.info("총 몇개의 food id를 획득하였지? {} 개", idList.size());
		
		// System.out.println(idList.toString());
		
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
		
		/**
		 * 레시피 상세 페이지 URL
		 * https://www.10000recipe.com/recipe/
		 * 마지막 / 뒤에 위에서 추출한 레시피의 ID가 들어가게 된다.
		 */
		String detail_url = "https://www.10000recipe.com/recipe/";
		
		// int count = 1;
		
		/** idList에 담긴 ID의 갯수 만큼 for문 실행 */
		for (int foodnum = 0; foodnum < idList.size(); foodnum++) {
			
			Document doc = Jsoup.connect(detail_url + idList.get(foodnum)).get();
			// 전체적인 틀(Elements) 확보 //
			Elements recipe_elems = doc.select("#contents_area_full");
			
			/** 레시피 제목 */
			String title = "";
			/** 레시피 원작자 */
			String recipe_ogWriter = "";
			/** 레시피 이미지 */
			String recipe_image = "";
			
			for (Element elem : recipe_elems) {
					
				title = elem.select("div[class=\"view2_summary st3\"] h3").text();
				
				recipe_ogWriter = elem.select("div[class=\"user_info2\"] span").text();
			
				recipe_image = elem.select("div[class=\"centeredcrop\"] img").attr("src");
			
			}// for
			
			
			/** 레시피 재료 추출(메인 + 양념) 정제된 Version: 데이터 분석 용 */
			List<Element> ingr_elems = doc.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"] ul li a");
			List<String> ingrList = new LinkedList<>();
			
			for (Element elem : ingr_elems) {
				
				String ingrStr = elem.toString();
				ingrList.add(ingrStr);
			}// for
			
			for (int n = 0; n < ingrList.size(); n++) {
				
				if (ingrList.get(n).contains("<span>")) {
					
					ingrList.set(n, ingrList.get(n).split("<span>")[0].split("\">")[1].trim());
					
				} else {
					
					ingrList.set(n, ingrList.get(n).split("</a>")[0].split("\">")[1].trim());
					
				}
				
			}// for
			
			   /////////////////////////////////////
			// List의 요소 삭제 한계로 인해 Iterator 투입!!!//
			   /////////////////////////////////////
			// System.out.println("\"구매\" 빠지기 전 ingrList 크기: " + ingrList.size());
			
			// 요소 순회를 위해 리스트를 이터레이터로 변환 //
			Iterator<String> testIt = ingrList.iterator();
			
			while (testIt.hasNext()) {
				// String 내에 "구매"가 포함되어있으면 해당 요소 삭제 //
				if(testIt.next().contains("구매")) {
					
					testIt.remove();
					
				}
				
			}// while
			
			// 삭제하고 남은 요소를 가지고 리스트로 재 변환! //
			testIt.forEachRemaining(ingrList :: add);
			
			
			// 데이터베이스에 대응하는 VO가 List를 멤버필드 타입으로 가질 수 없다는걸 깨달았기에, 다시 String으로 회귀... //
			String finalIngredientString = "";
			
			for (int i = 0; i < ingrList.size(); i++) {
				
				if(i == ingrList.size()-1) {
					
					finalIngredientString += ingrList.get(i);
					
				} else {
					
					finalIngredientString += ingrList.get(i) + ", ";
					
				}
				
			}
				
				
//////////////String recipeIngrCombined■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■				
			
			
//			
//			for (int i = 0; i < ingrList.size(); i++) {
//				
//				if (ingrList.get(i).contains("구매")) {
//					
//					ingrList.remove(i);
//					// ingrList.set(i, null);
//					
//				}
//				
//			}// for
			
			/** 메모: 재료칸에 div[class="best_tit"]가 2개면(조리도구 포함) div[class="best_tit"]가 1개면(조리도구 미포함) */
			List<Element> ingrOrTools = recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"]");
			
			// log.info("＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠ ingrOrTools: 이 놈은 {}개!!", ingrOrTools.size());
			
			/** 레시피 재료(메인재료): view 페이지내 보여주기 용 */
			String recipe_ingrMain = "";
			
			// 상세페이지내 재료가 1)메인재료/양념재료로 나누어져 있을경우 //
			if (recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"]").size() >= 2) {
			
				recipe_ingrMain = recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"]")
											 .get(0)
											 .select("ul")
											 .get(0)
											 .select("li")
											 .text()
											 .replace("구매", "");
			// 상세페이지내 재료가 2)메인재료만 있을경우 //
			} else {
				
				recipe_ingrMain = recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"]")
											 .select("ul li")
											 .text()
											 .replace("구매", "");
				
			}
			
//////////////String recipeIngrMain■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			
			
			/** 레시피 재료(양념재료): view 페이지내 보여주기 용 */
			String recipe_ingrSeasoning = "";
			
			// 상세페이지내 재료가 1)메인재료/양념재료로 나누어져 있을경우 //
			if (recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"] ul").size() >= 2) {
			
				recipe_ingrSeasoning = recipe_elems.select("div[class=\"cont_ingre2\"] div[class=\"ready_ingre3\"] ul")
												 .get(1)
												 .select("li")
												 .text()
												 .replace("구매", "");
			// 상세페이지내 재료가 2)메인재료만 있을경우(양념재료가 없음) //
			} else {
				
				recipe_ingrSeasoning = "";

				
			}
			
			/** 망할놈의 조리도구들을 걸러줄 빛같은 로직 들어갑니다 */
			/** 조리도구 데스노트 생성 */
			List<String> recipeTools = new ArrayList<>();
			
			/** 데스노트에 들어갈 이름들 기재 */
			String[] recipeToolsArray = {"볼", "냄비", "도마", "조리용나이프", "채반", "요리스푼", "접시", "국자", "대접", "믹싱볼", "채반", "프라이팬", "조리용스푼", "완성그릇", "주방가위", "키친타올", "요리집게", "위생장갑", "볶음팬", "면기"};
			
			/** 데스노트에 추가!! */
			recipeTools.addAll(Arrays.asList(recipeToolsArray));
			
			/** 양념재료 칸에 조리도구가 갑툭튀하면 데스노트 발동!! */
			for (int i = 0; i < recipeTools.size(); i++) {
				
				if (recipe_ingrSeasoning.contains(recipeTools.get(i))) {
					recipe_ingrSeasoning = "";
				}
				
			}// for
			
//////////////String recipeIngrSeasoning■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			
			/** 레시피 조리법 */
			List<Element> cookery_elems = doc.select("div[class=\"view_step\"] div[class=\"media-body\"]");
			// Element -> String 변환한 요소들을 담을 리스트 //
			List<String> cookeryList = new ArrayList<>();
			
			for (int i = 0; i < cookery_elems.size(); i++) {
				// toString()을 이용하면 Element -> String 으로 변환이 가능하다!
				cookeryList.add(cookery_elems.get(i).toString());
			}
			
			// System.out.println("다듬기 이전 String 리스트의 크기는?: " + cookeryList.size());
			
			// cookeryList.forEach(x -> System.out.println(x + "\n"));
			
			// System.out.println("===========================================================================================\n===============================================================================================");
			
			// 난잡한 html 코드가 섞인 String을 깔끔하게 정제한 최종 조리법 리스트 //
			List<String> cookeryFinal = new ArrayList<>();
			
			for (int i = 0; i < cookeryList.size(); i++) {
				
				// 갑툭튀 "<p>" 태그가 포함되어 있을 경우에 //
				if (cookeryList.get(i).contains("<p")) {
																											 // 갑툭튀 하는 <br> 태그를 제거한다.
					cookeryFinal.add("§" + (i+1) + "단계: " + cookeryList.get(i).split("<p")[0].split("\">")[1].replace("<br>", ""));
				
				// "<p>" 태그가 포함되어 있지 않을때 // 	
				} else {
					 																						 // 갑툭튀 하는 <br> 태그를 제거한다.					
					cookeryFinal.add("§" + (i+1) + "단계: " + cookeryList.get(i).split("\">")[1].replace("</div>", "").replace("<br>", ""));
				}
				
			}// for
			
			
			// 데이터베이스에 대응하는 VO가 List를 멤버필드 타입으로 가질 수 없다는걸 깨달았기에, 다시 String으로 회귀... //
			String cookeryFinalString = "";
			
//			for (String str : cookeryFinal) {
//				
//				cookeryFinalString += str;
//			}
			
			for (int i = 0; i < cookeryFinal.size(); i++) {
				
				cookeryFinalString += "<div class=\"recipe_cookery_step\">" + cookeryFinal.get(i) + "<br></div>";
			}
			
//////////////String recipeCookery■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			
			/** */
			List<Element> cookery_images_elems = doc.select("div[class=\"view_step\"] div[id^=\"stepimg\"]");
			
			// cookery_elems.forEach(System.out::println);
			
			// log.info("%%% {}개", cookery_images_elems.size());
			
			List<String> cookeryImageList = new ArrayList<>();
			
			for (int i = 0; i < cookery_images_elems.size(); i++) {
				
				cookeryImageList.add(cookery_images_elems.get(i).html());
				
			}
			
			// cookeryImageList.forEach(System.out::println);
			
			String cookeryImageString = "";
			
			for (int i = 0; i < cookeryImageList.size(); i++) {
				
				cookeryImageString += "<div class=\"recipe_cookery_image\">" + cookeryImageList.get(i) + "</div>";
			}

			
//////////////String recipeCookeryImages■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			
			
			
			
			
			
			
//			System.out.println(count + "." + title + "\nby" + recipe_ogWriter + "\n" + recipe_image 
//									 + "\n" + ingrList.size() + finalIngredientString + "\n" + recipe_ingrMain
//									 + "\n" + recipe_ingrSeasoning + "\n" + cookeryFinalString);
//			// cookeryFinal.forEach(System.out::println);
//			
//			count++;
			
			// 강철새잎 10새기 거르기
			if (recipe_ogWriter.equals("강철새잎")) {
				recipe_ogWriter = "";
			}
			
			
			recipeVO = RecipeVO.builder()
							   .recipeTitle(title)
							   .recipeOgWriter(recipe_ogWriter)
							   .recipeImage(recipe_image)
							   .recipeIngrCombined(finalIngredientString)
							   .recipeIngrMain(recipe_ingrMain)
							   .recipeIngrSeasoning(recipe_ingrSeasoning)
							   .recipeCookery(cookeryFinalString)
							   .recipeCookeryImages(cookeryImageString)
							   .build();
			
			/** recipeIngrSeasoning와 recipeCookeryImages 제외, 모든 멤버필드가 빈칸(null)이 아닐때만 dataList에 추가! */
			if (recipeVO.getRecipeTitle() != "" && recipeVO.getRecipeOgWriter() != "" && recipeVO.getRecipeImage() != "" 
				&& recipeVO.getRecipeIngrCombined() != "" && recipeVO.getRecipeIngrMain() != "" && recipeVO.getRecipeCookery() != "") {
				
				
				dataList.add(recipeVO);
				
			}
			
			
			
			
			
			
		}// for
		
		log.info("■■■■■■■■■■ null이 걸러진 최종형태인 dataList의 크기는?: " + dataList.size() + "■■■■■■■■■■\n");
		
		// dataList.forEach(System.out::println);
		
		
		
		return dataList;
	}

}
