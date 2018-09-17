package com.wumin.boot152.ons;


import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 阿里MQ消息发送端
 */
@Component
public class OpenservicesMqService {
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Value("${openservices.ONSAddr}")
//	private String oNSAddr;
//	@Value("${openservices.ProducerId}")
//	private String producerId;
//	@Value("${openservices.ConsumerId}")
//	private String consumerId;
//	@Value("${openservices.SendMsgTimeoutMillis}")
//	private String sendMsgTimeoutMillis;
//	@Value("${openservices.topic}")
//	private String topic;
//	@Value("${AccessKeyID}")
//	private String accessKeyID;
//	@Value("${AccessKeySecret}")
//	private String accessKeySecret;

	private void producer(String tag, String message) {
//		Properties properties = new Properties();
//		// 您在控制台创建的Producer ID
//		properties.put(PropertyKeyConst.ProducerId, producerId);
//		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//		properties.put(PropertyKeyConst.AccessKey, accessKeyID);
//		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//		properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
//		// 设置发送超时时间，单位毫秒
//		properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
//		// 设置 TCP 接入域名（此处以公共云生产环境为例）
//		properties.put(PropertyKeyConst.ONSAddr, oNSAddr);
//
//		Producer producer = ONSFactory.createProducer(properties);
//		// 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可
//		producer.start();
//		// 循环发送消息
//			Message msg = new Message( //
//					// Message所属的Topic
//					topic,
//					// Message Tag
//
//					tag,
//					// Message Body 可以是任何二进制形式的数据， MQ不做任何干预，
//					// 需要Producer与Consumer协商好一致的序列化和反序列化方式
//					message.getBytes());
//			// 设置代表消息的业务关键属性，请尽可能全局唯一。
//			// 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
//			// 注意：不设置也不会影响消息正常收发
//			//msg.setKey(UU);
//			// 同步发送消息，只要不抛异常就是成功
//			SendResult sendResult = producer.send(msg);
//		// 在应用退出前，销毁Producer对象
//		// 注意：如果不销毁也没有问题
//		producer.shutdown();
	}

	private void consumerTest(String tag) {
//		Properties properties = new Properties();
//		// 您在控制台创建的 Consumer ID
//		properties.put(PropertyKeyConst.ConsumerId, consumerId);
//		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//		properties.put(PropertyKeyConst.AccessKey, accessKeyID);
//		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//		properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
//		// 设置 TCP 接入域名（此处以公共云生产环境为例）
//		properties.put(PropertyKeyConst.ONSAddr, oNSAddr);
//		// 集群订阅方式 (默认)
//		// properties.put(PropertyKeyConst.MessageModel,
//		// PropertyValueConst.CLUSTERING);
//		// 广播订阅方式
//		// properties.put(PropertyKeyConst.MessageModel,
//		// PropertyValueConst.BROADCASTING);
//		Consumer consumer = ONSFactory.createConsumer(properties);
//		consumer.subscribe(topic, tag, new MessageListener() { // 订阅多个Tag
//					@Override
//					public Action consume(Message message, ConsumeContext context) {
//						return Action.CommitMessage;
//					}
//				});
//		// 订阅另外一个Topic
////		consumer.subscribe("TopicTestMQ-Other", "*", new MessageListener() { // 订阅全部Tag
////			@Override
////			public Action consume(Message message, ConsumeContext context) {
////						System.out.println("Receive: " + message);
////						return Action.CommitMessage;
////					}
////				});
//		consumer.start();
	}
	
}
