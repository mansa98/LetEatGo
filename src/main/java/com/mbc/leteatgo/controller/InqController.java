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

import com.mbc.leteatgo.domain.InqDTO;
import com.mbc.leteatgo.domain.InqVO;
import com.mbc.leteatgo.domain.UploadFile;
import com.mbc.leteatgo.service.InqService;
import com.mbc.leteatgo.service.FileUploadService;
import com.mbc.leteatgo.service.ImageService;
import com.mbc.leteatgo.util.FileUploadUtil;
import com.mbc.leteatgo.service.ImageStoreService;

import lombok.extern.slf4j.Slf4j;

@SessionAttributes("inqUpdateDTO")
@Controller
@RequestMapping("inq")
@Slf4j
public class InqController { 
	
	private static final Object CustomUser = null;

	@Autowired
	InqService inqService;
	
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
		
		model.addAttribute("inqDTO", new InqDTO());
		return "/inq/inqWrite";
	} //
	
	@PostMapping("/writeProc.do")
	public String writeProc(@ModelAttribute("inqDTO") InqDTO inqDTO, Model model) {
		
		log.info("InqDTO : {}", inqDTO);
		
		// VO 차원에서 암호화 파일 처리 
		// 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        // this.inqFile = inq.getInqFile().getOriginalFilename().trim().equals("") ?
        //		"" : FileUploadUtil.encodeFilename(inq.getInqFile().getOriginalFilename());
		
		InqVO inqVO = new InqVO(inqDTO);
		
		// 첨부 파일이 없다면...
		if (inqDTO.getInqFile().getOriginalFilename().trim().equals("") == false) {
			
			log.info("게시글 작성 처리(첨부 파일) : {}", inqDTO.getInqFile().getOriginalFilename());
			
			String actualUploadFilename = FileUploadUtil.encodeFilename(inqVO.getInqFile());
			inqVO.setInqFile(actualUploadFilename);
		
		} 
		
		log.info("InqVO : {}", inqVO);
		
		log.info("----- 게시글 저장 InqVO : {}", inqVO);
		
		String msg = "";
		
		// 첨부 파일이 있을 때만 저장
		if (inqDTO.getInqFile().getOriginalFilename().trim().equals("") == false) {
		
			msg = fileUploadService.storeUploadFile(inqVO.getInqNum(), inqDTO.getInqFile(), inqVO.getInqFile());
			log.info("msg : {}", msg);
		}
		
		if (inqVO != null && msg.equals("첨부 파일이 비어있거나 없습니다.") == false)  {
			inqVO = inqService.insertInq(inqVO);
			msg = "게시글 저장에 성공하였습니다.";
		}
			
		// TODO
		// /error/error
		// errMsg, movePage = /inq/list.do"
		// 정상 : 파일이 업로드 되었습니다.
		
		model.addAttribute("errMsg", msg);
		// model.addAttribute("movePage", "/inq/write.do");
		model.addAttribute("movePage", "/inq/list.do"); 
		
		return "/error/error"; 
	} //
	
	@GetMapping("/view.do/{inqNum}")
	public String view(@PathVariable("inqNum") int inqNum, Model model) {
		
		InqVO inqVO =inqService.selectInq(inqNum);
		log.info("InqVO : {}", inqVO);
		
		model.addAttribute("inq", inqVO);
		
		// 조회할 때마다 조회수 갱신(+)
		inqService.updateInqCountByInqNum(inqNum);
		
		return "/inq/inqView";
	}
	
// 	UPDATE #############################################################
	@GetMapping("/update.do/{inqNum}")
//	public String Update(@RequestParam("inqNum") int inqNum, Model model, HttpSession session) {
	public String Update(@PathVariable("inqNum") int inqNum, Model model, HttpSession session) {
		
		InqVO inqVO = inqService.selectInq(inqNum);
		log.info("inqVO(update) : {}", inqVO);
		session.setAttribute("inqUpdateSess", inqVO);
		log.info("InqVO : {}", inqVO);
		model.addAttribute("inq", inqVO);
		
		return "/inq/inqUpdate";
	}
	
	@PostMapping("/updateProc.do")
	public String UpdateProc(@RequestParam Map<String, Object> map,
							@RequestParam(name = "inqNum", required = true) int inqNum,
							@RequestParam("inqFile") MultipartFile inqFile,
							Model model,
							HttpSession session) 
	{
		
		log.info("------ updateProc.do");
		
		log.info("인자 현황 :");
		
		map.entrySet().forEach(x ->{log.info("{}", x);});
		
		boolean defaultFileDeleteYN = map.get("defaultFileDeleteYN") == null ? false
									: Boolean.parseBoolean(map.get("defaultFileDeleteYN").toString());
		log.info("첨부 파일이 비어있는지 여부 : {}", inqFile.isEmpty());
		log.info("첨부 파일명 : {}", inqFile != null ? inqFile.getOriginalFilename() : "");
		
		// ***
		String returnPath; // 글 수정 성공 실패 모두 /error/error 로 가도록 설정
		String movePage = "/inq/update.do?inqNum=" + map.get("inqNum").toString();
		String msg = ""; // 메시지
		map.entrySet().forEach(x -> {log.info(x + "");});
		
		InqVO defaultInqVO = (InqVO) session.getAttribute("inqUpdateSess"); 
		InqVO updateInqVO = new InqVO(map, inqFile); // map, inqFile 둘 다 생성해야함 ~ InqVO(Map<String, Object> map, MultipartFile inqFile)
		
		log.info("inqUpdateSession(기존 정보) : {}", defaultInqVO);
		log.info("수정 정보 : {}", updateInqVO);
		
		if (inqFile.isEmpty() == false) {
			String actualUploadFilename = FileUploadUtil.encodeFilename(inqFile.getOriginalFilename());
			updateInqVO.setInqOriginalFile(inqFile.getOriginalFilename());
			updateInqVO.setInqFile(actualUploadFilename);
			
			// 신규 업로드 파일 저장 (업로드)
			msg = fileUploadService.storeUploadFile(updateInqVO.getInqNum(), inqFile,
					updateInqVO.getInqFile());
			log.info("msg : {}", msg);
			
			// 기존 첨부 파일 삭제
			msg += fileUploadService.deleteUploadFile(defaultInqVO.getInqFile());
			log.info("msg : {}", msg);
			
		} else { // 첨부 파일이 없다면...
			
			log.info("첨부파일이 없다");
			
			if (defaultFileDeleteYN == true) {
				
				msg += fileUploadService.deleteUploadFile(defaultInqVO.getInqFile());
				
			} else { 
				// 기존 파일을 입력
				String originalFilename = defaultInqVO.getInqOriginalFile() != null
						? defaultInqVO.getInqOriginalFile() // 값이 있으면 가져오고?
						: null; // 없으면 null?
				updateInqVO.setInqOriginalFile(originalFilename);
				
				String encInqFilename = defaultInqVO.getInqFile() != null
						? defaultInqVO.getInqFile()
						: null;
				updateInqVO.setInqFile(encInqFilename);
			}
		}
		
		// 글내용(inqContent)비교 : 변경시에는 기존 삽입 이미지 삭제 등 처리
		log.info("기존 글내용 : {}", defaultInqVO.getInqContent());
		log.info("수정 글내용 : {}", updateInqVO.getInqContent());
		
		// 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)
//******이거 뭔 말임 *******************************************************************************************		
		if (defaultInqVO.getInqContent().trim().equals(updateInqVO.getInqContent().trim()) == false) {
			// 기존 데이터의 삽입 이미지 목록 (삽입 이미지 테이블(upload_file_tbl)의 PK(기본키)) 확보
			// ex) 글 내용중 이미지가 들어간 내용 ex) <img src="/inq/image/18" ...
			
			List<Integer> defaultImgList = inqService.getImageList(defaultInqVO.getInqContent().trim(),
					"/inq/image/");
			List<Integer> updateImgList = inqService.getImageList(updateInqVO.getInqContent().trim(),
					"/inq/image/");
			
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
//		inqTemp.setInqTitle(inqVO.getInqTitle());
//		inqTemp.setInqContent(inqVO.getInqContent());
		
		// 작성일을 최근 수정일로 변경
		updateInqVO.setInqDate(new Date(System.currentTimeMillis()));
		
		log.info("최종 게시글 수정 내용 : {}", updateInqVO);
		
		// 게시글 수정
		InqVO resultVO = inqService.updateInq(updateInqVO);
		if (resultVO == null) {
			msg = "게시글 수정에 실패했습니다.";
		} else {
			log.info("최종 저장 결과 : " + resultVO);
			msg = "게시글 수정에 성공했습니다.";
			
			movePage = "/inq/view.do/" + map.get("inqNum").toString();
		}
	
	model.addAttribute("errMsg", msg);
	
	model.addAttribute("movePage", movePage);
	returnPath = "error/error";
	
	return returnPath;
	}
	
// 	DELETE #############################################################
	@GetMapping("/deleteProc.do/{inqNum}")
	public String updateProc(@PathVariable("inqNum") int inqNum,
							 Model model) {
		
		log.info("deleteProc.do : inqNum : {}", inqNum);
		// 개별 게시글 보기로 이동(movePage)
		// 게시글 삭제 성공시에는 게시글 목록으로 이동(이미 삭제되었으므로)
		// 게시글 삭제 실패시에는 게시글 보기로 이동
		String returnPath; // 글삭제 성공 실패 모두 /error/error로 가도록 재설정
		String movePage = "/inq/view.do/" + inqNum;
		String msg = ""; // 메시지
		InqVO inqVO = inqService.selectInq(inqNum);
		
		log.info("기존 정보 : inqVO : {}", inqVO);
		
//		댓글이 있으면 삭제 불가 댓글 없을 경우 삭제...!!추가 해야될까??
//		####

			log.info("유저 점검 성공");
			
			// 삭제할 삽입 이미지 점검
			List<Integer> deleteImgList = inqService.getImageList(inqVO.getInqContent().trim(),
					"/inq/image/");
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
			if (inqVO.getInqOriginalFile() != null) {
				
				log.info("첨부파일 있음!");
				
				msg += fileUploadService.deleteUploadFile(inqVO.getInqFile());
				log.info("파일 삭제 msg : {}", msg);
			}
			// DB에서 게시글 삭제
			if (inqService.deleteById(inqNum) == true) {
				msg = "게시글 삭제에 성공했습니다.";
				movePage = "/inq/list.do";
			} else {
				msg = "게시글 삭제에 실패했습니다.";
			}
		
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", movePage);
		
		returnPath = "/error/error";
		return returnPath;
	}
	
	
}