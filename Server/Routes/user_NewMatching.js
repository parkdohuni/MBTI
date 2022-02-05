const Matching = require('../Class/Matching');

module.exports = function(app,db){
    var express = require('express');
    var router = express.Router();

    router.post('/',function(req,res,next){
        matching = require('../Global/global').matching;
        var UserData = {
            "id": req.body.id,
            "password":req.body.password,
            "pname":req.body.pname,
            "pgender":parseInt(req.body.pgender),
            "pmbti":parseInt(req.body.pmbti),
            "pdormitory":parseInt(req.body.pdormitory),
            "univ":parseInt(req.body.univ),
            "pmajor":parseInt(req.body.pmajor),
            "email":req.body.email,
            "psmoke":parseInt(req.body.psmoke),
            "pcomment":req.body.pcomment,
            "page":parseInt(req.body.page),
            "pcontact":req.body.pcontact,
            "pstime":parseInt(req.body.pstime),
            "pshour":parseInt(req.body.pshour),
            "hasMatchBefore":parseInt(req.body.hasMatchBefore),
            "isMatched":parseInt(req.body.isMatched)
        };
        matching.makeList(UserData,req,res,db);
    });
    return router;
};