
const ProfileData = require('../Class/ProfileData');
module.exports =class UserManager{
    constructor(){

    }

    getRoommate(req,res,db){
        var values = {
            "id":req.body.id,
        };
        db.query(`SELECT * FROM Matched,User WHERE Request.rid='${values.id}' AND Request.otherid=User.id`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                var items="{";
                items+=`"success":"true","Users":[`
                for(var i =0; i<results.length;++i){
                    var obj =
                        '{"id":'+ `"${results[i].id}",`+
                        '"password":'+`"${results[i].password}",`+
                        '"pname":'+`"${results[i].pname}",`+
                        '"pgender":'+`${results[i].pgender},`+
                        '"pmbti":'+`${results[i].pmbti},`+
                        '"pdormitory":'+`${results[i].pdormitory},`+
                        '"univ":'+`${results[i].univ},`+
                        '"pmajor":'+`${results[i].pmajor},`+
                        '"email":'+`"${results[i].email}",`+
                        '"psmoke":'+`${results[i].psmoke},`+
                        '"pcomment":'+`"${results[i].pcomment}",`+
                        '"page":'+`${results[i].page},`+
                        '"pcontact":'+`"${results[i].pcontact}",`+
                        '"pstime":'+`${results[i].pstime},`+
                        '"pshour":'+`${results[i].pshour},`+
                        '"hasMatchBefore":'+`${results[i].hasMatchBefore},`+
                        '"isMatched":'+`${results[i].isMatched}`+
                        "}"
                   
                    items += obj;
                    if(i+1<results.length)items+=",";
                }
                items+="]}";

                items= JSON.parse(items);

                console.log(items);
                res.send(items);

            }
        });
    }
    reqSerProfile(req,res,db){
        var profileNodes = [];
        db.query(`SELECT * FROM User WHERE pdormitory='${req.body.pdormitory}' AND pname='${req.body.pname}'AND NOT id='${req.body.id}' AND isMatched=0 AND pgender='${req.body.pgender}' 
        AND id NOT IN(SELECT otherid FROM Request WHERE rid ='${req.body.id}')
        AND id NOT IN(SELECT otherid FROM DenyMatch WHERE did ='${req.body.id}')`, function( error, results, fields) {
            if (error)
                console.log("error ocurred", error);
            else{
                console.log(results);
                for(var i = 0;i<results.length;++i){
                    var otherData = new ProfileData(
                        results[i].id,
                        results[i].password,
                        results[i].pname,
                        results[i].pgender,
                        results[i].pmbti,
                        results[i].pdormitory,
                        results[i].univ,
                        results[i].pmajor,
                        results[i].email,
                        results[i].psmoke,
                        results[i].pcomment,
                        results[i].page,
                        results[i].pcontact,
                        results[i].pstime,
                        results[i].pshour,
                        results[i].hasMatchBefore,
                        results[i].isMatched
                    );
                    profileNodes.push(otherData);
                }

                var items="{";
                items+=`"success":"true","Users":[`
                for(var i =0; i<profileNodes.length;++i){
                    var obj =
                        '{"id":'+ `"${profileNodes[i].id}",`+
                        '"password":'+`"${profileNodes[i].password}",`+
                        '"pname":'+`"${profileNodes[i].pname}",`+
                        '"pgender":'+`${profileNodes[i].pgender},`+
                        '"pmbti":'+`${profileNodes[i].pmbti},`+
                        '"pdormitory":'+`${profileNodes[i].pdormitory},`+
                        '"univ":'+`${profileNodes[i].univ},`+
                        '"pmajor":'+`${profileNodes[i].pmajor},`+
                        '"email":'+`"${profileNodes[i].email}",`+
                        '"psmoke":'+`${profileNodes[i].psmoke},`+
                        '"pcomment":'+`"${profileNodes[i].pcomment}",`+
                        '"page":'+`${profileNodes[i].page},`+
                        '"pcontact":'+`"${profileNodes[i].pcontact}",`+
                        '"pstime":'+`${profileNodes[i].pstime},`+
                        '"pshour":'+`${profileNodes[i].pshour},`+
                        '"hasMatchBefore":'+`${profileNodes[i].hasMatchBefore},`+
                        '"isMatched":'+`${profileNodes[i].isMatched}`+
                        "}"
                   
                    items += obj;
                    if(i+1<profileNodes.length)items+=",";
                }
                items+="]}";
                console.log(`items: ${items}`);
                items= JSON.parse(items);
                res.send(items);
            }
        });

    }
    reqCancel(req,res,db){
        var values = {
            "id":req.body.id,
            "otherid":req.body.otherid,
        };
        db.query(`DELETE FROM Request WHERE rid='${values.id}' AND otherid='${values.otherid}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                res.send({"success":true});
            }
        });
    }
    reqRoommate(req,res,db){
        var values = {
            "rid":req.body.id,
            "otherid":req.body.otherid,
            "status":"waiting"
        };
        db.query(`SELECT * FROM Request WHERE rid='${values.rid}' AND otherid='${values.otherid}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                if(results.length>0){
                    res.send({"success":false,"reason":"이미 요청하였습니다."});
                }
                else{
                    db.query(`SELECT * FROM User WHERE id='${values.otherid}'`,function( error, Users, fields) {
                        if (error){
                            console.log("error ocurred", error);
                            res.send({"success":false,"reason":"unknown error"});
                        } 
                        else{
                            if(Users[0].isMatched==1){
                                res.send({"success":false,"reason":"이미 매칭된 상대가 존재합니다."});
                            }else{
                                db.query(`INSERT INTO Request SET ?`,values,function( error, results, fields) {
                                    if (error){
                                        console.log("error ocurred", error);
                                        res.send({"success":false,"reason":"unknown error"});
                                    } 
                                    else{
                                         res.send({"success":true});
                                    }
                                })
                            }
                        }
                    });
                   
                }
            }
        });
    }
    resAcpt(req,res,db){
        var values = {
            "id":req.body.id,
            "otherid":req.body.otherid,
        };
        db.query(`DELETE FROM Request WHERE rid='${values.id}' OR rid='${values.otherid}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                db.query(`INSERT INTO Matched (mid,otherid) VALUES ?`,[[[req.body.id,req.body.otherid]]],function( error, results, fields) {
                    if (error){
                        console.log("error ocurred", error);
                        res.send({"success":false,"reason":"unknown error"});
                    } 
                    else{
                        db.query(`INSERT INTO Matched (mid,otherid) VALUES ?`,[[[req.body.otherid,req.body.id]]],function( error, results, fields) {
                            if (error){
                                console.log("error ocurred", error);
                                res.send({"success":false,"reason":"unknown error"});
                            } 
                            else{
                                db.query(`UPDATE User SET isMatched=1 WHERE id='${req.body.id}'OR id='${req.body.otherid}'`,function( error, results, fields) {
                                    if (error){
                                        console.log("error ocurred", error);
                                        res.send({"success":false,"reason":"unknown error"});
                                    } 
                                    else{
                                        db.query(`SELECT * FROM User WHERE User.id='${req.body.otherid}'`,function( error, results, fields) {
                                            if(error){
                                                console.log("error ocurred", error);
                                                res.send({"success":false,"reason":"unknown error"});
                                            }
                                            else{
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
                                                res.send(msg);
                                            }
                                        });
                                    }
                                })
                            }
                        })
                    }
                })
            }
        });
    }
    resDeny(req,res,db){
        var values = {
            "id":req.body.id,
            "otherid":req.body.otherid,
        };
        db.query(`DELETE FROM Request WHERE rid='${values.otherid}' AND otherid='${values.id}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                db.query(`INSERT INTO DenyMatch (did,otherid) VALUES ?`,[[[req.body.otherid,req.body.id]]],function( error, results, fields) {
                    if (error){
                        console.log("error ocurred", error);
                        res.send({"success":false,"reason":"unknown error"});
                    } 
                    else{
                        db.query(`INSERT INTO DenyMatch (did,otherid) VALUES ?`,[[[req.body.id,req.body.otherid]]],function( error, results, fields) {
                            if (error){
                                console.log("error ocurred", error);
                                res.send({"success":false,"reason":"이미 차단하였습니다."});
                            } 
                            else{
                                 res.send({"success":true});
                            }
                        })
                    }
                })
            }
        });
    }
    getReqList(req,res,db){
        var values = {
            "id":req.body.id,
        };
        db.query(`SELECT * FROM Request,User WHERE Request.rid='${values.id}' AND Request.otherid=User.id`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                var items="{";
                items+=`"success":"true","Users":[`
                for(var i =0; i<results.length;++i){
                    var obj =
                        '{"id":'+ `"${results[i].id}",`+
                        '"password":'+`"${results[i].password}",`+
                        '"pname":'+`"${results[i].pname}",`+
                        '"pgender":'+`${results[i].pgender},`+
                        '"pmbti":'+`${results[i].pmbti},`+
                        '"pdormitory":'+`${results[i].pdormitory},`+
                        '"univ":'+`${results[i].univ},`+
                        '"pmajor":'+`${results[i].pmajor},`+
                        '"email":'+`"${results[i].email}",`+
                        '"psmoke":'+`${results[i].psmoke},`+
                        '"pcomment":'+`"${results[i].pcomment}",`+
                        '"page":'+`${results[i].page},`+
                        '"pcontact":'+`"${results[i].pcontact}",`+
                        '"pstime":'+`${results[i].pstime},`+
                        '"pshour":'+`${results[i].pshour},`+
                        '"hasMatchBefore":'+`${results[i].hasMatchBefore},`+
                        '"isMatched":'+`${results[i].isMatched}`+
                        "}"
                   
                    items += obj;
                    if(i+1<results.length)items+=",";
                }
                items+="]}";

                items= JSON.parse(items);

                console.log(items);
                res.send(items);

            }
        });
    }
    getResList(req,res,db){
        var values = {
            "id":req.body.id,
        };
        db.query(`SELECT * FROM Request,User WHERE otherid='${values.id}' AND Request.rid=User.id`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                var items="{";
                items+=`"success":"true","Users":[`
                for(var i =0; i<results.length;++i){
                    var obj =
                        '{"id":'+ `"${results[i].id}",`+
                        '"password":'+`"${results[i].password}",`+
                        '"pname":'+`"${results[i].pname}",`+
                        '"pgender":'+`${results[i].pgender},`+
                        '"pmbti":'+`${results[i].pmbti},`+
                        '"pdormitory":'+`${results[i].pdormitory},`+
                        '"univ":'+`${results[i].univ},`+
                        '"pmajor":'+`${results[i].pmajor},`+
                        '"email":'+`"${results[i].email}",`+
                        '"psmoke":'+`${results[i].psmoke},`+
                        '"pcomment":'+`"${results[i].pcomment}",`+
                        '"page":'+`${results[i].page},`+
                        '"pcontact":'+`"${results[i].pcontact}",`+
                        '"pstime":'+`${results[i].pstime},`+
                        '"pshour":'+`${results[i].pshour},`+
                        '"hasMatchBefore":'+`${results[i].hasMatchBefore},`+
                        '"isMatched":'+`${results[i].isMatched}`+
                        "}"
                   
                    items += obj;
                    if(i+1<results.length)items+=",";
                }
                items+="]}";

                items= JSON.parse(items);

                console.log(items);
                res.send(items);

            }
        });
    }
    getDenyList(req,res,db){
        var values = {
            "id":req.body.id,
        };
        db.query(`SELECT * FROM DenyMatch,User WHERE DenyMatch.did='${values.id}' AND DenyMatch.otherid=User.id`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                var items="{";
                items+=`"success":"true","Users":[`
                for(var i =0; i<results.length;++i){
                    var obj =
                        '{"id":'+ `"${results[i].id}",`+
                        '"password":'+`"${results[i].password}",`+
                        '"pname":'+`"${results[i].pname}",`+
                        '"pgender":'+`${results[i].pgender},`+
                        '"pmbti":'+`${results[i].pmbti},`+
                        '"pdormitory":'+`${results[i].pdormitory},`+
                        '"univ":'+`${results[i].univ},`+
                        '"pmajor":'+`${results[i].pmajor},`+
                        '"email":'+`"${results[i].email}",`+
                        '"psmoke":'+`${results[i].psmoke},`+
                        '"pcomment":'+`"${results[i].pcomment}",`+
                        '"page":'+`${results[i].page},`+
                        '"pcontact":'+`"${results[i].pcontact}",`+
                        '"pstime":'+`${results[i].pstime},`+
                        '"pshour":'+`${results[i].pshour},`+
                        '"hasMatchBefore":'+`${results[i].hasMatchBefore},`+
                        '"isMatched":'+`${results[i].isMatched}`+
                        "}"
                   
                    items += obj;
                    if(i+1<results.length)items+=",";
                }
                items+="]}";

                items= JSON.parse(items);
                res.send(items);

            }
        });
    }
    wantDeny(req,res,db){
        var values = {
            "did":req.body.id,
            "otherid":req.body.otherid,
        };
        db.query(`SELECT * FROM DenyMatch WHERE did='${values.id}' AND otherid='${values.otherid}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                if(results.length>0){
                    res.send({"success":false,"reason":"이미 차단하였습니다."});
                }
                else{
                    db.query(`INSERT INTO DenyMatch SET ?`,values,function( error, results, fields) {
                        if (error){
                            console.log("error ocurred", error);
                            res.send({"success":false,"reason":"unknown error"});
                        } 
                        else{
                             res.send({"success":true});
                        }
                    })
                }
            }
        });
    }
    cancelDeny(req,res,db){
        var values = {
            "id":req.body.id,
            "otherid":req.body.otherid,
        };
        db.query(`DELETE FROM DenyMatch WHERE did='${values.id}' AND otherid='${values.otherid}'`,function( error, results, fields) {
            if(error){
                console.log("error ocurred", error);
                res.send({"success":false,"reason":"unknown error"});
            }
            else{
                res.send({"success":true});
            }
        });
    }
};
