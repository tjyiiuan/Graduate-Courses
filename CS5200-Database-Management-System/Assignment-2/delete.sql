USE `assignment2`;


# 1. Delete developer

DELETE FROM address 
WHERE
    address.id = (SELECT 
        id
    FROM
        (SELECT 
            a.id
        FROM
            address a
        JOIN person p ON a.person_id = p.id
        
        WHERE
            p.username = 'alice' AND a.primary = 1) AS target_id);

# 2. Delete widget

DELETE FROM widget 
WHERE
    widget.id IN (SELECT 
        *
    FROM
        (SELECT 
            wd.id AS wdid
        FROM
            widget wd
        JOIN page ON wd.page_id = page.id
            AND page.title = 'Contact') AS wdid)
    AND widget.order = (SELECT 
        o
    FROM
        (SELECT 
            MAX(widget.order) AS o
        FROM
            widget
        JOIN page ON widget.page_id = page.id
            AND page.title = 'Contact') AS ord) LIMIT 1;


# 3. Delete page

DELETE FROM page 
WHERE
    page.website_id = (SELECT 
        id
    FROM
        (SELECT 
            page.id
        FROM
            page
        JOIN website w ON page.website_id = w.id
            AND w.name = 'Wikipedia') AS ids)
    AND page.updated = (SELECT 
        up
    FROM
        (SELECT 
            MAX(page.updated) AS up
        FROM
            page
        JOIN website w ON page.website_id = w.id
            AND w.name = 'Wikipedia') AS up) LIMIT 1;

# 4. Delete website

DELETE FROM website 
WHERE
    website.name = 'CNET';

