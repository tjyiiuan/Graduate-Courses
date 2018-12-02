require('./db')();
const universityDao = require('./daos/university.dao.server');
const assert = require('assert');


testStudentsInitialCount = () =>
    universityDao.findAllStudents()
        .then(allstudents => assert(allstudents.length == 2))
        .catch(err => {
            console.log('testStudentsInitialCount failed');
            console.log(err.message);
        });

testQuestionsInitialCount = () =>
    universityDao.findAllQuestions()
        .then(allquestions => assert(allquestions.length == 4))
        .catch(err => {
            console.log('testQuestionsInitialCount failed');
            console.log(err.message);
        });

testAnswersInitialCount = () =>
    universityDao.findAllAnswers()
        .then(allanswers => assert(allanswers.length == 8))
        .catch(err => {
            console.log('testAnswersInitialCount failed');
            console.log(err.message);
        });

testDeleteAnswer = () =>
    universityDao.deleteAnswer(890)
        .then(() => universityDao.findAllAnswers()
            .then(allanswers => assert(allanswers.length == 7)))
        .then(() => universityDao.findAnswerByStudent(234)
            .then(allanswers => assert(allanswers.length == 3)))
        //.then(allanswers => console.log(allanswers.length)))
        .catch(err => {
            console.log('testDeleteAnswer failed');
            console.log(err.message);
        });

testDeleteQuestion = () =>
    universityDao.deleteQuestion(321)
        .then(() => universityDao.deleteAnswerByQuestion(321))
        .then(() => universityDao.findAllQuestions())
        .then(allquestions => assert(allquestions.length == 3))
        .catch(err => {
            console.log('testDeleteQuestion failed');
            console.log(err.message);
        });

testDeleteStudent = () =>
    universityDao.deleteStudent(234)
        .then(() => universityDao.deleteAnswerByStudent(234))
        .then(() => universityDao.findAllStudents())
        .then(allstudents => assert(allstudents.length == 1))
        .catch(err => {
            console.log('testDeleteStudent failed');
            console.log(err.message);
        });



universityDao.truncateDatabase()
    .then(() => universityDao.populateDatabase())
    .then(() => testStudentsInitialCount())
    .then(() => testQuestionsInitialCount())
    .then(() => testAnswersInitialCount())
    .then(() => testDeleteAnswer())
    .then(() => testDeleteQuestion())
    .then(() => testDeleteStudent());
