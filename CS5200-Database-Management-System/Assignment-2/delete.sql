USE `assignment2`;

#####################################################

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

#####################################################

DELETE FROM widget 
WHERE
    widget.order = (SELECT 
        o
    FROM
        (SELECT 
            MAX(widget.order) AS o
        FROM
            widget
        JOIN page ON widget.page_id = page.id
            AND page.title = 'Contact') AS ord) LIMIT 1;

#####################################################

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
        MAX(page.updated)
    FROM
        (SELECT 
            *
        FROM
            page) AS up) LIMIT 1;

#####################################################


DELETE FROM website 
WHERE
    website.name = 'CNET';

#####################################################
