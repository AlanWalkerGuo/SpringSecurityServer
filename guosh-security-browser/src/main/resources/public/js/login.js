var projectName="";

$(function(){
	//获取项目名
	var pathName=window.document.location.pathname
	projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	var tab = 'account_number';
	// 选项卡切换
	$(".account_number").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
        $(this).addClass("on");
        $(".message").removeClass("on");
        $(".form2").addClass("hide");
        $(".form1").removeClass("hide");
    });
	// 选项卡切换
	$(".message").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
		$(this).addClass("on");
        $(".account_number").removeClass("on");
		$(".form2").removeClass("hide");
		$(".form1").addClass("hide");
		
    });

	$('#num').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#pass').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#veri').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#num2').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#veri-code').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	// 按钮是否可点击
	function checkBtn()
	{
		$(".log-btn").off('click');
		if (tab == 'account_number') {
			var inp = $.trim($('#num').val());
			var pass = $.trim($('#pass').val());
			if (inp != '' && pass != '') {
				if (!$('.code').hasClass('hide')) {
					code = $.trim($('#veri').val());
					if (code == '') {
						$(".log-btn").addClass("off");
					} else {
						$(".log-btn").removeClass("off");
						sendBtn();
					}
				} else {
					$(".log-btn").removeClass("off");
						sendBtn();
				}
			} else {
				$(".log-btn").addClass("off");
			}
		} else {
			var phone = $.trim($('#num2').val());
			var code2 = $.trim($('#veri-code').val());
			if (phone != '' && code2 != '') {
				$(".log-btn").removeClass("off");
				sendBtn();
			} else {
				$(".log-btn").addClass("off");
			}
		}
	}

	function checkAccount(username){
		if (username == '') {
			$('.num-err').removeClass('hide').find("em").text('请输入账户');
			return false;
		} else {
			$('.num-err').addClass('hide');
			return true;
		}
	}

	function checkPass(pass){
		if (pass == '') {
			$('.pass-err').removeClass('hide').text('请输入密码');
			return false;
		} else {
			$('.pass-err').addClass('hide');
			return true;
		}
	}

	function checkCode(code){
		if (code == '') {
			 $('.tel-warn').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			// $('.tel-warn').addClass('hide');
			return true;
		}
	}

	//登陆发送验证码
	function checkPhone(phone){
		var status = true;
		if (phone == '') {
			$('.num2-err').removeClass('hide').find("em").text('请输入手机号');
			return false;
		}
		var param = /^1[34578]\d{9}$/;
		if (!param.test(phone)) {
			// globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
			$('.num2-err').removeClass('hide');
			$('.num2-err').text('手机号不合法，请重新输入');
			return false;
		}
		$.ajax({
            url: '/checkPhone',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {phone:phone,type:"login"},
            success:function(data){
                if (data.code == '0') {
                    $('.num2-err').addClass('hide');
                    // console.log('aa');
                    // return true;
                } else {
                    $('.num2-err').removeClass('hide').text(data.msg);
                    // console.log('bb');
					status = false;
					// return false;
                }
            },
            error:function(){
            	status = false;
                // return false;
            }
        });
		return status;
	}

	function checkPhoneCode(pCode){
		if (pCode == '') {
			$('.error').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			$('.error').addClass('hide');
			return true;
		}
	}

	// 登录点击事件
	function sendBtn(){
		var code='';
		var rememberme=false;
		//普通登陆
		if (tab == 'account_number') {
			$(".log-btn").click(function(){
				// var type = 'phone';
				var inp = $.trim($('#num').val());
				var pass = $.trim($('#pass').val());
				if($('#remember-me').is(':checked')){
					rememberme=true;
				}

				//验证账号密码是否是空
				if (checkAccount(inp) && checkPass(pass)) {
					//判断验证码是否为空
					if (!$('.code').hasClass('hide')) {
						 code = $.trim($('#veri').val());
						if (!checkCode(code)) {
							return false;
						}
					}
					$.ajax({
			            url: projectName+'/authentication/form',
			            type: 'post',
			            dataType: 'json',
			            data: "username="+inp+"&password="+pass+"&imageCode="+code+"&remember-me="+rememberme,
			            success:function(response){
							var url=window.location.href;
							if(url.indexOf('#')!=-1){
								window.location.href=projectName+url.substring(url.indexOf('#'))
								//window.history.back();
							}else{
								window.location.href=projectName+"/#/";
							}




			                // if (code == '0') {
			                //     // globalTip({'msg':'登录成功!','setTime':3,'jump':true,'URL':'http://www.ui.cn'});
			                //     globalTip(data.msg);
			                // } else if(code == '2') {
			                // 	$(".log-btn").off('click').addClass("off");
			                //     $('.pass-err').removeClass('hide').find('em').text("密码错误");
			                //     $('.pass-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
			                //     $('.code').removeClass('hide');
			                //     $('.code').find('img').attr('src',projectName+'/code/image?'+Math.random()).click(function(event) {
			                //     	$(this).attr('src', projectName+'/code/image?'+Math.random());
			                //     });;
			                //     return false;
			                // } else if(code == '3') {
			                // 	//验证码错误
			                // 	$(".log-btn").off('click').addClass("off");
			                //     $('.img-err').removeClass('hide').find('em').text("验证码错误");
			                //     $('.img-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
			                //     $('.code').removeClass('hide');
			                //     $('.code').find('img').attr('src',projectName+'/code/image?'+Math.random()).click(function(event) {
			                //     	$(this).attr('src', projectName+'/code/image?'+Math.random());
			                //     });
			                //     return false;
			                // } else if(code == '1'){
			                // 	$(".log-btn").off('click').addClass("off");
			                // 	$('.num-err').removeClass('hide').find('em').text(账号错误);
			                // 	$('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
			                // 	return false;
			                // }
			            },
			            error:function(e) {
							$(".log-btn").off('click').addClass("off");
							$('.code').removeClass('hide');
							$('.code').find('img').attr('src', projectName + '/code/image?' + Math.random()).click(function (event) {
								$(this).attr('src', projectName + '/code/image?' + Math.random());
							});

			            	if(e.responseJSON.object=='用户名或密码错误'){
			            		$('.num-err').removeClass('hide').find('em').text("用户名或密码错误");
								$('.num-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
							}else{
								$('.img-err').removeClass('hide').find('em').text(e.responseJSON.object);
								$('.img-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
							}
						}

			        });
				} else {
					return false;
				}
			});
		} else {
			//手机登陆
			$(".log-btn").click(function(){
				// var type = 'phone';
				var phone = $.trim($('#num2').val());
				var pcode = $.trim($('#veri-code').val());
				if (checkPhone(phone) && checkPass(pcode)) {
					$.ajax({
			            url: '/plogin',
			            type: 'post',
			            dataType: 'json',
			            async: true,
			            data: {phone:phone,code:pcode},
			            success:function(data){
			                if (data.code == '0') {
			                	// globalTip({'msg':'登录成功!','setTime':3,'jump':true,'URL':'http://www.ui.cn'});
			                	globalTip(data.msg);
			                } else if(data.code == '1') {
			                	$(".log-btn").off('click').addClass("off");
			                    $('.num2-err').removeClass('hide').text(data.msg);
			                    return false;
			                } else if(data.code == '2') {
			                	$(".log-btn").off('click').addClass("off");
			                    $('.error').removeClass('hide').text(data.msg);
			                    return false;
			                }
			            },
			            error:function(){
			                
			            }
			        });
				} else {
					$(".log-btn").off('click').addClass("off");
					// $('.tel-warn').removeClass('hide').text('登录失败');
					return false;
				}
			});
		}
	}

	// 登录的回车事件
	$(window).keydown(function(event) {
    	if (event.keyCode == 13) {
    		$('.log-btn').trigger('click');
    	}
    });


	$(".form-data").delegate(".send","click",function () {
		var phone = $.trim($('#num2').val());
		if (checkPhone(phone)) {
				$.ajax({
		            url: '/getcode',
		            type: 'post',
		            dataType: 'json',
		            async: true,
		            data: {phone:phone,type:"login"},
		            success:function(data){
		                if (data.code == '0') {
		                    
		                } else {
		                    
		                }
		            },
		            error:function(){
		                
		            }
		        });
	       	var oTime = $(".form-data .time"),
			oSend = $(".form-data .send"),
			num = parseInt(oTime.text()),
			oEm = $(".form-data .time em");
		    $(this).hide();
		    oTime.removeClass("hide");
		    var timer = setInterval(function () {
		   	var num2 = num-=1;
	            oEm.text(num2);
	            if(num2==0){
	                clearInterval(timer);
	                oSend.text("重新发送验证码");
				    oSend.show();
	                oEm.text("120");
	                oTime.addClass("hide");
	            }
	        },1000);
		}
    });



});