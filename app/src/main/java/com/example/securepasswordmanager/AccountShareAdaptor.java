package com.example.securepasswordmanager;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class AccountShareAdaptor extends ArrayAdapter<BluetoothDevice> {

    String[] NameAdapter;
    String[] IdAdapter;
    String[] PasswordAdapter;
    Context myContext;

    public AccountShareAdaptor(Context context,String[] Name, String[] Id, String[] Password){
        super(context, R.layout.lv_acctoshare);
        this.NameAdapter = Name;
        this.IdAdapter = Id;
        this.PasswordAdapter = Password;
        this.myContext = context;
    }
    @Override
    public int getCount() {
        return NameAdapter.length;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder myViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater myInflator = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = myInflator.inflate(R.layout.lv_acctoshare, parent, false);
            myViewHolder.myName = (TextView) convertView.findViewById(R.id.ReceivedName);
            myViewHolder.myId = (TextView) convertView.findViewById(R.id.ReceivedId);
            myViewHolder.myPassword = (TextView) convertView.findViewById(R.id.ReceivedPassword);
            myViewHolder.SelectAccountBtn = (Button) convertView.findViewById(R.id.SelectAccountBtn);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (AccountShareAdaptor.ViewHolder) convertView.getTag();
        }
        myViewHolder.myName.setText(NameAdapter[position]);
        myViewHolder.myId.setText(IdAdapter[position]);
        myViewHolder.myPassword.setText(PasswordAdapter[position]);
        myViewHolder.myPassword.setTransformationMethod(new PasswordTransformationMethod());
        myViewHolder.SelectAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                View parentrow = (View) v.getParent();
                ListView listView = (ListView) parentrow.getParent();

                final int position = listView.getPositionForView(parentrow);


                Intent myIntent = new Intent(myContext, Share.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_NAME",NameAdapter[position]);
                extras.putString("EXTRA_ID",IdAdapter[position]);
                extras.putString("EXTRA_PASSWORD",PasswordAdapter[position]);
                myIntent.putExtras(extras);
                myContext.startActivity(myIntent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView myName;
        TextView myId;
        TextView myPassword;
        Button SelectAccountBtn;
    }

}