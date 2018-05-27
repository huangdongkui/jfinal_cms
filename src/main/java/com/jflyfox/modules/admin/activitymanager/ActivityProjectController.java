package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;

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
        list();
    }

    public void list(){
        render(path + "project_list.html");
    }
}
