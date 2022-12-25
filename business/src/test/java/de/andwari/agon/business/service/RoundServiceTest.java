package de.andwari.agon.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoundServiceTest {

    @InjectMocks
    private RoundService service;

    @Test
    void testGetNumberOfRounds() {
        assertEquals(service.getMaxNumberOfRounds(4), 2);
        assertEquals(service.getMaxNumberOfRounds(5), 3);
        assertEquals(service.getMaxNumberOfRounds(6), 3);
        assertEquals(service.getMaxNumberOfRounds(7), 3);
        assertEquals(service.getMaxNumberOfRounds(8), 3);
        assertEquals(service.getMaxNumberOfRounds(9), 4);
        assertEquals(service.getMaxNumberOfRounds(10), 4);
        assertEquals(service.getMaxNumberOfRounds(11), 4);
        assertEquals(service.getMaxNumberOfRounds(12), 4);
        assertEquals(service.getMaxNumberOfRounds(13), 4);
        assertEquals(service.getMaxNumberOfRounds(14), 4);
        assertEquals(service.getMaxNumberOfRounds(15), 4);
        assertEquals(service.getMaxNumberOfRounds(16), 4);
        assertEquals(service.getMaxNumberOfRounds(17), 5);
        assertEquals(service.getMaxNumberOfRounds(32), 5);
        assertEquals(service.getMaxNumberOfRounds(33), 6);
    }

}