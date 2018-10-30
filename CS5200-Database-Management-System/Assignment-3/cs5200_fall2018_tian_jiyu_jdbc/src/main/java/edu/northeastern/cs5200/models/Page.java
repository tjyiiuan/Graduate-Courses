package edu.northeastern.cs5200.models;

import java.sql.Date;
import java.util.Collection;

public class Page {
	
	private int id;
	private String title;
	private String description;
	private Date created;
	private Date updated;
	private int views;
    private Collection<Widget> widgets;
    private int websiteId;
	
	public Page() {
		super();
	}
	
	public Page(int id, String title, String description, Date created, Date updated, int views, int WebsiteId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.created = created;
		this.updated = updated;
		this.views = views;
		this.setWebsiteId(WebsiteId);
	}
	
	public Page(int id, String title, String description, Date created, Date updated, int views) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.created = created;
		this.updated = updated;
		this.views = views;
	}
	
	public Page(int id, String title, String description, int views) {
		super();
		this.title = title;
		this.description = description;
		this.views = views;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public int getViews() {
		return views;
	}
	
	public void setViews(int views) {
		this.views = views;
	}
	
	public Collection<Widget> getWidgets() {
		return widgets;
	}
	
	public void setWidgets(Collection<Widget> widgets) {
		this.widgets = widgets;
	}

	public int getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}
}