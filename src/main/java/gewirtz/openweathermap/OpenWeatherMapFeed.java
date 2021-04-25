package gewirtz.openweathermap;

import java.util.List;

public class OpenWeatherMapFeed {
    public Main main;

    public static class Main {
        double temp;
        double feels_like;
        double temp_min;
        double temp_max;
        double pressure;
        double humidity;

        public double getTemp(){ return temp;}
        public double getFeelsLike(){ return feels_like;}
        public double getTempMin(){ return temp_min;}
        public double getTempMax(){ return temp_max;}
        public double getPressure(){ return pressure;}
        public double getHumidity(){ return humidity;}
    }
}
