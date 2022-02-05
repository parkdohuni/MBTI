module.exports = function(app,db){
    var express = require('express');
    var router = express.Router();
    router.post('/',function(req,res,next){
        signup = require('../Global/global').signup;
        signup.doSignup(req,res,db);
    });
    return router;
};