package brainzheimer.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import brainzheimer.PhrasesAndConstants;
import brainzheimer.model.*;
import java.util.*;




import java.util.Map;
import java.util.Optional;


import static com.amazon.ask.request.Predicates.intentName;

public class FragenHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("Fragen"));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
        AttributesManager attributesManager;
        attributesManager = input.getAttributesManager();
        Map<String,Object> attributes = attributesManager.getSessionAttributes();
        Map<String,Object> persistentAttributes = attributesManager.getPersistentAttributes();

        if(!persistentAttributes.containsKey("Level")) {
            persistentAttributes.put("Level", "1");
        }
        if(!attributes.containsKey("wrongAnswer")){
            attributes.put("wrongAnswer",0);
        }
        if(!attributes.containsKey("wrongAnswerEnd")){
            attributes.put("wrongAnswerEnd",0);
        }

        Random random = new Random();
        int chooseQuestion = random.nextInt(2);
        String levelFull = persistentAttributes.get("Level").toString();
        String levelShort = levelFull.substring(levelFull.length()-1);
        int lvl = Integer.parseInt(levelShort);
        String[] FrageAntwort = new Fragen().getFrage(lvl,chooseQuestion);
        if(!attributes.containsKey("LevelEnd")) {
            attributes.put("LevelEnd", lvl);
        }



        //SessionAttributes
        attributes.put("Antwort", FrageAntwort[1]);
        attributes.put("comesFromEinstufung", 0);
        //checks if answer is right
        if(attributes.containsKey("QuestionCount")){
            int questions = Integer.parseInt(attributes.get("QuestionCount").toString());
            questions++;
            attributes.put("QuestionCount",questions);
        } else {
            attributes.put("QuestionCount",1);
        }
        //Zahlt Fragen um am end auszugeben
        if(attributes.containsKey("QuestionCountEnd")){
            int questions = Integer.parseInt(attributes.get("QuestionCountEnd").toString());
            questions++;
            attributes.put("QuestionCountEnd",questions);
        } else {
            attributes.put("QuestionCountEnd",1);
        }


        int questions = Integer.parseInt(attributes.get("QuestionCount").toString());



        if(questions >= 10){
            int wrong = Integer.parseInt(attributes.get("wrongAnswer").toString());
            String adjust = adjustLevel(questions, wrong);
            attributes.put("QuestionCount",0);
            attributes.put("wrongAnswer",0);

            if(adjust.equals("up")){
                int currentLvl = lvl;
                currentLvl++;
                if(currentLvl > 3){
                    currentLvl = 3;
                }
                persistentAttributes.put("Level", currentLvl);
            }
            if(adjust.equals("down")){
                int currentLvl = lvl;
                currentLvl--;
                if(currentLvl < 1){
                    currentLvl = 1;
                }
                persistentAttributes.put("Level", currentLvl);
            }
        }




        //save PersistentAttributes
        attributesManager.setPersistentAttributes(persistentAttributes);
        attributesManager.savePersistentAttributes();

        ResponseBuilder responseBuilder = input.getResponseBuilder();


        if(questions > 1){
            String richtig = new Einstufungstest().getRandomResponse(random.nextInt(new Einstufungstest().getRandomResponseLength()));
            responseBuilder.withSimpleCard("Frage : ",FrageAntwort[2])
                    .withSpeech(richtig + FrageAntwort[0])
                    .withReprompt(PhrasesAndConstants.FRAGE_REPROMPT)
                    .withShouldEndSession(false);
        } else {
            responseBuilder.withSimpleCard("Frage : ", FrageAntwort[2])
                    .withSpeech(FrageAntwort[0])
                    .withReprompt(PhrasesAndConstants.FRAGE_REPROMPT)
                    .withShouldEndSession(false);
        }
        return responseBuilder.build();
    }



    String adjustLevel(int answer, int wrong) {
        if (answer > 3) {
            if (answer * 0.4 <= wrong) {
                return "down";
            }
            int right = answer - wrong;
            if (answer * 0.8 <= right) {
                return "up";
            }
        }
        return "";
    }
}



