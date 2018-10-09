USE `assignment2`;

#####################################################

DROP VIEW IF EXISTS `developer_roles_and_priviledges`;

CREATE VIEW `developer_roles_and_priviledges` AS
    SELECT 
        p.first_name AS FirstName,
        p.last_name AS LastName,
        p.username AS UserName,
        p.email AS Email,
        w.name AS Website,
        w.visits AS WebVisits,
        w.updated AS WebUpdatedDate,
        wr.role AS WebRole,
        wp.priviledge AS WebPriviledge,
        page.title AS PageTitle,
        page.views AS PageViews,
        page.updated AS PageUpdatedDate,
        pr.role AS PageRole,
        pp.priviledge AS PagePriviledge
    FROM
        person p
            JOIN
        developer d ON p.id = d.id
            JOIN
        website_role wr ON d.id = wr.developer_id
            JOIN
        website w ON wr.website_id = w.id
            JOIN
        website_priviledge wp ON wp.developer_id = d.id
            AND wp.website_id = w.id
            LEFT JOIN
        page ON w.id = page.website_id
            LEFT JOIN
        page_role pr ON page.id = pr.page_id
            AND d.id = pr.developer_id
            LEFT JOIN
        page_priviledge pp ON page.id = pp.page_id
            AND d.id = pp.developer_id
