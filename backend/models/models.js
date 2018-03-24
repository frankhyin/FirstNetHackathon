var mongoose = require('mongoose');
var connect = process.env.MONGODB_URI;
var Schema = mongoose.Schema;

mongoose.connect(connect);

var textSchema = Schema({
    id: {
        type: String,
        required: false
    },
    message: {
        type: String,
        required: false
    },
    location: {
        type: String,
        required: false
    },
    time: {
        type: String,
        required: false
    },
    created_at: { type : Date, default: Date.now }
});

var Text = mongoose.model('Text', textSchema);

module.exports = {
    Text: Text,
  };
