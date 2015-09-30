package org.developerworld.frameworks.yixin.api.dto;

import java.io.Serializable;

/**
 * 用户对象
 * 
 * @author Roy Huang
 * @version 20140309
 * 
 */
public class User implements Serializable {

	public final static Byte SUBSCRIPE_SUBSCRIPE = 1;
	public final static Byte SUBSCRIPE_UNSUBSCRIPE = 0;
	public final static Byte SEX_UNKNOW = 0;
	public final static Byte SEX_MAN = 1;
	public final static Byte SEX_LADY = 2;

	private Byte subscribe;
	private String openId;
	private String nickname;
	private Byte sex;
	private String city;
	private String headImgUrl;
	private Long subscribeTime;
	private String language;
	private String bkImage;
	private String mobile;

	public Byte getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Byte subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Long getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getBkImage() {
		return bkImage;
	}

	public void setBkImage(String bkImage) {
		this.bkImage = bkImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bkImage == null) ? 0 : bkImage.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((headImgUrl == null) ? 0 : headImgUrl.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((subscribe == null) ? 0 : subscribe.hashCode());
		result = prime * result
				+ ((subscribeTime == null) ? 0 : subscribeTime.hashCode());
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
		User other = (User) obj;
		if (bkImage == null) {
			if (other.bkImage != null)
				return false;
		} else if (!bkImage.equals(other.bkImage))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (headImgUrl == null) {
			if (other.headImgUrl != null)
				return false;
		} else if (!headImgUrl.equals(other.headImgUrl))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (subscribe == null) {
			if (other.subscribe != null)
				return false;
		} else if (!subscribe.equals(other.subscribe))
			return false;
		if (subscribeTime == null) {
			if (other.subscribeTime != null)
				return false;
		} else if (!subscribeTime.equals(other.subscribeTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [subscribe=" + subscribe + ", openId=" + openId
				+ ", nickname=" + nickname + ", sex=" + sex + ", city=" + city
				+ ", headImgUrl=" + headImgUrl + ", subscribeTime="
				+ subscribeTime + ", language=" + language + ", bkImage="
				+ bkImage + ", mobile=" + mobile + "]";
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
