CREATE SCHEMA `assignment2` DEFAULT CHARACTER SET utf8 ;

--#####################################################--

CREATE TABLE `assignment2`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `dob` DATE NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `assignment2`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_agreement` TINYINT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_person_generalization`
    FOREIGN KEY (`id`)
    REFERENCES `assignment2`.`person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`developer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `developerKey` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `developer_person_generalization`
    FOREIGN KEY (`id`)
    REFERENCES `assignment2`.`person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `personid` INT NULL,
  `street1` VARCHAR(45) NULL,
  `street2` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  `primary` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `address_person_composition_idx` (`personid` ASC),
  CONSTRAINT `address_person_composition`
    FOREIGN KEY (`personid`)
    REFERENCES `assignment2`.`person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`phone` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `personid` INT NULL,
  `phone` VARCHAR(45) NULL,
  `primary` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `phone_person_composition_idx` (`personid` ASC),
  CONSTRAINT `phone_person_composition`
    FOREIGN KEY (`personid`)
    REFERENCES `assignment2`.`person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

--#####################################################--

CREATE TABLE `assignment2`.`website` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  `visits` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `assignment2`.`page` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `created` DATE NULL,
  `updated` DATE NULL,
  `views` INT NULL,
  `website_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `page_website_composition_idx` (`website_id` ASC),
  CONSTRAINT `page_website_composition`
    FOREIGN KEY (`website_id`)
    REFERENCES `assignment2`.`website` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`widget_type` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`));

INSERT INTO `assignment2`.`widget_type` (`name`) VALUES ('youtube'), ('image'), ('heading'), ('html');

CREATE TABLE `assignment2`.`widget` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `width` INT NULL,
  `height` INT NULL,
  `css_class` VARCHAR(45) NULL,
  `css_style` VARCHAR(45) NULL,
  `text` VARCHAR(45) NULL,
  `order` INT NULL,
  `url` VARCHAR(45) NULL DEFAULT NULL,
  `shareble` TINYINT NULL DEFAULT NULL,
  `expandable` TINYINT NULL DEFAULT NULL,
  `src` VARCHAR(45) NULL DEFAULT NULL,
  `size` INT NULL DEFAULT NULL,
  `html` VARCHAR(45) NULL DEFAULT NULL,
  `page_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `widget_type_enum_idx` (`type` ASC),
  CONSTRAINT `widget_page_composition`
    FOREIGN KEY (`id`)
    REFERENCES `assignment2`.`page` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `widget_type_enum`
    FOREIGN KEY (`type`)
    REFERENCES `assignment2`.`widget_type` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--#####################################################--

CREATE TABLE `assignment2`.`priviledge` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`));

CREATE TABLE `assignment2`.`role` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`));

INSERT INTO `assignment2`.`priviledge` (`name`) VALUES ('create'), ('read'), ('update'), ('delete');
INSERT INTO `assignment2`.`role` (`name`) VALUES ('owner'), ('admin'), ('writer'), ('editor'), ('reviewer');

CREATE TABLE `assignment2`.`website_priviledge` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `priviledge` VARCHAR(45) NULL,
  `developer_id` INT NULL,
  `website_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `website_priviledge_enum_idx` (`priviledge` ASC),
  INDEX `websitepriviledge_developer_association_idx` (`developer_id` ASC),
  INDEX `websitepriviledge_website_association_idx` (`website_id` ASC),
  CONSTRAINT `website_priviledge_enum`
    FOREIGN KEY (`priviledge`)
    REFERENCES `assignment2`.`priviledge` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `websitepriviledge_developer_association`
    FOREIGN KEY (`developer_id`)
    REFERENCES `assignment2`.`developer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `websitepriviledge_website_association`
    FOREIGN KEY (`website_id`)
    REFERENCES `assignment2`.`website` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`website_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  `developer_id` INT NULL,
  `website_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `website_role_enum_idx` (`role` ASC),
  INDEX `websiterole_developer_association_idx` (`developer_id` ASC),
  INDEX `websiterole_website_association_idx` (`website_id` ASC),
  CONSTRAINT `website_role_enum`
    FOREIGN KEY (`role`)
    REFERENCES `assignment2`.`role` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `websiterole_developer_association`
    FOREIGN KEY (`developer_id`)
    REFERENCES `assignment2`.`developer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `websiterole_website_association`
    FOREIGN KEY (`website_id`)
    REFERENCES `assignment2`.`website` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`page_priviledge` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `priviledge` VARCHAR(45) NULL,
  `developer_id` INT NULL,
  `page_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `page_priviledge_enum_idx` (`priviledge` ASC),
  INDEX `pagepriviledge_developer_association_idx` (`developer_id` ASC),
  INDEX `pagepriviledge_page_association_idx` (`page_id` ASC),
  CONSTRAINT `page_priviledge_enum`
    FOREIGN KEY (`priviledge`)
    REFERENCES `assignment2`.`priviledge` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pagepriviledge_developer_association`
    FOREIGN KEY (`developer_id`)
    REFERENCES `assignment2`.`developer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `pagepriviledge_page_association`
    FOREIGN KEY (`page_id`)
    REFERENCES `assignment2`.`page` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `assignment2`.`page_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  `developer_id` INT NULL,
  `page_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `pagerole_developer_association_idx` (`developer_id` ASC),
  INDEX `page_role_enum_idx` (`role` ASC),
  INDEX `pagerole_page_association_idx` (`page_id` ASC),
  CONSTRAINT `page_role_enum`
    FOREIGN KEY (`role`)
    REFERENCES `assignment2`.`role` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pagerole_developer_association`
    FOREIGN KEY (`developer_id`)
    REFERENCES `assignment2`.`developer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `pagerole_page_association`
    FOREIGN KEY (`page_id`)
    REFERENCES `assignment2`.`page` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
