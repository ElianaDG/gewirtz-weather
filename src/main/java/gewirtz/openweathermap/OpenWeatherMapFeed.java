package gewirtz.openweathermap;

import java.util.List;

public class OpenWeatherMapFeed {
    Main main;
    long dt;
    String name;

    List<Weather> weather;

    public static class Weather{
        String icon;

        public String getIconUrl(){
            return "http://openweathermap.org/img/wn/" + icon + "@2x.png";
        }
    }

    public static class Main {
        double temp;
        public double getTemp(){ return temp;}
    }
}
