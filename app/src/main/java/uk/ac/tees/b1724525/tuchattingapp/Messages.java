package uk.ac.tees.b1724525.tuchattingapp;

public class Messages {

    String message; //message passed by user//
    String senderId;  //helps to identify who is sending message//
    long timestamp;    //shows the time of first message and last message//
    String sendername;
    String currenttime; //shows the current time of message//


    // create an empty constructor//


    public Messages(String enteredmessage, String uid, long time, String currenttime) {
    }



    //create constructor and select all//


    public Messages(String message, String senderId, long timestamp, String sendername, String currenttime) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.sendername = sendername;
        this.currenttime = currenttime;
    }

    //public Messages(String enteredmessage, String uid, long time, String sendername, String currenttime) {


    //public Messages(String enteredmessage, String uid, long time, String sendername, String currenttime) {


    //generate Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSendername () {return sendername; }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
