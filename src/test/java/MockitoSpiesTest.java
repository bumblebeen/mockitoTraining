import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSpiesTest {

    @Spy
    List<String> spyList = new ArrayList<>();

    @Test
    public void shouldTestSpies () {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

    @Test
    public void shouldTestSpiesWithAnnotation () {
        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

    @Test
    public void shouldTestAStubbedSpy() {
        List<String> list = new ArrayList<String>();
        List<String> spyList = Mockito.spy(list);

        assertEquals(0, spyList.size());

        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Test
    public void shouldTestDifferenceBetweenMockAndSpy() {
        List mockedList = Mockito.mock(ArrayList.class);

        mockedList.add("one");
        verify(mockedList).add("one");

        assertEquals(0, mockedList.size());

        List spyList = Mockito.spy(new ArrayList());

        spyList.add("one");
        verify(spyList).add("one");

        assertEquals(1, spyList.size());
    }
}
