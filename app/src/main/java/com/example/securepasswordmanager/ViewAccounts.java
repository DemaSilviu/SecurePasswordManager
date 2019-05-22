package com.example.securepasswordmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ViewAccounts extends AppCompatActivity
{
    private static final String File_Name = "AccInformations.txt";
    private  static int LENGHT = 100;
    ListView myListView;
    String[] Url = new String[LENGHT];
    String [] Name = new String [LENGHT];
    String [] Id = new String[LENGHT];
    String [] Password = new String[LENGHT];





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        int i=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accounts);
        myListView =(ListView) findViewById(R.id.listviewAcc);
        Load();
        AdaptorClass  myAdapter = new AdaptorClass(ViewAccounts.this,Url,Name,Id,Password);
        myListView.setAdapter(myAdapter);
    }

    public void Load()
    {
        int i = 0,j=0;
        try {
            FileInputStream fp = openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fp);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {

                Log.d("Line is :", line);
                switch (i)
                {
                    case 0:
                        Url[j] = line;
                        break;
                    case 1:
                        Name[j]=line;
                        break;
                    case 2:
                        Id[j]=line;
                        break;
                    case 3:
                        Password[j]=line;
                        break;
                }
                if(i!=3)
                {
                    i++;
                }
                else
                {
                    i=0;
                    j++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
