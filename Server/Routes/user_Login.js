
module.exports = function(app,db){
    var express = require('express');
    var router = express.Router();
    login = require('../Global/global').login;
    router.post('/',function(req,res,next){
        login.doLogin(req,res,db);
    });
    return router;
};