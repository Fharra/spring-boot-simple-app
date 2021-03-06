package simpleApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import simpleApp.web.SimpleController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringContextIntegrationTest {

	@Autowired
	SimpleController simpleController;

	@Test
	public void contextLoads() {
		assertThat(simpleController).isNotNull();
	}
}