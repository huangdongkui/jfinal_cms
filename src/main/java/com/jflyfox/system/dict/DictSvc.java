package com.jflyfox.system.dict;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfinal.template.ext.directive.Str;
import com.jflyfox.jfinal.base.BaseService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.beetl.ext.fn.ArrayUtil;

/**
 * 数据字典service
 *
 * @author flyfox 2014-2-11
 */
public class DictSvc extends BaseService {

    /**
     * 通过Key获取数据字典名称
     *
     * @param key
     * @return
     * @author flyfox 2013-11-19
     */
    public static SysDictDetail getDictDetail(String key) {
        return DictCache.getCacheMap().get(key);
    }

    /**
     * 通过Key获取数据字典名称
     *
     * @param key
     * @return
     * @author flyfox 2013-11-19
     */
    public static String getDictName(String key) {
        SysDictDetail detail = DictCache.getCacheMap().get(key);
        return detail == null ? null : detail.getStr("detail_name");
    }

    public String selectDictType(String selected) {
        List<SysDict> list = new ArrayList<SysDict>();
        list = SysDict.dao.find("select * from sys_dict");
        StringBuffer sb = new StringBuffer();
        for (SysDict dict : list) {
            sb.append("<option value=\"");
            sb.append(dict.getStr("dict_type"));
            sb.append("\" ");
            sb.append(dict.getStr("dict_type").equals(selected) ? "selected" : "");
            sb.append(">");
            sb.append(dict.getStr("dict_name"));
            sb.append("</option>");
        }
        return sb.toString();
    }

    public String selectDictDetailType(String selectDictDetailCode, String dictType) {
        List<SysDictDetail> list = new ArrayList<SysDictDetail>();
        list = SysDictDetail.dao.find("select * from sys_dict_detail where dict_type='" + dictType+"'");
        StringBuffer sb = new StringBuffer();
        for (SysDictDetail dict : list) {
            sb.append("<option value=\"");
            sb.append(dict.getStr("detail_code"));
            sb.append("\" ");
            sb.append(dict.getStr("detail_code").equals(selectDictDetailCode) ? "selected" : "");
            sb.append(">");
            sb.append(dict.getStr("detail_name"));
            sb.append("</option>");
        }
        return sb.toString();
    }

    public void updateDetail(SysDictDetail model) {
        model.update();
        DictCache.initDict();

    }

    public void addDetail(SysDictDetail model) {
        model.save();
        DictCache.initDict();
    }

    public void deleteDetail(SysDictDetail model) {
        model.deleteById(model.getInt("detail_id"));
        DictCache.initDict();
    }

    public String checkboxSysDictDetail(String selectedValues, String dictType) {

        List<String> selectedIds=new ArrayList<>();
        if(StringUtils.isNotBlank(selectedValues)){
            String[] belongfieldtypes = selectedValues.split(",");
            selectedIds= Arrays.asList(belongfieldtypes);
        }

        List<SysDictDetail> list = new ArrayList<SysDictDetail>();
        list = SysDictDetail.dao.find("select * from sys_dict_detail where dict_type='" + dictType+"'");
        StringBuffer sb = new StringBuffer();
        for (SysDictDetail dict : list) {

            String value = dict.getStr("detail_code");
            String ischecked="";
            if (selectedIds!=null&&selectedIds.contains(dict.getStr("detail_code"))) {
                ischecked="checked=\"checked\"";
            }

            String checkboxName = "cbx" + dict.getInt("detail_id").toString();
            String text = dict.getStr("detail_name");

            sb.append(String.format("<label class=\"checkbox-inline\">" +
                    "<input type=\"checkbox\" id=\"%s\" value=\"%s\"     %s>%s</input></label>", checkboxName, value,ischecked,text));
        }
        return sb.toString();
    }
}
