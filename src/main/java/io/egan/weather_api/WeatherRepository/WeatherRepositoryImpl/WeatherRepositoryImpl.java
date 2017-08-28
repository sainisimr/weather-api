package io.egan.weather_api.WeatherRepository.WeatherRepositoryImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.egan.weather_api.Entity.Weather;
import io.egan.weather_api.WeatherRepository.WeatherRepositoryCustom;

@Repository
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{

	@PersistenceContext
	private EntityManager em;

	
	@Override
	public String customfindLatestWeatherProperty(String city, String param) {
		TypedQuery<Weather> query = em
				.createQuery("SELECT e from Weather e where e.city=:pcity ORDER by e.timestamp Desc", Weather.class);
		query.setParameter("pcity", city);
		Weather value = query.getResultList().get(0);
		if (param.equalsIgnoreCase("Humidity"))
			return "Latest Reading of " + param + " in " +city+ " city" + " is " + value.getHumidity();
		else if (param.equalsIgnoreCase("Pressure"))
			return "Latest Reading of " + param + " in " +city+ " city" + " is " + value.getPressure();
		else if (param.equalsIgnoreCase("Temperature"))
			return "Latest Reading of " + param + " in " +city+ " city" + " is " + value.getTemperature();
		else if (param.equalsIgnoreCase("Speed"))
			return "Latest Reading of Wind " + param + " in " +city+ " city" + " is " + value.getWind().getSpeed();
		else if (param.equalsIgnoreCase("Degree"))
			return "Latest Reading of Wind" + param + " in " +city+ " city" + " is " + value.getWind().getDegree();
		return "Page Not Found. Search only available for the property of humidity, pressure and temperature.";

	}


	@Override
	public Weather dailyAveragedWeather(String city, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Weather averageWeather = null;
		Date supposed = null;
		try {
			supposed = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date dateSql = new java.sql.Date(supposed.getTime());
		System.out.println(dateSql);
		TypedQuery<Weather> query = em.createQuery("SELECT e from Weather e where e.city=:pcity and date(e.timestamp)=:day", Weather.class);
		query.setParameter("pcity", city);
		query.setParameter("day", dateSql);
		List<Weather> list = query.getResultList();
		if (!list.isEmpty()) {
			float averageTemp = 0, averageHum = 0, averagePres = 0;
			float total = list.size();
			averageWeather = new Weather();
			for (Weather weather : list) {
				averageTemp = averageTemp + Float.parseFloat(weather.getTemperature());
				averageHum = averageHum + Float.parseFloat(weather.getHumidity());
				averagePres = averagePres + Float.parseFloat(weather.getPressure());
			}
			averageWeather.setCity(city);
			averageWeather.setTimestamp(list.get(0).getTimestamp());
			averageWeather.setTemperature(String.format("%.2f", (averageTemp / total)));
			averageWeather.setHumidity(String.format("%.2f", (averageHum / total)));
			averageWeather.setPressure(String.format("%.2f", (averagePres / total)));
		}
		return averageWeather;
	}


}
