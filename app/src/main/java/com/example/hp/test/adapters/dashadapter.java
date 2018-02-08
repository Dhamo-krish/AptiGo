package com.example.hp.test.adapters;

/**
 * Created by HP on 9/12/2017.
 */

public class dashadapter {
    String testCata;
    public dashadapter(String t1,String t2,String t3,String t4,int t5)
    {
       testCata=t1;
        testdes=t2;
        testdate=t3;
        testdura=t4;
        nq=t5;
    }

    public String getTestCata() {
        return testCata;
    }

    public void setTestCata(String testCata) {
        this.testCata = testCata;
    }

    public String getTestdes() {
        return testdes;
    }

    public void setTestdes(String testdes) {
        this.testdes = testdes;
    }

    public String getTestdura() {
        return testdura;
    }

    public void setTestdura(String testdura) {
        this.testdura = testdura;
    }

    public String getTestdate() {
        return testdate;
    }

    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }

    String testdes;
    String testdura;
    String testdate;
    int nq;

    public int getNq() {
        return nq;
    }

    public void setNq(int nq) {
        this.nq = nq;
    }
}
