package com.example.hp.test.New_UI_HHS.Admin;

import java.util.ArrayList;

/**
 * Created by HP on 02-Oct-17.
 */

public class XYValues
{
//    private static String question,option1,option2,option3,option4;
    static Boolean flag;

    static ArrayList<String> list_question = new ArrayList<>();
    static ArrayList<String> list_option1 = new ArrayList<>();
    static ArrayList<String> list_option2 = new ArrayList<>();
    static ArrayList<String> list_option3 = new ArrayList<>();
    static ArrayList<String> list_option4 = new ArrayList<>();
    static ArrayList<String> list_correct = new ArrayList<>();

    public static ArrayList<String> getList_correct() {
        return list_correct;
    }

    public static void setList_correct(ArrayList<String> list_correct) {
        XYValues.list_correct = list_correct;
    }

    static ArrayList<Integer> list_number = new ArrayList<>();

    public static ArrayList<Integer> getList_number() {
        return list_number;
    }

    public static void setList_number(ArrayList<Integer> list_number) {
        XYValues.list_number = list_number;
    }

    public static ArrayList<String> getList_question() {
        return list_question;
    }

    public void setList_question(ArrayList<String> list_question) {
        this.list_question = list_question;
    }

    public static ArrayList<String> getList_option1() {
        return list_option1;
    }

    public void setList_option1(ArrayList<String> list_option1) {
        this.list_option1 = list_option1;
    }

    public static ArrayList<String> getList_option2() {
        return list_option2;
    }

    public void setList_option2(ArrayList<String> list_option2) {
        this.list_option2 = list_option2;
    }

    public static ArrayList<String> getList_option3() {
        return list_option3;
    }

    public void setList_option3(ArrayList<String> list_option3) {
        this.list_option3 = list_option3;
    }

    public static ArrayList<String> getList_option4() {
        return list_option4;
    }

    public void setList_option4(ArrayList<String> list_option4) {
        this.list_option4 = list_option4;
    }

    public XYValues()
    {

    }
//    public XYValues(String question, String option1, String option2, String option3, String option4) {
//        this.question = question;
//        this.option1 = option1;
//        this.option2 = option2;
//        this.option3 = option3;
//        this.option4 = option4;
//    }

    public XYValues(ArrayList<String> ques,ArrayList<String> op1,ArrayList<String> op2,ArrayList<String> op3,ArrayList<String> op4)
    {
        this.list_question = ques;
        this.list_option1 = op1;
        this.list_option2 = op2;
        this.list_option3 = op3;
        this.list_option4 = op4;
    }



//    public static String getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(String question) {
//        this.question = question;
//    }
//
//    public static String getOption1() {
//        return option1;
//    }
//
//    public void setOption1(String option1) {
//        this.option1 = option1;
//    }
//
//    public static String getOption2() {
//        return option2;
//    }
//
//    public void setOption2(String option2) {
//        this.option2 = option2;
//    }
//
//    public static String getOption3() {
//        return option3;
//    }
//
//    public void setOption3(String option3) {
//        this.option3 = option3;
//    }
//
//    public static String getOption4() {
//        return option4;
//    }
//
//    public void setOption4(String option4) {
//        this.option4 = option4;
//    }
}
