package io.egan.weather_api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.egan.weather_api.Constants.URI;
import io.egan.weather_api.Entity.Weather;
import io.egan.weather_api.Service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = URI.WEATHER)
@Api(tags = "Weather")
public class WeatherController {

	private WeatherService weatherservice;

	public WeatherController(WeatherService weatherservice) {
		this.weatherservice = weatherservice;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@ApiOperation(value = "Find all the weather details", notes = "Allows you to retrieve the weather of all the cities")
	public List<Weather> findAll() {
		return weatherservice.findAll();
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Page not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@RequestMapping(method = RequestMethod.GET, value = URI.PATH_VAR_ID)
	@ApiOperation(value = "Find weather by id", notes = "Returns the single city weather by id")
	public Weather findById(@PathVariable("id") String id) {
		return weatherservice.findByWeatherId(id);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Page not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@RequestMapping(method = RequestMethod.GET, value = URI.PATH_VAR_CITIES)
	@ApiOperation(value = "Find all the cities", notes = "Returns the list of cities")
	public List<String> findCities() {
		return weatherservice.findCities();
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Page not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@ApiOperation(value = "Find the latest weather of the city", notes = "Returns the latest weather of a single city by city name")
	@RequestMapping(method = RequestMethod.GET, value = URI.PATH_VAR_CITY)
	public Weather findLatestCityWeather(@PathVariable("city") String city) {
		return weatherservice.findTopByCityOrderByTimestampDesc(city);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Page not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@RequestMapping(method = RequestMethod.GET, value = URI.PATH_VAR_PARAM)
	@ApiOperation(value = "Find the weather Property", notes = "Returns the latest weather by property for a given city, e.g get the latest temperature for Chicago, get the latest humidity for Troy.")
	public String findLatestWeatherProperty(@PathVariable("city") String city, @PathVariable("param") String param) {
		return weatherservice.customfindLatestWeatherProperty(city, param);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Page not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@ApiOperation(value = "Find the particular day average weather of specific city", notes = "Returns the average weather in terms of temperature, humidity and pressure of a specific day of particular location")
	@RequestMapping(method = RequestMethod.GET, value = URI.PATH_VAR_DAILYAVG)
	public Weather dailyAveragedWeather(@PathVariable("city") String city, @PathVariable("date") String date) {
		return weatherservice.dailyAveragedWeather(city, date);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "internal server error") })
	@ApiOperation(value = "Create Weather JSON", notes = "Stores the JSON data in database")
	@RequestMapping(method = RequestMethod.POST)
	public Weather createWeatherData(@RequestBody Weather weather) {
		return weatherservice.createWeatherData(weather);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 404, message = "Bad Request"),
			@ApiResponse(code = 500, message = "internal server error") })
	@ApiOperation(value = "Update the weather details", notes = "update the weather JSON by id")
	@RequestMapping(method = RequestMethod.PUT, value = URI.PATH_VAR_ID)
	public Weather updateWeather(@PathVariable("id") String id, @RequestBody Weather weather) {
		return weatherservice.update(id, weather);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 404, message = "Bad Request"),
			@ApiResponse(code = 500, message = "internal server error") })
	@ResponseBody
	@ApiOperation(value = "Delete the weather details", notes = "Delete the weather JSON of specific id")
	@RequestMapping(method = RequestMethod.DELETE, value = URI.PATH_VAR_ID)
	public void deleteWeather(@PathVariable("id") String id) {
		weatherservice.delete(id);
	}
}
