package com.multimodule.customquartz.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyTask
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 13:33
 * @Version: 1.0
 */
public class MyTask {

    public void getWordTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("此时的时间为："+df.format(new Date())+"，毫秒数为："+System.currentTimeMillis());
    }
}
