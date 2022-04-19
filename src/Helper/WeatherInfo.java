package Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherInfo {
    public static String getWeather(String message, ModelforWeather model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&appid=7e71e0840dd0f838aa183f8a5cfcbc96&units=metric&lang=ru");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = " ";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);

        model.setName(object.getString("name"));


        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));
        model.setFeelslike(main.getDouble("feels_like"));
        JSONArray getArray = object.getJSONArray("weather");
        model.setTempmax(main.getDouble("temp_max"));
        model.setMinTemp(main.getDouble("temp_min"));
        model.setPressure(main.getDouble("pressure"));
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            //model.setMain((String) obj.get("main"));
        }
        return "Город:" + model.getName() + "\n" +
                "Температура:" + model.getTemp() + " C" + "\n" +
                "Влажность:" + model.getHumidity() + "%" + "\n" +
                "Ощущаеться как:" + model.getFeelslike() + "\n" +
                "Минимальная температура:" + model.getMinTemp() + "\n" +
                "Максимальная температура:" + model.getTempmax() + "\n" +
                "Давление:" + model.getPressure() + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() + ".png";
    }
}
