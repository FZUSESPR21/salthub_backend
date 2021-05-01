package com.team_five.salthub.util;

import org.springframework.mobile.device.Device;

/**
 * 设备工具类
 *
 * @date 2021/04/30
 */
public class DeviceUtil {
    public static String getDevice(Device device) {
        if (device.isMobile()) {
            return "mobile";
        }
        if (device.isNormal()) {
            return "PC";
        }
        if (device.isTablet()) {
            return "tablet";
        }
        return "other";
    }
}
