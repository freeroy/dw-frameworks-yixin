package org.developerworld.frameworks.yixin.message.request;

/**
 * 图片信息
 * @author Roy Huang
 * @version 20140307
 *
 */
public class ImageRequestMessage extends AbstractRequestMessage {

	private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((picUrl == null) ? 0 : picUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageRequestMessage other = (ImageRequestMessage) obj;
		if (picUrl == null) {
			if (other.picUrl != null)
				return false;
		} else if (!picUrl.equals(other.picUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageRequestMessage [picUrl=" + picUrl+ "]";
	}

}
