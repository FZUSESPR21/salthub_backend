package com.team_five.salthub.util;

//.zip .rar .js .css .xml .7z .ico .ppt .pptx .xap .xpi .swf .apk .cdf .gif .tar .gz .sh .bmp .json .svg
public class AttachmentUtil {
    public static boolean attachmentSuffix(String suffix) {
        switch (suffix) {
            case "zip":
            case "rar":
            case "js":
            case "css":
            case "xml":
            case "7z":
            case "ico":
            case "ppt":
            case "pptx":
            case "xap":
            case "xpi":
            case "swf":
            case "apk":
            case "cdf":
            case "gif":
            case "tar":
            case "gz":
            case "bmp":
            case "sh":
            case "json":
            case "svg":
                return true;
            default:
                return false;
        }
    }
}
