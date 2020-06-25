/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

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

public class EinstufungstestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("Einstufungstest"));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
        AttributesManager attributesManager;
        attributesManager = input.getAttributesManager();
        Map<String,Object> attributes = attributesManager.getSessionAttributes();
        Map<String,Object> persistentAttributes = attributesManager.getPersistentAttributes();


        ResponseBuilder responseBuilder = input.getResponseBuilder();

        String first = "";
        int counter;
        String Statistik = "";
        Random random = new Random();



        attributes.put("comesFromEinstufung",1);

        if(attributes.containsKey("finalStats")){
            Statistik = attributes.get("finalStats").toString();
        }



        String richtig = "";
        if(attributes.containsKey("counter")){
            counter = Integer.parseInt(attributes.get("counter").toString());
            counter++;
            richtig = new Einstufungstest().getRandomResponse(random.nextInt(new Einstufungstest().getRandomResponseLength()));
            attributes.put("counter",counter);
        } else {
            counter = 0;
            attributes.put("counter",counter);
        }

        if(!attributes.containsKey("Old")){
            attributes.put("Old",0);
        }

        if(counter == 0){
            first = "Einstufungstest ";
        }

        //Erstellen und auswerten der Statistik
        if(counter > 0){
            if(attributes.containsKey("Statistik")){
                String stats = attributes.get("Statistik").toString();
                    if(Integer.parseInt(attributes.get("Old").toString()) < Integer.parseInt(stats)){
                        Statistik += 0;
                    }else {
                        Statistik += 1;
                    }
                attributes.put("finalStats",Statistik);
                String StatistikOld = stats;
                attributes.put("Old",StatistikOld);
            } else {
                Statistik += 1;
                attributes.put("finalStats",Statistik);
            }
        }


        if(counter >= 6){


            String ende = "";

            if(!persistentAttributes.containsKey("EinstufungstestFinishe")){
                persistentAttributes.put("EinstufungstestFinishe",true);
            }

            String statistik;
            if(attributes.containsKey("Statistik")){
                statistik = attributes.get("Statistik").toString();
            } else {
                statistik = "0";
            }
            int[] level = new int[6];

            for(int i = 0; i < 6; i++){
                level[i] = Integer.parseInt(Statistik.substring(i,i+1));
            }

            // level als String
            String level1 = new Einstufungstest().getLevel(level);
            //int lvl = Integer.parseInt(level1.trim());
            persistentAttributes.put("Level", level1);
            //save Attributes for later
            attributesManager.setPersistentAttributes(persistentAttributes);
            attributesManager.savePersistentAttributes();
            // zum Testen des Arrays
            //String lvlarr = " 1 " + level[0] + " 2 " + level[1] + " 3 " + level[2] + " 4 " + level[3] + " 5 " + level[4] + " 6 " + level[5];

            responseBuilder.withSimpleCard(PhrasesAndConstants.CARD_TITLE," " + statistik + " Fehler \n" + level1)
                    .withSpeech(PhrasesAndConstants.EinstufungstestEnd + statistik + PhrasesAndConstants.EinstufungstestEnd1 + level1 + "\t. ..."+ PhrasesAndConstants.Fortfahren)
                    .withShouldEndSession(false);

        } else {
            responseBuilder.withSimpleCard(new Einstufungstest().getCardTitle(counter,false), new Einstufungstest().getCardTitle(counter,true))
                    .withSpeech(first + richtig + new Einstufungstest().getFrage(counter))
                    .withReprompt(PhrasesAndConstants.FRAGE_REPROMPT)
                    .withShouldEndSession(false);

        }
            // Render an error since we don't know what the users favorite color is.

        return responseBuilder.build();



    }



}
