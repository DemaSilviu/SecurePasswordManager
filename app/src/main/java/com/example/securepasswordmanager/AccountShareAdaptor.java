

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

//Adaptor that SelectAccToShare class needs in order to populate the list view
public class AccountShareAdaptor extends ArrayAdapter<BluetoothDevice> {
// Array of strings which will be used to store the name,id,password
    String[] NameAdapter;
    String[] IdAdapter;
    String[] PasswordAdapter;
    Context myContext;
//constructor
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
        // if converView its null then assign buttons and textViews
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
        // next lines we set the text inside the TextViews to use it furthermore into Share class
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
                // get the position to know which item was clicked
                final int position = listView.getPositionForView(parentrow);

                // create a new intent and send informations through a Bundle of extras to share activity
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