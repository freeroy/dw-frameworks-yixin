package org.developerworld.frameworks.yixin.api.dto;

import java.io.Serializable;

/**
 * 公众平台账号
 * 
 * @author Roy Huang
 * @version 20140411
 * 
 */
public class PublicAccount implements Serializable {

	private String openPid;
	private String nick;
	private String appId;

	public String getOpenPid() {
		return openPid;
	}

	public void setOpenPid(String openpid) {
		this.openPid = openpid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appid) {
		this.appId = appid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((openPid == null) ? 0 : openPid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicAccount other = (PublicAccount) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (openPid == null) {
			if (other.openPid != null)
				return false;
		} else if (!openPid.equals(other.openPid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PublicAccount [openPid=" + openPid + ", nick=" + nick
				+ ", appId=" + appId + "]";
	}

}
