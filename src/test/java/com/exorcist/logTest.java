package com.exorcist;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logTest {

    // 创建 Logger 实例
    private static final Logger logger = LoggerFactory.getLogger(logTest.class);

    @Test
    public void someMethod() {
        logger.debug("这是一个调试级别的日志消息");
        logger.info("这是一个信息级别的日志消息");
        logger.warn("这是一个警告级别的日志消息");
        logger.error("这是一个错误级别的日志消息");

        // 记录异常堆栈信息
        try {
            // 你的代码逻辑
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
    }
}
