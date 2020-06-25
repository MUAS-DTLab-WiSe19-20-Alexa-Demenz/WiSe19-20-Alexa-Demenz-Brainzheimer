package brainzheimer.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AntwortHandlerTest {

    private AntwortHandler handler;

    @Before
    public void setup() {
        handler = new AntwortHandler();
    }
    //return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));

    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        //when(inputMock.matches(intentName("AMAZON.CancelIntent"))).thenReturn(true);
        //when(inputMock.matches(intentName("AMAZON.StopIntent"))).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

}
