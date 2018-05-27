package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;

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

        render(path + "fill.html");
    }

}
