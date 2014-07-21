package org.z.cloud.common.util;

public class Common {

	public static String localName;
	public static String localIp;

	static {
		localName = CloudUtil.getLocalName();
		localIp = CloudUtil.getLocalIP();
	}

	public static void main(String[] args) {
		System.out.println(Common.localName);
		System.out.println(Common.localIp);
	}

}
