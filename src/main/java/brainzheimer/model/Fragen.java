package brainzheimer.model;

import java.util.*;

public class Fragen{


    Random random = new Random();

    //Random zahlen werden erzeugt fuer aufgaben
    int one = (random.nextInt(11) + 10);
    int two = random.nextInt(8) + 3;
    int three = random.nextInt(4) + 2;
    int four = random.nextInt(4) + 2;
    int five = random.nextInt(20) + 5;

    // nimmt Frage und zerteilt sie in Frage und Antwort
    public String[] getFrage(int level, int random){
        String[] frage = generateFrage(level,random);
        String[] frageAntwort = new String[3];
        if(level == 1){
            frageAntwort[0] = frage[0] + frage[1] + frage[2] + frage[3] + frage[4];
            frageAntwort[1] = frage[5];
            frageAntwort[2] = frageAntwort[0].replaceFirst("minus","-").replaceFirst("mal","*");
        } else {
            frageAntwort[0] = frage[0] + frage[1] + frage[2] + frage[3] + frage[4] + frage[5] + frage[6];
            frageAntwort[1] = frage[7];
            frageAntwort[2] = frageAntwort[0].replaceFirst("minus","-").replaceFirst("mal","*");
        }
        return frageAntwort;
    }

    // Erzeugt Fragen nach Schwierigkeitsstufe
    public String[] generateFrage(int level, int random){

        switch (level){
            case 1: {
                String[][] FragenLvlOne = new String[][]{
                        {"Was ist ", "" + one , " + ", "" + two, " " , "" + (one + two)},
                        {"Was ist ", "" + one  , " minus ", "" + two, " " , "" + (one - two)}
                };
                return FragenLvlOne[random];
            }
            case 2: {
                String[][] FragenLvlTwo = new String[][]{
                        {"Was ist ",one + "" , " + ", five + "", " minus " , two + "", " " , (one + five - two) + ""},
                        {"Was ist ",one + "" , " minus ", two + "", " + ", five + "", " ", ((one - two) + five) + ""}
                };
                return FragenLvlTwo[random];
            }
            case 3: {
                String[][] FragenLvlThree = new String[][]{
                        {"Was ist ",four + "" , " + ", two + "", " mal " , three + "", " " , (four + two * three) + ""},
                        {"Was ist ",two + "" , " mal ", three + "", " minus ", four + "", " ", ((two * three) - four) + ""}
                };
                return FragenLvlThree[random];
            }
            default: {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

}