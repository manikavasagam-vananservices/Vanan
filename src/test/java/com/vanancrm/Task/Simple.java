package com.vanancrm.Task;

public class Simple {

    public static void main(String args[]) {

        String url = "https://vanantranscript.com/Transcription-Quote";
        System.out.println(url.substring(url.indexOf("//")+2,url.indexOf(".")));
    }
}
