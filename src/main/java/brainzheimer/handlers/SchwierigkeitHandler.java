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

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import brainzheimer.PhrasesAndConstants;

import java.util.Optional;


import static com.amazon.ask.request.Predicates.intentName;

public class SchwierigkeitHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("Schwierigkeit"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {


        ResponseBuilder responseBuilder = input.getResponseBuilder();


        // Render an error since we don't know what the users favorite color is.
        responseBuilder
                .withSpeech("Dein level fehlt")
                .withReprompt(PhrasesAndConstants.FRAGE_REPROMPT)
                .withShouldEndSession(false);


        return responseBuilder.build();


    }

}
