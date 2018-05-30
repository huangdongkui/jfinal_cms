package com.jflyfox.modules.admin.file;

import com.jfinal.upload.UploadFile;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.system.file.model.SysFileUpload;
import com.jflyfox.system.file.util.FileUploadUtils;
import com.jflyfox.util.DateUtils;
import de.ruedigermoeller.serialization.dson.DsonDeserializer;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈文件上传下载〉
 * Created by huangdk on 2018/5/30.
 *
 * @version V1.0
 */
@ControllerBind(controllerKey = "/admin/file")
public class FileController extends BaseProjectController {
    public void upload() {

        String id = getParaToInt().toString();
        if(StringUtils.isEmpty(id)){
            renderMessageByFailed("表单失效无法上传");
        }

        SysFileUpload sysFileUpload = new SysFileUpload();
        final UploadFile uploadFile = getFiles().get(0);

        final String path = uploadHandler(uploadFile.getFile(), DateUtils.getNow());

        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        sysFileUpload.remove("id");
        //sysFileUpload.setBusinessId(id);
        sysFileUpload.set("business_id", id);
        sysFileUpload.setCreateId(userid);
        sysFileUpload.setCreateTime(now);

        sysFileUpload.setPath(path);
        sysFileUpload.setName(uploadFile.getFileName());
        sysFileUpload.setOriginalname(uploadFile.getOriginalFileName());
        sysFileUpload.setFactpath(uploadFile.getUploadPath());

        if (uploadFile.getFileName().indexOf(".") > 0) {
            final String[] split = uploadFile.getFileName().split("\\.");
            sysFileUpload.setExt(split[split.length - 1]);
        }
        sysFileUpload.saveLog();
        renderJson(sysFileUpload);
    }

}
