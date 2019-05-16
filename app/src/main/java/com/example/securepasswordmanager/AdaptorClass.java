package com.example.securepasswordmanager;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdaptorClass extends ArrayAdapter<String>
{
    String [] UrlAdapter ;
    String [] NameAdapter ;
    String [] IdAdapter ;
    String [] PasswordAdapter ;
    Context myContext;
    public AdaptorClass(Context context, String[] Url,String[] Name,String[] Id,String[] Password)
    {
        super(context, R.layout.lv_layout);
        this.UrlAdapter = Url;
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder myViewHolder = new ViewHolder();
        if(convertView == null)
        {
            LayoutInflater myInflator = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = myInflator.inflate(R.layout.lv_layout, parent, false);
            myViewHolder.myUrl = (TextView) convertView.findViewById(R.id.rv_layout_Url);
            myViewHolder.myName = (TextView) convertView.findViewById(R.id.rv_layout_Name);
            myViewHolder.myId = (TextView) convertView.findViewById(R.id.rv_layout_Id);
            myViewHolder.myPassword = (TextView) convertView.findViewById(R.id.rv_layout_Password);
            convertView.setTag(myViewHolder);
        }
        else
        {
            myViewHolder = (ViewHolder) convertView.getTag();

        }
            myViewHolder.myUrl.setText(UrlAdapter[position]);
            myViewHolder.myName.setText(NameAdapter[position]);
            myViewHolder.myId.setText(IdAdapter[position]);
            myViewHolder.myPassword.setText(PasswordAdapter[position]);
        return convertView;
    }

    static class ViewHolder
    {
        TextView myUrl;
        TextView myName;
        TextView myId;
        TextView myPassword;
    }
}
