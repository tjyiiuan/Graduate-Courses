package edu.northeastern.cs5200.models;

public class Widget {
	
	private int id;
	private String name;
	private String type;
	private int width;
	private int height;
	private String cssStyle;
	private String cssClass;
	private String text;
	private int order;
	private int pageId;
	
	public Widget() {
		super();
	}
	
	public Widget(String name, String type, int width, int height, String text, int order, int pageId) {
		super();
		this.setName(name);
		this.setType(type);
		this.setWidth(width);
		this.setHeight(height);
		this.setCssStyle("undefined");
		this.setCssClass("undefined");
		this.setText(text);
		this.setOrder(order);
		this.setPageId(pageId);
	}
	
	public Widget(int id, String name, String type, int width, int height, String cssStyle, String cssClass, String text, int order) {
		super();
		this.setId(id);
		this.setName(name);
		this.setType(type);
		this.setWidth(width);
		this.setHeight(height);
		this.setCssStyle(cssStyle);
		this.setCssClass(cssClass);
		this.setText(text);
		this.setOrder(order);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
}
