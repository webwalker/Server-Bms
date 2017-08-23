package bms.core.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description: 通过 Object getBean(String name)这个方法 获取注册的bean的实例 例如：
 *               AppropriationApplyService appropriationApplyService2 =
 *               (AppropriationApplyService
 *               )SpringContextUtil.getBean("appropriationApplyService"); 就可以获取到
 *               spring 中注册的AppropriationApplyService的实例
 * @author
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	@SuppressWarnings("rawtypes")
	public static Class getType(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 此方法仅限于本地测试使用，勿要在应用中使用
	 * 
	 * @Description:
	 * @param @param beanName 配置文件中的id或name
	 * @param @param springFile spring配置文件 ,默认为：src/applicationContext.xml
	 * @return Object
	 * @throws
	 */
	public static Object getBean(String beanName, String springFile) {
		if ((springFile == null) || (springFile.length() == 0)) {
			springFile = "src/applicationContext.xml";
		}
		applicationContext = new FileSystemXmlApplicationContext(springFile);
		return applicationContext.getBean(beanName);
	}
}
