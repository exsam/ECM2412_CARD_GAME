import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(TestSuite.class);

    System.out.println("-------------------");
    System.out.println("--- JUNIT TEST  ---");
    System.out.println("-------------------");
    System.out.println("\n TESTS RAN - " + result.getRunCount() + "\n");

    for (Failure failure : result.getFailures()) {
      System.out.print("FAILURE - ");
      System.out.print(failure.toString());
      System.out.print("\n");
    }

    System.out.println("-------------------");
    System.out.println("ALL TEST PASS? - " + result.wasSuccessful());
    System.out.println("-------------------");
  }
}
