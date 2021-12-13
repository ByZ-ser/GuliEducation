package com.zhang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        System.out.println(sdf.format(date));


        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf2.format(date));
        System.out.println(date);

    }
}
