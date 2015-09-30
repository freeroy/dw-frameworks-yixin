package org.developerworld.frameworks.yixin.message.p2p;

import org.developerworld.frameworks.yixin.message.P2pMessage;

/**
 * 抽象客服信息
 * 
 * @author Roy Huang
 * @version 20140307
 * 
 */
public abstract class AbstractP2pMessage implements P2pMessage {

	private String toUser;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toUser == null) ? 0 : toUser.hashCode());
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
		AbstractP2pMessage other = (AbstractP2pMessage) obj;
		if (toUser == null) {
			if (other.toUser != null)
				return false;
		} else if (!toUser.equals(other.toUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractCsMessage [toUser=" + toUser + "]";
	}

}
