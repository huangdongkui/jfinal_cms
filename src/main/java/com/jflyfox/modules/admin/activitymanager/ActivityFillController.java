package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.system.dict.DictSvc;

import java.util.Arrays;
import java.util.List;

/**
 * 〈活动填报〉
 * Created by huangdk on 2018/5/26.
 *
 * @version V1.0
 */

@ControllerBind(controllerKey = "/admin/activityfill")
public class ActivityFillController  extends BaseProjectController {
    private static final String path = "/pages/admin/activity/activity_";
    public void index() {
        fill();
    }

    public void fill(){

//        String[] belongfieldtypes = user.get("belongfieldtype").toString().split(",");
        //String[] belongfieldtypes=new String[10];
       // List<String> listValues= Arrays.asList(belongfieldtypes);

        setAttr("belongfieldselect", new DictSvc().checkboxSysDictDetail(null,"belongfield"));



       // String[] belongfieldtypes=new String[10];
       // List<String> listValues= Arrays.asList(belongfieldtypes);

        setAttr("projecttypeselect", new DictSvc().checkboxSysDictDetail(null,"projecttype"));




//        setAttr("tech_maturitySelect", new DepartmentSvc().selectDictType(17,16));
        render(path + "fill.html");
    }

}
