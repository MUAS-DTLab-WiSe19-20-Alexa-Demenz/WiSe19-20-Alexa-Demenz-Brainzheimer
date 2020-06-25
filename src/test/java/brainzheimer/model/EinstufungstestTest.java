package brainzheimer.model;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EinstufungstestTest {

    private Einstufungstest one = new Einstufungstest();



    @Test
    public void getFrage(){
        Assert.assertEquals("Frage 1 : Was ist 5 + 8", one.getFrage(0));
        Assert.assertEquals("Frage 5 : Was ist 5 mal 8", one.getFrage(4));
        assertNotEquals("Frage 5 : Was ist 6 mal 8", one.getFrage(7));
    }

    @Test
    public void getAntwort(){
        Assert.assertEquals("13", one.getAntwort(0));
        Assert.assertEquals("40", one.getAntwort(4));
    }


}