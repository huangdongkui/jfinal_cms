package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;

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
}
