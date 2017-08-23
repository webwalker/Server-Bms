package bms.core.common;

import org.apache.log4j.Logger;

/**
 * @author xu.jian
 * 
 */
public class Loggers {
	private static final Logger i = Logger.getLogger("InfoLogger");
	private static final Logger e = Logger.getLogger("ErrorLogger");

	public static void info(String data) {
		i.info(data);
	}

	public static void error(String data) {
		e.error(data);
	}
}
