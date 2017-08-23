package bms.core.common.jms;

import javax.jms.JMSException;

import org.springframework.jms.listener.SimpleMessageListenerContainer;

/**
 * @author xu.jian
 * 
 */
public class MessageContainer extends SimpleMessageListenerContainer {

	/**
	 * avoid jms connection exception causing app dead.
	 */
	@Override
	protected void doStart() {
		try {
			super.doStart();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
