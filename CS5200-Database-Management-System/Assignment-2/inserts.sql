
#####################################################
INSERT INTO `assignment2`.`person` (`id`, `first_name`, `last_name`, `username`, `password`, `email`) VALUES 
            (12, 'Alice', 'Wonder', 'alice', 'alice', 'alice@wonder.com'), 
            (23, 'Bob', 'Marley', 'bob', 'bob', 'bob@marley.com'), 
            (34, 'Charle', 'Garcia', 'charlie', 'charlie', 'chuch@garcia.com'),
            (45, 'Dan', 'Martin', 'dan', 'dan', 'dan@martin.com'), 
            (56, 'Ed', 'Karaz', 'ed', 'ed', 'ed@kar.com');

INSERT INTO `assignment2`.`developer` (`id`, `developer_key`) VALUES 
            (12, '4321rewq'),
            (23, '5432trew'), 
            (34, '6543ytre');

INSERT INTO `assignment2`.`user` (`id`, `user_key`) VALUES 
            (45, '7654fda'), 
            (56, '5678dfgh');

#####################################################

INSERT INTO `assignment2`.`website` (`id`, `name`, `description`, `created`, `updated`, `visits`) VALUES 
            (123, 'Facebook', 'an online social media and social networking service', CURDATE(), CURDATE(), 1234234), 
            (234, 'Twitter', 'an online news and social networking service', CURDATE(), CURDATE(), 4321543),
            (345, 'Wikipedia', 'a free online encyclopedia', CURDATE(), CURDATE(), 3456654),
            (456, 'CNN', 'an American basic cable and satellite television news channel', CURDATE(), CURDATE(), 6543345),
            (567, 'CNET', 'an American media website that publishes reviews, news, articles, blogs, podcasts and videos on technology and consumer electronics', CURDATE(), CURDATE(), 5433455),
            (678, 'Gizmodo', 'a design, technology, science and science fiction website that also writes articles on politics', CURDATE(), CURDATE(), 4322345);

INSERT INTO `assignment2`.`website_role` (`role`, `developer_id`, `website_id`) VALUES 
            ('owner', 12, 123), ('editor', 23, 123), ('admin', 34, 123),
            ('owner', 23, 234), ('editor', 34, 234), ('admin', 12, 234),
            ('owner', 34, 345), ('editor', 12, 345), ('admin', 23, 345),
            ('owner', 12, 456), ('editor', 23, 456), ('admin', 34, 456),
            ('owner', 23, 567), ('editor', 34, 567), ('admin', 12, 567),
            ('owner', 34, 678), ('editor', 12, 678), ('admin', 23, 678);

#####################################################

INSERT INTO `assignment2`.`page` (`id`, `title`, `description`, `created`, `updated`, `views`, `website_id`) VALUES 
            (123, 'Home', 'Landing page', '2018-09-05', '2018-10-08', '123434', 567),
            (234, 'About', 'Website description', '2018-09-05', '2018-10-08', '234545', 678),
            (345, 'Contact', 'Addresses, phones, and contact info', '2018-09-05', '2018-10-08', '345656', 345),
            (456, 'Preferences', 'Where users can configure their preferences', '2018-09-05', '2018-10-08', '456776', 456),
            (567, 'Profile', 'Users can configure their personal information', '2018-09-05', '2018-10-08', '567878', 567);

INSERT INTO `assignment2`.`page_role` (`role`, `developer_id`, `page_id`) VALUES 
            ('editor', 12, 123), ('reviewer', 23, 123), ('writer', 34, 123),
            ('editor', 23, 234), ('reviewer', 34, 234), ('writer', 12, 234),
            ('editor', 34, 345), ('reviewer', 12, 345), ('writer', 23, 345),
            ('editor', 12, 456), ('reviewer', 23, 456), ('writer', 34, 456),
            ('editor', 23, 567), ('reviewer', 34, 567), ('writer', 12, 567);

#####################################################

INSERT INTO `assignment2`.`widget` (`id`, `name`, `type`, `width`, `height`, `text`, `order`, `url`, `page_id`) VALUES 
            (123, 'head123', 'heading', null, null, 'Welcome', 0, null, 123),
            (234, 'post234', 'html', null, null, '<p>Lorem</p>', 0, null, 234),
            (345, 'head345', 'heading', null, null, 'Hi', 1, null,  345),
            (456, 'intro456', 'html', null, null, '<h1>Hi</h1>', 2, null, 345),
            (567, 'image345', 'image', 50, 100, null, 3, '/img/567.png', 345),
            (678, 'video456', 'youtube', 400, 300, null, 0, 'https://youtu.be/h67VX51QXiQ', 456);

#####################################################

INSERT INTO `assignment2`.`address` (`person_id`, `street1`, `city`, `zip`, `primary`) VALUES 
            (12, '123 Adam St.', 'Alton', '01234', 1),
            (12, '234 Birch St.', 'Boston', '02345', 0),
            (23, '345 Charles St.', 'Chelms', '03455', 1),
            (23, '456 Down St.', 'Dalton', '04566', 0),
            (23, '543 East St.', 'Everett', '01112', 0), 
            (34, '654 Frank St.', 'Foulton', '04322', 1);
            
INSERT INTO `assignment2`.`phone` (`person_id`, `phone`, `primary`) VALUES 
            (12, '123-234-3456', 1),
            (12, '234-345-4566', 0),
            (23, '345-456-5677', 1),
            (34, '321-432-5435', 1),
            (34, '432-432-5433', 0),
            (34, '543-543-6544', 0);
