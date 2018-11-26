const mongoose = require('mongoose');
const multiplechoicefalseSchema = mongoose.Schema({
    _id: Number,
    choices: String,
    correct: Number
});
module.exports = multiplechoicefalseSchema;
