package edu.northeastern.cs5200.models;

public class ImageWidget extends Widget{
	
	private String src;

	public ImageWidget() {
		super();
	}
	
	public ImageWidget(int id, String name, int width, int height, String cssStyle, String cssClass, String text, int order, String src) {
		super(id, name, width, height, cssStyle, cssClass, text, order);
		this.src = src;
	}

	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
}