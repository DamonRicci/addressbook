package ca.carleton.sysc4806.damon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = App.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AddressBookApiIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAb_returnsOk() {
        ResponseEntity<String> response = restTemplate.getForEntity("/ab", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
