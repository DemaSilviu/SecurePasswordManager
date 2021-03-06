package com.example.securepasswordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
// This is the first activity that will be shown to the user when he open the application
public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewAccountClicked();
        ViewAccountsClicked();
        ShareClicked();
        ReceivedAccountsClicked();
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
    private void ReceivedAccountsClicked()
    {
        Button ReceivedAccounts = findViewById(R.id.ReceivedAccountsBtn);
        ReceivedAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReceivedAccounts.class));
            }
        });
    }
    private void ViewAccountsClicked()
    {
        Button EditAccountsButton = findViewById(R.id.ViewAccountsButton);
        EditAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewAccounts.class));
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