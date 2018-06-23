package com.jflyfox.modules.admin.activitymanager;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.SessionUser;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.scoretemplate.BusiScoreTemplate;
import com.jflyfox.modules.admin.scoretemplate.BusiScoreTemplateRelationScore;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.department.SysDepartment;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.util.StrUtils;
import com.jflyfox.util.easypoi.ExcelExportUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        setAttr("busi_activity_id", busi_activity_id);
        setAttr("nowUser", getSessionUser());
        render(path + "project_list.html");
    }

    public void list() {

        final String busi_activity_id = getPara("busi_activity_id");

        String sql = "select a.id,a.project_name,b.realname,c.name as departname,99 as score,a.project_status\n" +
                "from busi_activity_project a\n" +
                "left join sys_user b on a.create_id=b.userid\n" +
                "left join sys_department c on b.departid=c.id\n" +
                "where a.deleted=0 and a.project_status=1 and a.busi_activity_id = " + busi_activity_id;
        List<Record> records = Db.find(sql);

        renderJson(records);

    }

    public void edit() {

        final SessionUser user = getSessionUser();
        Integer pid = getParaToInt();
        setAttr("filelist", ActivityService.genHtmllIFilesbybusinessid(pid.toString(), false));

        BusiActivityProject model = BusiActivityProject.dao.findById(pid);
        final SysUser sysUser = SysUser.dao.findById(model.getInt("create_id"));
        final SysDepartment sysDepartment = SysDepartment.dao.findById(sysUser.get("departid", "0"));
        setAttr("createname", sysUser.get("realname"));
        setAttr("createname", sysUser.get("realname"));
        setAttr("department", sysDepartment.getName());
        setAttr("model", model);

        setAttr("secendType", GenHtmlButtion(model.get("busi_activity_id").toString()));

        //从数据库中获取模版ID和赛事ID

        String selectSql = "select b.busi_score_template_id,\n" +
                " ( select c.id from busi_activity_slave c \n" +
                " where c.busi_activity_id=b.id and from_time<=now() and now()<to_time) as busi_activity_slave_id\n" +
                "from busi_activity_project a\n" +
                "left join busi_activity b on a.busi_activity_id=b.id\n" +
                " where a.id=?";

        final Record first = Db.findFirst(selectSql, pid);
        setAttr("busi_score_template_id",first.get("busi_score_template_id"));
        setAttr("busi_activity_slave_id",first.get("busi_activity_slave_id"));

        render(path + "project_jugde.html");
    }

    private String GenHtmlButtion(String busiActivityid) {
        String btnHtml = "<button type=\"button\" class=\"btn btn-info btn-second\" value=\"%s\">%s</button>";
        BusiActivity busiActivity = BusiActivity.dao.findById(busiActivityid);
        final Object busi_score_template_id = busiActivity.get("busi_score_template_id");

        final List<BusiScoreTemplate> byWhereList = BusiScoreTemplate.dao.findByWhere(" where parentId=?", busi_score_template_id);
        StringBuilder sb = new StringBuilder();

        for (BusiScoreTemplate busiScoreTemplate : byWhereList) {
            sb.append(String.format(btnHtml, busiScoreTemplate.getPath() + "," + busiScoreTemplate.getId(), busiScoreTemplate.getScorceContents()));
        }

        return sb.toString();
    }

    /**
     * 评价列表
     */
    public void scorelist() {

        String path = getPara("path");
        String userid = getSessionUser().getUserid().toString();
        String projectId = getPara("projectId");

        String sql = "select  a.*,\n" +
                " (select b.scorce_contents\n" +
                "  from busi_score_template b \n" +
                "  where b.id=a.parentId) as pre_node, " +
                "   IFNULL(b.jugde_score,0) as hasscore,b.id as busi_score_template_relation_score_id  " +
                " from busi_score_template a\n" +
                " left join busi_score_template_relation_score b \n" +
                " on b.busi_score_template_id=a.id and b.busi_activity_project_id=" + projectId + " and b.create_id=" + userid +
                " where LOCATE('" + path + "',a.path)=1\n" +
                "and length(a.path)>length('" + path + "') and level=3";

        List<Record> records = Db.find(sql);
        renderJson(records);
    }

    /**
     * 保存分数
     */
    public void save() {
        final String score_id = getPara("score_id");
        final String jugdescore = getPara("jugdescore");
        final Integer userid = getSessionUser().getUserid();
        String now = getNow();
        BusiScoreTemplateRelationScore model = new BusiScoreTemplateRelationScore();

        model.put("update_id", userid);
        model.put("update_time", now);
        //修改
        if (StringUtils.isNotBlank(score_id)) {
            model.setID(Integer.parseInt(score_id));
            model.setJugdeScore(Integer.parseInt(jugdescore));
            model.updateLog();
        }

        renderNull();
    }

    /**
     * 初始化评分表
     */
    public void initbBusiScoreTemplateRelationScore() {
        final String projectId = getPara("projectId");
        Integer busi_score_template_id = getParaToInt("busi_score_template_id");
        Integer busi_activity_slave_id = getParaToInt("busi_activity_slave_id");
        final Integer userid = getSessionUser().getUserid();
        String now = getNow();

        //获取模版评分项
        final List<BusiScoreTemplate> byWhere = BusiScoreTemplate.dao.findByWhere("where path like '" + busi_score_template_id + ",%' ");

        //判断是否需要初始化
        final BusiScoreTemplateRelationScore firstByWhere = BusiScoreTemplateRelationScore.dao.findFirstByWhere(" where busi_score_template_id=?  and busi_activity_slave_id=? and busi_activity_project_id=? and create_id=?", byWhere.get(0).getId(), busi_activity_slave_id, projectId, userid);
        if (firstByWhere == null) {

            List<BusiScoreTemplateRelationScore> list = new ArrayList<BusiScoreTemplateRelationScore>();


            for (BusiScoreTemplate busiScoreTemplate : byWhere) {
                BusiScoreTemplateRelationScore model = new BusiScoreTemplateRelationScore();
                model.put("create_id", userid);
                model.put("create_time", now);
                model.setBusiActivityProjectId(Integer.parseInt(projectId));
                model.setBusiActivitySlaveId(busi_activity_slave_id);
                model.setBusiScoreTemplateId(busiScoreTemplate.getId());
                model.setJugdeScore(0);
                list.add(model);
            }


            Db.batchSave(list, list.size());

        }


        renderNull();
    }

    /**
     * 获取总分
     */
    public void getTotalScore() {
        final String projectId = getPara("projectId");
        final String busi_activity_slave_id = getPara("busi_activity_slave_id");
        final Integer userid = getSessionUser().getUserid();

        String sql = "select sum(jugde_score) as jugde_score from busi_score_template_relation_score\n" +
                "where busi_activity_project_id=" + projectId + " and  busi_activity_slave_id=" + busi_activity_slave_id + " and create_id=" + userid;

        Record records = Db.findFirst(sql);
        renderText(records.get("jugde_score").toString());
    }

    /**
     * 报名管理
     */
    public void mnglist() {

        String departid = getPara("departid");
        if (departid == null) {
            departid = "-1";
        }
        SQLUtils sql = new SQLUtils(" from busi_activity_project t " +
                "left join busi_activity ba on ba.id=t.busi_activity_id\n" +
                "left join sys_user u on u.userid=t.create_id\n" +
                "left join sys_department d on d.id=u.departid\n" +
                " where t.deleted = 0 and t.project_status=1");

        if (StringUtils.isNotBlank(departid) && !departid.equals("-1")) {
            sql.whereEquals("departid", departid);
        }

        sql.setAlias("t");

        // 排序
        String orderBy = getBaseForm().getOrderBy();
        if (StrUtils.isEmpty(orderBy)) {
            sql.append(" order by t.create_time desc ");
        } else {
            sql.append(" order by t.").append(orderBy);
        }

        Page<BusiActivity> page = BusiActivity.dao.paginate(getPaginator(), "select t.id, t.create_time,activity_name,u.tel,u.realname,d.name as departname,t.project_name,t.from_belongfields ",
                sql.toString().toString());

        setAttr("page", page);
        //部门字典
        setAttr("departSelect", new DepartmentSvc().selectDepartByParentId(departid.equals("-1") ? 0 : Integer.parseInt(departid), 10));

        render(path + "project_mgnlist.html");

    }

    /**
     * 导出excel
     */
    public void exportExcel() {
        String departid = getPara("departid");
        if (departid == null) {
            departid = "-1";
        }
        SQLUtils sql = new SQLUtils(" from busi_activity_project t " +
                "left join busi_activity ba on ba.id=t.busi_activity_id\n" +
                "left join sys_user u on u.userid=t.create_id\n" +
                "left join sys_department d on d.id=u.departid\n" +
                " where t.deleted = 0 and t.project_status=1");

        if (StringUtils.isNotBlank(departid) && !departid.equals("-1")) {
            sql.whereEquals("departid", departid);
        }

        sql.setAlias("t");

        // 排序
        String orderBy = getBaseForm().getOrderBy();
        if (StrUtils.isEmpty(orderBy)) {
            sql.append(" order by t.create_time desc ");
        } else {
            sql.append(" order by t.").append(orderBy);
        }

        String selectFields = "select t.id, t.create_time,activity_name,u.tel,u.realname,d.name as departname," +
                "t.project_name, (select group_concat(s.detail_name) from sys_dict_detail s\n" +
                " where s.dict_type='belongfield' \n" +
                " and FIND_IN_SET(s.detail_code,t.from_belongfields)) as from_belongfields ";

        final List<BusiActivity> byWhere = BusiActivity.dao.find(selectFields + sql.toString());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();

        beanList.add(new ExcelExportEntity("序号", "no"));
        beanList.add(new ExcelExportEntity("项目名称", "project_name"));
        beanList.add(new ExcelExportEntity("单位", "departname"));
        beanList.add(new ExcelExportEntity("领域", "from_belongfields"));
        beanList.add(new ExcelExportEntity("申请人", "realname"));
        beanList.add(new ExcelExportEntity("联系方式", "tel"));
        beanList.add(new ExcelExportEntity("负责人", "project_leader"));
        beanList.add(new ExcelExportEntity("分数", "score"));
        beanList.add(new ExcelExportEntity("备注", "remarks"));

        // }

        int i = 1;
        for (BusiActivity busiActivity : byWhere) {
            Map<String, Object> map = new HashMap<>();
            map.put("no", i++);
            map.put("project_name", busiActivity.get("project_name"));
            map.put("departname", busiActivity.get("departname"));
            map.put("from_belongfields", busiActivity.get("from_belongfields"));
            map.put("realname", busiActivity.get("realname"));
            map.put("tel", busiActivity.get("tel"));
            map.put("project_leader", busiActivity.get("project_leader"));
            map.put("score", 0);
            map.put("remarks", busiActivity.get("remarks"));
            list.add(map);
        }

        renderText(ExcelExportUtils.export(beanList, list));
    }


    public void delete() {
        final String id = getPara("id");

        BusiActivityProject model = new BusiActivityProject();
        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.put("update_id", userid);
        model.put("update_time", now);
        model.deletelogicByIdLog(id);
        redirect("/admin/activityproject/mnglist");
    }
}
