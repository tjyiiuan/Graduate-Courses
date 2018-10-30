USE `assignment3`;
DROP procedure IF EXISTS `endorsedUsersForWeek`;

DELIMITER $$
USE `assignment3`$$
CREATE PROCEDURE `endorsedUsersForWeek` (IN start DATE, IN end DATE)
BEGIN
SELECT 
    pqa.id AS UserID,
    pqa.firstName AS FirstName,
    pqa.lastName AS LastName,
    pqa.an AS AnswerNumber
FROM
    (SELECT 
        COUNT(aid) AS an, p.*
    FROM
        person p
    JOIN (SELECT 
        a.id AS aid, q.id AS qid, a.postedBy AS pid
    FROM
        question q
    LEFT JOIN answer a ON q.id = a.questionID
    WHERE
        q.postedOn BETWEEN start AND end
            AND correctAnswer = 1) AS qa ON p.id = qa.pid
    ORDER BY an DESC
    LIMIT 5) AS pqa
ORDER BY pqa.firstName DESC;
END$$

DELIMITER ;
