package com.example.hp.test.TestPage;

/**
 * Created by HP on 9/13/2017.
 */

public class testpageadapter {
    public testpageadapter(String q,String op1,String op2,String op3,String op4,String r,String ans)
    {
        ques=q;
        option1=op1;
        option2=op2;
        option3=op3;
        option4=op4;
        result=r;
        answer=ans;
    }
    String ques;
    String option1;
    String option2;
    String option3;
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer;

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    String option4;
}
