package hello

import io.restassured.response.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat;
import static io.restassured.RestAssured.*
import static io.restassured.config.JsonConfig.jsonConfig
import static io.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*
//import static io.restassured.module.jsv.JsonSchemaValidator.*
import io.restassured.path.json.config.JsonParserType

/**
 * Created by Administrator on 2017/8/5.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = Application.class)
class HelloSpec extends Specification {
//    @LocalServerPort
//    private int port;

    def setup()  {
        baseURI = "http://localhost:8080/"
    }

    def "get hello"() {
        when:
        Response response = given().when().log().all().get("/").then().extract();

        then:
        println response.asString()
        assertThat(response.asString(), equalTo("Greetings from Spring Boot!"));
    }
}
