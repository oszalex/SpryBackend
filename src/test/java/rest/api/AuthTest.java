package rest.api;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import rest.Application;

import java.util.Arrays;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by chris on 09.11.14.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthTest {
    public static final String HOST = "http://localhost:";

    RestTemplate template = new TestRestTemplate();

    @Value("${local.server.port}")

    private int port;

    @Test

    public void checkAuth() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        long someRNDNumber = (long)(new Random().nextDouble()*999999999999L);
        String str_phoneNumber = String.valueOf(someRNDNumber);
        System.out.println("number: " + str_phoneNumber);

        //register
        ResponseEntity<String> entity = template.exchange(
                HOST + this.port + "/register/" + str_phoneNumber, HttpMethod.GET,
                new HttpEntity<Void>(headers), String.class);
        String token = entity.getBody();

        System.out.println("token: " + token);
        assertEquals(entity.getStatusCode(), HttpStatus.FOUND);


        //activate
        entity = template.exchange(HOST + this.port +
                        "/activate/" + str_phoneNumber + "/" + token, HttpMethod.GET,
                new HttpEntity<Void>(headers), String.class);

        String password = entity.getBody();
        System.out.println("pw: " + password);

        Assert.assertEquals(entity.getStatusCode(),HttpStatus.ACCEPTED);

    }
}