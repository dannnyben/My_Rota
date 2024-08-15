package com.example.my_rota;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    boolean HasAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AccountCheck();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void AccountCheck(){
        File path = getFilesDir();
        File fEmail = new File(path,"AccountEmail.txt");
        File fPass = new File(path, "AccountPass.txt");

        if(fEmail.exists() && fPass.exists()){
            HasAccount = true;
        }
        else {
            HasAccount = false;
        }

        Destination();
    }

    void Destination(){
        if(HasAccount){
            Intent i = new Intent(MainActivity.this, Log_In.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(MainActivity.this, AddAccount.class);
            startActivity(i);
        }
    }
}