USE `assignment3`;
DROP procedure IF EXISTS `getUnansweredQuestions`;

DELIMITER $$
USE `assignment3`$$
CREATE PROCEDURE `getUnansweredQuestions` ()
BEGIN
SELECT 
    qa.qid AS ID,
    qa.qtext AS Text,
    qa.module AS Module,
    MAX(qa.an) AS AnswerNumber
FROM
    (SELECT 
        q.text AS qtext,
            q.id AS qid,
            COUNT(a.id) AS an,
            SUM(correct_answer) AS ca,
            q.module
    FROM
        question q
    LEFT JOIN answer a ON q.id = a.question_id
    GROUP BY q.id
    HAVING ca = 0 OR MAX(a.correct_answer) IS NULL) AS qa
GROUP BY module;
END$$

DELIMITER ;
