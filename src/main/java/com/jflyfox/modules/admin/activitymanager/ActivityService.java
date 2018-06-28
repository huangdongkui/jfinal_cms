package com.jflyfox.modules.admin.activitymanager;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jflyfox.jfinal.base.BaseService;
import com.jflyfox.system.file.model.SysFileUpload;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈BusiActivitySlave服务类〉
 * Created by huangdk on 2018/5/26.
 *
 * @version V1.0
 */
public class ActivityService extends BaseService {

    /**
     *更新赛事时间
     * @param id
     * @param stringDate
     */
    public static void updateBusiActivitySlaveById(Integer id,String stringDate){
        BusiActivitySlave busiActivitySlave = BusiActivitySlave.dao.setID(id);
        busiActivitySlave.setFromTime(stringDate.split(" - ")[0]);
        busiActivitySlave.setToTime(stringDate.split(" - ")[1]);
        busiActivitySlave.update();
    }

    /**
     * 初始化赛事
     * @param busi_activity_id
     * @param listDate
     */
    public static void addBusiActivitySlave(Integer busi_activity_id, String[] listDate) {
        List<Record> list=new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            Record record=new Record();
            record.set("nodeid",i);
            record.set("busi_activity_id",busi_activity_id);
            record.set("from_time",listDate[i].split(" - ")[0]);
            record.set("to_time",listDate[i].split(" - ")[1]);
            list.add(record);
        }

        Db.batchSave("busi_activity_slave",list,4);
    }

    /**
     * 生成li代码 文件列表
     *
     * @param
     * @return
     */
    public static String genHtmllIFilesbybusinessid(final String business_id,boolean withDelete) {

        StringBuilder sbFilelist = new StringBuilder();
        final List<SysFileUpload> listSysFileUpload = SysFileUpload.dao.findByWhere("where business_id=?", business_id);
        for (SysFileUpload sysFileUpload : listSysFileUpload) {
           String path="admin/filemanager/downloadfile/?repath="+sysFileUpload.getPath()+"&filename="+sysFileUpload.getName();
            sbFilelist.append("<li class=\"list-group-item\"><span>" + sysFileUpload.getName() + "</span><a style=\"float: right;\" target=\"_blank\" href=\"" +path + "\"> <span class=\"badge\">下载</span></a>");

            if(withDelete){
                sbFilelist.append("<a style=\"float: right;\" href=\"javascript:(0);\" onclick=\"delfile(" + sysFileUpload.getId() + ",this);return false;\"><span class=\"badge\">删除</span></a></li>");
            }
        }
        return sbFilelist.toString();
    }
}
