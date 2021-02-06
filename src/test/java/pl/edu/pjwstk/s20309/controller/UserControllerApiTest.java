package pl.edu.pjwstk.s20309.controller;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import pl.edu.pjwstk.s20309.S20309Application;
import pl.edu.pjwstk.s20309.entity.User;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = S20309Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllEmployees() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/user/getAll",
                HttpMethod.GET, entity, String.class);
        assertNotNull(Objects.requireNonNull(response.getBody()));
    }

    @Test
    public void testGetUserById() {
        User user = restTemplate.getForObject(getRootUrl() + "/api/user/1", User.class);
        System.out.println(user.getFirstName());
        assertNotNull(user);
    }

    @Test
    public void testCreateEmployee() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setFirstName("peszek");
        user.setLastName("meszek");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/user/new", user, User.class);
        assertNotNull(postResponse);
        assertNotNull(Objects.requireNonNull(postResponse.getBody()));
    }

    @Test
    public void testUpdateEmployee() {
        int id = 1;
        User employee = restTemplate.getForObject(getRootUrl() + "/api/user/" + id, User.class);
        employee.setFirstName("admin1");
        employee.setLastName("admin2");
        restTemplate.put(getRootUrl() + "/api/user/update/" + id, employee);
        User updatedUser = restTemplate.getForObject(getRootUrl() + "/api/user/update/" + id, User.class);
        assertNotNull(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        int id = 2;
        User user = restTemplate.getForObject(getRootUrl() + "/api/delete/" + id, User.class);
        assertNotNull(user);
        restTemplate.delete(getRootUrl() + "/api/delete/" + id);
        try {
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
