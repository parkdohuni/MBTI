module.exports = function(app,db){
    var express = require('express');
    var router = express.Router();
    userManager = require('../Global/global').userManager
    router.post('/',function(req,res,next){
        userManager.getRoommate(req,res,db);
    });
    return router;
};