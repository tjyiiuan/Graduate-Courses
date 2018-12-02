const studentModel = require('../models/student.model.server');
const questionModel = require('../models/question.model.server');
const answerModel = require('../models/answer.model.server');


truncateDatabase = () =>
    studentModel.remove({}, function(err){
        if (err) {
            console.log(err.message)
        } else {
            console.log("ALL Students Removed.");
        }
    }).then(() => questionModel.remove({}, function(err){
        if (err) {
            console.log(err.message)
        } else {
            console.log("ALL Questions Removed.");
        }
    })).then(() => answerModel.remove({}, function(err){
        if (err) {
            console.log(err.message)
        } else {
            console.log("ALL Answers Removed.");
        }
    }));


populateDatabase = () =>
    studentModel.create({
        _id: 123,
        username: 'alice',
        password: 'alice',
        firstName: 'Alice',
        lastName: 'Wonderland',
        gradYear: 2020,
        scholarship: 15000
    }).then(newStudent => console.log(newStudent))
        .then(() => studentModel.create({
            _id: 234,
            username: 'bob',
            password: 'bob',
            firstName: 'Bob',
            lastName: 'Hope',
            gradYear: 2021,
            scholarship: 12000
        }).then(newStudent => console.log(newStudent)))
        .then(() => questionModel.create({
            _id: 321,
            question: 'Is the following schema valid?',
            points: 10,
            questionType: 'TRUE_FALSE',
            trueFalse: {
                _id: 321,
                isTrue: false
            }
        }).then(newQuestion => console.log(newQuestion)))
        .then(() => questionModel.create({
        _id: 432,
        question: 'DAO stands for Dynamic Access Object.',
        points: 10,
        questionType: 'TRUE_FALSE',
        trueFalse: {
            _id: 432,
            isTrue: false
        }
    }).then(newQuestion => console.log(newQuestion)))
        .then(() => questionModel.create({
            _id: 543,
            question: 'What does JPA stand for?',
            points: 10,
            questionType: 'MULTIPLE_CHOICE',
            multipleChoice: {
                _id: 543,
                choices: 'Java Persistence API,' +
                    'Java Persisted Application,' +
                    'JavaScript Persistence API,' +
                    'JSON Persistent Associations',
                correct: 1
            }
        }).then(newQuestion => console.log(newQuestion)))
        .then(() => questionModel.create({
            _id: 654,
            question: 'What does ORM stand for?',
            points: 10,
            questionType: 'MULTIPLE_CHOICE',
            multipleChoice: {
                _id: 321,
                choices: 'Object Relational Model,' +
                    'Object Relative Markup,' +
                    'Object Reflexive Model,' +
                    'Object Relational Mapping',
                correct: 4
            }
        }).then(newQuestion => console.log(newQuestion)))
        .then(() => answerModel.create({
            _id: 123,
            trueFalseAnswer: true,
            student: 123,
            question: 321
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 234,
            trueFalseAnswer: false,
            student: 123,
            question: 432
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 345,
            multipleChoiceAnswer: 1,
            student: 123,
            question: 543
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 456,
            multipleChoiceAnswer: 2,
            student: 123,
            question: 654
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 567,
            trueFalseAnswer: false,
            student: 234,
            question: 321
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 678,
            trueFalseAnswer: true,
            student: 234,
            question: 432
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 789,
            multipleChoiceAnswer: 3,
            student: 234,
            question: 543
        }).then(newAnswer => console.log(newAnswer)))
        .then(() => answerModel.create({
            _id: 890,
            multipleChoiceAnswer: 4,
            student: 234,
            question: 654
        }).then(newAnswer => console.log(newAnswer)));

createStudent = student =>
    studentModel.create(student);

deleteStudent = studentId =>
    studentModel.remove({_id: studentId})
//        .then(console.log("Student Deleted."))
        .then(() => answerModel.remove({student: studentId}));

createQuestion = question =>
    questionModel.create(question);

deleteQuestion = questionId =>
    questionModel.remove({_id: questionId});
//        .then(() => console.log("Question Deleted."));

answerQuestion = (answer) =>
    answerModel.create(answer);

deleteAnswer = answerId =>
    answerModel.remove({_id: answerId});
//        .then(console.log("Answer Deleted."))

deleteAnswerByQuestion = questionId =>
    answerModel.remove({question: questionId});
//        .then(console.log("Answer Deleted."));

deleteAnswerByStudent = studentId =>
    answerModel.remove({student: studentId});
//        .then(console.log("Answer Deleted."));


findAllStudents = () =>
    studentModel.find();

findStudentById = studentId =>
    studentModel.findById(studentId);

findStudentByName = studentName =>
    studentModel.find({firstName: studentName});

findAllQuestions = () =>
    questionModel.find();

findQuestionById = questionId =>
    questionModel.findById(questionId);

findAllAnswers = () =>
    answerModel.find();

findAnswerById = answerId =>
    answerModel.findById(answerId);

findAnswerByStudent = studentId =>
    answerModel.find({student: studentId});

findAnswerByQuestion = questionId =>
    answerModel.find({question: questionId});


module.exports = {
    truncateDatabase,
    populateDatabase,
    createStudent,
    deleteStudent,
    createQuestion,
    deleteQuestion,
    answerQuestion,
    deleteAnswer,
    deleteAnswerByQuestion,
    deleteAnswerByStudent,
    findAllStudents,
    findStudentById,
    findAllQuestions,
    findQuestionById,
    findAllAnswers,
    findAnswerById,
    findAnswerByStudent,
    findAnswerByQuestion
};


