package learn.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapboxAPITest {

    @Test
    void getCoordinates() {
        String address = "32 Sallyport House, Newcastle upon Tyne, NE1 3QL";

        String expected = "32%20Sallyport%20House,%20Newcastle%20upon%20Tyne,%20NE1%203QL";

//        Object actual = MapboxAPI.getCoordinates(address);

//        assertEquals(expected, actual);

    }
}