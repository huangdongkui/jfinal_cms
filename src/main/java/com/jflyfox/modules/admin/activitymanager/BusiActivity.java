package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;
import com.jflyfox.modules.admin.scoretemplate.BusiScoreTemplate;

/**
 * 〈活动对象〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */
@ModelBind(table = "busi_activity")
public class BusiActivity extends BaseProjectModel<BusiActivity> {
    private static final long serialVersionUID = 1L;

    public static final BusiActivity dao = new BusiActivity();

    // columns START
    public java.lang.Integer getId() {
        return get(ID);
    }

    public BusiActivity setID(java.lang.Integer value) {
        set(ID, value);
        return this;
    }

    public String getActivityName() {
        return get(activity_name);
    }

    public BusiActivity setActivityName(String value) {
        set(activity_name, value);
        return this;
    }

    public String getActivityStatus() {
        return get(activity_status);
    }

    public BusiActivity setActivityStatus(String value) {
        set(activity_status, value);
        return this;
    }


    public String getUpdateTime() {
        return  get(update_time);
    }

    public BusiActivity setUpdateTime(String value) {
        set(update_time, value);
        return this;
    }

    public String getUpdateId() {
        return get(update_id);
    }

    public BusiActivity setUpdateId(String value) {
        set(update_id, value);
        return this;
    }

    public String getCreateTime() {
        return get(create_time);
    }

    public BusiActivity setCreateTime(String value) {
        set(create_time, value);
        return this;
    }

    public String getCreateId() {
        return get(create_id);
    }

    public BusiActivity setCreateId(String value) {
        set(create_id, value);
        return this;
    }

    public String getRemarks() {
        return get(remarks);
    }

    public BusiActivity setRemarks(String value) {
        set(remarks, value);
        return this;
    }
    public String getBusiScoreTemplateId() {
        return get(busi_score_template_id);
    }

    public BusiActivity setBusiScoreTemplateId(String value) {
        set(busi_score_template_id, value);
        return this;
    }

    private String ID = "id"; // id
    private String activity_name = "activity_name";
    private String activity_status = "activity_status";
    private String busi_score_template_id = "busi_score_template_id";
    private String update_time = "update_time";
    private String update_id = "update_id";
    private String create_time = "create_time";
    private String create_id = "create_id";
    private String remarks = "remarks";


}
