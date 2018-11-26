const mongoose = require('mongoose')
const questionSchema = require('./question.schema.server')
const questionWidgetSchema = mongoose.Schema({
    questions: [{
        type: Number,
        ref: 'QuestionModel'
    }]
}, {collection: 'question-widgets'})
module.exports = questionWidgetSchema;