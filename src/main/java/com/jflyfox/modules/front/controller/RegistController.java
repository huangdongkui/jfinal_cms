package com.jflyfox.modules.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.ImageCode;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.site.TbSite;
import com.jflyfox.modules.front.interceptor.FrontInterceptor;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.dict.DictSvc;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.system.user.UserCache;
import com.jflyfox.system.userrole.SysUserRole;
import com.jflyfox.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

@ControllerBind(controllerKey = "/front/regist")
public class RegistController extends BaseProjectController {

	public static final String path = "/regist/";

	/**
	 * 注册
	 */
	@Before(FrontInterceptor.class)
	public void index() {

		//拦截请求
		final String registSwitch = getPara("registSwitch");
		if(registSwitch!=null){
			renderAuto(path + "regist_switch.html");
			return;
		}

		setAttr("registtype", getPara("registtype"));

		// 目录列表
		setAttr("folders_selected", "regist");

		String prePage = getPara("pre_page");
		if (StrUtils.isEmpty(prePage)) {
			prePage = getPrePage();
		}
		setAttr("pre_page", prePage);

//		SysUser user = (SysUser) getSessionUser();
//		// 如果已经登录了~您就别注册啦
//		if (user != null) {
//			redirect(prePage);
//		} else {
			setAttr("departSelect", new DepartmentSvc().selectDepartByParentId(0,10));

			//List<String> listValue=new ArrayList<>();

			setAttr("belongfieldselect", new DictSvc().checkboxSysDictDetail(null,"belongfield"));

			renderAuto(path + "show_regist.html");
		//}
	}

	/**
	 * 注册信息保存
	 */
	public void save() {
		JSONObject json = new JSONObject();
		json.put("status", 2);// 失败

		SysUser user = getModel(SysUser.class);
		String password = getPara("password");
		String password2 = getPara("password2");
		String key = user.getStr("email");
		String registtype=getPara("registtype");
		// 获取验证码
		String imageCode = getSessionAttr(ImageCode.class.getName());
		String checkCode = this.getPara("imageCode");

		if (StrUtils.isEmpty(imageCode) || !imageCode.equalsIgnoreCase(checkCode)) {
			json.put("msg", "验证码错误！");
			renderJson(json.toJSONString());
			return;
		}

		if (StrUtils.isEmpty(key) || key.indexOf("@") < 0) {
			json.put("msg", "email格式错误！");
			renderJson(json.toJSONString());
			return;
		}

		// 前台都验证了~没必要都进行逐一提示，错误的都是跳过了js验证，不怀好意的人
		String realname = user.getStr("realname");
		if (user.getInt("userid") != null || StrUtils.isEmpty(realname) //
				|| realname.length() < 2 || realname.length() > 20 // 名称长度限制
				|| StrUtils.isEmpty(password) || StrUtils.isEmpty(password2) //
				|| password.length() < 6 || password.length() > 20 // 密码长度限制
				|| !password.equals(password2)) {
			json.put("msg", "提交数据错误！");
			renderJson(json.toJSONString());
			return;
		}

		SysUser newUser = SysUser.dao.findFirstByWhere("where email = ? ", key);
		if (newUser != null) {
			json.put("msg", "邮箱已存在，请重新输入");
			renderJson(json.toJSONString());
			return;
		}
		key=user.getStr("tel");
		newUser = SysUser.dao.findFirstByWhere("where username = ? ", key);
		if (newUser != null) {
			json.put("msg", "手机号已存在，请重新输入");
			renderJson(json.toJSONString());
			return;
		}

		user.set("username", user.getStr("tel"));
		user.set("password", JFlyFoxUtils.passwordEncrypt(password));
		user.set("usertype", JFlyFoxUtils.USER_TYPE_NORMAL);
		user.set("departid", user.getInt("departid"));
		user.set("busitype",registtype);
		user.set("state", 2); // 需要认证
		// 站点设置
		TbSite site = getSessionSite().getModel();
		user.set("back_site_id", 0);
		user.set("create_site_id", site.getId());
		
		user.set("create_time", getNow());
		user.set("create_id", 1);
		user.set("belongfieldtype",getPara("belongfieldtype"));
		//setAttr("registtype",registtype);

		if(user.save()){
			SysUserRole sysUserRole=new SysUserRole();
			sysUserRole.set("userid",user.get("userid"));
			if(registtype.equals("0")){
				sysUserRole.set("roleid",4);
			}
			else if(registtype.equals("1")){
				sysUserRole.set("roleid",2);
			}

			sysUserRole.save();

		}
		
		UserCache.init(); // 设置缓存
		setSessionUser(user); // 设置session
		json.put("status", 1);// 成功

		renderJson(json.toJSONString());
	}


	public void checkTel(){
		Boolean isExist=false;
		final String tel = getPara("tel");

		final Record first = Db.findFirst("select * from sys_user where tel=?", tel);
		if(first!=null){
			isExist=true;
		}
		renderText(isExist.toString());
	}
}
