package bms.core.common.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Component;

/**
 * @author xu.jian
 * 
 */
@Component
public class SingleReceiver {
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;// 通过@Qualifier修饰符来注入对应的bean

	/**
	 * 构造函数
	 */
	public SingleReceiver() {
	}

	public String receiveMessage() {
		String my = "";
		try {
			TextMessage message = (TextMessage) jmsTemplate.receive();
			my = message.getText();
		} catch (JMSException e) {
			throw JmsUtils.convertJmsAccessException(e);
		}
		return my;
	}

}
