package org.developerworld.frameworks.yixin.message;

/**
 * 时间类型
 * @author Roy Huang
 * @version 20140312
 *
 */
public enum EventType {
	
	SUBSCRIBE("subscribe"),UNSUBSCRIBE("unsubscribe"),CLICK("CLICK"),YIXINSCAN("YIXINSCAN"),LOCATION("LOCATION");

	private String value;
	
	private EventType(String value){
		this.value=value;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
	
}
