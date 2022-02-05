module.exports =class Signup{
    constructor(){

    }

    doSignup(req,res,db){
        db.query(`SELECT * FROM User WHERE id= '${req.body.id}'`,function( error, results, fields) {
            if (error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            } 
            else{
                if(results.length > 0)
                    res.send({"success":false,"reason":"duplicated id!"});
                else{
                    var User = {
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
                        "hasMatchBefore":false,
                        "isMatched":false
                    }
                    db.query('INSERT INTO User SET ?', User, function( error, results, fields) {
                        if (error){
                            console.log("error ocurred", error);
                            res.send({"success":false,"reason":"register error!!"});
                        }else{
                            res.send({
                                "success":true,
                                "id":User.id,
                                "password":User.password,
                                "pname":User.pname,
                                "pgender":User.pgender,
                                "pmbti":User.pmbti,
                                "pdormitory":User.pdormitory,
                                "univ":User.univ,
                                "pmajor":User.pmajor,
                                "email":User.email,
                                "psmoke":User.psmoke,
                                "pcomment":User.pcomment,
                                "page":User.page,
                                "pcontact":User.pcontact,
                                "pstime":User.pstime,
                                "pshour":User.pshour,
                                "hasMatchBefore":User.hasMatchBefore
                            });
                        }
                    });
                }
            }
        })
    }
};
