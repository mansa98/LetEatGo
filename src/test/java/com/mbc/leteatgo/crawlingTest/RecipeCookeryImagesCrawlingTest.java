package com.mbc.leteatgo.crawlingTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RecipeCookeryImagesCrawlingTest {
	
	//@Test
	void simpleTest() throws IOException {
		
		String testUrl = "https://www.10000recipe.com/recipe/7024732";
		
		Document doc = Jsoup.connect(testUrl).get();
		
		//Elements elems = doc.select("#contents_area_full div[class=\"view_step\"] div[id^=\"stepimg\"]");
		
		List<Element> cookery_elems = doc.select("div[class=\"view_step\"] div[id^=\"stepimg\"]");
		
		// cookery_elems.forEach(System.out::println);
		
		log.info("%%% {}개", cookery_elems.size());
		
		List<String> cookeryImageList = new ArrayList<>();
		
		for (int i = 0; i < cookery_elems.size(); i++) {
			
			cookeryImageList.add(cookery_elems.get(i).html());
			
		}
		
		cookeryImageList.forEach(System.out::println);
		
		String cookeryImageString = "";
		
		for (int i = 0; i < cookeryImageList.size(); i++) {
			
			cookeryImageString += "<div class=\"recipe_cookery_image\">" + cookeryImageList.get(i) + "</div>";
		}
		
		log.info("◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎ 최종형태: {}", cookeryImageString);
		
//		String str = "";
//		
//		for (int i = 0; i < elems.size(); i++) {
//			
//			// str = elems.html().replace("id=stepimg" + i, "class=cookery_image");
//			str = "<div class=\"recipe_cookery_image\">" + elems.html() + "</div>";
//			
//			
//		}
		
		// String testList = elems.select("div[class=\"view_step\"] div[class=\"media-right\"] img").attr("src");
		
		//log.info("### str: {}", str);
	}

	@Test
	void test() throws IOException {

		String beef_url = "https://www.10000recipe.com/recipe/list.html?q=%EC%86%8C%EA%B3%A0%EA%B8%B0&order=date&page=";

		List<String> idList = new ArrayList<>();
		
		int pageNum = 1;

		/** 1~n페이지 분량의 레시피 ID 획득 이후 idList에 삽입 */
		for (int page = 1; page <= pageNum; page++) {

			Document doc = Jsoup.connect(beef_url + page).get();

			Elements id_elems = doc.select("div[class=\"common_sp_thumb\"] a");

			for (Element elem : id_elems) {

				String id = elem.attr("href").replace("/recipe/", "");
				idList.add(id);
			}

		} // for

		log.info("총 몇개의 food id를 획득하였지? {} 개", idList.size());

		// System.out.println(idList.toString());

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/**
		 * 레시피 상세 페이지 URL https://www.10000recipe.com/recipe/ 마지막 / 뒤에 위에서 추출한 레시피의 ID가
		 * 들어가게 된다.
		 */
		String detail_url = "https://www.10000recipe.com/recipe/";

		// int count = 1;

		/** idList에 담긴 ID의 갯수 만큼 for문 실행 */
		for (int foodnum = 0; foodnum < idList.size(); foodnum++) {

			Document doc = Jsoup.connect(detail_url + idList.get(foodnum)).get();
			// 전체적인 틀(Elements) 확보 //
			List<Element> cookery_elems = doc.select("div[class=\"view_step\"] div[id^=\"stepimg\"]");
			
			log.info("%%% {}개", cookery_elems.size());
			
			List<String> cookeryImageList = new ArrayList<>();
			
			for (int i = 0; i < cookery_elems.size(); i++) {
				
				cookeryImageList.add(cookery_elems.get(i).html());
				
			}
			
			log.info("■■■■ cookeryImageList의 크기: {}", cookeryImageList.size());
			// cookeryImageList.forEach(System.out::println);
			
			String cookeryImageString = "";
			
			for (int i = 0; i < cookeryImageList.size(); i++) {
				
				cookeryImageString += "<div class=\"recipe_cookery_image\">" + cookeryImageList.get(i) + "<br></div>";
			}
			
			log.info("◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎ 최종형태: {}", cookeryImageString);


		}// for
		
	}// test()

}
