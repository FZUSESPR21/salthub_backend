package com.team_five.salthub.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * 日志颜色配置
 *
 * @date 2021/05/06
 */
public class LogbackColorful extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:// ERROR等级为红色
                return ANSIConstants.RED_FG;
            case Level.WARN_INT:// WARN等级为黄色
                return ANSIConstants.YELLOW_FG;
            case Level.INFO_INT:// INFO等级为绿色
                return ANSIConstants.GREEN_FG;
            case Level.DEBUG_INT:// DEBUG等级为蓝色
                return ANSIConstants.BLUE_FG;
            default:// 其他为默认颜色
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
