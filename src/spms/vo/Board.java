package spms.vo;

import java.util.Date;

public class Board {
	protected int no;
	protected String name;
	protected String title;
	protected String story;
	protected Date createdDate;

	public int getNo() {
		return no;
	}
	public Board setNo(int no) {
		this.no = no;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public Board setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getTitle() {
		return title;
	}
	public Board setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getStory() {
		return story;
	}
	public Board setStory(String story) {
		this.story = story;
		return this;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public Board setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}

}
