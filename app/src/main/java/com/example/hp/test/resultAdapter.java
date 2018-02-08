package com.example.hp.test;

/**
 * Created by HP on 9/16/2017.
 */

public class resultAdapter {
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    String attend;
    public resultAdapter(String s1,String s2)

    {
        date=s2;
        attend=s1;

    }
}
