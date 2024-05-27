
window.onload = function(e){
	
	let deleteBtns = document.querySelectorAll("[id^=delete_my_btn]");
	
	for (deleteBtn of deleteBtns) {
		
		deleteBtn.onclick = (e) => {
			
			id = e.target.id;
			id = id.substring('delete_my_btn'.length);
			console.log("확인 용 : ", id);
			// let deleteBtn = document.getElementById('delete_btn'+id);
			
			if (confirm(`${id} 님의 회원 정보를 정말 삭제하시겠습니까?`) == true) {
				
				/*alert(`${id} 님의 회원 정보를 삭제하겠습니다.`);
				alert(`${id}`);*/
				axios.get(`/member/deleteMemberByMyId/${id}`)
				 .then(function(response) {
					
					console.log("response.data : ", response.data);
	
					// 오류 있을 때만 팝업으로 출력하도록 조치
					if (response.data === true) {
						msg = "회원 정보 삭제 처리에 성공하였습니다.";
					}
					
					alert(msg);
					// 화면 갱신 : refresh
					//location.reload();
					location.href="/logout";
				 })
				 .catch(function(err) {
					console.error("개별 회원 정보 삭제 중 서버 에러가 발견되었습니다.");
					alert(`${id} 님의 회원 정보 삭제 중 서버 에러가 발견되었습니다.`);				
				 });
				// axios
				
			} else {
				alert(`${id} 님의 회원 정보 삭제를 취소하였습니다.`);
			} // if 문
			
		} // onclick
		
	} // for
	
} // onload
