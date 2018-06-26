package test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.jflyfox.component.util.JFlyFoxUtils;
import com.jflyfox.modules.admin.activitymanager.BusiActivitySlave;
import com.jflyfox.system.userrole.SysUserRole;
import com.jflyfox.util.DateUtils;
import com.jflyfox.util.easypoi.ExcelExportUtils;
import com.jflyfox.util.encrypt.Md5Utils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈test〉
 * Created by huangdk on 2018/5/26.
 *
 * @version V1.0
 */
public class MainTest {
    public static void main(String[] args) {
        //System.out.println(DateUtils.getNow());

//        String value="true";
//        String checkboxName="cbx";
//        String text="技术";
//        String ff=String.format("<label class=\"checkbox-inline\">" +
//                "<input type=\"checkbox\" id=\"%s\" value=\"%s\">%s</input></label>",checkboxName,value,text);
//        System.out.println(ff);


//        try {
//            SysUserRole sysUserRole=new SysUserRole();
//            sysUserRole.set("userid","9");
//            sysUserRole.set("roleid",9);
//            sysUserRole.save();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //test();

        //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
//
////            beanList.add(new ExcelExportEntity(new ExcelExportEntity("学生姓名", "name"));
//        beanList.add(new ExcelExportEntity("学生性别", "sex"));
//        beanList.add(new ExcelExportEntity("进校日期", "registrationDate"));
//        //if(needBirthday()){
//        beanList .add(new ExcelExportEntity("出生日期", "birthday"));
//        // }
//        Map<String, Object> map=new HashMap<>();
//        map.put("sex","男");
//        map.put("registrationDate","2018-6-21");
//
//        list.add(map);
//
//        System.out.println(ExcelExportUtils.export(beanList, list));

        //final String s = JFlyFoxUtils.passwordDecrypt("fKwKfTnr+qE=");
        System.out.println(JFlyFoxUtils.passwordEncrypt("gddw@1234"));
        // new Md5Utils().getMD5(decryptPassword).toLowerCase();
    }

    public static void test() {
        try {

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();

//            beanList.add(new ExcelExportEntity(new ExcelExportEntity("学生姓名", "name"));
            beanList.add(new ExcelExportEntity("学生性别", "sex"));
            beanList.add(new ExcelExportEntity("进校日期", "registrationDate"));
            //if(needBirthday()){
            beanList .add(new ExcelExportEntity("出生日期", "birthday"));
            // }
            Map<String, Object> map=new HashMap<>();
            map.put("sex","男");
            map.put("registrationDate","2018-6-21");

            list.add(map);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList ,
                    list);

            FileOutputStream fos = new FileOutputStream("D:/excel/ExcelExportForMap.xls");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
