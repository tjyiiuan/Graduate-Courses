package edu.northeastern.cs5200.models;

public class HeadingWidget extends Widget{
	
	private int size;
	
	public HeadingWidget() {
		super();
	}
	
	public HeadingWidget(int id, String name, int width, int height, String cssStyle, String cssClass, String text, int order, int size) {
		super(id, name, width, height, cssStyle, cssClass, text, order);
		this.size = size;
	}
	
	public HeadingWidget(int id, String name, int width, int height, String cssStyle, String cssClass, String text, int order, int pageId, int size) {
		super(id, name, width, height, cssStyle, cssClass, text, order, pageId);
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	

}

