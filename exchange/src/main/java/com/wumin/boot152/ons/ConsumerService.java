package com.wumin.boot152.ons;

/**
 * 阿里MQ 消息消费端
 */

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.wumin.boot152.common.entity.DataMongoDBLogger;
import com.wumin.boot152.common.enums.EnumOnsProducerTag;
import com.wumin.boot152.common.util.DateUtils;
import com.wumin.boot152.ons.service.TestOnsService;
import com.wumin.boot152.redisson.RedissLockUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Component
public class ConsumerService implements CommandLineRunner {
    @Resource
    private MongoTemplate mongoTemplate;
//    @Resource
//    private TestOnsService testOnsService;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Value("${openservices.ONSAddr}")
//    private String oNSAddr;
//    @Value("${openservices.ProducerId}")
//    private String producerId;
//    @Value("${openservices.ConsumerId}")
//    private String consumerId;
//    @Value("${openservices.SendMsgTimeoutMillis}")
//    private String sendMsgTimeoutMillis;
//    @Value("${AccessKeyID}")
//    private String accessKeyID;
//    @Value("${AccessKeySecret}")
//    private String accessKeySecret;
//    @Value("${openservices.topic}")
//    private String openservicesTopic;
    @Override
    public void run(String... args) throws Exception {
//
//            Properties properties = new Properties();
//            // 您在控制台创建的 Consumer ID
//            properties.put(PropertyKeyConst.ConsumerId, consumerId);
//            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//            properties.put(PropertyKeyConst.AccessKey, accessKeyID);
//            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//            properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
//            // 设置 TCP 接入域名（此处以公共云生产环境为例）
//            properties.put(PropertyKeyConst.ONSAddr, oNSAddr);
//            // 集群订阅方式 (默认)
//            // properties.put(PropertyKeyConst.MessageModel,
//            // PropertyValueConst.CLUSTERING);
//            // 广播订阅方式
//            // properties.put(PropertyKeyConst.MessageModel,
//            // PropertyValueConst.BROADCASTING);
//            Consumer consumer = ONSFactory.createConsumer(properties);
//            consumer.subscribe(openservicesTopic, "*", new MessageListener() { // 订阅多个Tag
//                @Override
//                public Action consume(Message message, ConsumeContext context) {
//                    logger.info("消费的时间为:{}", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                    Long startTime = System.currentTimeMillis();
//
//                    String key = "Redisson:" + message.getTag() + ":" + SerializationUtils.deserialize(message.getBody());
//                    //尝试加锁,如果锁不住,就表示正在被调用,则消费此信息
//                    boolean b = RedissLockUtil.tryLock(key, 1, 180);
//                    if (!b) {
//                        return Action.ReconsumeLater;
//                        //return Action.CommitMessage;
//                    }
//                    //保存返回的数据
//                    Object o = null;
//                    String data = "";
//                    try {
//                        if (StringUtils.equals(EnumOnsProducerTag.TestTag.getTag(), message.getTag())) {
//                            //message body is Integer
//                            Integer id = SerializationUtils.deserialize(message.getBody());
//                            //data为保存mongo中的数据
//                            data=String.valueOf(id);
//                            testOnsService.doTest(id);
//                        }
//                        if(StringUtils.equals(EnumOnsProducerTag.TestMapTag.getTag(),message.getTag())){
//                            Map<String, Object> map = SerializationUtils.deserialize(message.getBody());
//                            data= JSONObject.toJSONString(map);
//                            testOnsService.doMapTest((Integer)map.get("id"),(String) map.get("type"));
//                        }
//                    } catch (Exception e) {
//                        //稍后再试
//                        return Action.ReconsumeLater;
//                    } finally {
//                        mongoDbLogger(data, message.getTag(), System.currentTimeMillis() - startTime, o);
//                    }
//                    return Action.CommitMessage;
//                }
//            });
//            consumer.start();

    }
    /**
     * 接口调用MongoDB存储
     *
     * @param
     * @param tag
     * @param
     */
    private void mongoDbLogger(String data, String tag, long time, Object o) {
        DataMongoDBLogger dataMongoDBLogger = new DataMongoDBLogger();
        dataMongoDBLogger.setCreateTime(new Date());
        dataMongoDBLogger.setData(data);
        dataMongoDBLogger.setDiffTime(time);
        dataMongoDBLogger.setDescription(tag);
        dataMongoDBLogger.setType("0");
        if (o != null)
            dataMongoDBLogger.setResult(o);
        mongoTemplate.save(dataMongoDBLogger);
    }
}
