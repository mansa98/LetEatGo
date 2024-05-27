/* **************************************************************************************************** */

// (원)글 삭제
// 0424 : 삭제부분 취소누르면 삭제안되게 수정(유재승)
function deleteNtc(ntcNum) {
	
	// console.log("ntcNum : ", ntcNum, ", ntcWriter : ", ntcWriter);
	// console.log("삭제할 게시글(원글) 아이디 : ",ntcNum);
	
	// 게시글 삭제 버튼
	let ntcDeleteBtn = document.getElementById(`ntc_delete_btn_${ntcNum}`);
	
	// 삭제할 게시글 패쓰워드
	// let ntcDeletePass = document.getElementById(`ntc_pass_${ntcNum}`);
	
	ntcDeleteBtn.onclick = function() {
		
		console.log("삭제할 게시글 아이디 : ", ntcNum);
		
		// 게시글 삭제 의사 재점검
		if (confirm("정말 삭제하시겠습니까?") == true) {
		
			// 전송
			// 삭제를 위한 AJAX 전송
			// 삭제할 게시글 아이디
			
			//////////////////////////////////////////////////////////////////////////
			// 
			// 주의사항) 특수문자(#)이 포함된 ntcPass 인자 전송할 경우 => encodeURIComponent 활용 !
			//  
			
			let str = `/ntc/deleteProc.do/${ntcNum}`;
			
			console.log('str : ', str);
			
			location.href = str;
				
			//} // if
		
		} else {
			
			alert("게시글 삭제를 취소하였습니다.");
			
		} // if (confirm("정말 삭제하시겠습니까?") == true	
		
	} // ntcDeleteBtn.onclick

} //