package edu.cs5774.project5;

import edu.cs5774.project5.Project;

import java.util.List;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 * Class that is owned by the root RSS object, needed for JAXB parsing of RSS feed.
 *
 */
public class Channel {
	private String title ;
    private String description ;
    private String link;
    private List<Project> item;
    @XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@XmlElement
	public List<Project> getItem() {
		return item;
	}
	public void setItem(List<Project> item) {
		this.item = item;
	}
	
}