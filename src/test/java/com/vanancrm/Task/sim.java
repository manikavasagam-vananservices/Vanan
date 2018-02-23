package com.vanancrm.Task;

import java.util.Calendar;

public class sim {
    public static void main(String args[]) {
        String mp = "1-22-33";
        String hr = mp.substring(0,mp.indexOf("-"));
        String mn = mp.substring(mp.indexOf("-")+1,mp.lastIndexOf("-"));
        String ss = mp.substring(mp.lastIndexOf("-")+1,mp.length());

        System.out.println(mp + ":" + hr+"_"+mn+"_"+ss);

        Calendar now = Calendar.getInstance();
        now = Calendar.getInstance();
        now.add(Calendar.DATE, 2);
        System.out.println(now.get(Calendar.YEAR) + "-" + (now.get(Calendar
                .MONTH)
                + 1)
                + "-" + now.get(Calendar.DATE));
    }
}
