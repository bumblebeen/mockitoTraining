import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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

//import org.junit.Assert;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

    //@InjectMocks annotation is used to create and inject the mock object missing
    @InjectMocks
    MathApplication mathApplication = new MathApplication();
    //MathApplication mathApplication;

    //@Mock annotation is used to create the mock object to be injected
    @Mock
    CalculatorService calcService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    public ArgumentCaptor<Integer> argumentCaptor;

    @Before
    public void setup () {
        // Via Constructor
        // mathApplication = new MathApplication(); // new MathApplication()
        // Via Setter
        // mathApplication.setCalculatorService(calcService);
    }

    @Test
    public void shouldCreateAMockUsingMockFunction() {
        Iterator mockIterator = mock(Iterator.class);
        given(mockIterator.next()).willReturn("Hello").willReturn("Mockito");
        String greeting = mockIterator.next() + " " + mockIterator.next();
        assertEquals("Hello Mockito", greeting);
    }

    @Test
    public void shouldTestAddViaStubbing(){
        //add the behavior of calc service to add two numbers
        when(calcService.add(10.0, 20.0)).thenReturn(30.00);

        //test the add functionality
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        verify(calcService).add(10.0, 20.0);
    }

    @Test
    public void shouldTestAddViaStubbingBddStyleSyntax(){
        //add the behavior of calc service to add two numbers
        given(calcService.add(10.0, 20.0)).willReturn(30.0);

        //test the add functionality
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        then(calcService).should().add(10.0, 20.0);
    }

    @Test
    public void shouldTestAddViaStubbingOtherSyntax(){
        //add the behavior of calc service to add two numbers
        doReturn(30.0).when(calcService).add(10.0, 20.0);

        //test the add functionality
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        then(calcService).should().add(10.0, 20.0);
        verify(calcService).add(10.0, 20.0);
    }

    @Test
    public void shouldTestVaryingCalls(){
        // Take note of multiple .thenReturn calls
        when(calcService.add(anyDouble(), anyDouble())).thenReturn(30.00).thenReturn(50.00);
        when(calcService.subtract(20.0,10.0)).thenReturn(10.00);

        // Notice we have 3 add calls
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
        assertEquals(mathApplication.add(10.0, 20.0), 50.0, 0);
        assertEquals(mathApplication.add(12.34, 56.78), 50.0, 0);
        assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);


        // Usage of atLeast, atMost, never
        verify(calcService, atLeast(1)).add(10.0, 20.0);
        verify(calcService, times(2)).add(10.0, 20.0);

        verify(calcService, atLeastOnce()).add(12.34, 56.78);

        verify(calcService, atMost(1)).subtract(20.0, 10.0);

        verify(calcService, never()).multiply(10.0,20.0);
    }

    @Test
    public void shouldTestOrderOfCalls(){
        when(calcService.add(10.0,20.0)).thenReturn(30.00);
        when(calcService.subtract(20.0,10.0)).thenReturn(10.00);

        //test the add functionality
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
        assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        InOrder order = inOrder(calcService);
        order.verify(calcService, times(2)).add(10.0, 20.0);
        // Not preferable when inOrder
        order.verify(calcService, atLeastOnce()).subtract(20.0, 10.0);
        order.verify(calcService, times(1)).add(10.0, 20.0);
    }

    @Test
    public void shouldReplyFromACallback ()     {
        //add the behavior to add numbers
        when(calcService.add(20.0, 10.0)).thenAnswer(new Answer<Double>() {
            @Override
            public Double answer(InvocationOnMock invocation) throws Throwable {
                //get the arguments passed to mock
                Object[] args = invocation.getArguments();
                double sum = 0;
                for(Object obj: args) {
                    sum+= Double.parseDouble(obj.toString());
                }
                //get the mock
                Object mock = invocation.getMock();

                //return the result
                return sum;
            }
        });

        assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

        verify(calcService).add(20.0, 10.0);
    }

    @Test
    public void shouldResetTheMock(){
        when(calcService.add(10.0,20.0)).thenReturn(30.00);

        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        reset(calcService);

        assertEquals(mathApplication.add(10.0, 20.0), 0.0, 0);
    }

    @Test (expected = RuntimeException.class)
    public void shouldTestExceptionUsingAnnotations(){
        //given(calcService.add(10.0, 20.0)).willThrow(new RuntimeException("I cannot add!"));
//        doThrow(new RuntimeException("WOOO")).when(calcService.add(10.0,20.0));
        //when(calcService.add(10.0, 20.0)).thenThrow(new RuntimeException("I cannot add!"));


        assertEquals(mathApplication.add(10.0, 20.0),30.0,0);

        // USELESS
        then(calcService).should(times(2)).add(10.0,20.0);
        verify(calcService, times(10000)).add(10.0, 20.0);
    }

    @Test
    public void shouldTestExceptionUsingExpectedException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("I cannot add!");

        given(calcService.add(10.0, 20.0)).willThrow(new RuntimeException("I cannot add!"));

        assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
    }

    @Test
    public void shouldTestExceptionOldSchool() {
        try {
            given(calcService.add(10.0, 20.0)).willThrow(new ArithmeticException("I cannot add!"));
            mathApplication.add(10.0, 20.0);
            fail("Arithmetic Exception didn't occur");
        } catch (ArithmeticException ae) {
            assertEquals("I cannot add!", ae.getMessage());
        }
    }

    @Test (timeout = 1000)
    public void shouldTestTimeout(){
        when(calcService.add(10.0,20.0)).thenAnswer(new Answer<Double>() {
            @Override
            public Double answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(500);
                return 30.0;
            }
        });

        assertEquals(mathApplication.add(10.0, 20.0), 30.0,0);

        // Should verify that within 1000ms the add function should be called
        verify(calcService).add(10.0, 20.0);
    }

    @Test
    public void shouldTestCaptors () {
        // simplifies creation of ArgumentCaptor -
        // useful when the argument to capture is a nasty generic class and you want to avoid compiler warnings
        when(calcService.square(anyInt())).thenReturn(100);
        assertEquals(100, mathApplication.square(10));

        verify(calcService).square(argumentCaptor.capture());
        assertEquals((Integer)10, argumentCaptor.getValue());

        reset(calcService);
        when(calcService.square(anyInt())).thenReturn(100);
        assertEquals(100, mathApplication.plusOneThenSquare(10));

        verify(calcService).square(argumentCaptor.capture());
        assertEquals((Integer)11, argumentCaptor.getValue());
    }
}