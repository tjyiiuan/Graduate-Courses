const mongoose = require('mongoose')
const student = require('./student.model.server')
const question = require('./question.model.server')
const answerSchema = mongoose.Schema({
    _id: Number,
    trueFalseAnswer: Boolean,
    multipleChoiceAnswer: Number,
    student: {type: Number, ref: 'student'},
    question: {type: Number, ref: 'question'}
}, {collection: 'answers'})
module.exports = answerSchema;