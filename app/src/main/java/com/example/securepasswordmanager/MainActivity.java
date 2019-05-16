package com.example.securepasswordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewAccountClicked();
        EditAccountsClicked();
        ShareClicked();
    }
    private void NewAccountClicked()
    {
        Button NewAccountButton = findViewById(R.id.NewAccountButton);
        NewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SaveToFile.class));
            }
        });
    }
    private void EditAccountsClicked()
    {
        Button EditAccountsButton = findViewById(R.id.EditAccountsButton);
        EditAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EditAccounts.class));
            }
        });
    }
    private void ShareClicked()
    {
        Button ShareButton = findViewById(R.id.ShareButton);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Share.class));
            }
        });
    }

}
