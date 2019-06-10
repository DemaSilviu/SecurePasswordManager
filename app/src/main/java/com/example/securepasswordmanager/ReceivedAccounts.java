package com.example.securepasswordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ReceivedAccounts extends AppCompatActivity implements FileDetails
{
    private int LENGHT = getFileLenght();
    ListView myListView;
    public String [] Url = new String [LENGHT];
    public  String [] Name = new String [LENGHT];
    public  String [] Id = new String[LENGHT];
    public  String [] Password = new String[LENGHT];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_accounts);

        myListView =(ListView) findViewById(R.id.AllAccountsReceivedBT);
        getFileLenght();
        Load();
        ReceivedAccountsAdaptor  myAdapter = new ReceivedAccountsAdaptor(ReceivedAccounts.this,Name,Id,Password);
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

                switch (i)
                {
                    case 0:
                        // Url[j] = line;
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


    }public int getFileLenght()
{
    try{

        File file =new File(File_Path);

        if(file.exists())
        {
            int linenumber=0;
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);



            while (lnr.readLine() != null)
            {
                linenumber++;
            }
            lnr.close();


            return linenumber/4;
        }else
        {
            Toast.makeText(this,"File Does Not Exist !",Toast.LENGTH_SHORT).show();
        }

    }catch(IOException e)
    {
        e.printStackTrace();
    }
    return 20;
}
}

