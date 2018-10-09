
# 0. Create new schema

DROP SCHEMA IF EXISTS `assignment2`;

CREATE SCHEMA `assignment2` DEFAULT CHARACTER SET utf8 ;

# 1. Create tables person, developer and user

CREATE TABLE `assignment2`.`person` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NULL,
    `last_name` VARCHAR(255) NULL,
    `username` VARCHAR(255) NULL,
    `password` VARCHAR(255) NULL,
    `email` VARCHAR(255) NULL,
    `dob` DATE NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `assignment2`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_agreement` TINYINT NULL DEFAULT 1,
    `user_key` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `user_person_generalization` FOREIGN KEY (`id`)
        REFERENCES `assignment2`.`person` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`developer` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `developer_key` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `developer_person_generalization` FOREIGN KEY (`id`)
        REFERENCES `assignment2`.`person` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`address` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT NULL,
    `street1` VARCHAR(255) NULL,
    `street2` VARCHAR(255) NULL,
    `city` VARCHAR(255) NULL,
    `state` VARCHAR(255) NULL,
    `zip` VARCHAR(255) NULL,
    `primary` TINYINT NULL,
    PRIMARY KEY (`id`),
    INDEX `address_person_composition_idx` (`person_id` ASC),
    CONSTRAINT `address_person_composition` FOREIGN KEY (`person_id`)
        REFERENCES `assignment2`.`person` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`phone` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT NULL,
    `phone` VARCHAR(255) NULL,
    `primary` TINYINT NULL,
    PRIMARY KEY (`id`),
    INDEX `phone_person_composition_idx` (`person_id` ASC),
    CONSTRAINT `phone_person_composition` FOREIGN KEY (`person_id`)
        REFERENCES `assignment2`.`person` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

# 2. Create tables website, page, widget

CREATE TABLE `assignment2`.`website` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `created` DATE DEFAULT NULL,
    `updated` DATE DEFAULT NULL,
    `visits` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `assignment2`.`page` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    `created` DATE NULL,
    `updated` DATE NULL,
    `views` INT NULL,
    `website_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `page_website_composition_idx` (`website_id` ASC),
    CONSTRAINT `page_website_composition` FOREIGN KEY (`website_id`)
        REFERENCES `assignment2`.`website` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`widget_type` (
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`name`)
);

INSERT INTO `assignment2`.`widget_type` (`name`) VALUES ('youtube'), ('image'), ('heading'), ('html');

CREATE TABLE `assignment2`.`widget` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    `type` VARCHAR(255) NULL,
    `width` INT NULL,
    `height` INT NULL,
    `css_class` VARCHAR(255) NULL,
    `css_style` VARCHAR(255) NULL,
    `text` VARCHAR(255) NULL,
    `order` INT NULL,
    `url` VARCHAR(255) NULL DEFAULT NULL,
    `shareble` TINYINT NULL DEFAULT NULL,
    `expandable` TINYINT NULL DEFAULT NULL,
    `src` VARCHAR(255) NULL DEFAULT NULL,
    `size` INT NULL DEFAULT NULL,
    `html` VARCHAR(255) NULL DEFAULT NULL,
    `page_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `widget_type_enum_idx` (`type` ASC),
    CONSTRAINT `widget_page_composition` FOREIGN KEY (`page_id`)
        REFERENCES `assignment2`.`page` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `widget_type_enum` FOREIGN KEY (`type`)
        REFERENCES `assignment2`.`widget_type` (`name`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

# Create other tables 

CREATE TABLE `assignment2`.`priviledge` (
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`name`)
);

CREATE TABLE `assignment2`.`role` (
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`name`)
);

INSERT INTO `assignment2`.`priviledge` (`name`) VALUES ('create'), ('read'), ('update'), ('delete');
INSERT INTO `assignment2`.`role` (`name`) VALUES ('owner'), ('admin'), ('writer'), ('editor'), ('reviewer');

CREATE TABLE `assignment2`.`website_priviledge` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `priviledge` VARCHAR(255) NULL,
    `developer_id` INT NULL,
    `website_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `website_priviledge_enum_idx` (`priviledge` ASC),
    INDEX `websitepriviledge_developer_association_idx` (`developer_id` ASC),
    INDEX `websitepriviledge_website_association_idx` (`website_id` ASC),
    CONSTRAINT `website_priviledge_enum` FOREIGN KEY (`priviledge`)
        REFERENCES `assignment2`.`priviledge` (`name`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `websitepriviledge_developer_association` FOREIGN KEY (`developer_id`)
        REFERENCES `assignment2`.`developer` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `websitepriviledge_website_association` FOREIGN KEY (`website_id`)
        REFERENCES `assignment2`.`website` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`website_role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(255) NULL,
    `developer_id` INT NULL,
    `website_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `website_role_enum_idx` (`role` ASC),
    INDEX `websiterole_developer_association_idx` (`developer_id` ASC),
    INDEX `websiterole_website_association_idx` (`website_id` ASC),
    CONSTRAINT `website_role_enum` FOREIGN KEY (`role`)
        REFERENCES `assignment2`.`role` (`name`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `websiterole_developer_association` FOREIGN KEY (`developer_id`)
        REFERENCES `assignment2`.`developer` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `websiterole_website_association` FOREIGN KEY (`website_id`)
        REFERENCES `assignment2`.`website` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`page_priviledge` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `priviledge` VARCHAR(255) NULL,
    `developer_id` INT NULL,
    `page_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `page_priviledge_enum_idx` (`priviledge` ASC),
    INDEX `pagepriviledge_developer_association_idx` (`developer_id` ASC),
    INDEX `pagepriviledge_page_association_idx` (`page_id` ASC),
    CONSTRAINT `page_priviledge_enum` FOREIGN KEY (`priviledge`)
        REFERENCES `assignment2`.`priviledge` (`name`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `pagepriviledge_developer_association` FOREIGN KEY (`developer_id`)
        REFERENCES `assignment2`.`developer` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `pagepriviledge_page_association` FOREIGN KEY (`page_id`)
        REFERENCES `assignment2`.`page` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `assignment2`.`page_role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(255) NULL,
    `developer_id` INT NULL,
    `page_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `pagerole_developer_association_idx` (`developer_id` ASC),
    INDEX `page_role_enum_idx` (`role` ASC),
    INDEX `pagerole_page_association_idx` (`page_id` ASC),
    CONSTRAINT `page_role_enum` FOREIGN KEY (`role`)
        REFERENCES `assignment2`.`role` (`name`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `pagerole_developer_association` FOREIGN KEY (`developer_id`)
        REFERENCES `assignment2`.`developer` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `pagerole_page_association` FOREIGN KEY (`page_id`)
        REFERENCES `assignment2`.`page` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
);
