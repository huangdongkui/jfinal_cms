package com.jflyfox.modules.admin.activitymanager;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

/**
 * 〈活动从表〉
 * Created by hdk on 2018/5/22.
 *
 * @version V1.0
 */

@ModelBind(table = "busi_activity_slave")
public class BusiActivitySlave  extends BaseProjectModel<BusiActivitySlave> {
    private static final long serialVersionUID = 1L;

    public static final BusiActivitySlave dao = new BusiActivitySlave();

    public java.lang.Integer getId() {
        return get(id);
    }

    public BusiActivitySlave setID(java.lang.Integer value) {
        set(id, value);
        return this;
    }

    public String getNodeid() {
        return get(nodeid);
    }

    public BusiActivitySlave setNodeid(String value) {
        set(nodeid, value);
        return this;
    }

    public String getBusiActivityId() {
        return get(busi_activity_id);
    }

    public BusiActivitySlave setBusiActivityId(String value) {
        set(busi_activity_id, value);
        return this;
    }

    public String getFromTime() {
        return get(from_time);
    }

    public BusiActivitySlave setFromTime(String value) {
        set(from_time, value);
        return this;
    }

    public String getToTime() {
        return get(to_time);
    }

    public BusiActivitySlave setToTime(String value) {
        set(to_time, value);
        return this;
    }

    public String getJudgesUid() {
        return JudgesUid;
    }

    public BusiActivitySlave setJudgesUid(String value) {
        set(JudgesUid,value);
        return this;
    }

    private String id="id";
    private String nodeid="nodeid";
    private String busi_activity_id="busi_activity_id";
    private String from_time="from_time";
    private String to_time="to_time";
    private String JudgesUid="id";



}
