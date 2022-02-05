module.exports = class ScoreTable{
    
    constructor(){
        
    }
    getSleepScore(userData, otherData){
        var ustime = userData.pstime;
        var ostime = otherData.pstime;
        var score = ustime - ostime + userData.pshour;
        return score;
    }
    getTotalScore(userData, otherData){
        var score = 0;
        const scoreTable = require('./mbti');
        const MBTI_INDEX = ["INFP","ENFP","INFJ","ENFJ","INTJ","ENTJ","INTP","ENTP","ISFP","ESFP","ISTP","ESTP","ISFJ","ESFJ","ISTJ","ESTJ"];
        const mbti_dependency = 1.5;
        const smoke_dependency = 1.2;
        const sleep_dependency = 1.4;
        score += scoreTable["mbtiInfo"][MBTI_INDEX[userData.pmbti]][MBTI_INDEX[otherData.pmbti]] *mbti_dependency;
        score += userData.psmoke*otherData.psmoke*smoke_dependency;
       //score += this.getSleepScore(userData,otherData)*this.sleep_dependency;
        return score;
    }
}