const mongoose = require('mongoose')
const Student = require('./student.model.server')
const Question = require('./question.model.server')
const answerSchema = mongoose.Schema({
    _id: Number,
    trueFalseAnswer: Boolean,
    multipleChoiceAnswer: Number,
    student: {type: Number, ref: 'Student'},
    question: {type: Number, ref: 'Question'}
}, {collection: 'answers'});
module.exports = answerSchema;