package com.multimodule.bootquartz.config;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MyMethodInvokingJobDetailFactoryBean
 * @Description: 将任务根据分组封装
 * @Author: FangKun
 * @Date: Created in 2019/4/7 10:29
 * @Version: 1.0
 */
public class MyMethodInvokingJobDetailFactoryBean {

    private Logger logger = LoggerFactory.getLogger(MyMethodInvokingJobDetailFactoryBean.class);

    //执行的任务类
    private MethodInvokingJobDetailFactoryBean bean;

//    private MyList myList;

    //MyMethodInvokingJobDetailFactoryBean 的数据结构
    private ConcurrentHashMap<String, List<JobDetail>> map=new ConcurrentHashMap();

//    private List<JobDetail> list=new ArrayList<>();
//
//    private void setList(List<JobDetail> list){ this.list=list; }
//
//    public List<JobDetail> getList(){ return list; }

    private void setMethodInvokingJobDetailFactoryBean(MethodInvokingJobDetailFactoryBean bean){
        this.bean=bean;
    }

    private void setMap(ConcurrentHashMap<String, List<JobDetail>> map){
        this.map=map;
    }

    public ConcurrentHashMap<String, List<JobDetail>> getMap(){
        return map;
    }

    /**
     * @MethodName setMethodInvokingJobDetailFactoryBean
     * @Description 根据分组将myList中的任务添加进去
     * @param myList
     * @Return void
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 13:01
     */
    public ConcurrentHashMap<String, List<JobDetail>>
        getConcurrentHashMap(MyList myList){
        try{
            if(myList.getList()!=null || myList.getList().size()>0 ){
                //遍历集合根据分组创建任务
                synchronized (MyMethodInvokingJobDetailFactoryBean.class){
                    bean=new MethodInvokingJobDetailFactoryBean();
                    bean.setTargetMethod("run");
                    bean.setConcurrent(false);
                    ArrayList<JobDetail> list=null;
                    for (String task : myList.getList()) {
                        list=new ArrayList<>();
                        bean.setTargetObject(Class.forName(task));
                        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(task).newInstance().getClass())
                                .withIdentity(task.substring(task.lastIndexOf(".") + 1), task.substring(task.lastIndexOf(".") + 1)).build();
                        list.add(jobDetail);
                        map.put(task,list);
                    }
                }
            }else{
                logger.info("MyList这个集合里面没有任务");
                throw new RuntimeException("MyList这个集合里面没有任务");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return map;
    }
}
