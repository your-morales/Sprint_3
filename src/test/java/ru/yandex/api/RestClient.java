package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {
  private final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
  private final String BASE_PATH;

  public RestClient(String base_path) {
    BASE_PATH = base_path;
  }

  @Step("Send POST request to /api/v1/courier")
  public Response create(CourierPojo createCourier) {
    return sendPost(createCourier, ""); //Создание пользователя
  }

  @Step("Send POST request to /api/v1/courier")
  public Response create(CreateOrderTest createOrderTest) {
    return buildUrl()
            .body(createOrderTest)
            .post(); //Создание пользователя
  }

  @Step("Send POST request to /api/v1/courier/login")
  public Response login(CourierPojo loginCourier) {
    return sendPost(loginCourier, "/login"); // Авторизация пользователя
  }

  @Step("Send GET request to /api/v1/courier/orders")
  public Response getOrders() {
    return buildJsonRequest()
            .get();
  }

  public Response delete(CourierPojo courierPojo) {
    return buildJsonRequest()
            .pathParam("id", courierPojo.getId())  // подставляем id
            .delete("{id}");
  }

  public Response cancel(String orderTrackCode) {
    return buildUrl()
            .queryParam("track", orderTrackCode)
            .put("/cancel");
  }


  private Response sendPost(CourierPojo courierPojo, String actionUrl) {
    return buildJsonRequest()
            .body(courierPojo)
            .post(actionUrl);
  }

  private RequestSpecification buildUrl() {
    return given().baseUri(BASE_URL)
            .basePath(BASE_PATH);
  }

  private RequestSpecification buildJsonRequest() {
    return buildUrl().contentType(ContentType.JSON);
  }


}
