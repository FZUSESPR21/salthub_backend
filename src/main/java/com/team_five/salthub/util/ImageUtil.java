package com.team_five.salthub.util;

import cn.hutool.core.img.ImgUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理
 *
 * @date 2021/05/07
 */
public class ImageUtil {
    public static boolean isLegal(File image) throws IOException {
        Image img = ImageIO.read(image);
        return img != null && img.getWidth(null) > 0 && img.getHeight(null) > 0;
    }

    public static void processing(File image) {
        ImgUtil.pressText(image, image, "Salt Hub", Color.WHITE, null, 0, 0, 0.0F);
    }

    public static boolean imageSuffix(String suffix) {
        switch (suffix) {
            case "jpg":
            case "png":
            case "jpeg":
                return true;
            default:
                return false;
        }
    }
}
