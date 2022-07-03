package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrderTest {
  private final RestClient client = new RestClient("/api/v1/orders");

  @Test
  public void checkOrder() {
    Response response = client.getOrders();
    compareResponseStatusAndOrderNotNull(response);
  }


  @Step("Compare Status code and Body not NULL")
  public void compareResponseStatusAndOrderNotNull(Response response) {
    response
            .then().statusCode(200)
            .assertThat().body("orders", notNullValue()); // 4 Проверь, что в тело ответа возвращается список заказов.
  }
}
