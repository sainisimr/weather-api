package io.egan.weather_api.WeatherRepository;

import io.egan.weather_api.Entity.Weather;

public interface WeatherRepositoryCustom {

	public String customfindLatestWeatherProperty(String city, String param);

	public Weather dailyAveragedWeather(String city, String date);
}
