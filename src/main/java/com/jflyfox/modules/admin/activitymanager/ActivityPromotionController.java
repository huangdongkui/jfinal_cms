package com.jflyfox.modules.admin.activitymanager;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.Paginator;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.util.StrUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈晋级〉
 * Created by huangdk on 2018/7/6.
 *
 * @version V1.0
 */
@ControllerBind(controllerKey = "/admin/activitypromotion")
public class ActivityPromotionController extends BaseProjectController {

    private static final String path = "/pages/admin/activity/";

    public void index() {
        render(path + "activity_promotion_list.html");
    }

    public void list() {
        String busi_activity_id = getPara("busi_activity_id");
        String busi_activity_save_id = getPara("busi_activity_save_id");
        String belongfield = getPara("belongfield");

        if (busi_activity_id == null) {
            busi_activity_id = "-1";
        }
        SQLUtils sql = new SQLUtils(" from busi_activity_project t " +
                "left join busi_activity ba on ba.id=t.busi_activity_id\n" +
                "left join busi_activity_slave s on s.busi_activity_id=ba.id " +
                "left join busi_actitity_promotion p on p.busi_activity_save_id=s.id and p.busi_activity_project_id=t.id " +
                " where t.deleted = 0 and t.project_status=1 ");
        if (StringUtils.isNotBlank(belongfield) && !belongfield.equals("-1")) {
            sql.append(" and find_in_set('" + belongfield + "',t.from_belongfields) ");
            setAttr("belongfield", belongfield);
        }
        if (StringUtils.isNotBlank(busi_activity_id) && !busi_activity_id.equals("-1")) {
            sql.whereEquals("t.busi_activity_id", busi_activity_id);
        }
        if (StringUtils.isNotBlank(busi_activity_save_id)) {
            sql.whereEquals("s.id", busi_activity_save_id);
        }

        sql.setAlias("t");

        // 排序
//        String orderBy = getBaseForm().getOrderBy();
//        if (StrUtils.isEmpty(orderBy)) {
//            sql.append(" order by t.create_time desc ");
//        } else {
        // sql.append(" order by scored desc");
//        }
        Paginator paginator = getPaginator();

        paginator.setPageSize(1000);
        Page<BusiActivity> page = BusiActivity.dao.paginate(paginator, "select t.id, t.create_time,activity_name,t.project_name,t.from_belongfields, "
                        + "(select ROUND(avg(tt.jugde_score),2) from (select sum(d.jugde_score) as jugde_score,d.busi_activity_project_id " +
                        " from busi_score_template_relation_score d " +
                        " group by d.create_id,d.busi_activity_project_id) tt " +
                        " where tt.busi_activity_project_id=t.id) " +
                        " as scored,t.project_leader,(select detail_name from sys_dict_detail where dict_type='activitystage' and detail_code=s.nodeid) as nodename," +
                        "p.id as promotionid,s.id as save_id,t.id as projectid ",
                sql.toString().toString());

        // setAttr("page", page);
        renderJson(page.getList());
    }

    /**
     * 晋级
     */
    public void pass() {
        String promotionid = getPara("promotionid");
        String save_id = getPara("save_id");
        String projectid = getPara("projectid");
        Boolean success = false;

        if (StrUtils.isEmpty(promotionid.trim())) {//晋级
            Record record = new Record();
            record.set("busi_activity_save_id", save_id);
            record.set("busi_activity_project_id", projectid);
            record.set("create_id", getSessionUser().getUserid());
            success = Db.save("busi_actitity_promotion", record);
        } else {//不能晋级
            success = Db.deleteById("busi_actitity_promotion", promotionid);
        }

        renderText(success.toString());
    }
}
