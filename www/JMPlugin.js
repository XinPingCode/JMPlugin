var exec = require('cordova/exec');

exports.isInstall = function (arg0, success, error){
    exec(success, error , 'JMPlugin', 'isInstall' ,[arg0]);
};
exports.jumpMeeting = function (arg0, arg1, success, error){
    exec(success, error , 'JMPlugin', 'jumpMeeting' ,[arg0,arg1]);
};
exports.jumpMeetingNum = function (arg0, arg1, arg2,success, error){
    exec(success, error , 'JMPlugin', 'jumpMeetingNum' ,[arg0,arg1,arg2]);
};
exports.startMeeting = function (arg0, success, error){
    exec(success, error , 'JMPlugin', 'startMeeting' ,[arg0]);
};