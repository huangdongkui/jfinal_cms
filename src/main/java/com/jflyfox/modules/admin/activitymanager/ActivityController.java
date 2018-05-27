package com.jflyfox.modules.admin.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.advicefeedback.TbAdviceFeedback;
import com.jflyfox.modules.admin.contact.TbContact;
import com.jflyfox.util.DateUtils;
import com.jflyfox.util.StrUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
                + " where deleted = 0 ");

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

    /**
     * 查询从表初赛复赛决赛。
     */
    public void busiActivitySlaveBy() {
        String busi_activity_id = getPara("busi_activity_id");
        final List<BusiActivitySlave> byWhere = BusiActivitySlave.dao.findByWhere(" where busi_activity_id=?", busi_activity_id);

        renderJson(byWhere);
    }

    public void busiActivitySlavesHomes(){
        JSONObject json = new JSONObject();
//        final List<BusiActivitySlave> byWhere = BusiActivitySlave.dao.findByWhere(" where busi_activity_id=?", );
String sql="select a.id,a.nodeid,\n" +
        "a.busi_activity_id,\n" +
        "a.from_time,a.to_time,b.activity_name\n" +
        "from busi_activity_slave a\n" +
        "left join busi_activity b on b.id=a.busi_activity_id";
        List<Record> records = Db.find(sql);
        renderJson(records);
    }

    public void delete() {
        BusiActivity model = new BusiActivity();
        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.put("update_id", userid);
        model.put("update_time", now);

        model.deletelogicByIdLog(getParaToInt());
        list();
    }

    public void add() {
       //ender(path + "add.html");
        edit();
    }

    public void save() {
        Integer pid = getParaToInt();
        BusiActivity model = getModel(BusiActivity.class);

        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.put("update_id", userid);
        model.put("update_time", now);

        String enrolldate = getPara("enrolldate");
        String firstdate = getPara("firstdate");
        String seconddate = getPara("seconddate");
        String threedate = getPara("threedate");


        Integer enrolldateid = getParaToInt("enrolldateid");
        Integer firstdateid = getParaToInt("firstdateid");
        Integer seconddateid = getParaToInt("seconddateid");
        Integer threedateid = getParaToInt("threedateid");

        if (pid != null && pid > 0) { // 更新
            if (model.updateLog()) {
                //更新赛事时间
                ActivityService.updateBusiActivitySlaveById(enrolldateid, enrolldate);
                ActivityService.updateBusiActivitySlaveById(firstdateid, firstdate);
                ActivityService.updateBusiActivitySlaveById(seconddateid, seconddate);
                ActivityService.updateBusiActivitySlaveById(threedateid, threedate);
            }

            //修改比赛时间
        } else { // 新增
            model.remove("id");
            model.put("create_id", userid);
            model.put("create_time", now);
            if (model.saveLog()) {

                String[] listDate = new String[]{enrolldate, firstdate, seconddate, threedate};

                ActivityService.addBusiActivitySlave(Integer.parseInt(model.get("id").toString()), listDate);
            }
            ;
            //添加比赛时间
        }

        renderMessage("保存成功");
    }

    public void edit() {
        BusiActivity model = BusiActivity.dao.findById(getParaToInt());
        String enrolldate = DateUtils.getNow();
        enrolldate=enrolldate+" - "+enrolldate;
        String firstdate = enrolldate;
        String seconddate = enrolldate;
        String threedate = enrolldate;

        if(model!=null) {

            final List<BusiActivitySlave> busiActivitySlaves = BusiActivitySlave.dao.findByWhere(" where busi_activity_id=? order by nodeid", model.getId());

            if (busiActivitySlaves.size() > 0) {
                enrolldate = busiActivitySlaves.get(0).get("from_time") + " - " + busiActivitySlaves.get(0).get("to_time");
                firstdate = busiActivitySlaves.get(1).get("from_time") + " - " + busiActivitySlaves.get(1).get("to_time");
                seconddate = busiActivitySlaves.get(2).get("from_time") + " - " + busiActivitySlaves.get(2).get("to_time");
                threedate = busiActivitySlaves.get(3).get("from_time") + " - " + busiActivitySlaves.get(3).get("to_time");

                setAttr("enrolldateid", busiActivitySlaves.get(0).get("id"));
                setAttr("firstdateid", busiActivitySlaves.get(1).get("id"));
                setAttr("seconddateid", busiActivitySlaves.get(2).get("id"));
                setAttr("threedateid", busiActivitySlaves.get(3).get("id"));
            }

            setAttr("model", model);
        }

        setAttr("enrolldate", enrolldate);
        setAttr("firstdate", firstdate);
        setAttr("seconddate", seconddate);
        setAttr("threedate", threedate);
        render(path + "edit.html");
    }
}
