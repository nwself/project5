package edu.cs5774.project5;

import edu.cs5774.project5.Channel;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlRootElement()
public class Rss {
	private Channel channel;
	  @XmlElement
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
}

		
