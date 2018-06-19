package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

/**
 * 〈活动对象〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */
@ModelBind(table = "busi_activity_project")
public class BusiActivityProject extends BaseProjectModel<BusiActivityProject> {
    private static final long serialVersionUID = 1L;

    public static final BusiActivityProject dao = new BusiActivityProject();

    // columns START
    public Integer getId() {
        return get(ID);
    }

    public BusiActivityProject setID(Integer value) {
        set(ID, value);
        return this;
    }
    public Integer getProjectStatus() {
        return get(project_status);
    }

    public BusiActivityProject setProjectStatus(Integer value) {
        set(project_status, value);
        return this;
    }

    public String  project_status="project_status";
    public String getProjectName() {
        return get(project_name);
    }

    public BusiActivityProject setProjectName(String value) {
        set(project_name, value);
        return this;
    }

    public String getProjectType() {
        return get(project_type);
    }

    public BusiActivityProject setProjectType(String value) {
        set(project_type, value);
        return this;
    }

    public String getProjectNo() {
        return get(project_no);
    }

    public BusiActivityProject setProjectNo(String value) {
        set(project_no, value);
        return this;
    }

    public String getUpdateTime() {
        return  get(update_time);
    }

    public BusiActivityProject setUpdateTime(String value) {
        set(update_time, value);
        return this;
    }

    public String getUpdateId() {
        return get(update_id);
    }

    public BusiActivityProject setUpdateId(String value) {
        set(update_id, value);
        return this;
    }

    public String getCreateTime() {
        return get(create_time);
    }

    public BusiActivityProject setCreateTime(String value) {
        set(create_time, value);
        return this;
    }

    public String getCreateId() {
        return get(create_id);
    }

    public BusiActivityProject setCreateId(String value) {
        set(create_id, value);
        return this;
    }

    public String getRemarks() {
        return get(remarks);
    }

    public BusiActivityProject setRemarks(String value) {
        set(remarks, value);
        return this;
    }
    public Integer getDeleted() {
        return get(deleted);
    }

    public BusiActivityProject setDeleted(Integer value) {
        set(ID, value);
        return this;
    }

    private String ID = "id";
    private String project_name = "project_name";//项目类型
    private String project_type = "project_type";//项目编号
    private String project_no = "project_no";
    private String from_project_name = "from_project_name";
    public String getFromProjectName() {
        return get(from_project_name);
    }

    public BusiActivityProject setFromProjectName(String value) {
        set(from_project_name, value);
        return this;
    }
    private String from_other_menbers = "from_other_menbers";
    public String getFromOtherMenbers() {
        return get(from_other_menbers);
    }

    public BusiActivityProject setFromOtherMenbers(String value) {
        set(from_other_menbers, value);
        return this;
    }
    private String from_belongfields = "from_belongfields";
    public String getFromBelongfields() {
        return get(from_belongfields);
    }

    public BusiActivityProject setFromBelongfields(String value) {
        set(from_belongfields, value);
        return this;
    }

    private String tech_maturity = "tech_maturity";
    public String getTechMaturity() {
        return get(tech_maturity);
    }

    public BusiActivityProject setTechMaturity(String value) {
        set(tech_maturity, value);
        return this;
    }

    private String core_tech = "core_tech";
    public String getCoreTech() {
        return get(core_tech);
    }

    public BusiActivityProject setCoreTech(String value) {
        set(core_tech, value);
        return this;
    }

    private String intell_right = "intell_right";
    public String getIntellRight() {
        return get(intell_right);
    }

    public BusiActivityProject setIntellRight(String value) {
        set(intell_right, value);
        return this;
    }

    private String market_competitive = "market_competitive";
    public String getMarketCompetitive() {
        return get(market_competitive);
    }

    public BusiActivityProject setMarketCompetitive(String value) {
        set(market_competitive, value);
        return this;
    }

    private String projected_returns = "projected_returns";
    public String getProjectedReturns() {
        return get(projected_returns);
    }

    public BusiActivityProject setProjectedReturns(String value) {
        set(projected_returns, value);
        return this;
    }
    private String core_tech_contents = "core_tech_contents";
    public String getCoreTechContents() {
        return get(core_tech_contents);
    }

    public BusiActivityProject setCoreTechContents(String value) {
        set(core_tech_contents, value);
        return this;
    }
    private String has_patent_count = "has_patent_count";
    public String getHasPatentCount() {
        return get(has_patent_count);
    }

    public BusiActivityProject setHasPatentCount(String value) {
        set(has_patent_count, value);
        return this;
    }
    private String apply_patent_count = "apply_patent_count";
    public String getApplyPatentCount() {
        return get(apply_patent_count);
    }

    public BusiActivityProject setApplyPatentCount(String value) {
        set(apply_patent_count, value);
        return this;
    }
    private String plan_need_money = "plan_need_money";
    public String getPlanNeedMoney() {
        return get(plan_need_money);
    }

    public BusiActivityProject setPlanNeedMoney(String value) {
        set(plan_need_money, value);
        return this;
    }
    private String project_abstract = "project_abstract";
    public String getProjectAbstract() {
        return get(project_abstract);
    }

    public BusiActivityProject setProjectAbstract(String value) {
        set(project_abstract, value);
        return this;
    }
    private String busi_activity_id="busi_activity_id";
    public String getBusiActivityId() {
        return get(busi_activity_id);
    }

    public BusiActivityProject setBusiActivityId(String value) {
        set(busi_activity_id, value);
        return this;
    }
    private String update_time = "update_time";
    private String update_id = "update_id";
    private String create_time = "create_time";
    private String create_id = "create_id";
    private String remarks = "remarks";
    private String deleted = "deleted";
    private String project_leader="project_leader";

    public String getProjectLeader() {
        return get(project_leader);
    }

    public BusiActivityProject setProjectLeader(String value) {
        set(project_leader, value);
        return this;
    }

}
