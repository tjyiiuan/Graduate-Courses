
#####################################################
# Website Priviledge
#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`website_role_AFTER_INSERT`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`website_role_AFTER_INSERT` AFTER INSERT ON `website_role` FOR EACH ROW
BEGIN
IF NEW.role = 'owner' OR NEW.role = 'admin' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('create', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('delete', NEW.developer_id, NEW.website_id);
 ELSEIF NEW.role = 'writer' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('create', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
ELSEIF NEW.role = 'editor' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
ELSEIF NEW.role = 'reviewer' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
END IF;
END$$
DELIMITER ;

#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`website_role_AFTER_UPDATE`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`website_role_AFTER_UPDATE` AFTER UPDATE ON `website_role` FOR EACH ROW
BEGIN
    DELETE FROM `website_priviledge` WHERE website_id = OLD.website_id AND developer_id = OLD.developer_id;
IF NEW.role = 'owner' OR NEW.role = 'admin' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('create', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('delete', NEW.developer_id, NEW.website_id);
ELSEIF NEW.role = 'writer' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('create', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
ELSEIF NEW.role = 'editor' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('update', NEW.developer_id, NEW.website_id);
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
ELSEIF NEW.role = 'reviewer' THEN
    INSERT INTO `website_priviledge` (priviledge, developer_id, website_id) VALUES ('read', NEW.developer_id, NEW.website_id);
END IF;
END$$
DELIMITER ;

#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`website_role_AFTER_DELETE`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`website_role_AFTER_DELETE` AFTER DELETE ON `website_role` FOR EACH ROW
BEGIN
DELETE FROM `website_priviledge` WHERE website_id = OLD.website_id AND developer_id = OLD.developer_id;
END
$$
DELIMITER ;

#####################################################
# Page Priviledge
#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`page_role_AFTER_INSERT`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`page_role_AFTER_INSERT` AFTER INSERT ON `page_role` FOR EACH ROW
BEGIN
IF NEW.role = 'owner' OR NEW.role = 'admin' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('create', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('delete', NEW.developer_id, NEW.page_id);
 ELSEIF NEW.role = 'writer' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('create', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
ELSEIF NEW.role = 'editor' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
ELSEIF NEW.role = 'reviewer' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
END IF;

END$$
DELIMITER ;

#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`page_role_AFTER_UPDATE`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`page_role_AFTER_UPDATE` AFTER UPDATE ON `page_role` FOR EACH ROW
BEGIN
    DELETE FROM `page_priviledge` WHERE page_id = OLD.page_id AND developer_id = OLD.developer_id;
IF NEW.role = 'owner' OR NEW.role = 'admin' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('create', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('delete', NEW.developer_id, NEW.page_id);
ELSEIF NEW.role = 'writer' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('create', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
ELSEIF NEW.role = 'editor' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('update', NEW.developer_id, NEW.page_id);
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
ELSEIF NEW.role = 'reviewer' THEN
    INSERT INTO `page_priviledge` (priviledge, developer_id, page_id) VALUES ('read', NEW.developer_id, NEW.page_id);
END IF;
END$$
DELIMITER ;

#####################################################

DROP TRIGGER IF EXISTS `assignment2`.`page_role_AFTER_DELETE`;

DELIMITER $$
USE `assignment2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `assignment2`.`page_role_AFTER_DELETE` AFTER DELETE ON `page_role` FOR EACH ROW
BEGIN
DELETE FROM `page_priviledge` WHERE page_id = OLD.page_id AND developer_id = OLD.developer_id;
END
$$
DELIMITER ;

