package com.jflyfox.modules.admin.activitymanager;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.SessionUser;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.scoretemplate.BusiScoreTemplate;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.department.SysDepartment;
import com.jflyfox.system.user.SysUser;
import com.jflyfox.util.StrUtils;
import org.apache.commons.lang.StringUtils;

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
        setAttr("nowUser",getSessionUser());
        render(path + "project_list.html");
    }

    public void list() {

        final String busi_activity_id = getPara("busi_activity_id");

        String sql="select a.id,a.project_name,b.realname,c.name as departname,99 as score,a.project_status\n" +
                "from busi_activity_project a\n" +
                "left join sys_user b on a.create_id=b.userid\n" +
                "left join sys_department c on b.departid=c.id\n" +
                "where a.deleted=0 and a.project_status=1 and a.busi_activity_id = "+busi_activity_id;
        List<Record> records = Db.find(sql);

        renderJson(records);

    }

    public void edit(){

        final SessionUser user = getSessionUser();
        Integer pid = getParaToInt();
        setAttr("filelist", ActivityService.genHtmllIFilesbybusinessid(pid.toString(),false));

        BusiActivityProject model = BusiActivityProject.dao.findById(pid);
        final SysUser sysUser = SysUser.dao.findById(model.getInt("create_id"));
        final SysDepartment sysDepartment = SysDepartment.dao.findById(sysUser.get("departid", "0"));setAttr("createname",sysUser.get("realname"));
        setAttr("createname",sysUser.get("realname"));
        setAttr("department",sysDepartment.getName());
        setAttr("model",model);

        setAttr("secendType",GenHtmlButtion(model.get("busi_activity_id").toString()));
        render(path + "project_jugde.html");
    }

    private String GenHtmlButtion(String busiActivityid){
        String btnHtml="<button type=\"button\" class=\"btn btn-info btn-second\" value=\"%s\">%s</button>";
        BusiActivity busiActivity=BusiActivity.dao.findById(busiActivityid);
        final Object busi_score_template_id = busiActivity.get("busi_score_template_id");

        final List<BusiScoreTemplate> byWhereList = BusiScoreTemplate.dao.findByWhere(" where parentId=?", busi_score_template_id);
        StringBuilder sb=new StringBuilder();

        for (BusiScoreTemplate busiScoreTemplate : byWhereList) {
            sb.append(String.format(btnHtml,busiScoreTemplate.getPath()+","+busiScoreTemplate.getId(),busiScoreTemplate.getScorceContents()));
        }

        return sb.toString();
    }
    /**
     * 评价列表
     */
    public void scorelist(){

        String path=getPara("path");

        String sql="select  a.*,\n" +
                " (select b.scorce_contents\n" +
                "  from busi_score_template b \n" +
                "  where b.id=a.parentId) as pre_node, \n" +
                "   0 as jugde_score  "+
                " from busi_score_template a\n" +
                "where LOCATE('"+path+"',a.path)=1\n" +
                "and length(a.path)>length('"+path+"') and level=3";

        List<Record> records = Db.find(sql);
        renderJson(records);
    }
    public void save(){

    }

    /**
     * 报名管理
     */
    public void mnglist(){

        String departid=getPara("departid");

        SQLUtils sql = new SQLUtils(" from busi_activity_project t "+
                "left join busi_activity ba on ba.id=t.busi_activity_id\n" +
                "left join sys_user u on u.userid=t.create_id\n" +
                "left join sys_department d on d.id=u.departid\n" +
                " where t.deleted = 0 and t.project_status=1");

        if(StringUtils.isNotBlank(departid)){
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
        setAttr("departSelect", new DepartmentSvc().selectDepartByParentId(departid==null?0:Integer.parseInt(departid),10));

        render(path + "project_mgnlist.html");

    }

    public void delete(){
        final String id =getPara("id");

        BusiActivityProject model = new BusiActivityProject();
        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.put("update_id", userid);
        model.put("update_time", now);
        model.deletelogicByIdLog(id);
        redirect("/admin/activityproject/mnglist");
    }
}
