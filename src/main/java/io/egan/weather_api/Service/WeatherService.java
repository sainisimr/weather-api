package io.egan.weather_api.Service;

import java.util.List;

import io.egan.weather_api.Entity.Weather;

public interface WeatherService {

	public List<Weather> findAll();

	public Weather findByWeatherId(String id);

	public List<String> findCities();

	public Weather findTopByCityOrderByTimestampDesc(String city);

	public String customfindLatestWeatherProperty(String city, String param);

	public Weather dailyAveragedWeather(String city, String date);

	public Weather createWeatherData(Weather weather);

	public Weather update(String id, Weather weather);

	public void delete(String id);
}
