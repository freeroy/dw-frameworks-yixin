package org.developerworld.frameworks.yixin.message.p2p;

/**
 * 名片消息
 * 
 * @author Roy Huang
 * @version 20140307
 * 
 */
public class CardP2pMessage extends AbstractP2pMessage {

	private String card;

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((card == null) ? 0 : card.hashCode());
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
		CardP2pMessage other = (CardP2pMessage) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardP2pMessage [card=" + card + "]";
	}

	

}
