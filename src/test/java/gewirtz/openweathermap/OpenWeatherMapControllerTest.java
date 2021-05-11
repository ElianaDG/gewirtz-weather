package gewirtz.openweathermap;

import io.reactivex.rxjava3.core.Single;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;


public class OpenWeatherMapControllerTest {

    private OpenWeatherMapController controller;
    private OpenWeatherMapService service;
    private OpenWeatherMapForecast forecast;
    private OpenWeatherMapFeed feed;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    private void givenOpenWeatherMapController() {
        service = mock(OpenWeatherMapService.class);
        forecast = mock(OpenWeatherMapForecast.class);
        feed = mock(OpenWeatherMapFeed.class);
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
                controller.celsius,
                controller.fahrenheit);
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
    public void onSearch_imperial(){
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
        verify(service).getWeatherForecast("New York", "imperial");
    }

    @Test
    public void onSearch_metric(){
        // given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "metric");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "metric");
        doReturn("New York").when(controller.locationTextField).getText();
        doReturn(true).when(controller.toggleUnits.get(0)).isSelected();

        // when
        controller.onSearch();

        // then
        verify(service).getCurrentWeather("New York", "metric");
        verify(service).getWeatherForecast("New York", "metric");
    }

    @Test
    public void setForecastLabels(){
        //given
        givenOpenWeatherMapController();

        OpenWeatherMapForecast.HourlyForecast hourlyForecast = mock(OpenWeatherMapForecast.HourlyForecast.class);
        OpenWeatherMapForecast.HourlyForecast.Main main = mock(OpenWeatherMapForecast.HourlyForecast.Main.class);
        List<OpenWeatherMapForecast.HourlyForecast.Weather> weather = Arrays.asList(
                mock(OpenWeatherMapForecast.HourlyForecast.Weather.class),
                mock(OpenWeatherMapForecast.HourlyForecast.Weather.class),
                mock(OpenWeatherMapForecast.HourlyForecast.Weather.class));

        hourlyForecast.main = main;
        main.temp = 70.0;
        hourlyForecast.weather = weather;

        doReturn(hourlyForecast).when(forecast).getForecastFor(anyInt());
        doReturn("http://openweathermap.org/img/wn/105@2x.png").
                when(hourlyForecast.weather.get(0)).getIconUrl();

        //when
        controller.setForecastLabels(forecast);

        //then
        for (int i = 0; i < controller.dayTextLabels.size(); i++) {
            verify(controller.dayWeatherLabels.get(i), times(1)).setText("70" + (char) 186);
            verify(controller.dayIconImageViews.get(i), times(1)).setImage(any(Image.class));
        }

    }

    @Test
    public void setFeedLabels(){
        //given
        givenOpenWeatherMapController();

        feed.main = mock(OpenWeatherMapFeed.Main.class);
        feed.main.temp = 65.50;

        doReturn(true).when(controller.toggleUnits.get(1)).isSelected();
        doReturn("http://openweathermap.org/img/wn/04n@2x.png").when(feed.weather.get(0)).getIconUrl();
        Image image = new Image(feed.weather.get(0).getIconUrl());

        // when
        controller.setFeedLabels(feed);

        // then
        verify(controller.currentWeatherLabel).setText("65.50");
        verify(controller.currentIconImageView).setImage(image);
    }
}
