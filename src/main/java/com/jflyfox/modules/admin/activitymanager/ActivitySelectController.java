package com.jflyfox.modules.admin.activitymanager;

import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈弹出框控制器〉
 * Created by huangdk on 2018/5/29.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/activityselect")
public class ActivitySelectController extends BaseProjectController {
    private static final String path = "/pages/admin/activity/activity_";

    public void index() {
        list();
    }

    public void list() {
        SysUser model = getModelByAttr(SysUser.class);

        SQLUtils sql = new SQLUtils(" from sys_user t " //
                + " left join sys_department d on d.id = t.departid " //
                + " where busitype = 1 and userid != 1 ");

        if (model.getAttrValues().length != 0) {
            sql.whereLike("username", model.getStr("username"));
            sql.whereLike("realname", model.getStr("realname"));
            sql.whereEquals("usertype", model.getInt("usertype"));
            sql.whereEquals("departid", model.getInt("departid"));
        }

        // 排序
        String orderBy = getBaseForm().getOrderBy();
        if (StrUtils.isEmpty(orderBy)) {
            sql.append(" order by userid desc");
        } else {
            sql.append(" order by ").append(orderBy);
        }

        Page<SysUser> page = SysUser.dao.paginate(getPaginator(), "select t.*,d.name as departname ", sql.toString()
                .toString());
        // 下拉框
        setAttr("departSelect", new DepartmentSvc().selectDepartByParentId(0, 10));

        setAttr("page", page);
        setAttr("attr", model);

        final Integer paraToInt = getParaToInt();
        final BusiActivitySlave busiActivitySlave = BusiActivitySlave.dao.findById(paraToInt);
        setAttr("JudgesUid", busiActivitySlave.getJudgesUid());
        setAttr("id", busiActivitySlave.getId());

        render(path + "selectjudge.html");
    }

    public void save() {

        final Integer paraToInt = getParaToInt("id");
        final BusiActivitySlave busiActivitySlave = BusiActivitySlave.dao.findById(paraToInt);

        final String judgesUid = getPara("JudgesUid");
        busiActivitySlave.set("JudgesUid", getPara("JudgesUid"));

        if (StringUtils.isNotBlank(judgesUid)) {

            final SysUser firstByWhereAndColumns = SysUser.dao.findFirstByWhereAndColumns(" where userid in (" + judgesUid + ")", " group_concat(realname) as judges_names ");
            busiActivitySlave.set("judges_names", firstByWhereAndColumns.get("judges_names"));
        } else {
            busiActivitySlave.set("judges_names", "");
        }

        if (busiActivitySlave.update()) {
            renderMessage("设置成功");
        } else {
            renderMessageByFailed("设置失败");
        }

    }
}
