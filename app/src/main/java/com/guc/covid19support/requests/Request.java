package com.guc.covid19support.requests;

public class Request {
    String reqId;
    String userName;
    String userAge;
    String userPhone;
    String symptoms;
    String reply;
    String timeStamp;


    public Request(){
        reqId= "";
        userName ="";
        userAge = "";
        userPhone = "";
        symptoms ="";
        reply = "";
        timeStamp = "";
    }
    public Request(String reqId, String userName, String userAge, String userPhone, String symptoms, String reply, String timeStamp) {
        this.reqId = reqId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.symptoms = symptoms;
        this.reply = reply;
        this.timeStamp= timeStamp;
    }
    //setters
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    //getters
    public String getReqId() {
        return reqId;
    }
    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public String getSymptoms() {
        return symptoms;
    }

    public String getReply() {
        return reply;
    }


}
