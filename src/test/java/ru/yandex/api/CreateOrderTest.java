package ru.yandex.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;


@RunWith(Parameterized.class)
public class CreateOrderTest {

  public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.metroStation = metroStation;
    this.phone = phone;
    this.rentTime = rentTime;
    this.deliveryDate = deliveryDate;
    this.comment = comment;
    this.color = color;
  }

  private final String firstName;
  private final String lastName;
  private final String address;
  private final String metroStation;
  private final String phone;
  private final int rentTime;
  private final String deliveryDate;
  private final String comment;
  private final String[] color;
  public String track = null;

  private final OrderRestClient client = new OrderRestClient("/api/v1/orders");


  @Parameterized.Parameters
  public static Object[][] setOrderBody() {
    return new Object[][]{
            {"DobuleColor", "lastName", "Address ", "metroStationNumber", "PhoneNumber", 1, "dateDelivery", "ok good", new String[]{"BLACK", "GREY"}}, // 3.2 можно указать оба цвета;
            {"OneColor", "lastName", "Address ", "metroStationNumber", "PhoneNumber", 2, "dateDelivery", "ooooks nice", new String[]{"BLACK"}},//3.1 можно указать один из цветов — BLACK или GREY;
            {"NotColor", "lastName", "Address ", "metroStationNumber", "PhoneNumber", 3, "dateDelivery", "good job", null}, // 3.3 можно совсем не указывать цвет;
    };
  }

  @Test
  public void sendCreateOrders() {
    CreateOrderTest order = new CreateOrderTest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    String jsonRequest = client.create(order)
            .then()
            .extract().asString();
    JsonPath jsonPath = new JsonPath(jsonRequest);
    track = jsonPath.getString("track");
  }

  @After
  public void canselCreateOrders() { //Подчищяем за собой
    if (track != null) {
      client.cancel(track)
              .then()
              .body("ok", equalTo(true)); // удаляем за собой
    }
    else {
      System.out.println("lalala");
    }
  }


}

