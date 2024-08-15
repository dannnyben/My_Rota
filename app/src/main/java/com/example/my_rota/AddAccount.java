package com.example.my_rota;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class AddAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_account);

        SubmitAccount();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void SubmitAccount(){
        Button Submit = findViewById(R.id.SubmitAccount);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account newAccount = new Account();
                EditText fNameInput = findViewById(R.id.FirstNameInput);
                EditText lNameInput = findViewById(R.id.LastNameInput);
                EditText Email = findViewById(R.id.EmailInput);
                EditText Password = findViewById(R.id.PasswordInput);
                EditText Pay = findViewById(R.id.HourlyPayInput);


                newAccount.FName = fNameInput.getText().toString();
                newAccount.LName = lNameInput.getText().toString();
                newAccount.Email = Email.getText().toString();
                newAccount.Password = Password.getText().toString();
                newAccount.UserPay = Float.parseFloat(Pay.getText().toString());

                SaveAccountToFile(newAccount);
            }
        });
    }


    void SaveAccountToFile(Account account) {
        File filesDir = getFilesDir();
        File AccountEmail = new File(filesDir, "AccountEmail.txt");
        File AccountPassword = new File(filesDir, "AccountPass.txt");
        File AccountFName = new File(filesDir, "AccountFName.txt");
        File AccountLName = new File(filesDir, "AccountLName.txt");
        File AccountPay = new File(filesDir, "AccountPay.txt");

        System.out.println("Password and Email Files Created");

        try{
            FileUtils.writeStringToFile(AccountEmail, account.Email);
            FileUtils.writeStringToFile(AccountPassword, account.Password);
            FileUtils.writeStringToFile(AccountFName, account.FName);
            FileUtils.writeStringToFile(AccountLName, account.LName);
            FileUtils.writeStringToFile(AccountPay, String.valueOf(account.UserPay));

            System.out.println("File Written Too");
            Intent i = new Intent(AddAccount.this, Log_In.class);
            startActivity(i);

        }catch(IOException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
}