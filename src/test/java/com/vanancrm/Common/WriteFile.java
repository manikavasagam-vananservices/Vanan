package com.vanancrm.Common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {

    public void writeFileContent(String fileName, List<String> fileContent) {

        BufferedWriter bw = null;
        FileWriter fw = null;
        String fileLocation = System.getProperty("user.dir")
                + "/src/test/resources/";
        try {

            fw = new FileWriter(fileLocation + fileName);
            bw = new BufferedWriter(fw);
            for (String content : fileContent) {
                bw.write(content);
            }

            System.out.println("File Createion Completed");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

}

