package com.wumin.boot152.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.wumin.boot152.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Properties;

//@Service
public class OnsProducerServiceImp implements OnsProducerService {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Value("${openservices.ONSAddr}")
//    private String oNSAddr;
//    @Value("${openservices.ProducerId}")
//    private String producerId;
//    @Value("${openservices.ConsumerId}")
//    private String consumerId;
//    @Value("${openservices.SendMsgTimeoutMillis}")
//    private String sendMsgTimeoutMillis;
//    @Value("${openservices.topic}")
//    private String topic;
//    @Value("${AccessKeyID}")
//    private String accessKeyID;
//    @Value("${AccessKeySecret}")
//    private String accessKeySecret;
//    //测试环境不用打开
//    @Value("${openservices.isOpen}")
//    private Boolean isOpen;


    @Override
    public void producer(String tag, Object message) {
//        if (isOpen) {
//            Properties properties = new Properties();
//            // 您在控制台创建的Producer ID
//            properties.put(PropertyKeyConst.ProducerId, producerId);
//            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//            properties.put(PropertyKeyConst.AccessKey, accessKeyID);
//            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//            properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
//            // 设置发送超时时间，单位毫秒
//            properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
//            // 设置 TCP 接入域名（此处以公共云生产环境为例）
//            properties.put(PropertyKeyConst.ONSAddr, oNSAddr);
//            Producer producer = ONSFactory.createProducer(properties);
//            // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可
//            producer.start();
//            byte[] bytes = null;
//            try {
//                // object to bytearray
//                ByteArrayOutputStream bo = new ByteArrayOutputStream();
//                ObjectOutputStream oo = new ObjectOutputStream(bo);
//                oo.writeObject(message);
//                bytes = bo.toByteArray();
//                bo.close();
//                oo.close();
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//            Message msg = new Message( //
//                    // Message所属的Topic
//                    topic,
//                    // Message Tag
//                    tag, // Message Body 可以是任何二进制形式的数据， MQ不做任何干预，
//                    // 需要Producer与Consumer协商好一致的序列化和反序列化方式
//                    bytes);
//            logger.info("提交时间为:{}", DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            // 延时消息，单位毫秒（ms），在指定延迟时间（当前时间之后）进行投递，例如消息在 3 秒后投递
//            long delayTime = System.currentTimeMillis() + 60000;
//            // 设置消息需要被投递的时间
//            msg.setStartDeliverTime(delayTime);
//            producer.send(msg);
//            producer.shutdown();
//        }
    }
}
