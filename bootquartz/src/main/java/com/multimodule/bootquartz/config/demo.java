package com.multimodule.bootquartz.config;

import com.multimodule.bootquartz.task.MyTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: demo
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/7 10:38
 * @Version: 1.0
 */
public class demo {

    public static void main(String [] argo){
        Map<String, List<String>> map=new HashMap<>();
        System.out.println(map.get("key"));
        System.out.println(map.containsKey("key"));

    }
}
