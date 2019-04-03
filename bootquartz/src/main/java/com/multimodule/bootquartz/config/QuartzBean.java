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
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 16:55
 * @Version: 1.0
 */
@Configuration
public class QuartzBean {

    private static final Logger lg = LoggerFactory.getLogger(QuartzBean.class);

    @Bean(name = "myCustomTask")
    public MyCustomTask getMyCustomTask(){
        return new MyCustomTask();
    }

    @Bean(name = "myTask")
    public MyTask getMyTask(){
        return new MyTask();
    }

    @Bean(name = "myList")
    public MyList getMyList(){
        MyList myList=new MyList();
        Method[] method = QuartzBean.class.getMethods();
        for (Method m : method) {
            if (!m.getName().equals("getThreadPoolTaskExecutor") && m.getName().substring(0,3).equals("get") &&
            !m.getName().equals("getMyList") && !m.getName().equals("getClass")) {
                myList.getList().add("com.multimodule.bootquartz.task." + m.getName().substring(3));
            }
        }
        return myList;
    }

    /**
     * @MethodName getThreadPoolTaskExecutor
     * @Description 自定义一个线程池，也可以使用quartz自带的
     * @param
     * @Return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/3 16:48
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor =new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(500);
        return threadPoolTaskExecutor;
    }
}
