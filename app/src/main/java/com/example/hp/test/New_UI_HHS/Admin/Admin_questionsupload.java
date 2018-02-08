package com.example.hp.test.New_UI_HHS.Admin;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.test.R;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adminFrag.questionsadap;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.apache.poi.hssf.record.pivottable.StreamIDRecord;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;




/**
 * Created by HP on 9/12/2017.
 */

public class Admin_questionsupload extends Fragment {
    public ArrayList<questionsadap> questions1=new ArrayList<questionsadap>();
    RecyclerView questions;
    Button button, excel_upload,updir,confirm;
    private static final String TAG = "Admin_questionsupload";
    private static final int PICK_FILE = 1;
    Uri fileUri = null;
    private String path;
    ArrayList<XYValues> uploadData;
    ArrayList<String> pathHistory;
    String lastDirectory;
    private String[] FilePathString;
    private String[] FileNameString;
    private File[] listFile;
    File file;
    ListView internalstorage;

    ArrayList<String> ques_list = new ArrayList<>();
    ArrayList<Integer> arraylist_number = new ArrayList<>();

    int count = 0;
    XYValues xyValues = new XYValues();

    ArrayList<String> arraylist_question = new ArrayList<>();
    ArrayList<String> arraylist_option1 = new ArrayList<>();
    ArrayList<String> arraylist_option2 = new ArrayList<>();
    ArrayList<String> arraylist_option3 = new ArrayList<>();
    ArrayList<String> arraylist_option4 = new ArrayList<>();
    ArrayList<String> arraylist_correct = new ArrayList<>();

    Firebase fb;
    String url="https://image-a519f.firebaseio.com/" ;
    private String tname;
    ProgressDialog progressDialog;

    int row,cols;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.aaa_questions,container,false);

        Firebase.setAndroidContext(getActivity());

        progressDialog = new ProgressDialog(getActivity());

        fb = new Firebase(url);

        excel_upload = (Button) view.findViewById(R.id.excel_upload);
        //questions=(RecyclerView)view.findViewById(R.id.questions);
        updir = (Button) view.findViewById(R.id.updir);
        confirm = (Button) view.findViewById(R.id.confirm);
        button=(Button)view.findViewById(R.id.upload);
        internalstorage = (ListView)view.findViewById(R.id.list);
        //xyValues.flag=false;
        //questions.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        for(int i=1;i<=dummy.getCount1();i++)
//        {
//            questions1.add(new questionsadap(i));
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),Admin_Home.class));


            }
        });

        CheckFilePermission();

        internalstorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i)))
                {
                    Log.d(TAG,"InternalStorage: Selected a file for upload: "+lastDirectory);
                    readExcelData(lastDirectory);
                }
                else
                {
                    count++;
                    pathHistory.add(count,(String)adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "InternalStorage: "+pathHistory.get(count));
                }
            }
        });

        excel_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internalstorage.setVisibility(View.VISIBLE);
                count =0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "BTNOnSDCard: "+pathHistory.get(count));
                checkInternalStorage();
                updir.setVisibility(View.VISIBLE);

            }
        });

        updir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count ==0)
                {
                    Log.d(TAG, "btnup dir: you have reached max hight..");
                }
                else
                {
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG,"btnupdir: "+pathHistory.get(count));
                }
            }
        });
//        final Admin_questions_itemadapter qi=new Admin_questions_itemadapter(R.layout.aaa_questionscard,questions1);
//
//        questions.setAdapter(qi);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                //System.out.println("Bow "+xyValues.getList_question());
                new MyTask().execute();
                //qi.setValues();
            }
        });


        return view ;

    }

    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            tname= dummy.getBd()+"@"+dummy.getBt()+"@"+dummy.getTdur()+"@"+dummy.getName()+"@"+dummy.getCount1();
            String url_new = url+"Test/"+dummy.getTdept()+"/"+dummy.getTyear()+"/";
            System.out.println("New URL "+url_new);
            fb = new Firebase(url_new);
            for(int i=1;i<=xyValues.getList_question().size();i++)
            {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(xyValues.getList_question().get(i-1));
                temp.add(xyValues.getList_option1().get(i-1));
                temp.add(xyValues.getList_option2().get(i-1));
                temp.add(xyValues.getList_option3().get(i-1));
                temp.add(xyValues.getList_option4().get(i-1));
                temp.add(xyValues.getList_correct().get(i-1));
                Admin_FB admin_fb = new Admin_FB();
                admin_fb.setQuestion(temp.get(0));
                admin_fb.setC1(temp.get(1));
                admin_fb.setC2(temp.get(2));
                admin_fb.setC3(temp.get(3));
                admin_fb.setC4(temp.get(4));
                admin_fb.setCorrect(temp.get(5));
                System.out.println("-----> "+temp);
                fb.child(tname).child(String.valueOf(i)).setValue(admin_fb);
            }
            progressDialog.dismiss();

            return null;
        }
    }

    private void checkInternalStorage() {
        Log.d(TAG,"CheckInternalStorage.");
        try
        {
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                toastMessage("No SD Card Found");
            }
            else
            {
                file = new File(pathHistory.get(count));
                Log.d(TAG, "CheckExternalStorage: Directory Path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();
            FilePathString = new String[listFile.length];
            FileNameString = new String[listFile.length];

            for(int i=0; i<listFile.length;i++)
            {
                FilePathString[i]=listFile[i].getAbsolutePath();
                FileNameString[i]=listFile[i].getName();
            }

            for(int i=0;i<listFile.length;i++)
            {
                Log.d("Files","FileName: "+ listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1,FilePathString);
            internalstorage.setAdapter(adapter);
        }
        catch(NullPointerException e)
        {
            Log.e(TAG,"CheckInternalStorage: NULLPOINTEREXCEPTION "+e.getMessage());
        }
    }

    private void CheckFilePermission()
    {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            int permissionCheck = getActivity().checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck = getActivity().checkSelfPermission("Manifest.permission.WRITE_EXTERNAL-STORAGE");

            if(permissionCheck != 0)
            {
                this.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
            }
            else
            {
                Log.d(TAG , "CheckPermissions: No Need to Check Permission. SDK version < LOLLIPOP");
            }
        }
    }

    private void readExcelData(String filePath)
    {
        Log.d(TAG, "ReadExccelData: Reading Excel File:");
        System.out.println("BOWWW "+filePath);

        File inputfile = new File(filePath);

        try
        {
            InputStream inputStream = new FileInputStream(inputfile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            //StringBuilder sb = new StringBuilder();
            System.out.println("Rows count is ----> "+rowsCount);
            int c;
            for(int r=1;r<11;r++)
            {
                ques_list.clear();
                Row row = sheet.getRow(r);
                int cellCount = row.getPhysicalNumberOfCells();
                System.out.println("Cell count is ----> "+cellCount);
                for(c=0;c<cellCount;c++)
                {
                    if(c>6)
                    {
                        Log.e(TAG,"readExcelData: ERROR: Excel file format INcorrect");
                        Toast.makeText(getActivity(),"Excel File Incorrect",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        String value = getCellAsString(row,c,formulaEvaluator);
                        String cellInfo = "r:" +r+ ";" + "c:" +c+ ";" + "v:" +value;
                        //set here
                        ques_list.add(value);
                        Log.d(TAG, "ReadDataFromExcel: " +cellInfo);
                        //sb.append(value +" ");
                    }
                }
                //sb.append(":");
                //add 5 array list
                //System.out.println("OWWW "+ques_list);
                arraylist_question.add(ques_list.get(0));
                arraylist_option1.add(ques_list.get(1));
                arraylist_option2.add(ques_list.get(2));
                arraylist_option3.add(ques_list.get(3));
                arraylist_option4.add(ques_list.get(4));
                arraylist_correct.add(ques_list.get(5));
             //   arraylist_number.add(++count);
//                System.out.println("11111Value retd "+arraylist_question);
//                System.out.println("11111Value op1 "+arraylist_option1);
//                System.out.println("11111Value op2 "+arraylist_option2);
//                System.out.println("11111Value op3 "+arraylist_option3);
//                System.out.println("11111Value op4 "+arraylist_option4);
            }
            System.out.println("ooooValue retd "+arraylist_question);
            System.out.println("ooooValue op1 "+arraylist_option1);
            System.out.println("ooooValue op2 "+arraylist_option2);
            System.out.println("ooooValue op3 "+arraylist_option3);
            System.out.println("ooooValue op4 "+arraylist_option4);
            System.out.println("ooooValue correct "+arraylist_correct);

           // xyValues.flag=true;
            //new XYValues(arraylist_question,arraylist_option1,arraylist_option2,arraylist_option3,arraylist_option4);
            xyValues.setList_question(arraylist_question);
            xyValues.setList_option1(arraylist_option1);
            xyValues.setList_option2(arraylist_option2);
            xyValues.setList_option3(arraylist_option3);
            xyValues.setList_option4(arraylist_option4);
            xyValues.setList_correct(arraylist_correct);
          //  xyValues.setList_number(arraylist_number);
            //System.out.println("List is "+ques_list.toString());
//            xyValues.setQuestion(ques_list.get(0));
//            xyValues.setOption1(ques_list.get(1));
//            xyValues.setOption2(ques_list.get(2));
//            xyValues.setOption3(ques_list.get(3));
//            xyValues.setOption4(ques_list.get(4));


            //Log.d(TAG, "readExcelData: STRINGBUILDER: "+sb.toString());
            //Toast.makeText(getActivity(),sb.toString(), Toast.LENGTH_SHORT).show();
            //parseStringBuilder(sb);


        }
        catch(FileNotFoundException e)
        {
            Log.e(TAG, "readExcelData: FileNotFoundException: "+ e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(TAG, "readExcelData: IOException: " + e.getMessage());
        }
    }

    private void parseStringBuilder(StringBuilder sb)
    {
        Log.d(TAG, "parseStringBuilder: Started parsing..");

        String[] row = sb.toString().split(":");
        System.out.println("Row length "+row.length);
        for(int i=0;i<row.length;i++)
        {
            String[] columns = row[i].split(",");
            System.out.println("Row is "+i+" ----> "+row[i].toString());
            System.out.println("Col is "+columns.toString());
            try
            {
//                String ques = columns[0];
//                String op1 = columns[1];
//                String op2 = columns[2];
//                String op3 = columns[3];
//                String op4 = columns[4];
                //String cellInfo = "(x,y): ("+x+","+y+")";
                //Log.d(TAG, "ParseStringBuilder: Data from row: " +cellInfo);
//
//                System.out.println("zzzzzzzzzzzzzz "+ques);
//                System.out.println("zzzzzzzzzzzzzz "+op1);
//                System.out.println("zzzzzzzzzzzzzz "+op2);
//                System.out.println("zzzzzzzzzzzzzz "+op3);
//                System.out.println("zzzzzzzzzzzzzz "+op4);
//
//                uploadData.add(new XYValues(ques,op1,op2,op3,op4));
            }
            catch(NumberFormatException e)
            {
                Log.e(TAG, "parseStringBuilder: NumberFormatException: "+e.getMessage());
            }
        }
       // printDataToLog();
    }

    private void printDataToLog()
    {
        Log.d(TAG, "Printing Log DATA....");
        for(int i=0;i<uploadData.size();i++)
        {
//            double x = uploadData.get(i).getX();
//            double y = uploadData.get(i).getY();

           // Log.d(TAG, "PrintingDataToLog: (x,y): ("+x+","+y+")");
        }
    }

    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator)
    {
        String value="";
        try
        {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType())
            {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell))
                    {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    }
                    else
                    {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        }
        catch(NullPointerException e)
        {
            Log.e(TAG, "getCEllAsString: NullPointerException: " + e.getMessage());
        }
        return  value;
    }

    private void toastMessage(String Message)
    {
        Toast.makeText(getActivity(),Message, Toast.LENGTH_SHORT).show();
    }

}
