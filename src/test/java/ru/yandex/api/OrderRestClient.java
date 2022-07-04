package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderRestClient extends RestClient{

  protected OrderRestClient(String base_path) {
    super(base_path);
  }

  @Step("Send POST request to /api/v1/courier")
  public Response create(CreateOrderTest createOrderTest) {
    return buildUrl()
            .body(createOrderTest)
            .post(); //Создание пользователя
  }

  @Step("Send GET request to /api/v1/orders")
  public Response getOrders() {
    return buildJsonRequest()
            .get();
  }

  public Response cancel(String orderTrackCode) {
    return buildUrl()
            .queryParam("track", orderTrackCode)
            .put("/cancel");
  }
}
