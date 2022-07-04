package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {
  private final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
  private final String BASE_PATH;

  protected RestClient(String base_path) {
    BASE_PATH = base_path;
  }

  protected RequestSpecification buildUrl() {
    return given().baseUri(BASE_URL)
            .basePath(BASE_PATH);
  }

  protected RequestSpecification buildJsonRequest() {
    return buildUrl().contentType(ContentType.JSON);
  }


}
