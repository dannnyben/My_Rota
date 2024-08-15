package com.example.my_rota;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Log_In extends AppCompatActivity {

    Account UserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);


        getUserAccount();
        LogIn();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void LogIn(){
        Button LogInButton = findViewById(R.id.LogInButton);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEmail();
            }
        });
    }

    void CheckEmail(){

        EditText EmailInput = findViewById(R.id.LogInEmail);
        TextView ErrorText = findViewById(R.id.ErrorText);

        if(EmailInput.getText().toString().equals(UserAccount.Email)){
            CheckPassword();
        }
        else{
            ErrorText.setText("Email is Wrong");
        }
    }

    void CheckPassword(){
        EditText PasswordInput = findViewById(R.id.LogInPassword);
        TextView ErrorText = findViewById(R.id.ErrorText);

        if(PasswordInput.getText().toString().equals(UserAccount.Password)) {
            Intent i = new Intent(Log_In.this, Home.class);
            startActivity(i);
        }
        else{
            ErrorText.setText("Password is Wrong");
        }
    }

    void getUserAccount(){
        File path = getFilesDir();
        File fileEmail = new File(path, "AccountEmail.txt");
        File filePass = new File(path, "AccountPass.txt");

        UserAccount = new Account();

        try {
            UserAccount.Email = FileUtils.readFileToString(fileEmail);
            UserAccount.Password = FileUtils.readFileToString(filePass);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}