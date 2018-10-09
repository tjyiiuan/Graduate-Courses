USE `assignment2`;

#####################################################

#  1.a. Retrieve all developers

SELECT 
    *
FROM
    developer d
        JOIN
    person p ON p.id = d.id;

# 1.b. Retrieve a developer with id equal to 34 

SELECT 
    *
FROM
    developer d
        JOIN
    person p ON p.id = d.id
WHERE
    p.id = 34;

# 1.c. Retrieve all developers who have a role in Twitter other than owner

SELECT 
    p.*, d.*
FROM
    developer d
        JOIN
    person p ON p.id = d.id
        JOIN
    website_role wr ON wr.developer_id = d.id
        JOIN
    website w ON w.id = wr.website_id
WHERE
    wr.role <> 'owner'
        AND w.name = 'Twitter';

#. 1.d. Retrieve all developers who are page reviewers of pages with less than 300000 visits 

SELECT 
    p.*, d.*
FROM
    developer d
        JOIN
    person p ON p.id = d.id
        JOIN
    page_role pr ON pr.developer_id = d.id
        JOIN
    page ON page.id = pr.page_id
WHERE
    page.views < 300000
        AND pr.role = 'reviewer';

# 1.e. Retrieve the writer developer who added a heading widget to CNET home page (charlie)

SELECT 
    p.*, d.*
FROM
    developer d
        JOIN
    person p ON p.id = d.id
        JOIN
    page_role pr ON pr.developer_id = d.id
        JOIN
    page ON page.id = pr.page_id
        JOIN
    website w ON page.website_id = w.id
WHERE
    pr.role = 'writer'
        AND page.title = 'Home'
        AND w.name = 'CNET';
                                 

#####################################################

# 2.a Retrieve the website with the least number of visits

SELECT 
    w.*
FROM
    website w
WHERE
    w.visits = (SELECT 
            MIN(visits)
        FROM
            website);

# 2.b. Retrieve the name of a website whose id is 678 
    
SELECT 
    w.name
FROM
    website w
WHERE
    w.id = 678;

# 2.c. Retrieve all websites with videos reviewed by bob
   
SELECT DISTINCT
    w.*
FROM
    website w
        JOIN
    website_role wr ON w.id = wr.website_id
        JOIN
    page ON page.website_id = w.id
        JOIN
    widget wd ON wd.page_id = page.id
        JOIN
    page_role pr ON page.id = pr.page_id
        JOIN
    developer d ON pr.developer_id = d.id
        JOIN
    person p ON d.id = p.id
WHERE
    wd.type = 'youtube'
        AND pr.role = 'reviewer'
        AND p.username = 'bob';

# 2.d. Retrieve all websites where alice is an owner
        
SELECT 
    w.*
FROM
    website w
        JOIN
    website_role wr ON w.id = wr.website_id
        JOIN
    developer d ON wr.developer_id = d.id
        JOIN
    person p ON d.id = p.id
WHERE
    p.username = 'alice'
        AND wr.role = 'owner';

# 2.e. Retrieve all websites where charlie is an admin and get more than 6000000 visits            
 
SELECT 
    w.*
FROM
    website w
        JOIN
    website_role wr ON w.id = wr.website_id
        JOIN
    developer d ON wr.developer_id = d.id
        JOIN
    person p ON d.id = p.id
WHERE
    p.username = 'charlie'
        AND wr.role = 'admin'
        AND w.visits > 6000000;

#####################################################

# 3.a. Retrieve the page with the most number of views

SELECT 
    *
FROM
    page
WHERE
    page.views = (SELECT 
            MAX(views)
        FROM
            page);

# 3.b. Retrieve the title of a page whose id is 234

SELECT 
    page.title
FROM
    page
WHERE
    page.id = 234;

# 3.c. Retrieve all pages where alice is an editor

SELECT 
    page.*
FROM
    page
        JOIN
    page_role pr ON page.id = pr.page_id
        JOIN
    developer d ON pr.developer_id = d.id
        JOIN
    person p ON d.id = p.id
WHERE
    p.username = 'alice'
        AND pr.role = 'editor';

# 3.d. Retrieve the total number of pageviews in CNET

SELECT 
    SUM(page.views)
FROM
    page
        JOIN
    website w ON page.website_id = w.id
WHERE
    w.name = 'CNET';

# 3.e. Retrieve the average number of page views in the Web site Wikipedia

SELECT 
    AVG(page.views)
FROM
    page
        JOIN
    website w ON page.website_id = w.id
WHERE
    w.name = 'Wikipedia';

#####################################################

# 4.a. Retrieve all widgets in CNET Home page

SELECT 
    wd.*
FROM
    widget wd
        JOIN
    page ON wd.page_id = page.id
        AND page.title = 'Home'
        JOIN
    website w ON page.website_id = w.id
        AND w.name = 'CNET';
        
# 4.b. Retrieve all youtube widgets in CNN

SELECT 
    wd.*
FROM
    widget wd
        JOIN
    page ON wd.page_id = page.id
        JOIN
    website w ON page.website_id = w.id
        AND w.name = 'CNN'
WHERE
    wd.type = 'youtube';

# 4.c. Retrieve all image widgets on pages reviewed by Alice

SELECT 
    wd.*
FROM
    widget wd
        JOIN
    page ON wd.page_id = page.id
        JOIN
    website w ON page.website_id = w.id
        JOIN
    page_role pr ON page.id = pr.page_id
        JOIN
    developer d ON pr.developer_id = d.id
        JOIN
    person p ON d.id = p.id AND p.username = 'alice'
WHERE
    wd.type = 'image'
        AND pr.role = 'reviewer';

# 4.d. Retrieve how many widgets are in Wikipedia    
        
SELECT 
    COUNT(*)
FROM
    website w
        JOIN
    page ON w.id = page.website_id
        AND w.name = 'Wikipedia'
        JOIN
    widget wd ON page.id = wd.page_id;

#####################################################

# 5.a. Retrieve the names of all the websites where Bob has DELETE privileges

SELECT DISTINCT
    w.name
FROM
    website w
        JOIN
    website_priviledge wp ON w.id = wp.website_id
        JOIN
    developer d ON wp.developer_id = d.id
        JOIN
    person p ON d.id = p.id AND p.username = 'bob'
WHERE
    wp.priviledge = 'delete';

# 5.b. Retrieve the names of all the pages where Charlie has CREATE privileges

SELECT DISTINCT
    page.title
FROM
    page
        JOIN
    page_priviledge pp ON page.id = pp.page_id
        JOIN
    developer d ON pp.developer_id = d.id
        JOIN
    person p ON d.id = p.id AND p.username = 'charlie'
WHERE
    pp.priviledge = 'create';


