const mongoose = require('mongoose');
const truefalsefalseSchema = mongoose.Schema({
    _id: Number,
    isTrue: Boolean
});
module.exports = truefalsefalseSchema;
