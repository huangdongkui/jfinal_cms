function loadPicimageCode() {
    document.getElementById("picimageCode").src = jflyfox.BASE_PATH + 'front/image_code?ran=' + Math.random();
}
var phonenumbers = '';
function oper_save() {

    phonenumbers = $('[name="model.tel"]').val();
    if (phonenumbers == '') {
        alert('手机不能为空！');
        return;
    } else {
        if (phonenumbers.length != 11) {
            alert('手机号码有误');
            return;
        }
    }

    var email = $('[name="model.email"]').val();
    var realname = $('[name="model.realname"]').val();
    if (email == '') {
        alert('邮箱不能为空！');
        return;
    }

    var regexEmail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if (!regexEmail.test(email)) {
        alert('邮箱格式不正确！');
        return;
    }

    if (realname == '') {
        alert('昵称不能为空！');
        return;
    }
    //
    // if (realname.length < 3) {
    //     alert('昵称长度必须大于3！');
    //     return;
    // }

    // if (realname.length > 15) {
    //     alert('昵称长度必须小于16！');
    //     return;
    // }

    var pwd = $('[name="password"]').val();
    var pwd2 = $('[name="password2"]').val();
    if (pwd == '') {
        alert('密码不能为空！');
        return;
    }
    if (pwd2 == '') {
        alert('确认密码不能为空！');
        return;
    }
    if (pwd.length < 6) {
        alert('密码长度必须大于等于6');
        return;
    }
    if (pwd.length > 20) {
        alert('密码长度必须小于等于20');
        return;
    }

    if (pwd != pwd2) {
        alert('两次密码不一致！');
        return;
    }
    var imageCode = $('[name="imageCode"]').val();
    if (imageCode == '') {
        alert('验证码不能为空！');
        return;
    }
    if (imageCode.length != 4) {
        alert('验证码输入错误！');
        return;
    }

    var arrIds = [];
    $(".divbelongfieldtype").find(":checkbox:checked").each(function (o) {
        arrIds.push($(this).val())
    });

    $("#belongfieldtype").val(arrIds.join(","));
debugger;
    var isPass=false;
    jQuery.ajax({
        type: 'POST',
        url: jflyfox.BASE_PATH + 'front/regist/checkTel',
        async: false,
        data:{tel:phonenumbers},
        success:function (data) {
            if(data=="true") {
                alert("手机号已经注册过!");
            }else{
                isPass=true;
            }
        }
    });
    if(isPass==true){
    jQuery.ajax({
        type: 'POST',
        url: jflyfox.BASE_PATH + 'front/regist/save',
        data: $("form").serialize(),
        success: function (data) {
            if (data.status == 1) {
                alert('注册成功');
                $.cookie('beginDate', null);

                window.top.location.href = prePage;
            } else {
                alert('保存失败：' + data.msg);
            }
        },
        error: function (html) {
            var flag = (typeof console != 'undefined');
            if (flag) console.log("服务器忙，提交数据失败，代码:" + html.status + "，请联系管理员！");
            alert("服务器忙，提交数据失败，请联系管理员！");
        }
    });}
}

var max = 60;
var $btnGetCode;
var text = "验证码有效秒数：";

function beginCount() {

    phonenumbers = $('[name="model.tel"]').val();
    if (phonenumbers == '') {
        alert('手机不能为空！');
        return;
    } else {
        if (phonenumbers.length != 11) {
            alert('手机号码有误');
            return;
        }
    }

    $.get(jflyfox.BASE_PATH + 'front/moblie_code?ran=' + Math.random(), {sendto: phonenumbers});

    //记下开始计数时间到cookie中，当页面刷新了也可以继续记数
    $.cookie('beginDate', new Date().getTime(), {expires: 60});

    //设置最大秒数
    max = 60;
    //倒数
    count();
}

$(function myfunction() {
    $btnGetCode = $("#btnGetCode");
    //最近点击时间
    var beginDate = parseInt($.cookie('beginDate'));
    //已过秒数
    var currentCount = Math.floor((new Date().getTime() - beginDate) / 1000);
    //剩下秒数
    max = max - currentCount;
    //倒数
    count();
});

//递归记数
function count() {

    if (max > 0 && max <= 60) {
        $btnGetCode.val(text + max--);
        timeoutID = setTimeout("count()", 1000);
    } else if (max == 0) {
        $btnGetCode.val("重新获取");
    }

}