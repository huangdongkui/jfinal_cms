package com.jflyfox.modules.admin.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.jfinal.base.SessionUser;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.foldernotice.TbFolderNotice;
import com.jflyfox.system.config.ConfigService;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.dict.DictSvc;
import com.jflyfox.system.file.model.SysFileUpload;
import com.jflyfox.system.file.util.FileUploadUtils;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.util.DateUtils;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.commons.lang.StringUtils;
import sun.misc.Request;

import java.util.Arrays;
import java.util.List;


/**
 * 〈活动填报〉
 * Created by huangdk on 2018/5/26.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/activityfill")
public class ActivityFillController extends BaseProjectController {
    private static final String path = "/pages/admin/activity/activity_";

    public void index() {
        String busi_activity_id = getPara("busi_activity_id");
        edit(busi_activity_id);
    }

    public void edit(String busi_activity_id) {

        if (busi_activity_id == null) {
            redirect("/admin/home");
            return;
        }
        final SessionUser sessionUser = getSessionUser();
        BusiActivityProject model = BusiActivityProject.dao.findFirstByWhere(" where busi_activity_id = ? and create_id = ? and deleted=0", busi_activity_id, sessionUser.getUserid().toString());


        if (model == null) {
            model = new BusiActivityProject();
            model.set("busi_activity_id", busi_activity_id);
        }

        setAttr("belongfieldselect", new DictSvc().checkboxSysDictDetail(model.getStr("from_belongfields"), "belongfield"));


        setAttr("projecttypeselect", new DictSvc().checkboxSysDictDetail(model.getStr("project_type"), "projecttype"));


        final DictSvc dictSvc = new DictSvc();


        //departmentSvc
        setAttr("tech_maturityselect", dictSvc.selectDictDetailType(model.getStr("tech_maturity"), "tech_maturity"));
        setAttr("core_techselect", dictSvc.selectDictDetailType(model.getStr("core_tech"), "core_tech"));
        setAttr("intell_rightselect", dictSvc.selectDictDetailType(model.getStr("intell_right"), "intell_right"));
        setAttr("market_competitiveselect", dictSvc.selectDictDetailType(model.getStr("market_competitive"), "market_competitive"));
        setAttr("projected_returnsselect", dictSvc.selectDictDetailType(model.getStr("projected_returns"), "projected_returns"));


        setAttr("core_tech_contents_li", genHtmlLiCode(model.getStr("core_tech_contents")));
        setAttr("filelist", ActivityService.genHtmllIFilesbybusinessid(model.get("id", "0").toString(), true));
        setAttr("model", model);
        render(path + "fill.html");
    }


    /**
     * 生成li代码
     *
     * @param core_tech_contents a,b,c
     * @return
     */
    private String genHtmlLiCode(String core_tech_contents) {
        if (StringUtils.isEmpty(core_tech_contents)) {
            return "";
        }

        String[] split = core_tech_contents.split("\\$\\$");
        StringBuilder sb = new StringBuilder();

        for (String s : split) {
            sb.append("<li class=\"list-group-item\"><span>" + s + "</span><a style=\"float: right;\" href=\"javascript:(0);\" onclick=\"deleteLi(this);return false;\">删除</a></li>");
        }

        return sb.toString();

    }

    public void save() {

        BusiActivityProject model = getModel(BusiActivityProject.class);

        final String busi_activity_id = model.get("busi_activity_id").toString();
        if (busi_activity_id == null) {
            redirect("/admin/home");
            return;
        }

        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.put("update_id", userid);
        model.put("update_time", now);
        if (model.getId() != null && model.getId() > 0) { // 更新
            model.update();
        } else { // 新增
            model.remove("id");
            model.put("create_id", userid);
            model.save();
        }

        //renderMessage("1");
        renderText(model.getId().toString());

    }

    public void submit() {
        String pid = getPara("id", "");
        final Integer updateCount = Db.update("update busi_activity_project set project_status=1 where id=?", pid);
        renderText(updateCount.toString());

    }

}
