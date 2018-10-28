package edu.northeastern.cs5200.daos;

import java.util.Collection;

import edu.northeastern.cs5200.models.Page;

public interface PageImpl {
	
	void createPageForWebsite(int websiteId,Page page);
	
	Collection<Page> findAllPages();
	
	Page findPageById(int pageId);
	
	Collection<Page> findPagesForWebsite(int websiteId);
	
	int updatePage(int pageId, Page page);
	
	int deletePage(int pageId);
}