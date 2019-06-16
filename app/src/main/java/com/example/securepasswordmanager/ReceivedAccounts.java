package com.example.securepasswordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
//Adaptor that ReceivedAccounts needs in order to populate the list view
public class ReceivedAccounts extends AppCompatActivity implements FileDetails
{
    private int LENGHT = 1;
    ListView myListView;
    public  String [] Name = new String [LENGHT];
    public  String [] Id = new String[LENGHT];
    public  String [] Password = new String[LENGHT];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_accounts);

        myListView =(ListView) findViewById(R.id.AllAccountsReceivedBT);
        inMethod();
        Load();
        ReceivedAccountsAdaptor  myAdapter = new ReceivedAccountsAdaptor(ReceivedAccounts.this,Name,Id,Password);
        myListView.setAdapter(myAdapter);
    }
    public void inMethod()
    {
        FileOutputStream fp = null;
        try {
            fp = openFileOutput(Received_File, MODE_PRIVATE);
            fp.write("LeagueOfLegends".getBytes());
            fp.write('\n');
            fp.write("wabla".getBytes());
            fp.write('\n');
            fp.write("wablagamingaccount".getBytes());
            fp.write('\n');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // store from file into the Arrays
    public void Load()
    {
        int i = 0,j=0;
        try {
            FileInputStream fp = openFileInput(Received_File);
            InputStreamReader isr = new InputStreamReader(fp);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {

                switch (i)
                {
                    case 0:
                        Name[j]=line;
                        break;
                    case 1:
                        Id[j]=line;
                        break;
                    case 2:
                        Password[j]=line;
                        break;
                }
                if(i!=2)
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

