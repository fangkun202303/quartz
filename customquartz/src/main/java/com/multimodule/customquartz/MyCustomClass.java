package com.multimodule.customquartz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: MyCustomClass
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 13:55
 * @Version: 1.0
 */
public class MyCustomClass {

    public static void main(String [] args){
        new ClassPathXmlApplicationContext("spring-mvc.xml");
    }
}
