package br.com.blocker.blockersapi;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockersApiApplicationTests {

	@Test
	void contextLoads() {
		BlockersApiApplication.main(new String[] {});
		assertTrue(true);
	}

}
