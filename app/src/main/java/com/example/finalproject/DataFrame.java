package com.example.finalproject;


import android.os.Parcel;
import android.os.Parcelable;

//https://www.geeksforgeeks.org/singleton-class-in-android/
//https://developer.android.com/reference/android/os/Parcel.html
public class DataFrame {

    String term_id = "";
    String term_title = "";
    String term_start = "";
    String term_end = "";

    String course_id = "";
    String course_title = "";
    String course_start = "";
    String course_end = "";
    String course_status = "";

    String instructor_name = "";
    String instructor_phone = "";
    String instructor_email = "";

    String assessment_id = "";
    String assessment_title = "";
    String assessment_date = "";

    String note = "";

    private void Dataframe() {}

    //Setters
    public void setTermId(String id) { this.term_id = id; }
    public void setTermTitle(String title) {this.term_title = title; }
    public void setTermStart(String date) {this.term_start = date; }
    public void setTermEnd(String date) { this.term_end = date; }
    public void setCourseId(String id ) { this.course_id = id; }
    public void setCourseTitle(String title) { this.course_title = title; }
    public void setCourseStart(String date) { this.course_start = date; }
    public void setCourseEnd(String date) { this.course_end = date; }
    public void setCourseStatus(String status) { this.course_status = status;}
    public void setInsName(String name) { this.instructor_name = name; }
    public void setInsPhone(String phone) { this.instructor_phone = phone; }
    public void setInsEmail(String email) { this.instructor_email = email; }
    public void setAssmntId(String id) { this.assessment_id = id; }
    public void setAssmntTitle(String title) { this.assessment_title = title; }
    public void setAssmntDate(String date) { this.assessment_date = date; }
    public void setNote(String message) { this.note = message; }

    //Getters
    public String getTermId() { return this.term_id; }
    public String getTermTitle() { return this.term_title; }
    public String getTermStart() { return this.term_start; }
    public String getTermEnd() { return this.term_end; }
    public String getCourseId() { return this.course_id; }
    public String getCourseTitle() { return this.course_title; }
    public String getCourseStart() { return this.course_start; }
    public String getCourseEnd() { return this.course_end; }
    public String getCourseStatus() { return this.course_status; }
    public String getInsName() { return this.instructor_name; }
    public String getInsPhone() { return this.instructor_phone; }
    public String getInstructorEmail() { return this.instructor_email; }
    public String getAssmntId() { return this.assessment_id; }
    public String getAssmntTitle() { return this.assessment_title; }
    public String getAssmntDate() { return this.assessment_date; }
    public String getNote() {return this.note; }

    //Deep Copy
    public DataFrame copy() {
        DataFrame newDF = new DataFrame();
        newDF.setTermId(this.getTermId());
        newDF.setTermTitle(this.getTermTitle());
        newDF.setTermStart(this.getTermStart());
        newDF.setTermEnd(this.getTermEnd());
        newDF.setCourseId(this.getCourseId());
        newDF.setCourseTitle(this.getCourseTitle());
        newDF.setCourseStart(this.getCourseStart());
        newDF.setCourseEnd(this.getCourseEnd());
        newDF.setCourseStatus(this.getCourseStatus());
        newDF.setInsName(this.getInsName());
        newDF.setInsPhone(this.getInsPhone());
        newDF.setInsEmail(this.getInstructorEmail());
        newDF.setAssmntId(this.getAssmntId());
        newDF.setAssmntTitle(this.getAssmntTitle());
        newDF.setAssmntDate(this.getAssmntDate());
        newDF.setNote(this.getNote());
        return newDF;
    }

}
