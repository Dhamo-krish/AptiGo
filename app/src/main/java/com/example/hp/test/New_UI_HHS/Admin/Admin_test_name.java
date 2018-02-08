package com.example.hp.test.New_UI_HHS.Admin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.test.R;
import com.example.hp.test.adapters.dummy;

/**
 * Created by HP on 9/12/2017.
 */

public class Admin_test_name extends Fragment {
    EditText count,name;
    Button create;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aaa_testname,container,false);
        name=(EditText)view.findViewById(R.id.testname);
        count=(EditText)view.findViewById(R.id.count);
        create=(Button)view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String tmp=count.getText().toString();
                try {
                    if (!tmp.equals(""))
                    {
                        int ct;
                        getFragmentManager().beginTransaction().replace(R.id.frame, new Admin_questionsupload()).commit();
                        ct = Integer.parseInt(count.getText().toString());
                        dummy.setCount1(ct);
                        dummy.setName(name.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Number of questions is empty", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NumberFormatException e)
                {
                    Toast.makeText(getActivity(), "Count is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }
}
