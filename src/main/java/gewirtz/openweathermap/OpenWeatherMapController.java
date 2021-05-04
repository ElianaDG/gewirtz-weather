package gewirtz.openweathermap;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class OpenWeatherMapController {

    @FXML
    RadioButton celsius, fahrenheit;
    @FXML
    ImageView currentIconImageView;
    @FXML
    Button searchButton;
    @FXML
    TextField locationTextField;
    @FXML
    List<Label> dayTextLabels, dayWeatherLabels;
    @FXML
    List<ImageView> dayIconImageViews;
    @FXML
    Label currentWeatherTextLabel, currentWeatherLabel, forecastLabel;
    @FXML
    List<RadioButton> toggleUnits;
    final ToggleGroup tempUnits = new ToggleGroup();

    public void initialize(){
        for (RadioButton rb : toggleUnits) {
            rb.setToggleGroup(tempUnits);
        }
    }

    public void onSearch(){
        String units = celsius.isSelected() ? "metric" : "imperial";

        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        Disposable currentDisposable = service.getCurrentWeather(locationTextField.getText(), units)
                //request the data in the background
                .subscribeOn(Schedulers.io())
                //work w the data in the foreground
                .observeOn(Schedulers.trampoline())
                //work w the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);

        Disposable forecastDisposable = service.getWeatherForecast(locationTextField.getText(), units)
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
                int day = 1;
                for(Label textLabel : dayTextLabels) {
                    String date = forecast.getForecastFor(day).getDate().toString();
                    textLabel.setText(date.substring(0, date.indexOf("11")));
                    day++;
                }

                day = 1;
                for(Label weatherLabel : dayWeatherLabels) {
                    weatherLabel.setText(String.valueOf(forecast.getForecastFor(day).main.temp));
                    day++;
                }

                day = 1;
                for (ImageView icon : dayIconImageViews) {
                    icon.setImage(new Image(forecast.getForecastFor(day).weather.get(0).getIconUrl()));
                    day++;
                }
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
