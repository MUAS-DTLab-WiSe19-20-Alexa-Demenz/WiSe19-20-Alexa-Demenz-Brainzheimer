package brainzheimer.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import brainzheimer.PhrasesAndConstants;
import brainzheimer.model.Einstufungstest;



import java.util.Map;
import java.util.Optional;


import static com.amazon.ask.request.Predicates.intentName;

public class AntwortHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AntwortIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        //Code zur Statistik


        AttributesManager attributesManager;
        attributesManager = input.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        Map<String, Object> persistentAttributes = attributesManager.getPersistentAttributes();


        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //Intent erstellen um den Intent zu triggern
        Intent intent1 = Intent.builder().withName("Einstufungstest").build();
        Intent intent2 = Intent.builder().withName("Fragen").build();

        Map<String, Slot> slots = intent.getSlots();
        Slot AntwortSlot = slots.get("number");

        //bool woher es kommt


        if (whereFrom(attributes.get("comesFromEinstufung").toString())) {
            int counter = Integer.parseInt(attributes.get("counter").toString());
            int stats;
            if (attributes.containsKey("Statistik")) {
                stats = Integer.parseInt(attributes.get("Statistik").toString());
                stats += 1;
                attributes.put("Statistik", stats);
            } else {
                stats = 1;
                attributes.put("Statistik", stats);
            }

            attributesManager.setSessionAttributes(attributes);


            ResponseBuilder responseBuilder = input.getResponseBuilder();
            if (new Einstufungstest().getAntwort(counter).equals(AntwortSlot.getValue())) {

                responseBuilder
                        .addDelegateDirective(intent1)
                        .withReprompt("")
                        .build();

            } else {
                //code zur statistik

                responseBuilder
                        .withSpeech(PhrasesAndConstants.DEINE_ANTWORT + " " + PhrasesAndConstants.FALSCH + ".  Die Richtige Antwort wäre " + new Einstufungstest().getAntwort(counter) + ": . . . Jetzt sage die lösung noch einmal")
                        .withReprompt("Sage die Lösung")
                        .withShouldEndSession(false)
                        .build();

            }

            return responseBuilder.build();
        } else {
            ResponseBuilder responseBuilder = input.getResponseBuilder();

            if (attributes.get("Antwort").toString().equals(AntwortSlot.getValue())) {
                responseBuilder
                        .addDelegateDirective(intent2)
                        .withReprompt("Sage die Lösung")
                        .withShouldEndSession(false)
                        .build();
            } else {
                if (attributes.containsKey("wrongAnswer")) {
                    int wrong = Integer.parseInt(attributes.get("wrongAnswer").toString());
                    wrong++;
                    attributes.put("wrongAnswer", wrong);
                } else {
                    attributes.put("wrongAnswer", 1);
                }
                //speichert gesamte falsche antworten
                if (attributes.containsKey("wrongAnswerEnd")) {
                    int wrong = Integer.parseInt(attributes.get("wrongAnswerEnd").toString());
                    wrong++;
                    attributes.put("wrongAnswerEnd", wrong);
                } else {
                    attributes.put("wrongAnswerEnd", 1);
                }

                responseBuilder
                        .withSpeech(PhrasesAndConstants.DEINE_ANTWORT + " " + PhrasesAndConstants.FALSCH + ".  Die Richtige Antwort wäre " + attributes.get("Antwort").toString() + ": \t Jetzt sage die lösung noch einmal")
                        .withReprompt("Sage die Lösung")
                        .withShouldEndSession(false)
                        .build();
            }

            return responseBuilder.build();
        }
    }


        public boolean whereFrom(String string){
        if(string.equals("1")){
            return true;
        }
            return false;
        }

    }

