package com.vanancrm.Common;

public interface AppData {

    public String APP_URL1 = "https://secure-dt.com/crm/public/easy-quote";
    public String APP_URL2 = "http://texasmutliservices.com/crm/public/easy-quote";
    public String Stage_URL = "http://texasmutliservices.com/crm/public/easy-quote";
    public String Live_URL = "https://secure-dt.com/crm/user/login";
    public String env[] = {"live","stage"};
    public String credentials[] = {"Developer","7Dr@vn^C@}", "", ""};
    public String service1 = "Transcription";
    public String service2 = "Translation";
    public String service3 = "Captioning";
    public String service4 = "Typing";

    String[] audioTranscription = {"Notarization","Additional Acceptance Testing","Certificate","Verbatim","Mailing and Notary","US transcriber","Time code","Speaker Count", "Other Services","Need Translation"};
    String[] videoTranscription = {"Notarization","Additional Acceptance Testing","Certificate","Verbatim","Mailing and Notary","US transcriber","Time code","Speaker Count", "Other Services","Need Translation","Need Captioning"};
    String[] documentTranscription = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary", "Other Services","Need Translation"};
    String[] scriptTranscription = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary", "Other Services"};

    String[] audioTranslation = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary","Time code","Speaker Count", "Other Services","Need Transcription"};
    String[] videoTranslation = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary","Time code","Speaker Count", "Other Services","Need Transcription","Need Captioning"};
    String[] documentTranslation = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary", "Other Services", "Hand Written"};
    String[] scriptTranslation = {"Notarization","Additional Acceptance Testing","Certificate","Mailing and Notary", "Other Services"};

    String[] audioTyping = {"Mailing and Notary", "Other Services"};
    String[] videoTyping = {"Mailing and Notary", "Other Services"};
    String[] documentTyping = {"Mailing and Notary", "Other Services","Formatting","Hand Written"};
    String[] scriptTyping = {"Mailing and Notary", "Other Services"};

    String[] mailingOption = {"Standard","2 - 3 days","Overnight"};
    String[] timeCodeOption = {"Every 3 sec ($0.50 per minute)","Every 30 sec ($0.50 per minute)","Every 1 minute ($0.25 per minute)","Speaker change ($0.50 per minute)"};
    String[] speakerCountOption = {"1 Speaker ($0.00 per minute)","2 Speakers ($0.10 per minute)","3 to 5 Speakers ($0.25 per minute)","6 to 10 Speakers ($0.35 per minute)","10+ Speakers ($0.50 per minute)"};
    String[] othersOption = {"Translation","Transcription","Typing","Captioning / Subtitling","Voice Over","Video Services","Writing","Proof Reading","Medical Transcription",
            "Medical Billing / Medical Coding","Dictation","App Translation","Software Solutions","Website Translation","Localization","CRM Solutions","Virtual Assistant",
            "Customized APIs","Excel Services","Data research","Conversion Rate Optimization","Web designing","Website Solutions"};


    String[] transcriptionFileHeading = {"Source","Tier","Purpose","Content","Unit","Base Price Status","Discount Status","Addional Services Status","SubTotal Status","Transaction Fee Status","Order Total Status","Order Value Status","OverAll Status"};
    String[] translationFileHeading = {"Source","Target","Tier","Purpose","Content","Unit","Base Price Status","Discount Status","Addional Services Status","SubTotal Status","Transaction Fee Status","Order Total Status","Order Value Status","OverAll Status"};
    String[] typingFileHeading = {"Source","Tier","Purpose","Content","Unit","Base Price Status","Discount Status","Addional Services Status","SubTotal Status","Transaction Fee Status","Order Total Status","Order Value Status","OverAll Status"};
}
