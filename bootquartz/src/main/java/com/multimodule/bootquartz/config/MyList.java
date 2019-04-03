package com.multimodule.bootquartz.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MyList
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/3 17:39
 * @Version: 1.0
 */
public class MyList {

    private List<String> list=new ArrayList();

    private void setList(List<String> list){
        this.list=list;
    }

    public List<String> getList(){
        return list;
    }
}
