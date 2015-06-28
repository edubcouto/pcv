package br.com.lebc.tests;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = { "classpath:/applicationContext.xml",
"/br/com/lebc/pcv/context-pcv.xml" })
public abstract class TestConfig {

}
