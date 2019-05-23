package com.provacredilink.sisplanetasapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.provacredilink.sisplanetasapi.model.Planeta;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SisplanetasApiApplicationTests {
	
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
    public void testGetAllPlanetas() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/planetas", HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }

   @Test
   public void testGetPlanetaById() {
       Planeta planeta = restTemplate.getForObject(getRootUrl() + "/planetas/1", Planeta.class);
       System.out.println(planeta.getNome());
       assertNotNull(planeta);
   }

   @Test
   public void testCreatePlaneta() {
       Planeta planeta = new Planeta();
       planeta.setId("99999");
       planeta.setNome("PlanetaMockTest");
       planeta.setClima("ClimaMockTest");
       planeta.setTerreno("TerrenoMockTest");
       ResponseEntity<Planeta> postResponse = restTemplate.postForEntity(getRootUrl() + "/planetas", planeta, Planeta.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }

   @Test
   public void testDeletePlaneta() {
        int id = 2;
        Planeta planeta = restTemplate.getForObject(getRootUrl() + "/planetas/" + id, Planeta.class);
        assertNotNull(planeta);
        restTemplate.delete(getRootUrl() + "/planetas/" + id);
        try {
             planeta = restTemplate.getForObject(getRootUrl() + "/planetas/" + id, Planeta.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }

}
