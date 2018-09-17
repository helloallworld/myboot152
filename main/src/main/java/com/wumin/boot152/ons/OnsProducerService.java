package com.wumin.boot152.ons;

/**
 * 阿里MQ消息队列发送消息端
 */
public interface OnsProducerService {
	/**
	 * @Title: producer
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param tag 标签
	 * @param @param message 参数
	 * @return void 返回类型
	 */
	public void producer(String tag, Object message);

}
