package io.egan.weather_api.WeatherServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egan.weather_api.Entity.Weather;
import io.egan.weather_api.Exception.NotFoundException;
import io.egan.weather_api.Service.WeatherService;
import io.egan.weather_api.WeatherRepository.WeatherRepository;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WeatherRepository repository;

	public WeatherServiceImpl(WeatherRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Weather> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Weather findByWeatherId(String id) {
		Weather existing = repository.findByWeatherId(id);
		if (existing == null) {
			throw new NotFoundException("Weather for the city with ID: " + id + " doesn't exist");
		}
		return existing;
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> findCities() {
		return repository.findCities();
	}

	@Transactional(readOnly = true)
	@Override
	public Weather findTopByCityOrderByTimestampDesc(String city) {
		Weather existing = repository.findTopByCityOrderByTimestampDesc(city);
		if (existing == null) {
			throw new NotFoundException("Weather for the city: " + city + " doesn't exist");
		}
		return existing;
	}

	@Transactional(readOnly = true)
	@Override
	public String customfindLatestWeatherProperty(String city, String param) {
		String existing = repository.customfindLatestWeatherProperty(city, param);
		if (existing == null) {
			throw new NotFoundException("Weather parameter: " + param + "for the city: " + city + " doesn't exist");
		}
		return existing;
	}

	@Transactional
	@Override
	public Weather createWeatherData(Weather weather) {
		return repository.save(weather);
	}

	@Transactional(readOnly = true)
	@Override
	public Weather dailyAveragedWeather(String city, String date) {
		return repository.dailyAveragedWeather(city, date);
	}

	@Transactional
	@Override
	public Weather update(String id, Weather weather) {
		Weather existing = repository.findByWeatherId(id);
		if (existing == null) {
			throw new NotFoundException("Weather for the city with id: " + id + " doesn't exist");
		}
		return repository.save(weather);

	}

	@Transactional
	@Override
	public void delete(String id) {
		Weather existing = repository.findByWeatherId(id);
		if (existing == null) {
			throw new NotFoundException("Weather for the city with id: " + id + " doesn't exist");
		}
		repository.delete(existing);

	}

}
