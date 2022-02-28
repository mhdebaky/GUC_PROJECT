package com.guc.covid19support;

public class UserData {
    private static String name;
    private static String age ;
    private static String phone ;
    private static String email ;
    private static Boolean isPatient;
    private static Boolean isDoctor;

    public UserData(){

    }

    public UserData(String name,String age ,String phone ,String email,Boolean isPatient ){
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.isPatient = isPatient;
        this.isDoctor = !isPatient;
    }

    public Boolean getisPatient() {
        return isPatient;
    }

    public void setisPatient(Boolean patient) {
        isPatient = patient;
    }

    public Boolean getisDoctor() {
        return isDoctor;
    }

    public void setisDoctor(Boolean doctor) {
        isDoctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
