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

var nlpParameters = {
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

/* Watson tone analyzer set up */
var ToneAnalyzerV3 = require('watson-developer-cloud/tone-analyzer/v3');
var tone_analyzer = new ToneAnalyzerV3({
    username: '9e29b6b8-7e44-47cb-81fb-9fa0e997567d',
    password: 'z1UceMUCmvcQ',
    version_date: '2017-09-21'
});

var toneParameters = {
    'content_type': 'text/plain'
};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/texts', function(req, res, next) {
    Text.find({})
    .then(function(result) {
        res.send(
            result
);
    })
    .catch(function(error) {
        res.send({
            success: false,
            error: error
        })
    })
})

router.get('/newestText', function(req, res, next) {
    Text.find({}).sort({_id: -1}).limit(1)
    .then(function(result) {
        res.send({
            success: true,
            newestText: result
        });
    })
    .catch(function(error) {
        res.send({
            success: false,
            error: error
        })
    })
})

// Post to the database.
router.post('/text/new', function(req, res, next) {
    console.log("message body" , req.body);
    nlpParameters.text = req.body.message;
    natural_language_understanding.analyze(nlpParameters, function(err, response) {
        if (err)
            console.log('error:', err);
        else
            console.log(JSON.stringify(response, null, 2));
    });

    // ToDo how to use for plain text
    toneParameters.tone_input = req.body.text;
    tone_analyzer.toneChat(toneParameters, function(error, response) {
            if (error)
                console.log('error:', error);
            else
                console.log( JSON.stringify(response, null, 2));
        }
    );

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
