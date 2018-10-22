package edu.northeastern.cs5200.models;

public class YouTubeWidget extends Widget {
	
    private String url;
    private boolean shareable;
    private boolean expandable;

	public YouTubeWidget() {
		super();
    }

	public YouTubeWidget(int id, String name, int width, int height, String cssStyle, String cssClass, String text, int order, String url, boolean shareable, boolean expandable) {
		super(id, name, width, height, cssStyle, cssClass, text, order);
        this.url = url;
        this.shareable = shareable;
        this.expandable = expandable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
