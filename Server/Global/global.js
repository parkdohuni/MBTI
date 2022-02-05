const Matching = require('../Class/Matching');
const Login = require('../Class/Login');
const Signup = require('../Class/Signup');
const ScoreTable = require('../Class/ScoreTable');
const UserManager = require('../Class/UserManager');

g_matching = new Matching();
g_Login = new Login();
g_Signup = new Signup();
g_ScoreTable = new ScoreTable();
g_UserManager = new UserManager();

module.exports =  {
    "matching" : g_matching,
    "login" : g_Login,
    "signup" : g_Signup,
    "scoreTable" : g_ScoreTable,
    "userManager" : g_UserManager
};