package com.multimodule.bootquartz.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.context.SmartLifecycle;

/**
 * @ClassName: MySchedulerFactoryBean
 * @Description: 自定义Scheduler工厂，使MySchedulerFactoryBean在context中有自己的生命周期
 * @Author: FangKun
 * @Date: Created in 2019/4/7 16:31
 * @Version: 1.0
 */
public class MySchedulerFactoryBean implements SmartLifecycle{

    private SchedulerFactory schedulerfactory;
    private Scheduler scheduler;

    /**
     * @MethodName startMyScheduler
     * @Description 启动我自己的Scheduler工厂
     * @param
     * @Return void
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 16:33
     */
    public void startMyScheduler(final Scheduler scheduler){

    }



    //****************     工厂生命周期       **************************

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public int getPhase() {
        return 0;
    }
}
