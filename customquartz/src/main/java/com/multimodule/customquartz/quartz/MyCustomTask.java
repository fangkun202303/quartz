package com.multimodule.customquartz.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyCustomTask
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 10:57
 * @Version: 1.0
 */
public class MyCustomTask {

    public void runTask(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date())+"MyCustomTask定时执行了这个方法");
    }
}
