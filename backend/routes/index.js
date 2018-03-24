var express = require('express');
var router = express.Router();
var models = require('../models/models');
const Text = models.Text;

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// Post to the database.
router.post('/text/new', function(req, res, next) {
    var new_text = new models.Text ({
        text: req.body.text
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
