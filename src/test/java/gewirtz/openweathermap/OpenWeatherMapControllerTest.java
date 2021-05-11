package gewirtz.openweathermap;

import io.reactivex.rxjava3.core.Single;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.mockito.Mockito.*;


public class OpenWeatherMapControllerTest {

    private OpenWeatherMapController controller;
    private OpenWeatherMapService service;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    private void givenOpenWeatherMapController() {
        service = mock(OpenWeatherMapService.class);

        controller = new OpenWeatherMapController(service);
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
    public void initialize() {
        //given
        givenOpenWeatherMapController();

        // when
        controller.initialize();

        // then
        verify(controller.fahrenheit).setSelected(true);
        verifyNoInteractions(controller.dayWeatherLabels);
        verifyNoInteractions(controller.dayIconImageViews);
        verifyNoInteractions(controller.dayTextLabels);

    }

    @Test
    public void onSearch(){
        // given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.locationTextField).getText();
        doReturn(true).when(controller.toggleUnits.get(1)).isSelected();

        // when
        controller.onSearch();

        // then
        verify(service).getCurrentWeather("New York", "imperial");
    }

    @Test
    public void onOpenWeatherMapFeed(){
        //given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.locationTextField).getText();
        doReturn(true).when(controller.toggleUnits.get(1)).isSelected();

        //when
        controller.onSearch();

        //then
        verify(service).getCurrentWeather("New York", "imperial");
    }

    @Test
    public void onOpenWeatherMapForecast(){
        //given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.locationTextField).getText();
        doReturn(true).when(controller.toggleUnits.get(1)).isSelected();

        //when
        controller.onSearch();

        //then
        verify(service).getWeatherForecast("New York", "imperial");

    }
}
