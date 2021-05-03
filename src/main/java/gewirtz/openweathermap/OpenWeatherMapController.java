package gewirtz.openweathermap;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapController {

    @FXML
    ImageView currentIconImage;
    @FXML
    TableView tableView;
    @FXML
    Button searchButton;
    @FXML
    TextField locationTextField;
    @FXML
    Label currentWeatherTextLabel, currentWeatherLabel, currentIconLabel, forecastLabel;
    @FXML
    TableColumn weatherColumn, iconColumn;

    @FXML
    public void initialize(){
    }



    public void onGetLocationWeather(){
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        Disposable disposable = service.getCurrentWeather(locationTextField.getText(), "imperial")
                //request the data in the background
                .subscribeOn(Schedulers.io())
                //work w the data in the foreground
                .observeOn(Schedulers.trampoline())
                //work w the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);

        currentWeatherTextLabel.setText("Current Weather:");

        disposable = service.getWeatherForecast(locationTextField.getText(), "imperial")
                //request the data in the background
                .subscribeOn(Schedulers.io())
                //work w the data in the foreground
                .observeOn(Schedulers.trampoline())
                //work w the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapForecast, this::onError);
    }

    public void onOpenWeatherMapForecast(OpenWeatherMapForecast forecast) {
        weatherColumn.setCellValueFactory(new PropertyValueFactory<OpenWeatherMapForecast.HourlyForecast.Main, String>("temp"));
        iconColumn.setCellValueFactory(new PropertyValueFactory<OpenWeatherMapForecast.HourlyForecast.Weather, String>("icon"));
        tableView.getItems().setAll(forecast.list);
        tableView.refresh();
    }

    private void onError(Throwable throwable) {
        //label that says there was an error
    }

    public void onOpenWeatherMapFeed(OpenWeatherMapFeed feed) {
        currentWeatherLabel.setText(String.valueOf(feed.main.temp));
    }
}
