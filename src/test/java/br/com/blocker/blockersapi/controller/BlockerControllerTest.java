package br.com.blocker.blockersapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.blocker.blockersapi.entity.ip.Ip;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BlockerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    ConnectionFactory cf;

    @BeforeEach
    public void setUp() {
        Flux.from(cf.create())
                .flatMap(c ->
                        c.createBatch()
                                .add("DROP TABLE IF EXISTS `ip`;")
                                .add("CREATE TABLE ip ( `id` int NOT NULL AUTO_INCREMENT, `address` VARCHAR(30) DEFAULT NULL, `origin` VARCHAR(20) DEFAULT NULL,  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .add("insert into ip(address, origin) values ( '192.168.0.1', 'Splunk') ")
                                .add("insert into ip(address, origin) values ( '192.168.0.17', 'System') ")
                                .execute()
                )
                .log()
                .blockLast();
    }
    
	
    @Test
    @Order(1)
    public void createCustomer() {
    	
        Ip ip = Ip.builder()
                .id(null)
                .address("192.168.0.18")
                .origin("Business")
                .build();

        webTestClient
                .post()
                .uri("/")
                .body(BodyInserters.fromValue(ip))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Ip.class)
                .value((ipAddress) ->
                        assertNotNull(ipAddress.getId())
                );
    }
    
    @Test
    @Order(2)
    public void getIp() {
        webTestClient
                .get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class)
                .value((ips) -> {
                    assertEquals(2, ips.size());
                });
    }

    @Test
    @Order(3)
    public void findByIp() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ip").queryParam("id", 1).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Ip.class)
                .value((ip) -> {
                    assertEquals("192.168.0.1", ip.getAddress());
                    assertEquals("Splunk", ip.getOrigin());
                });
    }
    
    @Test
    @Order(4)
    void delete() {
        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/").queryParam("id", 3).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
	                .is2xxSuccessful();
    }
    
}
