package de.andwari.agon.business.matcher;

import de.andwari.agon.business.factory.TestDataFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SwissMatchingServiceTest {

    @Test
    void test() {
        SwissMatchingService service = new SwissMatchingService();
        var event = TestDataFactory.createEvent();
        var matchings = service.getSwissMatchings(event);
        Assertions.assertNotNull(matchings);
    }

}