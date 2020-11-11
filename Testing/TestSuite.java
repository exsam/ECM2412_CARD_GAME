import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
        PlayerTest.class,
        CardTest.class,
        CardDeckTest.class,
        //CardGameTest.class
})

public class TestSuite {
}