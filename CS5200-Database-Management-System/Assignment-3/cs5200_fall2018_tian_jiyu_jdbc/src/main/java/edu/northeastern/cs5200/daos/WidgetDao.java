package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Widget;

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
	
    private PreparedStatement pStatement1 = null;
    private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
	private final String CREATE_WIDGET = "INSERT INTO widget (id, name, width, height, dtype, cssClass,cssStyle,text,`order`,url,src, size, html, pagId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String FIND_ALL_WIDGET = "select * from widget";
	private final String FIND_WIDGET_BY_ID = "select * from widget where widget.id=?";
	private final String FIND_WIDGET_BY_PAGE = "select * from widget where widget.pageId=?";
	private final String UPDATE_WIDGET = "UPDATE widget SET name=?, width=?, height=?, dtype=?, cssClass=?,cssStyle=?, text=? ,`order`=? ,url=?,src=?, size=?, html=? where widget.id=?";
	private final String DELETE_WIDGET = "DELETE from widget where widget.id=?";
	
	@Override
	public void createWidgetForPage(int pageId, Widget widget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Widget> findAllWidgets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Widget findWidgetById(int widgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Widget> findWidgetsForPage(int pageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateWidget(int widgetId, Widget widget) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteWidget(int widgetId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
