package bms.core.common.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import bms.core.common.Loggers;

/**
 * @author xu.jian
 * 
 */
@Component
public class MQueueSender {
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;// 通过@Qualifier修饰符来注入对应的bean

	public void send(final String msg) {
		Loggers.info("send message...");
		jmsTemplate.convertAndSend(msg);
	}

	/**
	 * 发送一条消息到指定的队列（目标）
	 * 
	 * @param queueName
	 *            队列名称
	 * @param message
	 *            消息内容
	 */
	public void send(String queueName, final String message) {
		Loggers.info("send message...");
		jmsTemplate.convertAndSend(queueName, message);
	}
}
