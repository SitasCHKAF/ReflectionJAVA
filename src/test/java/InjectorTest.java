import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }
    public void cleanUpStreams() {
        System.setOut(null);
    }


    @org.junit.jupiter.api.Test
    void Testinject() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        setUpStreams();
        SomeBean sb =(new Injector()).inject(new SomeBean());
        sb.foo();
        assertEquals("A"+System.lineSeparator()+"C"+System.lineSeparator(),output.toString());
        cleanUpStreams();
    }
}