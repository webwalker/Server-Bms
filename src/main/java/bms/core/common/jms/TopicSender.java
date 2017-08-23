package bms.core.common.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import bms.core.common.Loggers;

/**
 * @author xu.jian
 * 
 */
@Component
public class TopicSender {
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTemplate;

	public void send(final String msg) {
		try {
			Loggers.info("send MQ message...");
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					TextMessage message = session.createTextMessage(msg);
					session.close();
					return message;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送一条消息到指定的队列（目标）
	 * 
	 * @param queueName
	 *            队列名称
	 * @param message
	 *            消息内容
	 */
	public void send(String topicName, final String message) {
		Loggers.info("send MQ message...");
		jmsTemplate.send(topicName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}
