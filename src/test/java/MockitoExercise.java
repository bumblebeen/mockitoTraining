import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.util.MockUtil;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Iterator;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.anyDouble;
import static org.mockito.BDDMockito.atLeast;
import static org.mockito.BDDMockito.atLeastOnce;
import static org.mockito.BDDMockito.atMost;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.inOrder;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MockitoExercise {

    @Spy
    MathApplication mathApplication;
    @Mock
    CalculatorService calculatorService;

    @Before
    public void setup () {
//        mathApplication = new MathApplication();
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
    public void ifYouStubMeCorrectlyYouMayPassSinceImStubbedToDeath () {
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

        // DO NOT MODIFY BELOW THIS LINE
        assertEquals("NAHULOG", first);
        assertEquals("LOG", second);
        verify(mockiTlog, atLeast(3)).log(anyString());
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
