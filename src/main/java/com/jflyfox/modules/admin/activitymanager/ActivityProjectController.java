package com.jflyfox.modules.admin.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.SessionUser;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.system.department.SysDepartment;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.system.user.UserController;
import com.jflyfox.system.user.UserSvc;

import java.util.List;

/**
 * 〈评委评分项目列表〉
 * Created by huangdk on 2018/5/27.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/activityproject")
public class ActivityProjectController extends BaseProjectController {
    private static final String path = "/pages/admin/activity/";

    public void index() {
        final String busi_activity_id = getPara("busi_activity_id");
        setAttr("busi_activity_id",busi_activity_id);
        render(path + "project_list.html");
    }

    public void list() {

        final String busi_activity_id = getPara("busi_activity_id");

        String sql="select a.id,a.project_name,b.realname,c.name as departname,99 as score,'未提交' as projectstatus\n" +
                "from busi_activity_project a\n" +
                "left join sys_user b on a.create_id=b.userid\n" +
                "left join sys_department c on b.departid=c.id\n" +
                "where a.busi_activity_id = "+busi_activity_id;
        List<Record> records = Db.find(sql);

        renderJson(records);

    }

    public void edit(){

        final SessionUser user = getSessionUser();
        Integer pid = getParaToInt();

        BusiActivityProject model = BusiActivityProject.dao.findById(pid);
        final SysUser sysUser = SysUser.dao.findById(model.getInt("create_id"));
        final SysDepartment sysDepartment = SysDepartment.dao.findById(sysUser.get("departid", "0"));setAttr("createname",sysUser.get("realname"));
        setAttr("createname",sysUser.get("realname"));
        setAttr("department",sysDepartment.getName());
        setAttr("model",model);
        render(path + "project_jugde.html");
    }
    public void save(){

    }
}
