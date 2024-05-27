package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbc.leteatgo.domain.BoardVO;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.service.BoardService;
import com.mbc.leteatgo.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
// 게시글 목록 
public class BoardListController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberServiceImpl memberServiceImpl;

	@GetMapping("list.do")
	public String Boardlist(@RequestParam(value = "currPage", defaultValue = "1") int currPage,
//				 			limit 페이지당 보여줄 게시글 수
							@RequestParam(value = "limit", defaultValue = "10") int limit, Model model,
							@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
							String searchKeyword) {

		Page<BoardVO> list = null;
		if (searchKeyword == null) {
			list = boardService.boardList(pageable);
		} else {
			list = boardService.boardSearchList(searchKeyword, pageable);
		}

		log.info("게시글 목록");
		List<BoardVO> boardList = new ArrayList<>();

//		// 총 게시글 수
//		int listCount = boardService.selectBoardsCount();

//		boardList = boardService.selectBoardsByPaging(currPage, limit);

//		// 총 게시글 수 (댓글들을 제외한)
		int listCount = boardService.selectBoardsCount();
//		// 댓글들 제외
//		boardList = boardService.selectBoardsByPagingWithoutReplies(currPage, limit);
//		boardList = boardService.selectBoard();
		boardList = boardService.selectBoardsByPaging(currPage, limit);

		for (BoardVO testVO : boardList) {
			log.info("boardList : {}", boardList);
		}
//		if(searchKeyword == null) {
//			list = boardService.boardList(pageable);
//		} else {
//			list = boardService.boardSearchList(searchKeyword, pageable);
//		}

//		list = boardService.boardList(pageable);
//		log.info("게시글 개수 : " + list.getSize());
//		list.forEach(x ->{log.info("{}",x);});
//		log.info("리스트 개수 : " + listCount);

		// 총 페이지 수
		// int maxPage=(int)((double)listCount/limit+0.95); // 0.95를 더해서 올림 처리
//		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작페이지수(1, 11, 21, ...)
		// int startPage = (((int) ((double)currPage / 10 + 0.9)) -1) * 10 + 1;
//		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
//		int endPage = startPage + 10;

//		int maxPage = list.getPageable().getPageNumber();
//		int startPage = Math.max(maxPage - 4, 1);
//		int endPage = Math.min(startPage + 5, list.getTotalPages());

		// 총 페이지 수
//   	int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
//   	int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = PageVO.getEndPage(currPage, limit);

		if (endPage > maxPage)
			endPage = maxPage;

		if (currPage > maxPage) {
			currPage = maxPage;
		}

		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);

		pageVO.setPrePage(pageVO.getCurrPage() - 1 < 1 ? 1 : pageVO.getCurrPage() - 1);
		pageVO.setNextPage(currPage + 1);

//		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("boardList", boardList);
		return "/board/list";
	} //
	
}