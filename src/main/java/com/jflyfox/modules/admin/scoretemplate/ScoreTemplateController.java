package com.jflyfox.modules.admin.scoretemplate;

import com.jfinal.template.ext.directive.Str;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import jdk.nashorn.internal.ir.ReturnNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void children(){
        String parentId=getPara("parentId");



        List listResults=getScoretemplateByParentId(parentId);

      //  List listResults=new ArrayList();
     //   listResults.add(hmResult);

        renderJson(listResults);
    }

    private List getScoretemplateByParentId(String parentId) {
      //  List listResults=new ArrayList();
     //   List<BusiScoreTemplate> busiScoreTemplates = BusiScoreTemplate.dao.findByWhere("parentId");
        List<BusiScoreTemplate> busiScoreTemplates= BusiScoreTemplate.dao.findByWhereAndColumns(" where parentId="+parentId,"id,parentId,scorce_contents as text,'[]' as notes");

        return busiScoreTemplates;
    }


}
