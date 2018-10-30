USE `assignment3`;
DROP procedure IF EXISTS `endorsedUsersForWeek`;

DELIMITER $$
USE `assignment3`$$
CREATE PROCEDURE `endorsedUsersForWeek` (IN start DATE, IN end DATE)
BEGIN
SELECT 
    pqa.id AS UserID,
    pqa.first_name AS FirstName,
    pqa.last_name AS LastName,
    pqa.an AS AnswerNumber
FROM
    (SELECT 
        COUNT(aid) AS an, p.*
    FROM
        person p
    JOIN (SELECT 
        a.id AS aid, q.id AS qid, a.posted_by AS pid
    FROM
        question q
    LEFT JOIN answer a ON q.id = a.question_id
    WHERE
        q.posted_on BETWEEN start AND end
            AND correct_answer = 1) AS qa ON p.id = qa.pid
    ORDER BY an DESC
    LIMIT 5) AS pqa
ORDER BY pqa.first_name DESC;
END$$

DELIMITER ;
