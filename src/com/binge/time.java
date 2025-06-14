package com.binge;

public class time {
    private int hour;
    private int minute;
    private int second;

    public time(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    public time(String currentTime){
        String[] time = currentTime.split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        second = Integer.parseInt(time[2]);
    }

    public String getCurrentTime(){
        return hour + ":" + minute + ":" + second;
    }

    public void oneSecondPassed(){
        second++;
        if (second == 60){
            minute++;
            second =0;
            if(minute == 60){
                hour++;
                minute = 0;
            }
        }
    }
}
