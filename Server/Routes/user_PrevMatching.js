module.exports = function(app,db){
    var express = require('express');
    var router = express.Router();
    matching = require('../Global/global').matching;
    router.post('/',function(req,res,next){
        matching.prevList(req,res,db);
    });
    return router;
};