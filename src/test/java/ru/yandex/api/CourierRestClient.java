package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierRestClient extends RestClient {

  protected CourierRestClient(String base_path) {
    super(base_path);
  }

  @Step("Send POST request to /api/v1/courier")
  public Response create(CourierPojo createCourier) {
    return sendPost(createCourier, ""); //Создание пользователя
  }

  @Step("Send POST request to /api/v1/courier/login")
  public Response login(CourierPojo loginCourier) {
    return sendPost(loginCourier, "/login"); // Авторизация пользователя
  }

  public Response delete(CourierPojo courierPojo) {
    return buildJsonRequest()
            .pathParam("id", courierPojo.getId())  // подставляем id
            .delete("{id}");
  }

  private Response sendPost(CourierPojo courierPojo, String actionUrl) {
    return buildJsonRequest()
            .body(courierPojo)
            .post(actionUrl);
  }


}
