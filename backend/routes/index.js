var express = require('express');
var router = express.Router();
var models = require('../models/models');
const Text = models.Text;

var NaturalLanguageUnderstandingV1 = require('watson-developer-cloud/natural-language-understanding/v1.js');

/* Watson natural language processing setup */
var natural_language_understanding = new NaturalLanguageUnderstandingV1({
    'username': '4d6b45d2-222d-43bc-8edc-2022fc65a036',
    'password': 'SfjAeBjmpZOw',
    'version': '2018-03-16'
});

var parameters = {
    'features': {
        'entities': {
            'emotion': true,
            'sentiment': true,
            'limit': 2
        },
        'keywords': {
            'emotion': true,
            'sentiment': true,
            'limit': 2
        },
        'concepts': {
            'limit': 3
        },
        'emotion': {
            'targets': []
        },
        'sentiment': {
            'targets': [
            ]
        }

    }
}

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// Post to the database.
router.post('/text/new', function(req, res, next) {
    console.log("message body" , req.body);
    parameters.text = req.body.message;
    natural_language_understanding.analyze(parameters, function(err, response) {
        if (err)
            console.log('error:', err);
        else
            console.log(JSON.stringify(response, null, 2));
    });

    var new_text = new models.Text ({
        id: req.body.id,
        message: req.body.message,
        location: req.body.location,
        time: req.body.time
    });
    new_text.save(function(error, text) {
        if (error) {
            console.log("Error: ", error)
            res.json({success: false, error: error})
        } else {
            res.json({success: true})
        }
    });
});

module.exports = router;
