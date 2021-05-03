package gewirtz.openweathermap;

import java.util.Date;
import java.util.List;

public class OpenWeatherMapForecast {

    List<HourlyForecast> list;

    static class HourlyForecast{

        long dt;
        Main main;
        List<Weather> weather;

        static class Main {
            double temp;
        }

        static class Weather{
            String main; //description of weather
            String icon;

            public String getIconUrl(){
                return "http://openweathermap.org/img/wn/" + icon + "@2x.png";
            }

        }

        public Date getDate(){
            return new Date(dt * 1000);
        }

        @Override
        public String toString() {
            return getDate() + " " +
                    main.temp + " " +
                    weather.get(0).main + " " +
                    weather.get(0).getIconUrl();
        }

    }
}
