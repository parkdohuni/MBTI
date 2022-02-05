const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mySQL  = require('./Module/db');
require('./Global/global');

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

app.use(function(req,res,next){
	res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	next();
});

var dbConnection = mySQL.init();

app.use('/User/Login',require('./Routes/user_Login')(app,dbConnection));
app.use('/User/Register',require('./Routes/user_Register')(app,dbConnection));
app.use('/User/NewMatch',require('./Routes/user_NewMatching')(app,dbConnection));
app.use('/User/PrevMatch',require('./Routes/user_PrevMatching')(app,dbConnection));
app.use('/User/SearchProfile',require('./Routes/user_SearchProfile')(app,dbConnection));
app.use('/User/Request',require('./Routes/user_ReqRoommate')(app,dbConnection));
app.use('/User/Deny',require('./Routes/user_Deny')(app,dbConnection));
app.use('/User/DenyList',require('./Routes/user_DenyList')(app,dbConnection));
app.use('/User/DenyCancel',require('./Routes/user_DenyCancel')(app,dbConnection));
app.use('/User/RequestList',require('./Routes/user_RequestList')(app,dbConnection));
app.use('/User/ReqCancel',require('./Routes/user_ReqCancel')(app,dbConnection));
app.use('/User/ResponseList',require('./Routes/user_ResponseList')(app,dbConnection));
app.use('/User/ResponseAcpt',require('./Routes/user_ResponseAcpt')(app,dbConnection));
app.use('/User/ResponseDeny',require('./Routes/user_ResponseDeny')(app,dbConnection));
app.use('/User/MyRoommate',require('./Routes/user_MyRoommate')(app,dbConnection));


app.listen(5000,function(req,res){
    console.log('connected!!');
});