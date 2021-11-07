package br.com.blocker.blockersapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class IpServiceTest {
	
    @Autowired
    ConnectionFactory cf;
    
	@Autowired
	IpService service;
	
    @BeforeEach
    void setUp() {
        Flux.from(cf.create())
                .flatMap(c ->
                        c.createBatch()
                                .add("DROP TABLE IF EXISTS `ip`;")
                                .add("DROP TABLE IF EXISTS `ipv6`;")
                                .add("CREATE TABLE ip ( `id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` int NOT NULL, `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .add("insert into ip(address, origin) values ( '-1062797052', 'IpServiceT') ")
                                .add("insert into ip(address, origin) values ( '-1062797037', 'IpServiceT') ")
                                .add("CREATE TABLE `ipv6` (`id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` varbinary(16) NOT NULL,  `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;")
                                .add("insert into ipv6(address, origin) values ( INET6_ATON('fe80::a8c6:6cff:fe96:7fd1'), 'Ipv6Test') ")
                                .execute()
                )
                .log()
                .blockLast();
    }	

	@Test
	@Order(1)
	void findAllTest() {		
		StepVerifier.create(service.findAll())
				.expectNextCount(2)
				.verifyComplete();
	}
	
	@Test
	@Order(2)
	void findByIdIpv4Test() {		
		StepVerifier.create(service.findByIdIpv4(1L))
		.expectNextCount(1)
		.verifyComplete();		
	}
	
	@Test
	@Order(3)
	void findByIdIpv6Test() {		
		StepVerifier.create(service.findByIdIpv6(1L))
		.expectNextCount(1)
		.verifyComplete();
	}
	
	@Test
	@Order(4)
	void deleteByIdTest() {		
		StepVerifier.create(service.deleteById(1L))
		.expectNextCount(1)
		.verifyComplete();
	}	
}
