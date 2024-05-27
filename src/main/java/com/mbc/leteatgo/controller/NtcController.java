package com.mbc.leteatgo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mbc.leteatgo.domain.NtcDTO;
import com.mbc.leteatgo.domain.NtcDTO;
import com.mbc.leteatgo.domain.NtcVO;
import com.mbc.leteatgo.domain.UploadFile;
import com.mbc.leteatgo.service.NtcService;
import com.mbc.leteatgo.service.FileUploadService;
import com.mbc.leteatgo.service.ImageService;
import com.mbc.leteatgo.util.FileUploadUtil;
import com.mbc.leteatgo.service.ImageStoreService;

import lombok.extern.slf4j.Slf4j;

@SessionAttributes("ntcUpdateDTO")
@Controller
@RequestMapping("ntc")
@Slf4j
public class NtcController { 
	
	private static final Object CustomUser = null;

	@Autowired
	NtcService ntcService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ImageStoreService imageStoreService;
	
	@Value("${spring.servlet.multipart.max-file-size}") 
	String uploadFileMaxSize;
		
	@GetMapping("/write.do")
	public String write(Model model) {
		
		model.addAttribute("ntcNtcDTO", new NtcDTO());
		return "/ntc/ntcWrite";
	} //
	
	@PostMapping("/writeProc.do")
	public String writeProc(@ModelAttribute("ntcDTO") NtcDTO ntcDTO, Model model) {
		
		log.info("NtcDTO : {}", ntcDTO);
		
		// VO 차원에서 암호화 파일 처리 
		// 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        // this.ntcFile = ntc.getNtcFile().getOriginalFilename().trim().equals("") ?
        //		"" : FileUploadUtil.encodeFilename(ntc.getNtcFile().getOriginalFilename());
		
		NtcVO ntcVO = new NtcVO(ntcDTO);
		
		// 첨부 파일이 없다면...
		if (ntcDTO.getNtcFile().getOriginalFilename().trim().equals("") == false) {
			
			log.info("게시글 작성 처리(첨부 파일) : {}", ntcDTO.getNtcFile().getOriginalFilename());
			
			String actualUploadFilename = FileUploadUtil.encodeFilename(ntcVO.getNtcFile());
			ntcVO.setNtcFile(actualUploadFilename);
		
		} 
		
		log.info("NtcVO : {}", ntcVO);
		
		log.info("----- 게시글 저장 NtcVO : {}", ntcVO);
		
		String msg = "";
		
		// 첨부 파일이 있을 때만 저장
		if (ntcDTO.getNtcFile().getOriginalFilename().trim().equals("") == false) {
		
			msg = fileUploadService.storeUploadFile(ntcVO.getNtcNum(), ntcDTO.getNtcFile(), ntcVO.getNtcFile());
			log.info("msg : {}", msg);
		}
		
		if (ntcVO != null && msg.equals("첨부 파일이 비어있거나 없습니다.") == false)  {
			ntcVO = ntcService.insertNtc(ntcVO);
			msg = "게시글 저장에 성공하였습니다.";
		}
			
		// TODO
		// /error/error
		// errMsg, movePage = /ntc/list.do"
		// 정상 : 파일이 업로드 되었습니다.
		
		model.addAttribute("errMsg", msg);
		// model.addAttribute("movePage", "/ntc/write.do");
		model.addAttribute("movePage", "/ntc/list.do"); 
		
		return "/error/error"; 
	} //
	
	@GetMapping("/view.do/{ntcNum}")
	public String view(@PathVariable("ntcNum") int ntcNum, Model model) {
		
		NtcVO ntcVO =ntcService.selectNtc(ntcNum);
		log.info("NtcVO : {}", ntcVO);
		
		model.addAttribute("ntc", ntcVO);
		
		// 조회할 때마다 조회수 갱신(+)
		ntcService.updateNtcCountByNtcNum(ntcNum);
		
		return "/ntc/ntcView";
	}
	
// 	UPDATE #############################################################
	@GetMapping("/update.do/{ntcNum}")
//	public String Update(@RequestParam("ntcNum") int ntcNum, Model model, HttpSession session) {
	public String Update(@PathVariable("ntcNum") int ntcNum, Model model, HttpSession session) {
		
		NtcVO ntcVO = ntcService.selectNtc(ntcNum);
		log.info("ntcVO(update) : {}", ntcVO);
		session.setAttribute("ntcUpdateSess", ntcVO);
		log.info("NtcVO : {}", ntcVO);
		model.addAttribute("ntc", ntcVO);
		
		return "/ntc/ntcUpdate";
	}
	
	@PostMapping("/updateProc.do")
	public String UpdateProc(@RequestParam Map<String, Object> map,
							@RequestParam(name = "ntcNum", required = true) int ntcNum,
							@RequestParam("ntcFile") MultipartFile ntcFile,
							Model model,
							HttpSession session) 
	{
		
		log.info("------ updateProc.do");
		
		log.info("인자 현황 :");
		
		map.entrySet().forEach(x ->{log.info("{}", x);});
		
		boolean defaultFileDeleteYN = map.get("defaultFileDeleteYN") == null ? false
									: Boolean.parseBoolean(map.get("defaultFileDeleteYN").toString());
		log.info("첨부 파일이 비어있는지 여부 : {}", ntcFile.isEmpty());
		log.info("첨부 파일명 : {}", ntcFile != null ? ntcFile.getOriginalFilename() : "");
		
		// ***
		String returnPath; // 글 수정 성공 실패 모두 /error/error 로 가도록 설정
		String movePage = "/ntc/update.do?ntcNum=" + map.get("ntcNum").toString();
		String msg = ""; // 메시지
		map.entrySet().forEach(x -> {log.info(x + "");});
		
		NtcVO defaultNtcVO = (NtcVO) session.getAttribute("ntcUpdateSess"); 
		NtcVO updateNtcVO = new NtcVO(map, ntcFile); // map, ntcFile 둘 다 생성해야함 ~ NtcVO(Map<String, Object> map, MultipartFile ntcFile)
		
		log.info("ntcUpdateSession(기존 정보) : {}", defaultNtcVO);
		log.info("수정 정보 : {}", updateNtcVO);
		
		if (ntcFile.isEmpty() == false) {
			String actualUploadFilename = FileUploadUtil.encodeFilename(ntcFile.getOriginalFilename());
			updateNtcVO.setNtcOriginalFile(ntcFile.getOriginalFilename());
			updateNtcVO.setNtcFile(actualUploadFilename);
			
			// 신규 업로드 파일 저장 (업로드)
			msg = fileUploadService.storeUploadFile(updateNtcVO.getNtcNum(), ntcFile,
					updateNtcVO.getNtcFile());
			log.info("msg : {}", msg);
			
			// 기존 첨부 파일 삭제
			msg += fileUploadService.deleteUploadFile(defaultNtcVO.getNtcFile());
			log.info("msg : {}", msg);
			
		} else { // 첨부 파일이 없다면...
			
			log.info("첨부파일이 없다");
			
			if (defaultFileDeleteYN == true) {
				
				msg += fileUploadService.deleteUploadFile(defaultNtcVO.getNtcFile());
				
			} else { 
				// 기존 파일을 입력
				String originalFilename = defaultNtcVO.getNtcOriginalFile() != null
						? defaultNtcVO.getNtcOriginalFile() // 값이 있으면 가져오고?
						: null; // 없으면 null?
				updateNtcVO.setNtcOriginalFile(originalFilename);
				
				String encNtcFilename = defaultNtcVO.getNtcFile() != null
						? defaultNtcVO.getNtcFile()
						: null;
				updateNtcVO.setNtcFile(encNtcFilename);
			}
		}
		
		// 글내용(ntcContent)비교 : 변경시에는 기존 삽입 이미지 삭제 등 처리
		log.info("기존 글내용 : {}", defaultNtcVO.getNtcContent());
		log.info("수정 글내용 : {}", updateNtcVO.getNtcContent());
		
		// 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)
//******이거 뭔 말임 *******************************************************************************************		
		if (defaultNtcVO.getNtcContent().trim().equals(updateNtcVO.getNtcContent().trim()) == false) {
			// 기존 데이터의 삽입 이미지 목록 (삽입 이미지 테이블(upload_file_tbl)의 PK(기본키)) 확보
			// ex) 글 내용중 이미지가 들어간 내용 ex) <img src="/ntc/image/18" ...
			
			List<Integer> defaultImgList = ntcService.getImageList(defaultNtcVO.getNtcContent().trim(),
					"/ntc/image/");
			List<Integer> updateImgList = ntcService.getImageList(updateNtcVO.getNtcContent().trim(),
					"/ntc/image/");
			
			log.info("-----------------------------");
			for (int s : defaultImgList) {
				log.info("기존 업로드 이미지 : " + s);
			}
			for (int s : updateImgList) {
				log.info("신규 업로드 이미지 : " + s);
			}
			log.info("-----------------------------");
			
//******이거 뭔 말임 *******************************************************************************************		
			// 삭제할 글 내용에 삽입 이미지 목록
			List<Integer> deleteExceptionImgList = new ArrayList<>();
			
			// 기존에 이미지가 있지만 신규에는 이미지가 없을때는 기존 이미지 모두 삭제
			if(updateImgList.size() == 0) {
				log.info("기존 글내용의 모든 이미지 삭제");
			} else {
				log.info("기존글의 이미지들의 선별적 삭제");
				if (defaultImgList.size() > 0) {
					for (int s : defaultImgList) { 
						if (updateImgList.contains(s) == false) {
							log.info("실제 삭제할 기존 이미지 기본키(PK) : " + s);
							deleteExceptionImgList.add(s);
						} // 
					} // for 
				} // if
				
				// 삭제할 이미지 있으면 삭제
				if (deleteExceptionImgList.size() > 0) {
					for (int imageId : deleteExceptionImgList) {
						UploadFile uploadFile = imageService.load(imageId);
						
						log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
						imageStoreService.deleteById(imageId);
						log.info("이미지를 삭제하였습니다.");
						
						
					}
				}
			}
		} 
		else { // 변경 내용이 없다면...
			msg = "게시글 수정(변경) 내용이 없습니다.";
		}
//		ntcTemp.setNtcTitle(ntcVO.getNtcTitle());
//		ntcTemp.setNtcContent(ntcVO.getNtcContent());
		
		// 작성일을 최근 수정일로 변경
		updateNtcVO.setNtcDate(new Date(System.currentTimeMillis()));
		
		log.info("최종 게시글 수정 내용 : {}", updateNtcVO);
		
		// 게시글 수정
		NtcVO resultVO = ntcService.updateNtc(updateNtcVO);
		if (resultVO == null) {
			msg = "게시글 수정에 실패했습니다.";
		} else {
			log.info("최종 저장 결과 : " + resultVO);
			msg = "게시글 수정에 성공했습니다.";
			
			movePage = "/ntc/view.do/" + map.get("ntcNum").toString();
		}
	
	model.addAttribute("errMsg", msg);
	
	model.addAttribute("movePage", movePage);
	returnPath = "error/error";
	
	return returnPath;
	}
	
// 	DELETE #############################################################
	@GetMapping("/deleteProc.do/{ntcNum}")
	public String updateProc(@PathVariable("ntcNum") int ntcNum,
							 Model model) {
		
		log.info("deleteProc.do : ntcNum : {}", ntcNum);
		// 개별 게시글 보기로 이동(movePage)
		// 게시글 삭제 성공시에는 게시글 목록으로 이동(이미 삭제되었으므로)
		// 게시글 삭제 실패시에는 게시글 보기로 이동
		String returnPath; // 글삭제 성공 실패 모두 /error/error로 가도록 재설정
		String movePage = "/ntc/view.do/" + ntcNum;
		String msg = ""; // 메시지
		NtcVO ntcVO = ntcService.selectNtc(ntcNum);
		
		log.info("기존 정보 : ntcVO : {}", ntcVO);
		
//		댓글이 있으면 삭제 불가 댓글 없을 경우 삭제...!!추가 해야될까??
//		####

			log.info("유저 점검 성공");
			
			// 삭제할 삽입 이미지 점검
			List<Integer> deleteImgList = ntcService.getImageList(ntcVO.getNtcContent().trim(),
					"/ntc/image/");
			for (int s : deleteImgList) { 
				log.info("삭제할 업로드 이미지 : " + s);
			}
			if (deleteImgList.size() > 0) {
				for (int imageId : deleteImgList) {
					// 삭제할 이미지 파일 경로 확보
					UploadFile uploadFile = imageService.load(imageId);
					// 삽입 이미지 삭제
					log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
					// 삽입 이미지 테이블(upload_file_tbl)에서도 해당 이미지 수록 내용 삭제
					imageStoreService.deleteById(imageId);
				}
			}
			if (ntcVO.getNtcOriginalFile() != null) {
				
				log.info("첨부파일 있음!");
				
				msg += fileUploadService.deleteUploadFile(ntcVO.getNtcFile());
				log.info("파일 삭제 msg : {}", msg);
			}
			// DB에서 게시글 삭제
			if (ntcService.deleteById(ntcNum) == true) {
				msg = "게시글 삭제에 성공했습니다.";
				movePage = "/ntc/list.do";
			} else {
				msg = "게시글 삭제에 실패했습니다.";
			}
		
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", movePage);
		
		returnPath = "/error/error";
		return returnPath;
	}
	
	
}