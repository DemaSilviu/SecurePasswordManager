package com.example.securepasswordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EditAccounts extends ViewAccounts {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accounts);
        Load();
    }
    public void Load()
    {
        Intent myIntent = getIntent();
        int position = myIntent.getIntExtra("Position", 0);
        Log.d("Position : ","Value" + position); // nu intra pe Edit Accounts niciodata
    }
}
