package org.ohosdev.hapviewerweb.model;

import cn.hutool.json.JSONArray;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hap安装包信息
 * @author westinyang
 */
public class HapInfo {
    /* app. */
    public Image icon;
    public String iconPath;
    public String iconBase64;
    public byte[] iconBytes;
    public String labelName;
    public String appName;
    public String packageName;
    public String versionName;
    public String versionCode;
    public String vendor;
    public String minAPIVersion;
    public String targetAPIVersion;
    public String apiReleaseType;

    /* module. */
    public String mainElement;
    public JSONArray requestPermissions;
    public List<String> requestPermissionNames;

    /* more */
    public Map<String, Object> moreInfo;

    /* 额外 */
    public String hapFilePath;
    public Set<String> techList;
    private String techDesc;

    public String getTechDesc() {
        if (techList != null && techList.size() > 0) {
            techDesc = String.join("、", techList);
        } else {
            techDesc = "原生开发或未知开发技术";
        }
        return techDesc;
    }

    public void setTechDesc(String techDesc) {
        this.techDesc = techDesc;
    }
}
