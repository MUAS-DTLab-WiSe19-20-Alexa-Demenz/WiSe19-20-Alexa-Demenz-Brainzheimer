package brainzheimer.model;




public class Einstufungstest{

    private int level = 0;




    private String[][] einstufungsFragen = new String[][]{
            {"Frage 1 : Was ist", "5", "+", "8", "13"},
            {"Frage 2 : Was ist", "7", "+", "9", "16"},
            {"Frage 3 : Was ist", "10", "minus", "8", "2"},
            {"Frage 4 : Was ist", "19", "minus", "11", "8"},
            {"Frage 5 : Was ist", "5", "mal", "8", "40"},
            {"Frage 6 : Was ist", "3", "mal", "9", "27"}

};

    private String[] randomResponse = new String[]{
            "Wow, dass war klasse ! ",
            "Der Hammer ! ",
            "Super ! ",
            "Toll ! ",
            "Oha, das war gut ! ",
            "Fantastisch ! ",
            "Stark ! ",
            "Du bist Spitze ! ",
            "Weltklasse ! ",
            "Das war gut ! "
    };

    public String getRandomResponse(int index) {
        return randomResponse[index];
    }

    public int getRandomResponseLength(){
        return randomResponse.length;
    }

    public String getFrage(int index){
        if(index < 0 || index > 5){
            index = 0;
        }
        return einstufungsFragen[index][0] + " " + einstufungsFragen[index][1] + " " + einstufungsFragen[index][2]
                + " " + einstufungsFragen[index][3];
    }

    public String getCardTitle(int index,boolean frage){
        if(index < 0 || index > 5){
            index = 0;
        }
        if(frage){
            return einstufungsFragen[index][0].substring(10) + " " + einstufungsFragen[index][1] + " " + einstufungsFragen[index][2].replaceFirst("minus", "-").replaceFirst("mal", "*")
                    + " " + einstufungsFragen[index][3];
        }
        return einstufungsFragen[index][0].substring(0,7);
    }
    public String getAntwort(int index){
        if(index < 0 || index >= 6){
            index = 1;
        }
        return einstufungsFragen[index][4];
    }


    public String getLevel(int [] Statistik) {

            int points = 0;
            for (int i = 0; i < Statistik.length; i++) {
                if((Statistik[i] == 1)) {
                    if((i < 2)){
                        points++;
                    }
                    else if(i < 4) {
                        points += 2;
                    }else{
                        points += 3;
                    }
                }
            }
            if(points >= 7){
                level = 3;
            }
            else if(points >= 5){
                level = 2;
            }
            else{
                level = 1;
            }

        return " Ihr Level ist : " + level;
    }
}