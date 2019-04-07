package com.multimodule.bootquartz.config;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MyCronTriggerFactoryBean
 * @Description: TODO
 * @Author: FangKun
 * @Date: Created in 2019/4/7 13:13
 * @Version: 1.0
 */
@SuppressWarnings("all")
public class MyCronTriggerFactoryBean {

    private Logger logger = LoggerFactory.getLogger(MyCronTriggerFactoryBean.class);

    //触发器
    private CronTriggerFactoryBean bean;

    private Map<String, Map<String,Object>> triggers= new HashMap<>();

    private void setBean(CronTriggerFactoryBean bean){
        this.bean=bean;
    }

    public void setTriggers(Map<String, Map<String,Object>> triggers){
        this.triggers=triggers;
    }
    public Map<String, Map<String,Object>> getTriggers(){
        return triggers;
    }

    /**
     * @MethodName setCronTriggerFactoryBean
     * @Description 将MyMethodInvokingJobDetailFactoryBean中的任务都创建触发器
     * @param mb
     * @Return java.util.List<org.springframework.scheduling.quartz.CronTriggerFactoryBean>
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/4/7 13:49
     */
    public synchronized Map<String, Map<String,Object>> getCronTriggerFactoryBean(MyMethodInvokingJobDetailFactoryBean mb) {
        ConcurrentHashMap.KeySetView<String, List<JobDetail>> set = mb.getMap().keySet();
        for (String s: set) {
            List<JobDetail> beans = mb.getMap().get(s);
            if(beans.size()==1){
                String cess=null;
                try {
                    cess=String.valueOf(Class.forName(s).getField("timeString").get(Class.forName(s).newInstance()));
                } catch (NoSuchFieldException e) {
                    logger.info(e.getMessage()+"--->>> 没有这个类");
                    throw new RuntimeException(e.getMessage()+"--->>> 没有这个类");
                } catch (ClassNotFoundException e) {
                    logger.info(e.getMessage()+"--->>> 没有这个成员变量");
                    throw new RuntimeException(e.getMessage()+"--->>> 没有这个成员变量");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                        .withIdentity(s.substring(s.lastIndexOf(".")+1),s.substring(s.lastIndexOf(".")+1))
                        .withSchedule(CronScheduleBuilder.cronSchedule(cess))
                        .startNow()
                        .build();
//                List<Trigger> list=new ArrayList<>();
//                list.add(cronTrigger);
                Map<String, Object> m= new HashMap<>();
                m.put("trigger", cronTrigger);
                m.put("job", beans.get(0));
                triggers.put(s,m);
            }else if(beans.size()>1){
                String cess=null;
                try {
                    cess=String.valueOf(Class.forName(s).getDeclaredField("timeString").get(Class.forName(s).newInstance()));
                } catch (NoSuchFieldException e) {
                    logger.info(e.getMessage()+"--->>> 没有这个类");
                    throw new RuntimeException(e.getMessage()+"--->>> 没有这个类");
                } catch (ClassNotFoundException e) {
                    logger.info(e.getMessage()+"--->>> 没有这个成员变量");
                    throw new RuntimeException(e.getMessage()+"--->>> 没有这个成员变量");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
//                List<Trigger> list=new ArrayList<>();
                Map<String, Object> mt= new HashMap<>();
                for (JobDetail m : beans) {
                    CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                            .withIdentity(s.substring(s.lastIndexOf(".")+1),s.substring(s.lastIndexOf(".")+1))
                            .withSchedule(CronScheduleBuilder.cronSchedule(cess))
                            .startNow()
                            .build();

                    mt.put("trigger", cronTrigger);
                    mt.put("job", beans.get(0));
                    triggers.put(s,mt);
                }
            }else{
                throw new RuntimeException("ConcurrentHashMap.KeySetView<String, List<MethodInvokingJobDetailFactoryBean>>中没有任务");
            }
        }
        return triggers;
    }
}
