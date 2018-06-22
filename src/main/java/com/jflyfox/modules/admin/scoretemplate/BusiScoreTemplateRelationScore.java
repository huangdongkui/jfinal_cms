package com.jflyfox.modules.admin.scoretemplate;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

/**
 * 〈活动对象〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */
@ModelBind(table = "busi_score_template_relation_score")
public class BusiScoreTemplateRelationScore extends BaseProjectModel<BusiScoreTemplateRelationScore> {
    private static final long serialVersionUID = 1L;

    public static final BusiScoreTemplateRelationScore dao = new BusiScoreTemplateRelationScore();

    // columns START
    public Integer getId() {
        return get(ID);
    }

    public BusiScoreTemplateRelationScore setID(Integer value) {
        set(ID, value);
        return this;
    }

    public BusiScoreTemplateRelationScore setBusiScoreTemplateId(Integer value) {
        set(busi_score_template_id, value);
        return this;
    }

    public Integer getBusiScoreTemplateId() {
        return get(busi_score_template_id);
    }


    public Integer getDeleted() {
        return get(deleted);
    }

    public BusiScoreTemplateRelationScore setDeleted(Integer value) {
        set(ID, value);
        return this;
    }

    public Integer getBusiActivityProjectId() {
        return get(busi_activity_project_id);
    }

    public BusiScoreTemplateRelationScore setBusiActivityProjectId(Integer value) {
        set(busi_activity_project_id, value);
        return this;
    }

    public Integer getJugdeScore() {
        return get(jugde_score);
    }

    public BusiScoreTemplateRelationScore setJugdeScore(Integer value) {
        set(jugde_score, value);
        return this;
    }


    public String getUpdateTime() {
        return get(update_time);
    }

    public BusiScoreTemplateRelationScore setUpdateTime(String value) {
        set(update_time, value);
        return this;
    }

    public String getUpdateId() {
        return get(update_id);
    }

    public BusiScoreTemplateRelationScore setUpdateId(String value) {
        set(update_id, value);
        return this;
    }

    public String getCreateTime() {
        return get(create_time);
    }

    public BusiScoreTemplateRelationScore setCreateTime(String value) {
        set(create_time, value);
        return this;
    }

    public String getCreateId() {
        return get(create_id);
    }

    public BusiScoreTemplateRelationScore setCreateId(String value) {
        set(create_id, value);
        return this;
    }

    public Integer getBusiActivitySlaveId() {
        return get(busi_activity_slave_id);
    }

    public BusiScoreTemplateRelationScore setBusiActivitySlaveId(Integer value) {
        set(busi_activity_slave_id, value);
        return this;
    }

    public String getRemarks() {
        return get(remarks);
    }

    public BusiScoreTemplateRelationScore setRemarks(String value) {
        set(remarks, value);
        return this;
    }

    private String ID = "id"; // id
    private String busi_score_template_id = "busi_score_template_id";
    private String busi_activity_slave_id = "busi_activity_slave_id";
    private String busi_activity_project_id = "busi_activity_project_id";
    private String jugde_score = "jugde_score";

    private String update_time = "update_time";
    private String update_id = "update_id";
    private String create_time = "create_time";
    private String create_id = "create_id";
    private String deleted = "deleted";
    private String remarks = "remarks";

}
