package com.jflyfox.modules.admin.scoretemplate;

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;

import java.util.List;

/**
 * 〈评分模版〉
 * Created by huangdk on 2018/5/24.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/scoretemplate")
public class ScoreTemplateController extends BaseProjectController {
    private static final String path = "/pages/admin/scoretemplate/scoretemplate_";

    public void index() {
        list();
    }

    public void list() {
        render(path + "list.html");
    }

    public void children() {
        String parentId = getPara("parentId");

        List listResults = getScoretemplateByParentId(parentId);

        renderJson(listResults);
    }

    private List getScoretemplateByParentId(String parentId) {

        String sql = "select id,scorce_contents as text,exists(select * from busi_score_template t where t.parentId=t1.id) as nodes,scorce,path\n" +
                "from busi_score_template t1\n" +
                "where t1.parentId=?";

        BusiScoreTemplate.dao.find(sql, parentId);

        List<BusiScoreTemplate> busiScoreTemplates = BusiScoreTemplate.dao.find(sql, parentId);
        return busiScoreTemplates;
    }


    public void edit() {
        final Integer paraToInt = getParaToInt();
        final BusiScoreTemplate model = BusiScoreTemplate.dao.findById(paraToInt);
        setAttr("model", model);
        render(path + "edit.html");
    }

    public void add() {
        final Integer parentId = getParaToInt("parentId");
        final String path = getPara("path");

        final BusiScoreTemplate model = new BusiScoreTemplate();

        model.set("parentId", parentId);
        model.set("path", path);

        setAttr("model", model);
        render(ScoreTemplateController.path + "edit.html");
    }

    public void save() {
        Integer pid = getParaToInt();
        BusiScoreTemplate model = getModel(BusiScoreTemplate.class);
        model.setUpdateId(getSessionUser().getUserid().toString());
        model.setUpdateTime(getNow());

        if (pid != null && pid > 0) { // 更新
            model.update();
        } else { // 新增
            model.remove("id");
            model.put("create_id", getSessionUser().getUserid());
            model.put("create_time", getNow());

            String path=model.getPath();
            if(path.startsWith("null")){//去除null
                path=path.replace("null,","");
            }
            model.setPath(path);
            model.setLevel(path.split(",").length);
            model.save();
        }
        renderMessage("保存成功");
    }

    public void delete() {
        final Integer id = getParaToInt();
        if (BusiScoreTemplate.dao.deleteByIdLog(id)) {
            renderText("删除成功");
        } else {
            renderText("删除失败");
        }
    }
}
