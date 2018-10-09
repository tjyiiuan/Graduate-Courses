USE `assignment2`;
SET SQL_SAFE_UPDATES = 0;

# 1. Update developer

UPDATE phone 
SET 
    phone = '333-444-5555'
WHERE
    phone.primary = 1
        AND phone.person_id = (SELECT 
            p.id
        FROM
            person p
        WHERE
            p.username = 'charlie');

# 2. Update widget

UPDATE widget 
SET 
    widget.order = (SELECT DISTINCT
            *
        FROM
            (SELECT 
                MAX(wd.order)
            FROM
                widget wd) AS max_order) + 1 - widget.order
WHERE
    widget.page_id = (SELECT 
            id
        FROM
            (SELECT DISTINCT
                page.id
            FROM
                widget
            JOIN page ON widget.page_id = page.id
            WHERE
                widget.name = 'head345') AS target_id);
    
# 3. Update page

UPDATE page
        JOIN
    website w ON page.website_id = w.id 
SET 
    page.title = CONCAT('CNET - ', page.title)
WHERE
    w.name = 'CNET';

# 4. Update roles

UPDATE page_role pr1
        JOIN
    page_role pr2 
SET 
    pr1.role = pr2.role,
    pr2.role = pr1.role
WHERE
    pr1.developer_id = (SELECT 
            id
        FROM
            person p
        WHERE
            p.username = 'charlie')
        AND pr2.developer_id = (SELECT 
            id
        FROM
            person p
        WHERE
            p.username = 'bob')
        AND pr1.page_id = (SELECT 
            page.id
        FROM
            page
                JOIN
            website w ON page.website_id = w.id
        WHERE
            page.title LIKE '%Home%'
                AND w.name = 'CNET')
        AND pr2.page_id = (SELECT 
            page.id
        FROM
            page
                JOIN
            website w ON page.website_id = w.id
        WHERE
            page.title LIKE '%Home%'
                AND w.name = 'CNET');
