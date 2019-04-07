package com.multimodule.bootquartz.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: MyQuartzOfListConfigBean
 * @Description: 执行多个触发器的类
 * @Author: FangKun
 * @Date: Created in 2019/4/7 14:12
 * @Version: 1.0
 */
@Configuration
public class MyQuartzOfListConfigBean {

    /**
     * @MethodName getMyMethodInvokingJobDetailFactoryBean
     * @Description 任务调度集合
     * @param myList
     * @Return com.multimodule.bootquartz.config.MyMethodInvokingJobDetailFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 13:13
     */
    @Bean(name = "myMethodInvokingJobDetailFactoryBean")
    public MyMethodInvokingJobDetailFactoryBean getMyMethodInvokingJobDetailFactoryBean(MyList myList){
        MyMethodInvokingJobDetailFactoryBean bean=new MyMethodInvokingJobDetailFactoryBean();
        bean.getConcurrentHashMap(myList);
        return bean;
    }

    /**
     * @MethodName getMyCronTriggerFactoryBean
     * @Description 将多个不同的任务调度添加自己的触发器
     * @param mb
     * @Return com.multimodule.bootquartz.config.MyCronTriggerFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 13:53
     */
    @Bean(name = "myCronTriggerFactoryBean")
    public MyCronTriggerFactoryBean getMyCronTriggerFactoryBean(MyMethodInvokingJobDetailFactoryBean mb){
        MyCronTriggerFactoryBean bean=new MyCronTriggerFactoryBean();
        bean.getCronTriggerFactoryBean(mb);
        return bean;
    }

    /**
     * @MethodName getMySchedulerFactoryBean
     * @Description 批量执行任务
     * @param myCronTriggerFactoryBean
     * @param threadPoolTaskExecutor
     * @Return org.springframework.scheduling.quartz.SchedulerFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 14:03
     */
    @Bean(name = "mySchedulerFactoryBean")
    public SchedulerFactoryBean getMySchedulerFactoryBean(MyCronTriggerFactoryBean myCronTriggerFactoryBean, ThreadPoolTaskExecutor threadPoolTaskExecutor){
        SchedulerFactoryBean schedulerFactoryBean =new SchedulerFactoryBean();

        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Set<String> set = myCronTriggerFactoryBean.getTriggers().keySet();
        set.forEach(key-> {
            try {
                Scheduler scheduler = schedulerfactory.getScheduler();
                scheduler.scheduleJob((JobDetail) myCronTriggerFactoryBean.getTriggers().get(key).get("job"),
                        (Trigger) myCronTriggerFactoryBean.getTriggers().get(key).get("trigger"));
                scheduler.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //但这个连接池没有自定义时，我们使用默认的
        if(threadPoolTaskExecutor!=null){
            schedulerFactoryBean.setTaskExecutor(threadPoolTaskExecutor);
        }
        return schedulerFactoryBean;
    }
}
