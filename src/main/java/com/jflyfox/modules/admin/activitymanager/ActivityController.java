package com.jflyfox.modules.admin.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.util.StrUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈活动〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/activity")
public class ActivityController extends BaseProjectController {

    private static final String path = "/pages/admin/activity/activity_";

    public void index() {
        list();
    }

    public void list() {
        SQLUtils sql = new SQLUtils(" from busi_activity t "
                + " where 1 = 1 ");

            sql.setAlias("t");

        // 排序
        String orderBy = getBaseForm().getOrderBy();
        if (StrUtils.isEmpty(orderBy)) {
            sql.append(" order by t.create_time desc ");
        } else {
            sql.append(" order by t.").append(orderBy);
        }

       Page<BusiActivity> page = BusiActivity.dao.paginate(getPaginator(), "select t.* ",
              sql.toString().toString());

        setAttr("page", page);

        render(path + "list.html");
    }


    public void busiActivitySlaveBy(){

        String busi_activity_id=getPara("busi_activity_id");
        JSONObject json = new JSONObject();
       // SQLUtils sql = new SQLUtils(" from busi_activity_slave " ////
             //   + " where 1 = 1 ");
       // sql.whereEquals("busi_activity_id","5");
        final List<BusiActivitySlave> byWhere = BusiActivitySlave.dao.findByWhere(" where busi_activity_id=?", busi_activity_id);
        //json.put()
        //String jsons = "{\"total\":" + byWhere.size() + ",\"rows\":" + byWhere.toString() + "}";
       // json.put("total",byWhere.size());
       // json.put("rows",byWhere);
        renderJson(byWhere);
    }
}
