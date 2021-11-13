package br.com.blocker.blockersapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
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
class BlockerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    ConnectionFactory cf;
    
    @Autowired
    IpService service;

    @BeforeEach
    void setUp() {
        Flux.from(cf.create())
                .flatMap(c ->
                        c.createBatch()
                                .add("DROP TABLE IF EXISTS `blocker-test.ip`;")
                                .add("DROP TABLE IF EXISTS `blocker-test.ipv6`;")
                                .add("CREATE TABLE `blocker-test.ip` ( `id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` int NOT NULL, `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .add("insert into `blocker-test.ip` (address, origin) values ( '-1062797052', 'STS-Test') ")
                                .add("insert into `blocker-test.ip` (address, origin) values ( '-1062797037', 'STS-Test') ")
                                .add("CREATE TABLE `blocker-test.ipv6` (`id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` varbinary(16) NOT NULL,  `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .execute()
                )
                .log()
                .blockLast();
    }
	
    @Test
    @Order(1)
    void addIpV4() {
    	
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
                        assertEquals("IpV4: 192.168.1.1 - Saved", s)
                );
    }
    
    @Test
    @Order(2)
    void addIpV6() {
    	
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
    void invalidIp() {
    	
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
    void invalidSize() {
    	
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

    @Test
    void publishInternalsIpv4Test() {

        webTestClient
                .get()
                .uri("/publish/ipv4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .value(s ->
                        assertEquals("Published IpV4", s)
                );
    }
}
