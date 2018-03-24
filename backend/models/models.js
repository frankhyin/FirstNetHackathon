var mongoose = require('mongoose');
var connect = process.env.MONGODB_URI;
var Schema = mongoose.Schema;

mongoose.connect(connect);

var textSchema = Schema({
    text: {
        type: String,
        required: true
    },
});

var Text = mongoose.model('Text', textSchema);

module.exports = {
    Text: Text,
  };
