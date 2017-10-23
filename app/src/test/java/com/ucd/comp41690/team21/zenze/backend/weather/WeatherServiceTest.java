package com.ucd.comp41690.team21.zenze.backend.weather;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by timothee on 18/10/17.
 */

public class WeatherServiceTest {
    // Todo add unit tests for methods.

    @Test
    public void test_getUrlFromLocation() {
        double[] location = {1.0,2.0};
        assertEquals(WeatherService.getUrlFromLocation(location), "http://api.openweathermap.org/data/2.5/weather?appid=d9ae475ffdf0ffe9da6c449cb86acb8b&lat=1.0&lon=2.0");
    }

}
