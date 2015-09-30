package org.developerworld.frameworks.yixin.api.dto;

/**
 * 授权范围
 * 
 * @author Roy Huang
 * @version 20140309
 * 
 */
public enum WebAuthScope {

	SNSAPI_BASE("snsapi_base");

	private String name;

	private WebAuthScope(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
