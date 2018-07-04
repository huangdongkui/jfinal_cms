package com.jflyfox.util.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.jflyfox.system.file.util.FileUploadUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈excel导出〉
 * Created by huangdk on 2018/6/21.
 *
 * @version V1.0
 */
public class ExcelExportUtils {
    /**
     *
     * @param column
     * @param data
     * @return 路径
     */
    public static String export(List<ExcelExportEntity> column, List<Map<String, Object>> data){

        String fileName=FileUploadUtils.getBasePath()+"TempExpotFile\\统计报表.xls";
        String path  = FileUploadUtils.getRootPath()+fileName;
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),column,data);

            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }


    public static String exportMulitSheet(List<Map<String,Object>> list){

        String fileName=FileUploadUtils.getBasePath()+"TempExpotFile\\报名总表.xls";
        String path  = FileUploadUtils.getRootPath()+fileName;
        try {

            Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
