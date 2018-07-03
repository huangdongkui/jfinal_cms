package com.jflyfox.modules.admin.activitymanager;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈统一报表〉
 * Created by huangdk on 2018/7/3.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/report")
public class ActivityReportController extends BaseProjectController {
    private static final String path = "/pages/admin/activity/";

    public void index() {
        render(path + "project_reportlist.html");
    }

    public void reportbyJudges() {
        String busi_activity_id = getPara("busi_activity_id");
        String busi_activity_slave_id = getPara("busi_activity_slave_id");
        String belongfield = getPara("belongfield");

        final List<Record> records = getList(busi_activity_id, busi_activity_slave_id, belongfield);

        setAttr("list", records);

        renderJson(records);

    }

    public List<Record> getList(String busi_activity_id, String busi_activity_slave_id, String belongfield) {

        String sql = "SELECT \n" +
                "(\n" +
                "SELECT COUNT(*)\n" +
                "FROM busi_activity_project p\n" +
                "WHERE p.busi_activity_id=" + busi_activity_id + " AND p.deleted=0 AND p.project_status=1 #field# AND func_splitString(u.belongfieldtype,',',p.from_belongfields)>0) AS total,\n" +
                "\n" +
                "u.realname,\n" +
                "m.activity_name,\n" +
                "(CASE s.nodeid WHEN 0 THEN '填报' WHEN 1 THEN '初赛' WHEN 2 THEN '复赛' WHEN 3 THEN '决赛' END) AS nodename,\n" +
                " u.userid,\n" +
                " (\n" +
                "SELECT COUNT(rrscreateid)\n" +
                "FROM (\n" +
                "SELECT\n" +
                "rs.create_id AS rrscreateid\n" +
                "FROM busi_score_template_relation_score rs\n" +
                "left join busi_activity_project p \n" +
                "on p.id=rs.busi_activity_project_id "+
                "WHERE rs.busi_activity_slave_id=26\n" +
                "and p.busi_activity_id=" +busi_activity_id+
                " #field# AND p.deleted=0 AND p.project_status=1 "+
                "GROUP BY rs.busi_activity_project_id,rs.create_id\n" +
                "HAVING SUM(rs.jugde_score)>0\n" +
                ") tt\n" +
                "WHERE tt.rrscreateid=u.userid\n" +
                "GROUP BY tt.rrscreateid) AS scorecount\n" +
                "FROM sys_user u\n" +
                "LEFT JOIN busi_activity_slave s ON s.busi_activity_id=" + busi_activity_id + " AND s.id=" + busi_activity_slave_id + " " +
                "LEFT JOIN busi_activity m ON m.id=s.busi_activity_id\n" +
                "WHERE FIND_IN_SET(u.userid,s.JudgesUid)";


        if (belongfield.equals("-1")) {
            sql = sql.replace("#field#", "");
        } else
            sql = sql.replace("#field#", "and find_in_set('" + belongfield + "',p.from_belongfields)");


        final List<Record> records = Db.find(sql);

        return records;

    }
}
