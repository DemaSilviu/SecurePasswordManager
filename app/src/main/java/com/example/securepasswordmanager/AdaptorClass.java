package com.example.securepasswordmanager;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AdaptorClass extends ArrayAdapter<String> {
    String[] UrlAdapter;
    String[] NameAdapter;
    String[] IdAdapter;
    String[] PasswordAdapter;
    Context myContext;

    public AdaptorClass(Context context, String[] Url, String[] Name, String[] Id, String[] Password) {
        super(context, R.layout.lv_layout);
        this.UrlAdapter = Url;
        this.NameAdapter = Name;
        this.IdAdapter = Id;
        this.PasswordAdapter = Password;
        this.myContext = context;
    }

    @Override
    public int getCount() {
        return NameAdapter.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder myViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater myInflator = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = myInflator.inflate(R.layout.lv_layout, parent, false);
            myViewHolder.myUrl = (TextView) convertView.findViewById(R.id.rv_layout_Url);
            myViewHolder.myName = (TextView) convertView.findViewById(R.id.rv_layout_Name);
            myViewHolder.myId = (TextView) convertView.findViewById(R.id.rv_layout_Id);
            myViewHolder.myPassword = (TextView) convertView.findViewById(R.id.rv_layout_Password);
            myViewHolder.EditAccBtn = (Button) convertView.findViewById(R.id.EditAccBtn);
            myViewHolder.DeleteAccBtn = (Button) convertView.findViewById(R.id.DeleteAccBtn);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }
        myViewHolder.myUrl.setText(UrlAdapter[position]);
        myViewHolder.myName.setText(NameAdapter[position]);
        myViewHolder.myId.setText(IdAdapter[position]);
        myViewHolder.myPassword.setText(PasswordAdapter[position]);
        myViewHolder.EditAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentrow = (View) v.getParent();
                ListView listView = (ListView) parentrow.getParent();
                final int position = listView.getPositionForView(parentrow);
                Intent myIntent = new Intent(myContext, EditAccounts.class);
                myIntent.putExtra("Position", position);
                myContext.startActivity(myIntent);
            }
        });
        myViewHolder.DeleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentrow = (View) v.getParent();
                ListView listView = (ListView) parentrow.getParent();
                int position = listView.getPositionForView(parentrow);
                position = position * 4 + 1;
                try
                {
                    DeleteAccount(position);

                }
                catch (IOException ie)
                {
                    ie.printStackTrace();
                }


            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView myUrl;
        TextView myName;
        TextView myId;
        TextView myPassword;
        Button EditAccBtn;
        Button DeleteAccBtn;
    }
    public void DeleteAccount(int DeleteLine) throws IOException
    {
        File inputFile = new File("/data/data/com.example.securepasswordmanager/files/AccInformations.txt");
        File tempFile = new File("/data/data/com.example.securepasswordmanager/files/AccTemporaryInformations.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        int count = 0;
        while ((currentLine = reader.readLine()) != null) {
            count++;
            if (count == DeleteLine || count == DeleteLine+1 || count==DeleteLine+2 || count==DeleteLine+3 ) {
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);

    }
}