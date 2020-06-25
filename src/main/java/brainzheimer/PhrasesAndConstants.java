package brainzheimer;

import brainzheimer.model.Einstufungstest;

public class PhrasesAndConstants {

    private PhrasesAndConstants() {
        throw new IllegalStateException("Utility class");
    }




    public static final String CARD_TITLE = "Brainzheimer";
    public static final String COLOR_KEY = "COLOR";
    public static final String COLOR_SLOT = "Color";
    public static final String ANTWORT_SLOT = "number";
    public static final String ANTWORT1 = new Einstufungstest().getAntwort(1);
    public static final String DEINE_ANTWORT = "Deine Antwort";
    public static final String RICHTIG  = "ist Richtig";
    public static final String FALSCH = "ist Falsch";
    public static final String FRAGE1 = new Einstufungstest().getFrage(1);
    public static final String FRAGE_REPROMPT = "Bitte sage eine passende Antwort";
    public static final String OPERATOR = "+";
    public static final String EinstufungstestEnd = "Sie haben den Einstufungstest mit ";
    public static final String EinstufungstestEnd1 = " Fehlern beendet.";
    public static final String Fortfahren = " Wenn Sie fortfahren möchten sagen Sie Gehirntraining.";

    public static final String WELCOME = "Hi. Willkommen bei Brainzheimer. Sage Einstufungstest um zu starten. ";
    public static final String WELCOME1 = "Hi. Willkommen zurück bei Brainzheimer. Sage Gehirntraining um zu starten. ";
    public static final String WELCOME_CARD = "Willkommen beim Gehirntraining";
    public static final String HELP = "Mit dem Befehl nächste Frage kannst du die Frage überspringen.";
    public static final String HELP_REPROMPT = "Bitte sage mir eine Zahl als ergebnis.";
    public static final String CANCEL_AND_STOP = "Danke, dass du deinen Verstand mit Brainzheimer geschärft hast. Ich wuensche dir noch einen schönen tag";
    public static final String FALLBACK = "Tut mir leid, das weiss ich nicht. Sage einfach Hilfe.";



    public static final String GOOD_BYE ="Danke dass du Brainzheimer benutzt hast. Ich wuensche dir noch einen schoenen tag";


}
