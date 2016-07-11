package siedlervoncatan.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainTest extends Application
{

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Result result = JUnitCore.runClasses(HandelTest.class, PositionTest.class);

        for (Failure failure : result.getFailures())
        {
            System.out.println(failure);
            System.out.println(failure.getTrace());
        }
        System.out.println("Anzahl failures: " + result.getFailureCount());
        System.exit(0);
    }
}
