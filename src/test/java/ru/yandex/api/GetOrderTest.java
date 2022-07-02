package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrderTest {

  @Test
  public void checkOrder() {
    Response response = sendGetOrders();
    compareResponseStatusAndOrderNotNull(response);
  }

  @Step("Send GET request to /api/v1/courier/orders")
  public Response sendGetOrders() {
    return given().baseUri("http://qa-scooter.praktikum-services.ru").basePath("/api/v1/orders").get();
  }

  @Step("Compare Status code and Body not NULL")
  public void compareResponseStatusAndOrderNotNull(Response response) {
    response
            .then().statusCode(200)
            .assertThat().body("orders", notNullValue()); // 4 Проверь, что в тело ответа возвращается список заказов.
  }
}
