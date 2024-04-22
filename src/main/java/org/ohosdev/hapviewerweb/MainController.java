package org.ohosdev.hapviewerweb;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.ohosdev.hapviewerweb.common.MD5Util;
import org.ohosdev.hapviewerweb.model.HapInfo;
import org.ohosdev.hapviewerweb.util.HapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.ohosdev.hapviewerweb.common.ApiResult;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;

/**
 * Main Controller
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    // 根目录
    public static final String ROOT_DIR = new File("").getAbsolutePath();
    // 文件上传目录
    public static final String UPLOAD_DIR = ROOT_DIR + File.separator + "uploads";

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        request.setAttribute("QQGroupNum", "528608689");
        request.setAttribute("WeChatNum", "westin1024");
        request.setAttribute("QQGroupQRCode", "/assets/img/qq-group.png");
        return "index";
    }

    @ResponseBody
    @PostMapping("/upload")
    public Object upload(MultipartFile file) {
        // 如果不存在则创建
        FileUtil.mkdir(UPLOAD_DIR);
        // 临时文件名 MD5(UUID + 时间戳)
        var fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
        fileName = MD5Util.md5(fileName) + ".hap";
        // 临时路径
        String tmpFilePath = UPLOAD_DIR + File.separator + fileName;

        try {
            // 保存文件
            FileUtil.writeFromStream(file.getInputStream(), tmpFilePath);

            // 解析App
            HapInfo hapInfo = HapUtil.parse(tmpFilePath);
            return ApiResult.success(hapInfo);
        } catch (Exception e) {
            // e.printStackTrace();
            return ApiResult.failure("hap文件解析失败，目前仅支持解析 API9+ (Stage模型) 的应用安装包");
        } finally {
            if (FileUtil.exist(tmpFilePath)) {
                try {
                    FileUtil.del(tmpFilePath);
                } catch (Exception ignored) {
                }
            }
        }
    }

}
