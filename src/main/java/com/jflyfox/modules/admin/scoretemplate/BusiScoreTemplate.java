package com.jflyfox.modules.admin.scoretemplate;

import com.alibaba.fastjson.annotation.JSONField;
import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 〈活动对象〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */
@ModelBind(table = "busi_score_template")
public class BusiScoreTemplate extends BaseProjectModel<BusiScoreTemplate> {
    private static final long serialVersionUID = 1L;

    public static final BusiScoreTemplate dao = new BusiScoreTemplate();

    // columns START
    public Integer getId() {
        return get(ID);
    }
    public BusiScoreTemplate setID(Integer value) {
        set(ID, value);
        return this;
    }

    public BusiScoreTemplate setParentId(Integer value) {
        set(ID, value);
        return this;
    }

    public Integer getParentId() {
        return get(parentId);
    }


    public Integer getDeleted() {
        return get(deleted);
    }

    public BusiScoreTemplate setDeleted(Integer value) {
        set(ID, value);
        return this;
    }
    public String getScorceContents() {
        return get(scorce_contents);
    }

    public BusiScoreTemplate setScorceContents(String value) {
        set(scorce_contents, value);
        return this;
    }

    public String getScorce() {
        return get(scorce);
    }

    public BusiScoreTemplate setScorce(String value) {
        set(scorce, value);
        return this;
    }

    public String getScoretplId() {
        return get(scoretpl_id);
    }

    public BusiScoreTemplate setScoretplId(String value) {
        set(scoretpl_id, value);
        return this;
    }

    public String getUpdateTime() {
        return  get(update_time);
    }

    public BusiScoreTemplate setUpdateTime(String value) {
        set(update_time, value);
        return this;
    }

    public String getUpdateId() {
        return get(update_id);
    }

    public BusiScoreTemplate setUpdateId(String value) {
        set(update_id, value);
        return this;
    }

    public String getCreateTime() {
        return get(create_time);
    }

    public BusiScoreTemplate setCreateTime(String value) {
        set(create_time, value);
        return this;
    }

    public String getCreateId() {
        return get(create_id);
    }

    public BusiScoreTemplate setCreateId(String value) {
        set(create_id, value);
        return this;
    }

    private String ID = "id"; // id
    private String parentId = "parentId";
    private String scorce_contents = "scorce_contents";
    private String scorce = "scorce";
    private String scoretpl_id = "scoretpl_id";
    private String update_time = "update_time";
    private String update_id = "update_id";
    private String create_time = "create_time";
    private String create_id = "create_id";
    private String deleted = "deleted";


    @Override
    public String toString() {
        return super.toString().replaceAll("#","[]");
    }
}
