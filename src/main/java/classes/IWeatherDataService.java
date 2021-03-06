package classes;

import exceptions.*;

public interface IWeatherDataService
{
    public WeatherData getWeatherData(Location location) throws WeatherDataServiceException;
}
