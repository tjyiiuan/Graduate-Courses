USE `assignment2`;

#####################################################

SELECT 
    *
FROM
    developer d
        JOIN
    person ON person.id = d.id;

SELECT 
    *
FROM
    developer d
        JOIN
    person p ON p.id = d.id
WHERE
    p.id = 34;

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

SELECT 
    w.*
FROM
    website w
WHERE
    w.visits = (SELECT 
            MIN(visits)
        FROM
            website);

SELECT 
    w.name
FROM
    website w
WHERE
    w.id = 678;

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

SELECT 
    *
FROM
    page
WHERE
    page.views = (SELECT 
            MAX(views)
        FROM
            page);

SELECT 
    page.title
FROM
    page
WHERE
    page.id = 234;

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

SELECT 
    SUM(page.views)
FROM
    page
        JOIN
    website w ON page.website_id = w.id
WHERE
    w.name = 'CNET';

SELECT 
    AVG(page.views)
FROM
    page
        JOIN
    website w ON page.website_id = w.id
WHERE
    w.name = 'Wikipedia';

#####################################################

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


