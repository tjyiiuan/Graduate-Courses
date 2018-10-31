package edu.northeastern.cs5200;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.daos.*;
import edu.northeastern.cs5200.models.*;

public class hw_jdbc_tian_jiyu {
	
    public static void main(String[] args) {
    	
        DeveloperDao devdao = DeveloperDao.getInstance();
        UserDao userdao = UserDao.getInstance();
        RoleDao roledao = RoleDao.getInstance();
        WebsiteDao webdao = WebsiteDao.getInstance();
        PageDao pagedao = PageDao.getInstance();
        WidgetDao widdao = WidgetDao.getInstance();
        
    	// Developer, User, Phone, Address        
        
        Collection<Phone> aliceph = new ArrayList<Phone>();
        Phone alicep1 = new Phone("234-345-4566",false,12);
        Phone alicep2 = new Phone("123-234-3456",true,12);
        aliceph.add(alicep1);
        aliceph.add(alicep2);

        Collection<Address> alicead = new ArrayList<Address>();
        Address alicea1 = new Address("123 Adam St.","","Alton","","01234",true,12);
        Address alicea2 = new Address("234 Birch St","","Boston","","02345",false,12);
        alicead.add(alicea1);
        alicead.add(alicea2);

        Collection<Phone> bobph = new ArrayList<Phone>();
        Phone bobp1 = new Phone("345-456-5677",false,23);
        bobph.add(bobp1);

        Collection<Address> bobad = new ArrayList<Address>();
        Address boba1 = new Address("345 Charles St.","","Chelms","","03455",true,23);
        Address boba2 = new Address("456 Down St.","","Dalton","","04566",false,23);
        Address boba3 = new Address("543 East St.","","Everett","","01112",false,23);
        bobad.add(boba1);
        bobad.add(boba2);
        bobad.add(boba3);

        Collection<Phone> charlieph = new ArrayList<Phone>();
        Phone charliep1 = new Phone("321-432-5435",true,34);
        Phone charliep2 = new Phone("432-432-5433",false,34);
        Phone charliep3 = new Phone("543-543-6544",false,34);
        charlieph.add(charliep1);
        charlieph.add(charliep2);
        charlieph.add(charliep3);

        Collection<Address> charliead = new ArrayList<Address>();
        Address charliea1 = new Address("654 Frank St.","","Foulton","","04322",true,34);
        charliead.add(charliea1);

        Developer al = new Developer(12,"Alice","Wonder","alice","alice","alice@wonder.com",null,alicead,aliceph, "4321rewq");
        devdao.createDeveloper(al);

        Developer bo = new Developer(23,"Bod","Marley","bob","bob","bob@marley.com",null,bobad,bobph, "5432trew");
        devdao.createDeveloper(bo);

        Developer ch = new Developer(34,"Charles","Garcia","charlie","charlie","chuch@garcia.com",null,charliead,charlieph, "6543ytre");
        devdao.createDeveloper(ch);

        User dan = new User(45,"Dan","Martin","dan","dan","dan@martin.com");
        userdao.createUser(dan);

        User ed = new User(56,"Ed","Karaz","ed","ed","ed@kar.com");
        userdao.createUser(ed);
        
        // Website, Page, Widget
        
        java.util.Date websiteDate = new java.util.Date();
        
		Developer developer;
		Website website;
		int developerId;
        
		developer = devdao.findDeveloperByUsername("alice");
		developerId = developer.getId();
		website = new Website(123, "Facebook", "an online media and social networking service", websiteDate, websiteDate, 1234234);
		webdao.createWebsiteForDeveloper(developerId, website);
		website = new Website(456, "CNN", "an American basic cable and satellite television news channel", websiteDate, websiteDate,6543345);
		webdao.createWebsiteForDeveloper(developerId, website);

		developer = devdao.findDeveloperByUsername("bob");
		developerId = developer.getId();
		website = new Website(234, "Twitter", "an online news and social networking service", websiteDate, websiteDate, 4321543);
		webdao.createWebsiteForDeveloper(developerId, website);
		website = new Website(567, "CNET", "an American media website that publishes reviews, news, articles, blogs, podcasts and videos on technology and consumer electronics",
				websiteDate, websiteDate, 5433455);
		webdao.createWebsiteForDeveloper(developerId, website);

		developer = devdao.findDeveloperByUsername("charlie");
		developerId = developer.getId();
		website = new Website(345, "Wikipedia", "a free online encyclopedia", websiteDate, websiteDate, 3456654);
		webdao.createWebsiteForDeveloper(developerId, website);
		website = new Website(678, "Gizmodo", "a design, technology, science and science fiction website that also writes articles on politics",
				websiteDate, websiteDate, 4322345);
		webdao.createWebsiteForDeveloper(developerId, website);

		Developer alice = devdao.findDeveloperByUsername("alice");
		int aliceId = alice.getId();
		Developer bob = devdao.findDeveloperByUsername("bob");
		int bobId = bob.getId();
		Developer charlie = devdao.findDeveloperByUsername("charlie");
		int charlieId = charlie.getId();

		roledao.assignWebsiteRole(aliceId, 123, 1);
		roledao.assignWebsiteRole(bobId, 123, 4);
		roledao.assignWebsiteRole(charlieId, 123, 2);

		roledao.assignWebsiteRole(bobId, 234, 1);
		roledao.assignWebsiteRole(charlieId, 234, 4);
		roledao.assignWebsiteRole(aliceId, 234, 2);

		roledao.assignWebsiteRole(charlieId, 345, 1);
		roledao.assignWebsiteRole(aliceId, 345, 4);
		roledao.assignWebsiteRole(bobId, 345, 2);

		roledao.assignWebsiteRole(aliceId, 456, 1);
		roledao.assignWebsiteRole(bobId, 456, 4);
		roledao.assignWebsiteRole(charlieId, 456, 2);

		roledao.assignWebsiteRole(bobId, 567, 1);
		roledao.assignWebsiteRole(charlieId, 567, 4);
		roledao.assignWebsiteRole(aliceId, 567, 2);

		roledao.assignWebsiteRole(charlieId, 678, 1);
		roledao.assignWebsiteRole(aliceId, 678, 4);
		roledao.assignWebsiteRole(bobId, 678, 2);

		java.sql.Date createdate = java.sql.Date.valueOf("2018-09-05");
		java.sql.Date updatedate = java.sql.Date.valueOf("2018-10-24");
		
		int websiteId;
		websiteId = 567;
		Page Home = new Page(123, "Home", "Landing page", createdate, updatedate, 123434, websiteId);
		pagedao.createPageForWebsite(websiteId, Home);

		websiteId = 678;
		Page About = new Page(234, "About", "Website descriptio", createdate, updatedate, 234545, websiteId);
		pagedao.createPageForWebsite(websiteId, About);

		websiteId = 345;
		Page Contact = new Page(345, "Contact", "Addresses, phones and contact info", createdate, updatedate, 345656, websiteId);
		pagedao.createPageForWebsite(websiteId, Contact);

		websiteId = 456;
		Page Preferences = new Page(456, "Preferences", "Where users can configure their preferences", createdate, updatedate, 456776, websiteId);
		pagedao.createPageForWebsite(websiteId, Preferences);

		websiteId = 567;
		Page Profile = new Page(567, "Profile", "Users can configure their personal information", createdate, updatedate, 567878, websiteId);
		pagedao.createPageForWebsite(websiteId, Profile);


		Developer alicee = devdao.findDeveloperByUsername("alice");
		int aliceeId = alicee.getId();
		Developer bobb = devdao.findDeveloperByUsername("bob");
		int bobbId = bobb.getId();
		Developer charliee = devdao.findDeveloperByUsername("charlie");
		int charlieeId = charliee.getId();
		
		roledao.assignPageRole(aliceeId, 123, 4);
		roledao.assignPageRole(bobbId, 123, 5);
		roledao.assignPageRole(charlieeId, 123, 3);

		roledao.assignPageRole(bobbId, 234, 4);
		roledao.assignPageRole(charlieeId, 234, 5);
		roledao.assignPageRole(aliceeId, 234, 3);

		roledao.assignPageRole(charlieeId, 345, 4);
		roledao.assignPageRole(aliceeId, 345, 5);
		roledao.assignPageRole(bobbId, 345, 3);

		roledao.assignPageRole(aliceeId, 456, 4);
		roledao.assignPageRole(bobbId, 456, 5);
		roledao.assignPageRole(charlieeId, 456, 3);

		roledao.assignPageRole(bobbId, 567, 4);
		roledao.assignPageRole(charlieeId, 567, 5);
		roledao.assignPageRole(aliceeId, 567, 3);
	
		Widget widget;
		int pageId;

		pageId = 123;
		widget = new HeadingWidget(1, "head123", 0, 0, null, null, "Welcome", 0, pageId);
		widdao.createWidgetForPage(pageId, widget);

		pageId = 234;
		widget = new HtmlWidget(2, "post234", 0, 0, null, null, "<p>Lorem</p>", 0, pageId, null);
		widdao.createWidgetForPage(pageId, widget);

		pageId = 345;
		widget = new HeadingWidget(3, "head345", 0, 0, null, null, "Hi", 1, pageId, 0);
		widdao.createWidgetForPage(pageId, widget);

		widget = new HtmlWidget(4, "intro456", 0, 0, null, null, "<h1>Hi</h1>", 2, pageId, null);
		widdao.createWidgetForPage(pageId, widget);

		widget = new ImageWidget(5, "image345", 50, 100, null, null, null, 3, pageId, "/img/567.png");
		widdao.createWidgetForPage(pageId, widget);

		pageId = 456;
		widget = new YouTubeWidget(6, "video456", 400, 300, null, null, null, 0, pageId, "http://youtu.be/h67VX51QXiQ");
		widdao.createWidgetForPage(pageId, widget);
    }
    
    private void update() {		
    	
 		int developerId;
		int widgetId;
		int websiteId;   	
		
		DeveloperDao developerDao = DeveloperDao.getInstance();
		PageDao pageDao = PageDao.getInstance();
		RoleDao roleDao = RoleDao.getInstance();
		WebsiteDao websiteDao = WebsiteDao.getInstance();		
		WidgetDao widgetDao = WidgetDao.getInstance();
		
		Developer developer;
		Widget widget;
		Website website;
		
		developer = developerDao.findDeveloperByUsername("charlie");
		
		if (developer != null) {
			developerId = developer.getId();
			Collection<Phone> phones = developer.getPhoneNumbers();
			if (phones != null) {
				for (Phone phone : phones) {
				if (phone.isPrimary()) {
					phone.setPhone("333-444-5555");
					break;
			} else {}
				}
			}
			developer.setPhoneNumbers(phones);
			developerDao.updateDeveloper(developerId, developer);
		} else {}


		widgetId = 3;
		widget = widgetDao.findWidgetById(widgetId);
		widget.setOrder(3);
		widgetDao.updateWidget(widgetId, widget);

		widgetId = 4;
		widget = widgetDao.findWidgetById(4);
		widget.setOrder(1);
		widgetDao.updateWidget(widgetId, widget);

		widgetId = 5;
		widget = widgetDao.findWidgetById(5);
		widget.setOrder(2);
		widgetDao.updateWidget(widgetId, widget);


		
		websiteId = 567;
		website = websiteDao.findWebsiteById(websiteId);
		Collection<Page> pages = pageDao.findPagesForWebsite(websiteId);
		
		for (Page page : pages) {
			int pageId = page.getId();
			page.setTitle("CNET-" + page.getTitle());
			pageDao.updatePage(pageId, page);
		}

		
		int pageId = 123;
		Developer c = developerDao.findDeveloperByUsername("charlie");
		int cId = c.getId();
		Developer b = developerDao.findDeveloperByUsername("bob");
		int bId = b.getId();
		
		roleDao.deletePageRole(cId, pageId, 3);
		roleDao.deletePageRole(bId, pageId, 5);
		roleDao.assignPageRole(cId, pageId, 5);
		roleDao.assignPageRole(bId, pageId, 3);
	}

    
	private void DELETE() {
		
		int developerId;
		int pageId;
		int websiteId;	
		
		DeveloperDao developerDao = DeveloperDao.getInstance();
		PageDao pageDao = PageDao.getInstance();
		WebsiteDao websiteDao = WebsiteDao.getInstance();
		
		Developer developer;
		Page page;
		Website website;

		
		developer = developerDao.findDeveloperByUsername("alice");
		if (developer != null) {
			developerId = developer.getId();
			Collection<Address> addresses = developer.getAddresses();
			if (addresses == null) {
				return;
			} else {				
				for (Address address : addresses) {
					if (address.isPrimary()) {
						addresses.remove(address);
						break;
					}
				}
			}
			developer.setAddresses(addresses);
			developerDao.updateDeveloper(developerId, developer);
		} else {}


		pageId = 345;
		page = pageDao.findPageById(pageId);
		if (page != null) {
			Collection<Widget> widgets = page.getWidgets();
			if (widgets != null && !widgets.isEmpty()) {
				int max = 0;
				Widget maxWidget = new Widget();
				for (Widget widget : widgets) {
					if (widget.getOrder() > max) {
						max = widget.getOrder();
						maxWidget = widget;
					}
				}
				widgets.remove(maxWidget);
			} else {}
			page.setWidgets(widgets);
			pageDao.updatePage(pageId, page);
		} else {}


		websiteId = 345;
		website = websiteDao.findWebsiteById(websiteId);
		if (website != null) {
			Collection<Page> pages = website.getPages();
			if (pages != null && !pages.isEmpty()) {
				java.sql.Date date = java.sql.Date.valueOf("2017-01-01");
				Page maxPage = new Page();
				for (Page pg : pages) {
					java.sql.Date time = pg.getUpdated();
					if (date.compareTo(time) < 0) {
						date = time;
						maxPage = pg;
					}
				}
				pages.remove(maxPage);
			} else {}
			website.setPages(pages);
			websiteDao.updateWebsite(websiteId, website);
		} else {}


		websiteId = 567;
		website = websiteDao.findWebsiteById(websiteId);
		if (website != null) {
			websiteDao.deleteWebsite(websiteId);
		} else {}
	}
}
