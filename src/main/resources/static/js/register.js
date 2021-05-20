window.onload = function() {
	$('#doSignUp').click(function(e){	 
	    var userid = $("#userId").val();
	    var userpwd = $("#userPw").val();
	    var inputPwdCfm = $("#re_password").val();
	    var email = $("#userMail").val();
	    var birthday = $("#userBirthdate").val();
	    var phoneNumber = $("#userPhone").val();
	  
	    if(userid == ""){
	        alert("아이디를 입력해 주세요"); 
	        $("#userId").focus();
	        return false;
	    }else if($('#userName').val() == ""){
	        alert("이름을 입력해주세요");
	        $('#u_name').focus();
	        return false;
	    }else if(userpwd == ""){
	        alert("비밀번호를 입력해 주세요"); 
	        $("#userPw").focus();
	        return false;
	    }else if(userpwd != inputPwdCfm){
	        alert("비밀번호가 서로 다릅니다. 비밀번호를 확인해 주세요."); 
	        $("#re_password").focus();
	        return false; 
	    }else if(email == ""){
	        alert("이메일을 입력해주세요");
	        $("#userMail").focus();
	        return false;
	    }else if(birthday == ""){
	        alert("생년월일을 입력해주세여");
	        $("#userBirthdate").focus();
	        return false;
	    }else if(phoneNumber == ""){
	        alert("전화번호를 입력해주세요");
	        $("#userPhone").focus();
	        return false;
	    }	   
	    alert($('#userId').val() + "님 환영합니다!");
	    $(this).submit(); 
	});
	$('#birth_check').click(function(){
		var birthDate = $('#type_birth').val();
		var splitDate = birthDate.split("-");
		if(birthDate == "" || splitDate.length !=3 || splitDate[1].length !=2 || splitDate[2].length !=2 || splitDate[0].length !=4){
			alert("맞는 양식을 입력하세요");
			return false;
		}
		$("#userBirthdate").attr('value', birthDate);
	});
	
$('#userId').on("propertychange change keyup paste input", function(){

	console.log("keyup 테스트");	

			$.ajax({
				url : "/memberIdChk",
				type : "post",
				dataType : "json",
				data : {"userId" : $("#userId").val()},
				success : function(data){
					if(data == 1){
						$('.id_input_re_2').css("display","inline-block");
						$('.id_input_re_1').css("display", "none");	
						$("#doSignUp").attr("disabled", "disabled");
					}else if(data == 0){
						$("#doSignUp").removeAttr("disabled");
						$('.id_input_re_1').css("display","inline-block");
						$('.id_input_re_2').css("display", "none");	
					}
				}
			})
		

});
$('#userPhone').on("propertychange change keyup paste input", function(){
		
		$.ajax({
				url : "/phoneChk",
				type : "post",
				dataType : "json",
				data : {"userId" : $("#userId").val()},
				success : function(){
				
				let phoneNumber = $('#userPhone').val;
				let checkNum = /[0-9]/;
				
				if(phoneNumber !== checkNum){
							$('.phone_alert_re_1').css("display","inline-block");
						}else if(phoneNumber !== checkNum){
							$('.phone_alert_re_1').css("display", "none");	
						}
					}
			})

});
}
