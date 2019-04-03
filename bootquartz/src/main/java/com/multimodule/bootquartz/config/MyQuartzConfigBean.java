package com.multimodule.bootquartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @ClassName: MyQuartzBean
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 15:32
 * @Version: 1.0
 */
@Configuration
public class MyQuartzConfigBean {

    //一秒执行一次
    @Value("${quartz.cronExpressionOf1}")
    public String cronExpressionOf1;

    //每隔五秒执行一次
    @Value("${quartz.cronExpressionOf5}")
    public String cronExpressionOf5;

    /**
     * @MethodName getMethodInvokingJobDetailFactoryBean
     * @Description 任务调度
     * @param MyList
     * @Return org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/3 16:08
     */
    @Bean(name = "detailFactoryBean")
    public MethodInvokingJobDetailFactoryBean getMethodInvokingJobDetailFactoryBean(MyList myList){
        MethodInvokingJobDetailFactoryBean bean=new MethodInvokingJobDetailFactoryBean();
        //设置任务调蓄的线程并发
        bean.setConcurrent(false);

        myList.getList().stream().forEach(task ->{
            //设置任务类
            bean.setTargetObject(task);
            bean.setGroup(task.substring(task.lastIndexOf(".")+1));
            //执行那个方法，这里用到反射，规定这个任务类中只能有一个方法为共有的，这样就不需要配置文件
            String name1 =null;
            try {
                Class<?> aClass = Class.forName(task);

                bean.setTargetMethod(aClass.getMethods()[0].getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return bean;
    }

    /**
     * @MethodName getSchedulerFactoryBean
     * @Description 设置调度
     * @param cronTriggerFactoryBean
     * @Return org.springframework.scheduling.quartz.SchedulerFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/3 16:43
     */
    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean,ThreadPoolTaskExecutor threadPoolTaskExecutor){
        SchedulerFactoryBean schedulerFactoryBean =new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean.getObject());
        //但这个连接池没有自定义时，我们使用默认的
        if(threadPoolTaskExecutor!=null){
            schedulerFactoryBean.setTaskExecutor(threadPoolTaskExecutor);
        }
        return schedulerFactoryBean;
    }

    /**
     * @MethodName getCronTriggerFactoryBean
     * @Description 添加CronTriggerFactoryBean触发器的工厂
     * @param detailFactoryBean
     * @Return org.springframework.scheduling.quartz.CronTriggerFactoryBean
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/3 16:19
     */
    @Bean(name = "cronTriggerFactoryBean")
    public CronTriggerFactoryBean getCronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean detailFactoryBean){
        CronTriggerFactoryBean cronTriggerFactoryBean=new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(detailFactoryBean.getObject());
        try{
            cronTriggerFactoryBean.setCronExpression(cronExpressionOf5);
        }catch (Exception e){
            System.out.println("MyQuartzBean这个类的getCronTriggerFactoryBean方法在设置时间上出了问题");
        }
        return cronTriggerFactoryBean;
    }

}
