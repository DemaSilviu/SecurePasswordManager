package com.example.securepasswordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
// class responsable when the user wants to save a new account inside his application
public class SaveToFile extends AppCompatActivity implements Accounts,FileDetails
{
    EditText UrlEditText;
    EditText NameEditText;
    EditText PasswordEditText;
    EditText IdEditText;
    Button SaveToFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_file);
        UrlEditText = findViewById(R.id.UrlEditText);
        NameEditText = findViewById(R.id.NameEditText);
        PasswordEditText = findViewById(R.id.PasswordEditText);
        IdEditText = findViewById(R.id.IdEditText);
        SaveToFileButton = findViewById(R.id.SaveToFileButton);
        storage();
    }
    public void storage()
    {
        Button SaveToFileButton = (Button) findViewById(R.id.SaveToFileButton);
        SaveToFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Save();
            }
        });
    }
    // check if the user completed all fields
    public int checks()
    {
        if(TextUtils.isEmpty(UrlEditText.getText().toString()))
        {
            Toast.makeText(this,"URL is empty, try again !",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(NameEditText.getText().toString()))
        {
            Toast.makeText(this,"Name is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(PasswordEditText.getText().toString()))
        {
            Toast.makeText(this,"Password is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(TextUtils.isEmpty(IdEditText.getText().toString()))
        {
            Toast.makeText(this,"Id is empty, try again",Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
    // method which saves data inside the internal storage file
    public void Save()
    {

        String UrlText = UrlEditText.getText().toString();
        String NameText = NameEditText.getText().toString();
        String PasswordText = PasswordEditText.getText().toString();
        String IdText = IdEditText.getText().toString();
        checks();
        if (checks() != 0) {
            FileOutputStream fp = null;

            try {
                fp = openFileOutput(File_Name, MODE_PRIVATE | MODE_APPEND);
                fp.write(UrlText.getBytes());
                fp.write('\n');
                fp.write(NameText.getBytes());
                fp.write('\n');
                fp.write(IdText.getBytes());
                fp.write('\n');
                fp.write(PasswordText.getBytes());
                fp.write('\n');
                Clear();
                Toast.makeText(this, "Successfully saved !" , Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fp != null) {
                    try {
                        fp.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // method responsable for clearing the EditTexts in order to allow the user save another account without being force to click again NewAccount
    public void Clear()
    {
        UrlEditText.getText().clear();
        NameEditText.getText().clear();
        PasswordEditText.getText().clear();
        IdEditText.getText().clear();
    }

}
