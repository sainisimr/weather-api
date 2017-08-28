package io.egan.weather_api.WeatherRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.egan.weather_api.Entity.Weather;


public interface WeatherRepository extends CrudRepository<Weather, String>, WeatherRepositoryCustom{

	public List<Weather> findAll();

	public Weather findByWeatherId(String id);

	@Query(value="SELECT DISTINCT e.city FROM weather e ORDER BY e.city ASC", nativeQuery=true)
	public List<String> findCities();


	public Weather findTopByCityOrderByTimestampDesc(String city); //Derived Query

	public String customfindLatestWeatherProperty(String city, String param);

	public Weather dailyAveragedWeather(String city, String date);
	
	public Weather save(Weather weather);

	public void delete(Weather weather);
}
