/**
 * 
 */

// 도로명 주소 검색
function getPostcodeAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수(도로명 주소)
            var fullAddrJibun = ''; // 최종 주소 변수(지번 주소)
            var extraAddr = ''; // 조합형 주소 변수
            
            ////////////////////////////////////////////////////////////////
            
            console.log("도로명 주소 : " + data.roadAddress);
            console.log("지번 주소 : " + data.jibunAddress);
            console.log("지번 주소(자동처리 : 지번 미출력시 자동 입력처리) : " + data.autoJibunAddress);

            // javateacher) 이 부분을 생략하여 도로명과 지번이 같이 넘어가도록 조치
            
            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            /*
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                // fullAddr = data.jibunAddress;
                fullAddrJibun = data.jibunAddress;
            }
            */

            fullAddr = data.roadAddress;
            // 지번 미입력시 : 자동 입력 지번 주소 활용(data.autoJibunAddress)
            fullAddrJibun = data.jibunAddress == '' ? data.autoJibunAddress : data.jibunAddress;


            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            // if(data.userSelectedType === 'R'){
                
            // 법정동명이 있을 경우 추가한다.
            if(data.bname !== ''){
                extraAddr += data.bname;
            }
            // 건물명이 있을 경우 추가한다.
            if(data.buildingName !== ''){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            
            // 조합형 주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
            // fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                // fullAddrJibun += (extraAddr !== '' ? ' ('+ extraAddr +')' : ''); // javateacher 추가
            // }

            // javateacher end)
            
            ////////////////////////////////////////////////////////////////
                
            // 주소 정보 전체 필드 및 내용 확인 : javateacher
            var output = '';
            for (var key in data) {
                output += key + ":" +  data[key]+"\n";
            }
            
            console.log("-----------------------------")
            console.log(output);
            console.log("-----------------------------")

            // 3단계 : 해당 필드들에 정보 입력
            // 우편번호와 주소 정보를 해당 필드에 넣는다.

            // javateacher) 본 회원가입 코드에서는 도로명으로 선택하든 지번 주소로 선택하든
            // 일괄적으로 도로명으로 기본주소가 들어가도록 설정하였습니다.
            
            let zipFld = document.getElementById('memberZip');
            let roadAddressFld = document.getElementById('memberRoadAddress');
            let jibunAddressFld = document.getElementById('memberJibunAddress');
			let detailAddressFld = document.getElementById('memberDetailAddress');
            // let addressFldErrPnl = document.getElementById('address_fld_err_pnl');

            zipFld.value = data.zonecode; // 5자리 우편번호 사용
            roadAddressFld.value = fullAddr; // 도로명 주소
			jibunAddressFld.value = fullAddrJibun; // 지번 주소
            
            // 커서를 상세주소 필드로 이동한다.
            detailAddressFld.focus();                        

            // 주소 필드 점검
            // isCheckAddressFldValid(zipFld, roadAddressFld, jibunAddressFld, detailAddressFld, addressFldErrPnl);
        }   
    }).open();
}


window.onload=function(){
        	
        	let doubledIdCheck = false;
        	
        	let idCheckButton = document.getElementById('idCheck_btn');
        	
        	idCheckButton.onclick = function(e){
        		
        		
        		console.log("아이디 : " + $('#memberId').val());
        		
        		// 아이디 중복 체크
        		$(function(){
     	           $.ajax({
     	               type: 'POST',
     	               url: '/member/idCount',
     	               data: {"memberId" : $('#memberId').val() },
     	               success: function(response) {
     	            	   console.log("response : "+ response);
     	                   if (response > 0) {
     	                       alert("중복된 아이디 입니다.")
     	                       doubledIdCheck = false;
     	                   } else {
     	                       alert("사용가능한 아이디 입니다.")
     	                       doubledIdCheck = true;
     	                   }
     	               },
     	           });// ajax
             	});
        	}// onclick
        	
        	
        	
			let doubledNickCheck = false;
        	
        	let nickCheckButton = document.getElementById('nickCheck_btn');
        	
        	
        	nickCheckButton.onclick = function(e){
            	
        		console.log("닉네임 : " + $('#memberNick').val());
        		
        		// 닉네임 중복 체크
        		$(function(){
     	           $.ajax({
     	               type: 'POST',
     	               url: '/member/nickCount',
     	               data: {"memberNick" : $('#memberNick').val() },
     	               success: function(response) {
     	            	   console.log("response : "+ response);
     	                   if (response > 0) {
     	                       alert("중복된 닉네임 입니다.")
     	                       doubledNickCheck = false;
     	                   } else {
     	                       alert("사용가능한 닉네임 입니다.")
     	                       doubledNickCheck = true;
     	                   }
     	               },
     	           });// ajax
             	});
        	}// onclick
        	
        	
			let submit_btn = document.getElementById('submit_btn');
        	
        	let frm = document.getElementById('frm');
        	
        	// 중복 체크해야 회원가입 버튼 활성화
        	frm.onsubmit = function(e){
        		
        		if(doubledIdCheck == true && doubledNickCheck == true){
        			return true;
        		}else{
        			alert("중복 체크를 확인해 주세요")
            		return false;
        		}
        		
        	}
        	
            // (검색된) 주소 삭제
           let addressDeleteBtn = document.getElementById("address_delete_btn");

           addressDeleteBtn.onclick = function(e) {

               console.log("주소 삭제");
       		
       		let zipFld = document.getElementById('memberZip');
               let roadAddressFld = document.getElementById('memberRoadAddress');
               let jibunAddressFld = document.getElementById('memberJibunAddress');
       		
               zipFld.value = "";
               roadAddressFld.value = "";
               jibunAddressFld.value = "";

           } //
       		
       	}// onload
        	
/////////////////////////////////////////////////////////////////////////////////////////////



   