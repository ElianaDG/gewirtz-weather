package gewirtz.openweathermap;

import gewirtz.openweathermap.OpenWeatherMapFeed;
import gewirtz.openweathermap.OpenWeatherMapService;
import io.reactivex.rxjava3.core.Single;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class OpenWeatherMapServiceTest {

    OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();

    @Test
    public void getCurrentWeather(){
        //given
        OpenWeatherMapService service = factory.newInstance();

        //when
        Single<OpenWeatherMapFeed> single = service.getCurrentWeather("London", "imperial");
        OpenWeatherMapFeed feed = single.blockingGet();

        //then
        assertNotNull(feed);
        assertNotNull(feed.main);
        assertTrue(feed.main.temp > 0);
        assertEquals("London", feed.name);
        assertTrue(feed.main.getTemp() < 150);
        assert(feed.dt > 0);



    }

    @Test
    public void getWeatherForecast(){
        //given
        OpenWeatherMapService service = factory.newInstance();

        //when
        Single<OpenWeatherMapForecast> single = service.getWeatherForecast("London", "imperial");
        OpenWeatherMapForecast forecast = single.blockingGet();

        //then
        assertNotNull(forecast);
        assertNotNull(forecast.list);
        assertFalse(forecast.list.isEmpty());
        assertTrue(forecast.list.get(0).dt > 0);
        assertNotNull(forecast.list.get(0).weather);



    }
}
