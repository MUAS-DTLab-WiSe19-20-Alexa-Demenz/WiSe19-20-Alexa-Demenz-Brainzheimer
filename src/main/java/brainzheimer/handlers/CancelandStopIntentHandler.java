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
import com.amazon.ask.model.Response;
import brainzheimer.PhrasesAndConstants;

import java.util.*;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class CancelandStopIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        AttributesManager attributesManager;
        attributesManager = input.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        Map<String, Object> persistentAttributes = attributesManager.getPersistentAttributes();

        if (attributes.containsKey("comesFromEinstufung")){
            if(attributes.get("comesFromEinstufung").toString().equals("1")){
                return input.getResponseBuilder()
                        .withSpeech("Danke, dass du deinen Verstand mit Brainzheimer geschärft hast. Hoffentlich hat dir der Einstufungstest gefallen. Ich wuensche dir noch einen schönen tag")
                        .withSimpleCard(PhrasesAndConstants.CARD_TITLE, PhrasesAndConstants.CANCEL_AND_STOP)
                        .withShouldEndSession(true)
                        .build();
            } else if(attributes.get("comesFromEinstufung").toString().equals("0")){

                int questions = Integer.parseInt(attributes.get("QuestionCountEnd").toString());
                int wrong = Integer.parseInt(attributes.get("wrongAnswerEnd").toString());
                int right = questions - wrong;
                String level = persistentAttributes.get("Level").toString();
                int levelEnd = Integer.parseInt(level.substring(level.length()-1));
                int levelBegin = Integer.parseInt(attributes.get("LevelEnd").toString());

                return input.getResponseBuilder()
                        .withSpeech("Du hast das Gehirntraining nach " + Integer.toString(questions) + " Fragen beendet, davon waren " +  Integer.toString(right) + " richtig. " + levelChange(levelBegin,levelEnd) + " Dein neues Level ist " + levelEnd)
                        .withSimpleCard(PhrasesAndConstants.CARD_TITLE, "Danke ! \n" + "Fragen : " + questions + "\n" + "Richtig : " + right + "\n" + "Falsch : " + wrong + "\n" + "Level : " + level)
                        .withShouldEndSession(true)
                        .build();

            }
        }
        return input.getResponseBuilder()
                .withSpeech(PhrasesAndConstants.CANCEL_AND_STOP)
                .withSimpleCard(PhrasesAndConstants.CARD_TITLE, PhrasesAndConstants.CANCEL_AND_STOP)
                .withShouldEndSession(true)
                .build();


    }
    String levelChange(int begin,int end){
        if(begin > end){
            return " Somit ist dein Level um " + (begin - end) + " gesunken ! ";
        }
        else if(begin < end){
            return " Somit ist dein Level um " + (end - begin) + " gestiegen ! ";
        }
        else {
            return " Dein Level hat sich nicht verändert ! ";
        }
    }
}
