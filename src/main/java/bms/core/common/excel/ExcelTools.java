package bms.core.common.excel;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 2010-11-09 取、设实体对象的各属性值
 * 
 * @author xu.jian
 * 
 */
public class ExcelTools {

	/**
	 * 读取实体bean属性值
	 * 
	 * @param bean
	 *            实体对象
	 * @param propertyName
	 *            要取的属性值
	 * @return 返回实体对象的属性值
	 */
	@SuppressWarnings("finally")
	public static Object getPropertyValue(Object bean, String propertyName) {
		Object result = null;
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(propertyName, bean.getClass());
			Method m = pd.getReadMethod();
			result = m.invoke(bean);
		} finally {
			return result;
		}
	}

	/**
	 * 设置实体bean的属性值
	 * 
	 * @param bean
	 *            实体对象
	 * @param propertyName
	 *            要设置的属性
	 * @param value
	 *            属性值
	 */
	public static void setProperty(Object bean, String propertyName,
			Object value) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(propertyName, bean.getClass());
			Method m = pd.getWriteMethod();
			// 设置属性值
			m.invoke(bean, value);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void print(Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			System.out.print(fields[i].getName() + "："
					+ ExcelTools.getPropertyValue(bean, fields[i].getName())
					+ " | ");
		}
		System.out.println();
	}

	/*
	 * 
	 * 
	 * // 测试 public static void main(String[] args) {
	 * 
	 * Bean bean = new Bean();
	 * 
	 * Field[] fieldss = bean.getClass().getDeclaredFields();
	 * CommonTools.setProperty(bean, fieldss[4].getName(), true);
	 * CommonTools.setProperty(bean, "createTime", new Date());
	 * 
	 * System.out.println(bean.getCreateTime());
	 * System.out.println(bean.isFlag());
	 * 
	 * 
	 * 
	 * // Bean bean = new Bean(); // bean.setId(100); //
	 * bean.setUsername("test"); // bean.setSex("m"); // bean.setCreateTime(new
	 * Date()); // bean.setFlag(false); // Field fields[] =
	 * bean.getClass().getDeclaredFields(); for (Field field : fields) { Object
	 * tmpObject = getPropertyValue(bean, field.getName());
	 * System.out.println(tmpObject); } }
	 */
}