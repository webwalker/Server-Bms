package bms.core.common.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Component;

import bms.core.common.Loggers;

/**
 * 已配置为多线程监听消费
 * 
 * @author xu.jian
 * 
 */
@Component
public class MessageReceiver implements MessageListener {

	// @Autowired
	// AppService appService;
	String messageText = "";

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				String text = textMessage.getText();
				messageText = String.format("Received: %s", text);
				System.out.println(messageText);
				Loggers.info(messageText);
				// appService.asyncTask(text);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (JMSException e) {
				e.printStackTrace();
				// Redelivery补发消息
				throw JmsUtils.convertJmsAccessException(e);
			}
		}
	}
}
