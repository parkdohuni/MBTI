module.exports =class Login{
    constructor(){

    }

    doLogin(req,res,db){
        var id = req.body.id;
        var password = req.body.password;
        db.query('SELECT * FROM User WHERE id = ?', [id], function( error, results, fields) {
            if (error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            } 
            else{
                if(results.length > 0) {
                    if(results[0].password == password){
                        var msg = {
                            "success":true,
                            "id":results[0].id,
                            "password":results[0].password,
                            "pname":results[0].pname,
                            "pgender":results[0].pgender,
                            "pmbti":results[0].pmbti,
                            "pdormitory":results[0].pdormitory,
                            "univ":results[0].univ,
                            "pmajor":results[0].pmajor,
                            "email":results[0].email,
                            "psmoke":results[0].psmoke,
                            "pcomment":results[0].pcomment,
                            "page":results[0].page,
                            "pcontact":results[0].pcontact,
                            "pstime":results[0].pstime,
                            "pshour":results[0].pshour,
                            "hasMatchBefore":results[0].hasMatchBefore,
                            "isMatched":results[0].isMatched,
                            "roommate":{}
                        };
                        if(results[0].isMatched==1){
                            db.query(`SELECT * FROM User,Matched WHERE mid = '${msg.id}' AND otherid=User.id`,function( error, roommates, fields) {
                                if(error){
                                    console.log("error ocurred", error);
                                    res.send({"success":false,"reason":"unknown error"});
                                }
                                else{
                                    var rommate = {
                                        "id":roommates[0].id,
                                        "password":roommates[0].password,
                                        "pname":roommates[0].pname,
                                        "pgender":roommates[0].pgender,
                                        "pmbti":roommates[0].pmbti,
                                        "pdormitory":roommates[0].pdormitory,
                                        "univ":roommates[0].univ,
                                        "pmajor":roommates[0].pmajor,
                                        "email":roommates[0].email,
                                        "psmoke":roommates[0].psmoke,
                                        "pcomment":roommates[0].pcomment,
                                        "page":roommates[0].page,
                                        "pcontact":roommates[0].pcontact,
                                        "pstime":roommates[0].pstime,
                                        "pshour":roommates[0].pshour,
                                        "hasMatchBefore":roommates[0].hasMatchBefore,
                                        "isMatched":roommates[0].isMatched
                                    };
                                    msg['roommate']=rommate;
                                    console.log(msg);
                                    res.send(msg);
                                }
                            });
                        }
                        else{
                            res.send(msg);
                        }
                    }
                    else
                        res.send({"success":false,"reason":"id or password not correct!"});
                }
                else
                    res.send(JSON.stringify({"success":false,"reason":"id or password not correct!"}));
            }
        })
    }
};
