var NaturalLanguageUnderstandingV1 = require('watson-developer-cloud/natural-language-understanding/v1.js');

/* Watson natural language processing setup */
var natural_language_understanding = new NaturalLanguageUnderstandingV1({
    'username': '4d6b45d2-222d-43bc-8edc-2022fc65a036',
    'password': 'SfjAeBjmpZOw',
    'version': '2018-03-16'
});

module.exports = function () {

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
                'targets': []
            }

        }
    }

    var nlpService = {};

    nlpService.analyzeText = function (text) {
        parameters.text = text;
        return new Promise(function (resolve, reject) {
            natural_language_understanding.analyze(parameters, function(err, response) {
                if (err) {
                    console.log(err);
                    reject(err);
                }
                else {
                    console.log(response);
                    return resolve(response)
                }
            });
        })
    }


    return nlpService;
}