package com.multimodule.bootquartz.config;

import com.multimodule.bootquartz.task.MyCustomTask;
import com.multimodule.bootquartz.task.MyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: QuartzBean
 * @Description: 所有的任务配置bean都在这
 * @Author: FangKun
 * @Date: Created in 2019/4/3 16:55
 * @Version: 1.0
 */
@Configuration
public class QuartzBean {

    @Bean(name = "myCustomTask")
    public MyCustomTask getMyCustomTask(){
        return new MyCustomTask();
    }

    @Bean(name = "myTask")
    public MyTask getMyTask(){
        return new MyTask();
    }

}
