package gewirtz.openweathermap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

    @GET("/data/2.5/weather?appid=905f6f00b51877b5949460eb57dbebab")
    Single<OpenWeatherMapFeed> getCurrentWeather(@Query("q")String location);
}
