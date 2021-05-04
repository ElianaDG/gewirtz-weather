package gewirtz.openweathermap;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OpenWeatherMapController {

    public void initialize(){

    }

    @FXML
    RadioButton celsius, fahrenheit;
    @FXML
    ImageView currentIconImageView;
    @FXML
    Button searchButton;
    @FXML
    TextField locationTextField;
    @FXML
    Label dayOneTextLabel, dayTwoTextLabel, dayThreeTextLabel, dayFourTextLabel, dayFiveTextLabel;
    @FXML
    Label currentWeatherTextLabel, currentWeatherLabel, currentIconLabel, forecastLabel;
    @FXML
    Label dayOneWeatherLabel, dayTwoWeatherLabel, dayThreeWeatherLabel, dayFourWeatherLabel, dayFiveWeatherLabel;
    @FXML
    ImageView dayOneIconImageView, dayTwoIconImageView, dayThreeIconImageView, dayFourIconImageView, dayFiveIconImageView;

    public void onSearch(){
        String units = celsius.isSelected() ? "metric" : "imperial";
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        Disposable disposable = service.getCurrentWeather(locationTextField.getText(), units)
                //request the data in the background
                .subscribeOn(Schedulers.io())
                //work w the data in the foreground
                .observeOn(Schedulers.trampoline())
                //work w the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);

        disposable = service.getWeatherForecast(locationTextField.getText(), units)
                //request the data in the background
                .subscribeOn(Schedulers.io())
                //work w the data in the foreground
                .observeOn(Schedulers.trampoline())
                //work w the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapForecast, this::onError);
    }

    public void onOpenWeatherMapForecast(OpenWeatherMapForecast forecast) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                dayOneTextLabel.setText(String.valueOf(forecast.getForecastFor(1).getDate()));
                dayTwoTextLabel.setText(String.valueOf(forecast.getForecastFor(2).getDate()));
                dayThreeTextLabel.setText(String.valueOf(forecast.getForecastFor(3).getDate()));
                dayFourTextLabel.setText(String.valueOf(forecast.getForecastFor(4).getDate()));
                dayFiveTextLabel.setText(String.valueOf(forecast.getForecastFor(5).getDate()));

                dayOneWeatherLabel.setText(String.valueOf(forecast.getForecastFor(1).main.temp));
                dayTwoWeatherLabel.setText(String.valueOf(forecast.getForecastFor(2).main.temp));
                dayThreeWeatherLabel.setText(String.valueOf(forecast.getForecastFor(3).main.temp));
                dayFourWeatherLabel.setText(String.valueOf(forecast.getForecastFor(4).main.temp));
                dayFiveWeatherLabel.setText(String.valueOf(forecast.getForecastFor(5).main.temp));

                dayOneIconImageView.setImage(new Image(forecast.list.get(0).weather.get(0).getIconUrl()));
                dayTwoIconImageView.setImage(new Image(forecast.list.get(0).weather.get(0).getIconUrl()));
                dayThreeIconImageView.setImage(new Image(forecast.list.get(0).weather.get(0).getIconUrl()));
                dayFourIconImageView.setImage(new Image(forecast.list.get(0).weather.get(0).getIconUrl()));
                dayFiveIconImageView.setImage(new Image(forecast.list.get(0).weather.get(0).getIconUrl()));
            }
        });
    }

    private void onError(Throwable throwable) {
        //label that says there was an error
    }

    public void onOpenWeatherMapFeed(OpenWeatherMapFeed feed) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                currentWeatherLabel.setText(String.valueOf(feed.main.temp));
                currentIconImageView.setImage(new Image(feed.weather.get(0).getIconUrl()));
            }
        });
    }
}
