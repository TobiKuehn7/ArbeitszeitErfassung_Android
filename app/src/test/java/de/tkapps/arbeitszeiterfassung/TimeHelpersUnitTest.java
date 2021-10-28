package de.tkapps.arbeitszeiterfassung;

import org.junit.Test;
import java.util.Date;
import java.util.TimeZone;

import de.tkapps.arbeitszeiterfassung.helpers.TimeHelpers;
import static org.junit.Assert.assertEquals;


public class TimeHelpersUnitTest {

    @Test
    public void saveStringToDate_isCorrect() {
        // expected date
        long time = 1633684797000L;

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Date expectedDate = new Date(time);

        // actual date
        String timeString = "2021-10-08T11:19:57";
        Date actualDate = TimeHelpers.saveStringToDate(timeString);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void showStringToDate_isCorrect() {
        // expected date
        long time = 1633684797000L;
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Date expectedDate = new Date(time);

        // actual date
        String timeString = "*: 08.10.2021, 11:19:57";
        Date actualDate = TimeHelpers.showStringToDate(timeString);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void dateToSaveString_isCorrect() {
        // expected string
        String expectedString = "2021-10-08T11:19:57";

        // actual string
        long time = 1633684797000L;
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Date actualDate = new Date(time);
        String actualString = TimeHelpers.dateToSaveString(actualDate);

        assertEquals(expectedString, actualString);
    }

    @Test
    public void dateToShowString_isCorrect() {
        // expected string
        String expectedString = "08.10.2021, 11:19:57";

        // actual string
        long time = 1633684797000L;
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Date actualDate = new Date(time);
        String actualString = TimeHelpers.dateToShowString(actualDate);

        assertEquals(expectedString, actualString);
    }

    @Test
    public void calcTimeDiff_isCorrect(){
        // expected date difference
        Date expectedDateDifference = new Date(1800000);

        // actual date difference
        long timeStart = 1633684797000L;
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Date actualDateStart = new Date(timeStart);
        long timeEnd = 1633686597000L;
        Date actualDateEnd = new Date(timeEnd);
        Date actualDateDifference = TimeHelpers.calcTimeDiff(actualDateStart, actualDateEnd);


        assertEquals(expectedDateDifference, actualDateDifference);
    }

}