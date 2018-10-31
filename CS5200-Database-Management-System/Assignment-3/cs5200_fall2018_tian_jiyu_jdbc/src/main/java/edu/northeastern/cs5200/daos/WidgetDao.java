package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Widget;
import edu.northeastern.cs5200.models.HeadingWidget;
import edu.northeastern.cs5200.models.HtmlWidget;
import edu.northeastern.cs5200.models.ImageWidget;
import edu.northeastern.cs5200.models.YouTubeWidget;

public class WidgetDao implements WidgetImpl {
	
	private static WidgetDao instance = null;

	private WidgetDao() {
		
	}

	public static WidgetDao getInstance() {
		if (instance == null) {
			instance = new WidgetDao();
		}
		return instance;
	}
	
    private PreparedStatement pStatement = null;
    // private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
    private final String CREATE_WIDGET_FOR_PAGE = "INSERT INTO widget(id, name, width, height, cssClass, cssStyle, text, `order`, pageId) VALUES(?,?,?,?,?,?,?,?,?)";
    private final String CREATE_HEADINGWIDGET = "INSERT INTO HeadingWidget(id, size) VALUES(?,?)";
    private final String CREATE_HTMLWIDGET = "INSERT INTO HtmlWidget(id, html) VALUES(?,?)";
    private final String CREATE_IMAGEWIDGET = "INSERT INTO ImageWidget(id, src) VALUES(?,?)";
    private final String CREATE_YOUTUBEWIDGET = "INSERT INTO YouTubeWidget(id, url, shareble, expandable) VALUES(?,?,?,?)";

    private final String FIND_ALL_WIDGETS = "SELECT * FROM widget";
    private final String FIND_WIDGET_BY_ID = "SELECT * FROM widget WHEREId=?";
    private final String FIND_WIDGETS_FOR_PAGE = "SELECT * FROM widget WHERE pageId = ?";
    
    private final String UPDATE_WIDGET = "UPDATE widget SET name = ?, width = ?, height = ?, cssClass = ?, cssStyle = ?, text = ?, `order` = ?, pageId = ? WHERE id = ?";
    
    private final String DELETE_HEADING = "DELETE FROM heading WHERE id = ?";
    private final String DELETE_HTML = "DELETE FROM html WHERE id = ?";
    private final String DELETE_IMAGE = "DELETE FROM image WHERE id = ?";
    private final String DELETE_YOUTUBE = "DELETE FROM youtube WHERE id = ?";
    private final String DELETE_WIDGET = "DELETE FROM widget WHERE id = ?"; 

	@Override
	public void createWidgetForPage(int pageId, Widget widget) {
		
		try {
			pStatement = Connection.getConnection().prepareStatement(CREATE_WIDGET_FOR_PAGE);
			pStatement.setInt(1, widget.getId());
			pStatement.setString(2, widget.getName());
			pStatement.setInt(3, widget.getWidth());
			pStatement.setInt(4, widget.getHeight());
			pStatement.setString(5, widget.getCssClass());
			pStatement.setString(6, widget.getCssStyle());
			pStatement.setString(7, widget.getText());
			pStatement.setInt(8, widget.getOrder());
			pStatement.setInt(9, pageId);
			pStatement.executeUpdate();

			if (widget instanceof HeadingWidget) {
				HeadingWidget headingWidget = (HeadingWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_HEADINGWIDGET);
				pStatement.setInt(1, widget.getId());
				pStatement.setInt(2, headingWidget.getSize());
				pStatement.executeUpdate();
			}

			if (widget instanceof HtmlWidget) {
				HtmlWidget htmlWidget = (HtmlWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_HTMLWIDGET);
				pStatement.setString(1, htmlWidget.getHtml());
				pStatement.setInt(2, widget.getId());
				pStatement.executeUpdate();
			}

			if (widget instanceof ImageWidget) {
				ImageWidget imageWidget = (ImageWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_IMAGEWIDGET);
				pStatement.setString(1, imageWidget.getSrc());
				pStatement.setInt(2, widget.getId());
				pStatement.executeUpdate();
			}

			if (widget instanceof YouTubeWidget) {
				YouTubeWidget youTubeWidget = (YouTubeWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_YOUTUBEWIDGET);
				pStatement.setString(1, youTubeWidget.getUrl());
				pStatement.setBoolean(2, youTubeWidget.isShareable());
				pStatement.setBoolean(3, youTubeWidget.isExpandable());
				pStatement.setInt(4, widget.getId());
				pStatement.executeUpdate();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			} finally {
				try {
					pStatement.close();
					Connection.closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
						}
				}    
		}
	

	@Override
	public Collection<Widget> findAllWidgets() {
		
		List<Widget> widgets = new ArrayList<Widget>();

		try {
			pStatement = Connection.getConnection().prepareStatement(FIND_ALL_WIDGETS);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				int id = rSet.getInt("id");
				String name = rSet.getString("name");
				int width = rSet.getInt("width");
				int height = rSet.getInt("height");
				String cssClass = rSet.getString("cssClass");
				String cssStyle = rSet.getString("cssStyle");
				String text = rSet.getString("text");
				int order = rSet.getInt("order");
				
				int pageId = rSet.getInt("pageId");
				Widget widget = new Widget(id, name, width, height, cssClass, cssStyle, text, order, pageId);
				widgets.add(widget);
				}
	        } catch (ClassNotFoundException | SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		try {
	        			rSet.close();
	        			pStatement.close();
	        			Connection.closeConnection();
	        			} catch (SQLException e) {
	        				e.printStackTrace();
	        				}
	        		}      
			return widgets;
		}

	@Override
	public Widget findWidgetById(int widgetId) {
		
		Widget widget = null;
		
		try {
			pStatement = Connection.getConnection().prepareStatement(FIND_WIDGET_BY_ID);
			pStatement.setInt(1, widgetId);
			rSet = pStatement.executeQuery();
			if (rSet.next()) {
				int id = rSet.getInt("id");
				String name = rSet.getString("name");
				int width = rSet.getInt("width");
				int height = rSet.getInt("height");
				String cssClass = rSet.getString("cssClass");
				String cssStyle = rSet.getString("cssStyle");
				String text = rSet.getString("text");
				int order = rSet.getInt("order");
				int pageId = rSet.getInt("pageId");
				
				widget = new Widget(id, name, width, height, cssClass, cssStyle, text, order, pageId);
				}
	        } catch (ClassNotFoundException | SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		try {
	        			rSet.close();
	        			pStatement.close();
	        			Connection.closeConnection();
	        			} catch (SQLException e) {
	        				e.printStackTrace();
	        				}
	        		}      
			return widget;
		}

	@Override
	public Collection<Widget> findWidgetsForPage(int pageId) {
		
		List<Widget> widgets = new ArrayList<Widget>();
		
		try {
			pStatement = Connection.getConnection().prepareStatement(FIND_WIDGETS_FOR_PAGE);
			pStatement.setInt(1, pageId);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				int id = rSet.getInt("id");
				String name = rSet.getString("name");
				int width = rSet.getInt("width");
				int height = rSet.getInt("height");
				String cssClass = rSet.getString("cssClass");
				String cssStyle = rSet.getString("cssStyle");
				String text = rSet.getString("text");
				int order = rSet.getInt("order");
				
				Widget widget = new Widget(id, name, width, height, cssClass, cssStyle, text, order, pageId);
				widgets.add(widget);
				}
			} catch (ClassNotFoundException | SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		try {
	        			rSet.close();
	        			pStatement.close();
	        			Connection.closeConnection();
	        			} catch (SQLException e) {
	        				e.printStackTrace();
	        				}
	        		}      
			return widgets;
		}

	@Override
	public int updateWidget(int widgetId, Widget widget) {
		
		int res = 0;
		
		try {
			pStatement = Connection.getConnection().prepareStatement(UPDATE_WIDGET);
			pStatement.setString(1, widget.getName());
			pStatement.setInt(2, widget.getWidth());
			pStatement.setInt(3, widget.getHeight());
			pStatement.setString(4, widget.getCssClass());
			pStatement.setString(5, widget.getCssStyle());
			pStatement.setString(6, widget.getText());
			pStatement.setInt(7, widget.getOrder());
			pStatement.setInt(8, widget.getPageId());
			pStatement.setInt(9, widgetId);
			res = pStatement.executeUpdate();

			if (widget instanceof HeadingWidget) {
				pStatement = Connection.getConnection().prepareStatement(DELETE_HEADING);
				pStatement.setInt(1, widgetId);
				pStatement.executeUpdate();
				
				HeadingWidget headingWidget = (HeadingWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_HEADINGWIDGET);
				pStatement.setInt(1, widget.getId());
				pStatement.setInt(2, headingWidget.getSize());
				pStatement.executeUpdate();
			}

			if (widget instanceof HtmlWidget) {
				pStatement = Connection.getConnection().prepareStatement(DELETE_HTML);
				pStatement.setInt(1, widgetId);
				pStatement.executeUpdate();
				
				HtmlWidget htmlWidget = (HtmlWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_HTMLWIDGET);
				pStatement.setString(1, htmlWidget.getHtml());
				pStatement.setInt(2, widget.getId());
				pStatement.executeUpdate();
			}

			if (widget instanceof ImageWidget) {
				pStatement = Connection.getConnection().prepareStatement(DELETE_IMAGE);
				pStatement.setInt(1, widgetId);
				pStatement.executeUpdate();
				
				ImageWidget imageWidget = (ImageWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_IMAGEWIDGET);
				pStatement.setString(1, imageWidget.getSrc());
				pStatement.setInt(2, widget.getId());
				pStatement.executeUpdate();
				}

			if (widget instanceof YouTubeWidget) {
				pStatement = Connection.getConnection().prepareStatement(DELETE_YOUTUBE);
				pStatement.setInt(1, widgetId);
				pStatement.executeUpdate();
				
				YouTubeWidget youTubeWidget = (YouTubeWidget) widget;
				pStatement = Connection.getConnection().prepareStatement(CREATE_YOUTUBEWIDGET);
				pStatement.setString(1, youTubeWidget.getUrl());
				pStatement.setBoolean(2, youTubeWidget.isShareable());
				pStatement.setBoolean(3, youTubeWidget.isExpandable());
				pStatement.setInt(4, widget.getId());
				pStatement.executeUpdate();
			}
			} catch (ClassNotFoundException | SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		try {
	        			pStatement.close();
	        			Connection.closeConnection();
	        			} catch (SQLException e) {
	        				e.printStackTrace();
	        				}
	        		}      
			return res;
		}

	@Override
	public int deleteWidget(int widgetId) {
		
		int res = 0;
		
		try {
			pStatement = Connection.getConnection().prepareStatement(DELETE_WIDGET);
			pStatement.setInt(1, widgetId);
			res = pStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
        	e.printStackTrace();
        	} finally {
        		try {
        			pStatement.close();
        			Connection.closeConnection();
        			} catch (SQLException e) {
        				e.printStackTrace();
        				}
        		}      
		return res;
		}
	}
