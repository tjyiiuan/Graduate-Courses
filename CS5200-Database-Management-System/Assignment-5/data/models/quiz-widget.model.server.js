const mongoose = require('mongoose')
const quizwidgetSchema = require('./quiz-widget.schema.server')
const quizwidgetModel = mongoose.model('QuizWidgetModel', quizwidgetSchema)
module.exports = quizwidgetModel;