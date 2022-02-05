const mysql = require('mysql');
var db_info = {
    host : 'localhost',
    user : 'root',
    password : 'password',
    database : 'MobileAppProgramming'
};

module.exports = {
    init : function(){
        var connection = mysql.createConnection(db_info);
        connection.connect(function (err){
            if(err)console.log('mysql connect error!'+err);
            else console.log('mysql connection success!');
        });
        return connection;
    }
}