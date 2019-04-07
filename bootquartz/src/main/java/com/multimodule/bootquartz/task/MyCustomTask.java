package com.multimodule.bootquartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyCustomTask
 * @Description: 使用boot自带的
 * @Author: FangKun
 * @Date: Created in 2019/4/3 17:05
 * @Version: 1.0
 */
//@Component
public class MyCustomTask implements Job {

    public String timeString="*/1 * * * * ?";

//    @Scheduled(cron="0/1 * * * * ?")
    public void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("此时的时间为："+df.format(new Date())+"，毫秒数为："+System.currentTimeMillis());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("MyCustomTask此时的时间为："+df.format(new Date())+"，毫秒数为："+System.currentTimeMillis());
    }
}
