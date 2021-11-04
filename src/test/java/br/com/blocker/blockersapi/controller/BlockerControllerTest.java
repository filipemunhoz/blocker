package br.com.blocker.blockersapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.blocker.blockersapi.request.IpRequest;
import br.com.blocker.blockersapi.service.IpService;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BlockerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    ConnectionFactory cf;
    
    @Autowired
    IpService service;

    @BeforeEach
    public void setUp() {
        Flux.from(cf.create())
                .flatMap(c ->
                        c.createBatch()
                                .add("DROP TABLE IF EXISTS `ip`;")
                                .add("DROP TABLE IF EXISTS `ipv6`;")
                                .add("CREATE TABLE ip ( `id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` int NOT NULL, `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .add("insert into ip(address, origin) values ( '-1062797052', 'STS-Test') ")
                                .add("insert into ip(address, origin) values ( '-1062797037', 'STS-Test') ")
                                .add("CREATE TABLE `ipv6` (`id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` varbinary(16) NOT NULL,  `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .execute()
                )
                .log()
                .blockLast();
    }
	
    @Test
    @Order(1)
    public void addIpV4() {
    	
    	IpRequest ip = new IpRequest("192.168.1.1", "STS-IPV4");

        webTestClient
                .post()
                .uri("/")
                .body(BodyInserters.fromValue(ip))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .value(s ->
                        assertEquals(s, "IpV4: 192.168.1.1 - Saved")
                );
    }
    
    @Test
    @Order(2)
    public void addIpV6() {
    	
    	IpRequest ip = new IpRequest("fe80::a8c6:6cff:fe96:7fd1", "STS-IPV6");

        webTestClient
                .post()
                .uri("/")
                .body(BodyInserters.fromValue(ip))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .value(s ->
                        assertEquals("IpV6: fe80::a8c6:6cff:fe96:7fd1 - Saved", s)
                );
    }

    @Test
    @Order(3)
    public void invalidIp() {
    	
    	IpRequest ip = new IpRequest("AAA.BBB.CCC.DDD", "STS-IPV6");

        webTestClient
                .post()
                .uri("/")
                .body(BodyInserters.fromValue(ip))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
    
    @Test
    @Order(4)
    public void invalidSize() {
    	
    	IpRequest ip = new IpRequest("AAA", "STS-IPV6");

        webTestClient
                .post()
                .uri("/")
                .body(BodyInserters.fromValue(ip))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }    
    
    
    
    @Disabled
    @Test
    @Order(3)
    public void loadDataToRedis() {    	
    	service.loadDataToRedis().subscribe(System.out::println);
    }
    	
    
//    @Test
//    @Order(4)
//    public void getIp() {
//        webTestClient
//                .get()
//                .uri("/")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(List.class)
//                .value((ips) -> {
//                    assertEquals(2, ips.size());
//                });
//    }

//    @Test
//    @Order(3)
//    public void findByIp() {
//        webTestClient
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/ip").queryParam("id", 1).build())
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(Ipv4.class)
//                .value((ip) -> {
//                    assertEquals("192.168.0.1", ip.getAddress());
//                    assertEquals("Splunk", ip.getOrigin());
//                });
//    }
//    
//    @Test
//    @Order(4)
//    void delete() {
//        webTestClient
//                .delete()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/").queryParam("id", 3).build())
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//	                .is2xxSuccessful();
//    }
    
}
