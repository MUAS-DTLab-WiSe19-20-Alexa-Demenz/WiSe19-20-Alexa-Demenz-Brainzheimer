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

import java.util.Map;
import java.util.Optional;

import brainzheimer.model.Einstufungstest;
import brainzheimer.model.Fragen;
import com.amazon.ask.response.ResponseBuilder;
import brainzheimer.PhrasesAndConstants;


import static com.amazon.ask.request.Predicates.requestType;
import static com.amazon.ask.request.Predicates.intentName;

public class LaunchRequestHandler implements RequestHandler {


    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {


        AttributesManager attributesManager;
        attributesManager = input.getAttributesManager();
        Map<String,Object> persistentAttributes = attributesManager.getPersistentAttributes();



        //Intent zu Einstufungstest
        Intent intent = Intent.builder().withName("Einstufungstest").build();
        //Intnt zu Fragen
        Intent intent1 = Intent.builder().withName("Fragen").build();




        ResponseBuilder responseBuilder = input.getResponseBuilder();

        if(persistentAttributes.containsKey("EinstufungstestFinishe")){
            responseBuilder.withSimpleCard("Brainzheimer","Willkommen zurück beim Gehirntraining")
                    .withSpeech(PhrasesAndConstants.WELCOME1)
                    .withReprompt("")
                    .withShouldEndSession(false)
                    .build();
        } else {
            //"I want to tell you a secret. <amazon:effect name=\"whispered\">I am not a real human.</amazon:effect>.Can you believe it?"

            responseBuilder.withSimpleCard("Brainzheimer","Willkommen beim Gehirntraining")
                    .withSpeech(PhrasesAndConstants.WELCOME)
                    .withReprompt("")
                    .withShouldEndSession(false)
                    .build();

        }

        return responseBuilder.build();
    }

}
