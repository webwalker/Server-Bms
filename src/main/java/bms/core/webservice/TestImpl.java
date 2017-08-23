package bms.core.webservice;

import javax.jws.WebService;

/**
 * @author xu.jian
 * 
 */
@WebService(endpointInterface = "bms.core.webservice.ITestService")
public class TestImpl implements ITestService {

	@Override
	public int add(int num1, int num2) {
		return num1 + num2;
	}

	@Override
	public RetInfo getRetInfo(String name, int age) {
		RetInfo retInfo = new RetInfo();
		retInfo.setAge(age);
		retInfo.setName(name);
		return retInfo;
	}
}
