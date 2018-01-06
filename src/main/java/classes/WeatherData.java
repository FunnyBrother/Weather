package classes;

import weatherclasses.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherData
{
    private City city;
    private Clouds clouds;
    private Direction direction;
    private Humidity humidity;
    private Precipitation precipitation;
    private Pressure pressure;
    private Temperature temperature;
    private Visibility visibility;
    private Weather weather;
    private Wind wind;
    private Speed speed;
    private Sun sun;

    private void setAttribute(String currentTagName, Element rootElement)
    {
        NodeList currentList = rootElement.getElementsByTagName(currentTagName);
        Node currentNode = currentList.item(0);
        Element currentElement = (Element) currentNode;

        switch (currentTagName)
        {
            case "city":
                city = new City(currentElement.getAttribute("id"), currentElement.getAttribute("name"));
                break;
            case "coord":
                Coord coord = new Coord(currentElement.getAttribute("lon"), currentElement.getAttribute("lat"));
                city.setCoordinates(coord);
                break;
            case "country":
                city.setCountry(currentElement.getTextContent());
                break;
            case "sun":
                sun = new Sun(currentElement.getAttribute("rise"), currentElement.getAttribute("set"));
                city.setSun(sun);
                break;
            case "temperature":
                temperature = new Temperature(currentElement.getAttribute("value"), currentElement.getAttribute("min"),
                        currentElement.getAttribute("max"), currentElement.getAttribute("unit"));
                break;
            case "humidity":
                humidity = new Humidity(currentElement.getAttribute("value"), currentElement.getAttribute("unit"));
                break;
            case "pressure":
                pressure = new Pressure(currentElement.getAttribute("value"), currentElement.getAttribute("unit"));
                break;
            case "wind":
                wind = new Wind();
                break;
            case "speed":
                Speed speed = new Speed(currentElement.getAttribute("value"), currentElement.getAttribute("name"));
                wind.setSpeed(speed);
                break;
            case "direction":
                Direction direction = new Direction(currentElement.getAttribute("value"),
                        currentElement.getAttribute("code"), currentElement.getAttribute("name"));
                wind.setDirection(direction);
                break;
            case "clouds":
                clouds = new Clouds(currentElement.getAttribute("value"), currentElement.getAttribute("name"));
                break;
            case "visibility":
                visibility = new Visibility(currentElement.getAttribute("value"));
                break;
            case "precipitation":
                precipitation = new Precipitation(currentElement.getAttribute("value"),
                        currentElement.getAttribute("mode"));
                break;
            case "weather":
                weather = new Weather(currentElement.getAttribute("value"), currentElement.getAttribute("icon"),
                        currentElement.getAttribute("number"));
                break;
        }
    }

    public void setAllWeatherDataByTagNames(Document doc)
    {
        NodeList list = doc.getElementsByTagName("current");
        Node rootNode = list.item(0);
        Element rootElement = (Element) rootNode;

        setAttribute("city", rootElement);
        setAttribute("coord", rootElement);
        setAttribute("country", rootElement);
        setAttribute("sun", rootElement);
        setAttribute("temperature", rootElement);
        setAttribute("humidity", rootElement);
        setAttribute("pressure", rootElement);
        setAttribute("wind", rootElement);
        setAttribute("speed", rootElement);
        setAttribute("direction", rootElement);
        setAttribute("clouds", rootElement);
        setAttribute("visibility", rootElement);
        setAttribute("precipitation", rootElement);
        setAttribute("weather", rootElement);
    }

    @Override
    public String toString()
    {
        return "WeatherData \n[Clouds = " + getClouds()
                + ", \nWind = " + getWind()
                + ", \nHumidity = " + getHumidity()
                + ", \nPressure = " + getPressure()
                + ", \nVisibility = " + getVisibility()
                + ", \nPrecipitation=" + getPrecipitation()
                + ", \nWeather = " + getWeather()
                + ", \nTemperature = " + getTemperature() + "]";
    }

    public Clouds getClouds()
    {
        return clouds;
    }

    public Humidity getHumidity()
    {
        return humidity;
    }

    public Pressure getPressure()
    {
        return pressure;
    }

    public Speed getSpeed()
    {
        return speed;
    }

    public Sun getSun()
    {
        return sun;
    }

    public Temperature getTemperature()
    {
        return temperature;
    }

    public Visibility getVisibility()
    {
        return visibility;
    }

    public Weather getWeather()
    {
        return weather;
    }

    public Wind getWind()
    {
        return wind;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }
}
