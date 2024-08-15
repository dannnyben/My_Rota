package com.example.my_rota;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Add_To_Rota extends AppCompatActivity {

    String Day = "";
    String StartTime = "";
    String EndTime = "";
    String weekNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_to_rota);

        AddRota();
        GoBackToHome();
        GetTabSelector();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void GoBackToHome(){
        ImageButton backbutton = findViewById(R.id.Add_BackButton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Add_To_Rota.this, Home.class);
                startActivity(i);
            }
        });
    }

    void GetTabSelector(){
        TabLayout tab = findViewById(R.id.Add_tabLayout);

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabpos = tab.getPosition();

                switch(tabpos){
                    case 0:
                        Day = "Monday";
                        break;
                    case 1:
                        Day = "Tuesday";
                        break;
                    case 2:
                        Day = "Wednesday";
                        break;
                    case 3:
                        Day = "Thursday";
                        break;
                    case 4:
                        Day = "Friday";
                        break;
                    case 5:
                        Day = "Saturday";
                        break;
                    case 6:
                        Day = "Sunday";
                        break;
                }

                System.out.println(Day);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void AddRota(){
        TextView error = findViewById(R.id.Add_ErrorText);
        EditText startTime = findViewById(R.id.Add_StartTimeInput);
        EditText endTime = findViewById(R.id.Add_EndTimeInput);
        EditText WeekNumber = findViewById(R.id.Add_WeekNumberInput);
        Button submitButton = findViewById(R.id.Add_SubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = startTime.getText().toString();
                EndTime = endTime.getText().toString();
                weekNumber = WeekNumber.getText().toString();

                startTime.setText("");
                endTime.setText("");
                WeekNumber.setText("");
                startTime.setHint("Start Time");
                endTime.setHint("End Time");
                WeekNumber.setHint("Week Number");

                createNewWeekFolder(error);
            }
        });
    }

    void createNewWeekFolder(TextView error){
        String path = getFilesDir().toString() + "\\Week" + "_" + weekNumber;
        File f = new File(path);

        if(f.exists()){
            getFiles();
        }
        else{
            f.mkdir();
            System.out.println("Directory Created " + "Week" +  "_" + weekNumber);
            error.setText("Created Successfully");
            error.setTextColor(Color.GREEN);
            saveDay();
        }
    }

    void getFiles(){
        TextView error = findViewById(R.id.Add_ErrorText);
        String path = getFilesDir().toString() + "\\Week" + "_" + weekNumber;
        File f = new File(path);
        File[] List = f.listFiles();

        if(List != null){
            for(int i = 0; i < List.length; i++){
                if(List[i].getName().contains(Day + ".txt")){
                    System.out.println(List[i].getName());
                    error.setText("Day is already in week");
                    error.setTextColor(Color.RED);
                    break;
                }
                else{
                    saveDay();
                }
            }
        }
        else{
            System.out.println("Directory is Empty");
        }
    }

    void saveDay() {
        String path = getFilesDir().toString() + "/Week" + "_" + weekNumber;
        File newDay = new File(path, "/" + Day + ".txt");
        File newDayStartTime = new File(path, "/" + Day + "_" + "StartTime.txt");
        File newDayEndTime = new File(path, "/" + Day + "_" + "EndTime.txt");

        System.out.println("newDay Files Created");

        try{
            FileUtils.writeStringToFile(newDay, Day);

            FileUtils.writeStringToFile(newDayStartTime, StartTime);

            FileUtils.writeStringToFile(newDayEndTime, EndTime);

            System.out.println("Data Written To newDay Files");

        }catch(IOException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
}