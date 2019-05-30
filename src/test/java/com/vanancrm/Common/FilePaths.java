package com.vanancrm.Common;

public interface FilePaths {
    public String screenshotParentPath = System.getProperty("user.dir") +  "//screenshots//";
    public String testDataParentPath = System.getProperty("user.dir") + "//src//test//resources//testdata//";
    // Basic price calculation(Transcription, Translation, Captioning, Typing)
    public String level1 = testDataParentPath + "EasyQuoteL1.xlsx";
    // Notarization(Transcription, Translation)
    public String level2 = testDataParentPath + "EasyQuoteL2.xlsx";
    //Quality Check(Transcription, Translation)
    public String level3 = testDataParentPath + "EasyQuoteL3.xlsx";
    //Certificate(Transcription, Translation)
    public String level4 = testDataParentPath + "EasyQuoteL4.xlsx";
    //Verbatim(Transcription)
    public String level5 = testDataParentPath + "EasyQuoteL5.xlsx";
    //Mailing(Transcription, Translation, Captioning, Typing)
    public String level6 = testDataParentPath + "EasyQuoteL6.xlsx";
    //US transcriber(Transcription, Captioning)
    public String level7 = testDataParentPath + "EasyQuoteL7.xlsx";
    //I have translation/transcription(Captioning)
    public String level8 = testDataParentPath + "EasyQuoteL8.xlsx";
    //Time code(Transcription, Translation)
    public String level11 = testDataParentPath + "EasyQuoteL11.xlsx";
    //Speaker Count(Transcription, Translation)
    public String level15 = testDataParentPath + "EasyQuoteL15.xlsx";

    // Notarization + US transcriber(Transcription)
    public String level9 = testDataParentPath + "EasyQuoteL9.xlsx";
    // Notarization + Quality Check (Transcription, Translation)
    public String level10 = testDataParentPath + "EasyQuoteL10.xlsx";
    // Notarization + Verbatim(Transcription)
    public String level12 = testDataParentPath + "EasyQuoteL12.xlsx";
    // Notarization + Certificate(Transcription, Translation)
    public String level13 = testDataParentPath + "EasyQuoteL13.xlsx";
    //Notarization + Time code(Transcription, Translation)
    public String level14 = testDataParentPath + "EasyQuoteL14.xlsx";
    //Notarization + Speaker Count(Transcription, Translation)
    public String level16 = testDataParentPath + "EasyQuoteL16.xlsx";
    //Notarization + Mailing(Transcription, Translation)
    public String level17 = testDataParentPath + "EasyQuoteL17.xlsx";
    //Certificate  + Speaker Count(Transcription, Translation)
    public String level18 = testDataParentPath + "EasyQuoteL18.xlsx";
    //Certificate  + Time code(Transcription, Translation)
    public String level19 = testDataParentPath + "EasyQuoteL19.xlsx";
    //Certificate + Quality Check(Transcription, Translation)
    public String level20 = testDataParentPath + "EasyQuoteL20.xlsx";
    //Speaker Count + Quality Check(Transcription, Translation)
    public String level21 = testDataParentPath + "EasyQuoteL21.xlsx";
    //Time code  + Quality Check(Transcription, Translation)
    public String level22 = testDataParentPath + "EasyQuoteL22.xlsx";

    //Transcription
    public String transcription = testDataParentPath + "Transcription.xlsx";
    //Translation
    public String translation = testDataParentPath + "Translation.xlsx";
    //Typing
    public String typing = testDataParentPath + "Typing.xlsx";
}
