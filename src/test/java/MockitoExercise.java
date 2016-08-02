import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.internal.util.MockUtil;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MockitoExercise {

    MathApplication mathApplication;
    CalculatorService calculatorService;

    @Before
    public void setup () {
        mathApplication = new MathApplication();
    }

    @Test
    public void mockMeIfYouCan () {
        assertTrue(new MockUtil().isMock(calculatorService));
    }

    @Test
    public void iAlwaysWantedToBeASpy () {
        assertTrue(new MockUtil().isSpy(mathApplication));
    }

    @Test
    public void ifYouStubMeCorrectlyYouMayPassSinceYouveSTUBMeToDeath () {
        TestLogger mockiTlog = mock(TestLogger.class);
        MyLogger logger = new MyLogger(mockiTlog);

        // STUB HERE
        String value = "";

        // DO NOT MODIFY BELOW THIS LINE
        assertEquals("YOU KILLED ME", value);
        verify(mockiTlog).log(anyString());
    }

    @Test
    public void imExpectingANumberOfTimesYouUseYourStubToMe() {
        TestLogger mockiTlog = mock(TestLogger.class);
        MyLogger logger = new MyLogger(mockiTlog);
        // DO NOT EDIT ABOVE THIS LINE

        // WORK HERE
        String first = "";
        String second = "";
        String third = "";

        // DO NOT MODIFY BELOW THIS LINE
        assertEquals("NAHULOG", first);
        assertEquals("LOG", second);
        assertEquals("LOG", third);
        verify(mockiTlog, atLeast(3)).log(anyString());
    }

    @Test
    public void solveThePuzzleLikeABoss() {
        // Make sure you completed the 2nd test case before doing this
        final double num1 = 1.0;
        final double num2 = 2.0;
        final double num3 = 3.0;
        double result = 0;
        mathApplication.setCalculatorService(calculatorService);
        InOrder order = inOrder(calculatorService);
        // TODO: READ INSTRUCTION
        // SOLVE THE PUZZLE
        // Using the 3 numbers make the variable "result" 16
        // You can add more verification
        // P.S. you shouldn't use atLeast, atMost

        order.verify(calculatorService, times(1)).add(anyDouble(), anyDouble());
        order.verify(calculatorService, atLeast(100)).subtract(anyDouble(), anyDouble());
        order.verify(calculatorService, atMost(2)).multiply(anyDouble(), anyDouble());

        // DO NOT MODIFY BELOW THIS LINE
        assertEquals(16.0, result, 0.0);
    }

    @Test
    public void shouldReplyFromACallback () {
        TestLogger mockiTlog = mock(TestLogger.class);
        MyLogger logger = new MyLogger(mockiTlog);
        mathApplication.setCalculatorService(calculatorService);
        double addedValue = 0.0;
        // DO NOT MODIFY ABOVE THIS LINE

        // TODO: Stub the Calculator Service's add method. Answer with a CALLBACK
        // To ensure you used a callback
        // Stub the TestLogger as well and trigger the expectations inside the call back

        // START HERE

        // DO NOT MODIFY BELOW THIS LINE
        assertEquals(addedValue, 30.0, 0);
        assertEquals("I AM BACK", logger.log(anyString()));

        verify(calculatorService).add(20.0, 10.0);
        verify(mockiTlog).log("20.0");
        verify(mockiTlog).log("10.0");
    }

    @Test (expected = ArithmeticException.class)
    public void shouldThrowAnArithmeticError () {
        mathApplication.setCalculatorService(calculatorService);

        // START HERE

        mathApplication.divide(10.0, 0.0);
        then(calculatorService).should().divide(10.0,0.0);
    }

    @Test
    public void removeRecordOfKilledPersons () {
        // Make the HITMAN invisible by removing the number of killed persons
        // from the police even if he already killed so many
        HitMan hm = new HitMan();
        hm.kill("Jollibee");
        hm.kill("McDonalds");

        HitMan hiddenHitman = hm;
        String wayOfKillingKentucky = "";
        String wayOfKillingRazon = "";

        assertEquals(wayOfKillingKentucky, hiddenHitman.kill("Kentucky"));
        assertEquals(wayOfKillingRazon, hiddenHitman.kill("Razon"));
        assertEquals("HEADSHOT Bonchon", hiddenHitman.kill("Bonchon"));

        // integrity above all :)
        // hard code the answer here
        int noOfKilledPersons = 0;
        // DO NOT MODIFY BELOW THIS LINE
        assertEquals(0, hiddenHitman.getKilledPersons());
        assertEquals(noOfKilledPersons, hiddenHitman.getActualKilledPersons());
    }

    class HitMan {
        public List<String> killedPersons;
        HitMan() { killedPersons = new ArrayList<>(); }
        public String kill (String name) {
            String wayOfKilling = "Strangled";
            killedPersons.add(name);
            if(name.length() > 5) wayOfKilling = "Shot";
            return wayOfKilling + " " + name;
        }
        public int getKilledPersons () {
            return killedPersons.size();
        }
        public int getActualKilledPersons () {
            killedPersons.remove(0);
            return killedPersons.size();
        }
    }

    class MyLogger {
        TestLogger tLog;
        MyLogger(TestLogger tlog) { this.tLog = tlog; }
        public String log (String s) { return tLog.log(s); }
    }
    interface TestLogger {
        public String log (String s);
    }
}
