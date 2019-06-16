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

public class ReceivedAccountsAdaptor extends ArrayAdapter<BluetoothDevice>
{
    String[] NameAdapter;
    String[] IdAdapter;
    String[] PasswordAdapter;
    Context myContext;

    public ReceivedAccountsAdaptor(Context context,String[] Name, String[] Id, String[] Password)
    {
        super(context, R.layout.lv_received_accounts_ui);
        this.NameAdapter = Name;
        this.IdAdapter = Id;
        this.PasswordAdapter = Password;
        this.myContext = context;
    }
    @Override
    public int getCount()
    {
        return NameAdapter.length;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder myViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater myInflator = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = myInflator.inflate(R.layout.lv_received_accounts_ui, parent, false);
            myViewHolder.myName = (TextView) convertView.findViewById(R.id.sharedAccName);
            myViewHolder.myId = (TextView) convertView.findViewById(R.id.sharedAccId);
            myViewHolder.myPassword = (TextView) convertView.findViewById(R.id.sharedAccPassword);
            myViewHolder.UseAccountBtn = (Button) convertView.findViewById(R.id.UseAccountBtn);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ReceivedAccountsAdaptor.ViewHolder) convertView.getTag();
        }
        myViewHolder.myName.setText(NameAdapter[position]);
        myViewHolder.myId.setText(IdAdapter[position]);
        myViewHolder.myPassword.setText(PasswordAdapter[position]);
        myViewHolder.myPassword.setTransformationMethod(new PasswordTransformationMethod());
        final ViewHolder finalMyViewHolder = myViewHolder;
        myViewHolder.UseAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finalMyViewHolder.myPassword.setTransformationMethod(null);
            }
        });

        return convertView;
    }

    static class ViewHolder
    {
        TextView myName;
        TextView myId;
        TextView myPassword;
        Button UseAccountBtn;
    }
}
