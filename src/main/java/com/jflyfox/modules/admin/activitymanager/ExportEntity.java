package com.jflyfox.modules.admin.activitymanager;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈评分导出模型〉
 * Created by Administrator on 2018/7/4 0004.
 *
 * @version V1.0
 */
public class ExportEntity implements Serializable {


    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getFrom_belongfields() {
        return from_belongfields;
    }

    public void setFrom_belongfields(String from_belongfields) {
        this.from_belongfields = from_belongfields;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProject_leader() {
        return project_leader;
    }

    public void setProject_leader(String project_leader) {
        this.project_leader = project_leader;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {

        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Excel(name = "序号")
    private Integer no;
    @Excel(name = "项目名称")
    private String project_name;
    @Excel(name = "单位")
    private String departname;
    @Excel(name = "领域")
    private String from_belongfields;
    @Excel(name = "申请人")
    private String realname;
    @Excel(name = "联系方式")
    private String tel;
    @Excel(name = "负责人")
    private String project_leader;
    @Excel(name = "分数")
    private Float score;
    @Excel(name = "备注")
    private String remarks;
}
