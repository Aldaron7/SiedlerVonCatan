package siedlervoncatan.test;

import java.lang.reflect.Field;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class TestParameterName
{
    public int           i          = 5;

    public Integer       test       = 5;

    public String        omghi      = "der";

    public static String testStatic = "THIS IS STATIC";
    @FXML
    private ImageView    test_0_1;

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException
    {
        TestParameterName t = new TestParameterName();
        for (Field f : t.getClass().getFields())
        {
            System.out.println(f.getGenericType() + " " + f.getName() + " = " + f.get(t));
        }
    }
}
