package uk.ac.tees.b1724525.tuchattingapp;

public class userprofile {


    public String username,userUID; //user details i want to pass to database//

    public userprofile() {
    }

    //created constructors

    public userprofile(String username, String userUID) {
        this.username = username;
        this.userUID = userUID;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
