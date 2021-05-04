package gewirtz.openweathermap;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class OpenWeatherMapControllerTest {

    private OpenWeatherMapController controller;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
    }

    private void givenOpenWeatherMapController(){
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        controller = new OpenWeatherMapController();
        controller.celsius = mock(RadioButton.class);
        controller.fahrenheit = mock(RadioButton.class);
        controller.locationTextField = mock(TextField.class);
        controller.searchButton = mock(Button.class);
        controller.currentWeatherLabel = mock(Label.class);
        controller.currentIconImageView = mock(ImageView.class);
        controller.dayTextLabels = mock(ArrayList.class);
        controller.dayWeatherLabels = mock(ArrayList.class);
        controller.dayIconImageViews = mock(ArrayList.class);

        controller.toggleUnits = Arrays.asList(
                mock(RadioButton.class),
                mock(RadioButton.class));
    }

    @Test
    public void initialize(){
        //given
        givenOpenWeatherMapController();

        // when
        controller.initialize();

        // then
        verify(controller.fahrenheit).setSelected(true);

    }

    @Test
    public void onSearchCelsius(){
        //given
        givenOpenWeatherMapController();
        controller.celsius.setSelected(true);
        controller.fahrenheit.setSelected(false);

        //when
        controller.onSearch();

        //then
        verify(controller.celsius).setSelected(true);
    }

    @Test
    public void onSearchFahrenheit(){
        //given
        givenOpenWeatherMapController();
        controller.fahrenheit.setSelected(true);
        controller.celsius.setSelected(false);

        //when
        controller.onSearch();

        //then
        verify(controller.fahrenheit).setSelected(true);
    }
}

