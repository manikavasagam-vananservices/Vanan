package com.vanancrm.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateComparison {
    public static void main(String args[]) throws ParseException {

        //mintime += (Integer.parseInt(times[0])/60)*60;
        System.out.println((Integer.parseInt("02")*60));
        /*String leng1 = "01-22-20".replace("-" ,":");
        String leng2 = "04-22-20".replace("-" ,":");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = timeFormat.parse(leng1);
        Date date2 = timeFormat.parse(leng2);
        int totalLength =0;
        System.out.println(totalLength+"==========="+timeFormat.format(new Date(date2.getTime()-date1.getTime())));*/



          /*if (temp.contains("-")) {
                String lengths[] = temp.split("-");
                if (!lengths[0].equals("00")) {
                    totalLen = (int) Math.round(Integer.parseInt(lengths[0]) * 60);
                    totalLen += Integer.parseInt(lengths[1]);
                } else {
                    totalLen = Integer.parseInt(lengths[1]);
                }

                if (!lengths[2].equals("00")) {
                    totalSec += Integer.parseInt(lengths[2]);
                    if (totalSec >= 60) {

                        minutes = totalSec / 60;
                        if ((totalSec % 60) > 0) {
                            totalSecond = totalSec % 60;
                        } else {
                            totalSecond = 0;
                        }
                    } else {
                        totalSecond = totalSec;
                    }
                }
                totalLength += totalLen;
            } else {
                totalPa = Integer.parseInt(temp);
                totalPages += totalPa;
            }
            if (i == sheet.getLastRowNum()) {
                totalLength += minutes;
            }*/
        String leng1 = "01-22-20".replace("-" ,":");
        String leng2 = "04-22-20".replace("-" ,":");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = timeFormat.parse(leng1);
        Date date2 = timeFormat.parse(leng2);
        int totalLength =0;
        System.out.println(timeFormat.format(new Date(date2.getTime()-date1.getTime())));
    }

    private String timeConversion(int totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;
        return hours + ":" + minutes;
    }

    private String checkStatus(double data1, double data2, String message) {
        String status;
        System.out.println(message);
        if (data1 == data2) {
            System.out.print(": Pass\n");
            status = "Pass";
        } else {
            System.out.print(": Fail\n");
            System.out.println("Expected : " + data1);
            System.out.println("Actual : " + data2);
            status = "Fail\n" + "Expected : " + data1 + "\nActual : " + data2;
        }
        return status;
    }
}
