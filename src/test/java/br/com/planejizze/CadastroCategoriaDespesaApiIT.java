package br.com.planejizze;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCategoriaDespesaApiIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200QuandoConsultarListaDeCategoriaDespesa() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given()
                .basePath("/categoriaDespesa")
                .port(port)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y")
                .when()
                .get("/findAll")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus200QuandoCadastrarCategoriaDespesa() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given()
                .basePath("/categoriaDespesa")
                .body("{\"nome\": \"teste\"}")
                .port(port)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y")
                .when()
                .post("/save")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
