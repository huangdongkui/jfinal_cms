package com.jflyfox.api.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.jflyfox.api.form.ApiResp;
import com.jflyfox.api.form.ApiForm;
import com.jflyfox.api.interceptor.ApiInterceptor;
import com.jflyfox.api.service.ApiService;
import com.jflyfox.api.util.ApiUtils;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.activitymanager.BusiActivity;
import com.jflyfox.modules.admin.activitymanager.BusiActivitySlave;
import com.jflyfox.util.StrUtils;

import java.util.List;

@ControllerBind(controllerKey = "/api")
//@Before(ApiInterceptor.class)
public class ApiController extends BaseProjectController {

	ApiService service = new ApiService();

	/**
	 * api测试入口
	 * 
	 * 2016年10月3日 下午5:47:55 flyfox 369191470@qq.com
	 */
	public void index() {
		ApiForm from = getForm();
		
		renderJson(new ApiResp(from).addData("notice", "api is ok!"));
	}

	/**
	 * 开关调试日志
	 * 
	 * 2016年10月3日 下午5:47:46 flyfox 369191470@qq.com
	 */
	public void debug() {
		ApiForm from = getForm();
		
		ApiUtils.DEBUG = !ApiUtils.DEBUG;
		renderJson(new ApiResp(from).addData("debug", ApiUtils.DEBUG));
	}

	/**
	 * 获取信息入口
	 * 
	 * 2016年10月3日 下午1:38:27 flyfox 369191470@qq.com
	 */
	public void action() {
		long start = System.currentTimeMillis();

		ApiForm from = getForm();
		if (StrUtils.isEmpty(from.getMethod())) {
			String method = getPara();
			from.setMethod(method);
		}

		// 调用接口方法
		ApiResp resp = service.action(from);
		// 没有数据输出空
		resp = resp == null ? new ApiResp(from) : resp;
		
		// 调试日志
		if (ApiUtils.DEBUG) {
			log.info("API DEBUG ACTION \n[from=" + from + "]" //
					+ "\n[resp=" + JsonKit.toJson(resp) + "]" //
					+ "\n[time=" + (System.currentTimeMillis() - start) + "ms]");
		}
		renderJson(resp);
	}

	public ApiForm getForm() {
		ApiForm form = getBean(ApiForm.class, null);
		return form;
	}

	public void activityslist(){
		final List<BusiActivity> byWhereAndColumns = BusiActivity.dao.findByWhereAndColumns("where deleted=?", "id,activity_name", 0);
		renderJson(byWhereAndColumns);
	}

	public void busiActivitySlaveList(){

		String busi_activity_id=getPara("busi_activity_id");

		final List<BusiActivitySlave> byWhere = BusiActivitySlave.dao.findByWhereAndColumns("where busi_activity_id=?", "id,nodeid,(select detail_name from sys_dict_detail where dict_type='activitystage' and detail_code=nodeid) as nodename", busi_activity_id);

		renderJson(byWhere);
	}


}
