package com.example.securepasswordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditAccounts extends AppCompatActivity
{
    private static final String File_Name = "AccInformations.txt";
    EditText EditUrlEditText;
    EditText EditNameEditText;
    EditText EditIdEditText;
    EditText EditPasswordEditText;
    Button SaveEditedAccountsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accounts);
        EditUrlEditText = findViewById(R.id.EditUrlEditText);
        EditNameEditText = findViewById(R.id.EditNameEditText);
        EditIdEditText = findViewById(R.id.EditIdEditText);
        EditPasswordEditText = findViewById(R.id.EditPasswordEditText);
        SaveButtonClicked();
    }
    private void SaveButtonClicked()
    {
        Button SaveEditedAccountButton = (Button) findViewById(R.id.SaveEditedAccountsBtn);
        SaveEditedAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Save();
            }
        });
    }
    public static void ChangeFile(int lineNumber, String[] data) throws IOException
    {
        Path path = Paths.get("/data/data/com.example.securepasswordmanager/files/AccInformations.txt");
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.set(lineNumber , data[0]);
        lines.set(lineNumber+1,data[1]);
        lines.set(lineNumber+2, data[2]);
        lines.set(lineNumber+3,data[3]);
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Save()
    {
        String UrlEditText = EditUrlEditText.getText().toString();
        String NameEditText = EditNameEditText.getText().toString();
        String PasswordEditText = EditPasswordEditText.getText().toString();
        String IdEditText = EditIdEditText.getText().toString();
        String [] data = new String[4];
        data[0]=UrlEditText;
        data[1]=NameEditText;
        data[2]=IdEditText;
        data[3]=PasswordEditText;
        checks();
        Intent myIntent = getIntent();
        int position = myIntent.getIntExtra("Position", 0);
        position=position*4; // each position means actually 4 lines inside the file
        Log.d("Position : ","Value inside save method from editaccounts when button pressed !!" + position);
        if(checks() !=0)
        {
            try {
                ChangeFile(position,data); // edit data
                Toast.makeText(this, "Account Successfully Edited" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditAccounts.this,MainActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public int checks()
    {
        if(TextUtils.isEmpty(EditUrlEditText.getText().toString()))
        {
            Toast.makeText(this,"URL is empty, try again !",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(EditNameEditText.getText().toString()))
        {
            Toast.makeText(this,"Name is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(EditPasswordEditText.getText().toString()))
        {
            Toast.makeText(this,"Password is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(EditIdEditText.getText().toString()))
        {
            Toast.makeText(this,"Id is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
