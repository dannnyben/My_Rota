package com.example.my_rota;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

public class SeeRotas extends AppCompatActivity {

    String SelectedWeek = "";
    String selectedDay = "";
    float UserPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_rotas);

        GetWeekTabData();
        GetWeekTabSelection();
        GetDayTabSelection();
        BackToHome();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void BackToHome(){
        ImageButton BTH = findViewById(R.id.See_BackToHome);

        BTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeRotas.this, Home.class);
                startActivity(i);
            }
        });
    }

    void GetWeekTabData(){
        String path = getFilesDir().toString();
        File WeekFinder = new File(path);
        TabLayout WeekTab = (TabLayout) findViewById(R.id.WeekSelector);

        File[] DirectoryList = WeekFinder.listFiles();

        if(DirectoryList != null){
            for(int i = 0; i < DirectoryList.length; i++){
                if(DirectoryList[i].getName().contains("Week")){
                    System.out.println(DirectoryList[i].getName());

                    WeekTab.addTab(WeekTab.newTab().setText(DirectoryList[i].getName()));
                }
            }
        }
    }

    void GetDayTabData(){
        File[] FileList = new File[7];
        String path = getFilesDir().toString() + "/" + SelectedWeek;
        File DayFinder = new File(path);
        TabLayout DayTab = (TabLayout) findViewById(R.id.DaySelector);

        FileList = DayFinder.listFiles();

        if(FileList != null){
            for(int i = 0; i < FileList.length; i++){
                if(!FileList[i].getName().contains("StartTime") && !FileList[i].getName().contains("EndTime")){
                    DayTab.addTab(DayTab.newTab().setText(FileList[i].getName().replace(".txt", "")));
                }
                else{
                    break;
                }
            }
        }
    }

    void GetWeekTabSelection(){
        TabLayout WeekTab = (TabLayout) findViewById(R.id.WeekSelector);
        TabLayout DayTab = (TabLayout) findViewById(R.id.DaySelector);

        int pos = WeekTab.getSelectedTabPosition();

        if(pos == 0){
            SelectedWeek = (String) WeekTab.getTabAt(pos).getText();
            DayTab.removeAllTabs();
            GetDayTabData();
        }

        WeekTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = WeekTab.getSelectedTabPosition();

                switch(pos){
                    case 0:
                        SelectedWeek = (String) WeekTab.getTabAt(pos).getText();
                        DayTab.removeAllTabs();
                        GetDayTabData();
                    case 1:
                        SelectedWeek = (String) WeekTab.getTabAt(pos).getText();
                        DayTab.removeAllTabs();
                        GetDayTabData();
                    case 2:
                        SelectedWeek = (String) WeekTab.getTabAt(pos).getText();
                        DayTab.removeAllTabs();
                        GetDayTabData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void GetDayTabSelection(){
        TabLayout WeekTab = (TabLayout) findViewById(R.id.WeekSelector);
        TabLayout DayTab = (TabLayout) findViewById(R.id.DaySelector);
        TextView StartTimeDisplay = findViewById(R.id.See_StartTimeDisplay);
        TextView EndTimeDisplay = findViewById(R.id.See_EndTimeDisplay);
        TextView HoursWorkedDisplay = findViewById(R.id.See_HoursWorkedDisplay);
        TextView PayForShiftDisplay = findViewById(R.id.See_PayForShiftDisplay);

        int pos = DayTab.getSelectedTabPosition();

        if(pos == 0){
            int StartTime = 0;
            int EndTime = 0;
            int Hours = 0;

            selectedDay = DayTab.getTabAt(pos).getText().toString();
            Thread Logic = new Thread(new Runnable() {
                @Override
                public void run() {
                    selectedDay = selectedDay.replace(".txt", "");
                }
            });
            Logic.run();
            System.out.println(selectedDay);
            String path = getFilesDir().toString() + "/" + SelectedWeek;
            File DayFinder = new File(path);
            File[] list = DayFinder.listFiles();

            File StartTimeFile = new File(path, selectedDay + "_StartTime.txt");
            File EndTimeFile = new File(path, selectedDay + "_EndTime.txt");

            for(int i = 0; i < list.length; i++) {
                if (list[i].getName().contains(selectedDay)) {
                    try {
                        StartTime = Integer.parseInt(FileUtils.readFileToString(StartTimeFile).toString());
                        EndTime = Integer.parseInt(FileUtils.readFileToString(EndTimeFile).toString());
                        Hours = EndTime - StartTime;
                        GetUserPay();

                        StartTimeDisplay.setText(StartTime + ":00");
                        EndTimeDisplay.setText(EndTime + ":00");
                        HoursWorkedDisplay.setText(String.valueOf(Hours));
                        PayForShiftDisplay.setText(String.valueOf((int) (Hours * UserPay)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        DayTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = DayTab.getSelectedTabPosition();
                int StartTime = 0;
                int EndTime = 0;
                int Hours = 0;

                switch(pos){
                    case 0:
                        selectedDay = DayTab.getTabAt(pos).getText().toString();
                        Thread Logic = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                selectedDay = selectedDay.replace(".txt", "");
                            }
                        });
                        Logic.run();
                        System.out.println(selectedDay);
                        String path = getFilesDir().toString() + "/" + SelectedWeek;
                        File DayFinder = new File(path);
                        File[] list = DayFinder.listFiles();

                        File StartTimeFile = new File(path, selectedDay + "_StartTime.txt");
                        File EndTimeFile = new File(path, selectedDay + "_EndTime.txt");

                        for(int i = 0; i < list.length; i++) {
                            if (list[i].getName().contains(selectedDay)) {
                                try {
                                    StartTime = Integer.parseInt(FileUtils.readFileToString(StartTimeFile).toString());
                                    EndTime = Integer.parseInt(FileUtils.readFileToString(EndTimeFile).toString());
                                    Hours = EndTime - StartTime;
                                    GetUserPay();

                                    StartTimeDisplay.setText(StartTime + ":00");
                                    EndTimeDisplay.setText(EndTime + ":00");
                                    HoursWorkedDisplay.setText(String.valueOf(Hours));
                                    PayForShiftDisplay.setText(String.valueOf((int) (Hours * UserPay)));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void GetUserPay(){
        File filesDir = getFilesDir();
        File AccountPay = new File(filesDir, "AccountPay.txt");

        try{
            UserPay = Float.parseFloat(FileUtils.readFileToString(AccountPay));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}