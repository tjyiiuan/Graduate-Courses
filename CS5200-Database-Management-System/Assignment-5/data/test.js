require('./db')();
const universityDao = require('./daos/university.dao.server');

truncateDatabase = () =>
    universityDao.truncateDatabase();

populateDatabase = () =>
    universityDao.populateDatabase();

testStudentsInitialCount = () =>
    universityDao.findAllStudents().then(allstudents => console.log("Students Count: ", allstudents.length));

testQuestionsInitialCount = () =>
    universityDao.findAllQuestions().then(allquestions => console.log("Questions Count: ", allquestions.length));

testAnswersInitialCount = () =>
    universityDao.findAllAnswers().then(allanswers => console.log("Answers Count: ", allanswers.length));

testDeleteAnswer = () =>
    universityDao.deleteAnswer(654);
    universityDao.findAllAnswers();
    universityDao.findAnswerByStudent(234);

testDeleteQuestion = () =>
    universityDao.deleteQuestion(321);
    universityDao.deleteAnswerByQuestion(321);

testDeleteStudent = () =>
    universityDao.deleteStudent(234);
    universityDao.deleteAnswerByStudent(234);
    universityDao.findAllStudents().then(allstudents => console.log("Students Count: ", allstudents.length));




module.exports = {
    truncateDatabase,
    populateDatabase,
    testStudentsInitialCount,
    testQuestionsInitialCount,
    testAnswersInitialCount,
    testDeleteAnswer,
    testDeleteQuestion,
    testDeleteStudent
}