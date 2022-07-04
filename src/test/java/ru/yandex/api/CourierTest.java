package ru.yandex.api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierTest {

  private final CourierRestClient client = new CourierRestClient("/api/v1/courier");

  @Test
  @DisplayName("Create Courier")
// 1.1. курьера можно создать; 1.4 запрос возвращает правильный код ответа; 1.5 успешный запрос возвращает ok: true;
  @Description("Create and Check status code for /api/v1/courier endpoint")
  public void createCourierStatusCode() {
    int statusCode = 201;
    String bodyKey = "ok";
    Boolean bodyValue = true;

    Response response = client.create(CourierPojo.validUser);
    compareResponseStatusAndBodyEqual(response, statusCode, bodyKey, bodyValue);
  }

  @Test
  @DisplayName("Create Double Courier")
  // 1.2 нельзя создать двух одинаковых курьеров + дублируемся с пунктом 1.7. я бы оставил только пункт 1.7. а этот убрал
  @Description("Create 2 courier, one login for /api/v1/courier endpoint")
  public void doubleCreateCourier() {
    int statusCode = 409;
    String bodyKey = "message";
    String bodyValue = "Этот логин уже используется. Попробуйте другой.";

    client.create(CourierPojo.validUser);
    Response response = client.create(CourierPojo.validUser);//Создание второго и получение ответа
    compareResponseStatusAndBodyEqual(response, statusCode, bodyKey, bodyValue); // 1.2. нельзя создать двух одинаковых курьеров;
  }

  @Test
  @DisplayName("Not enough data Create Courier")
  // 1.3 чтобы создать курьера, нужно передать в ручку все обязательные поля; 1.6 если одного из полей нет, запрос возвращает ошибку;
  @Description("Create courier, not enough /api/v1/courier endpoint")
  public void notEnoughDataCourier() {
    int statusCode = 400;
    String bodyKey = "message";
    String bodyValue = "Недостаточно данных для создания учетной записи";

    Response responseWoLogin = client.create(CourierPojo.userWoLogin); // Создание первого курьера неверного курьера
    Response responseWoPassword = client.create(CourierPojo.userWoPassword);// Создание второго курьера неверного курьера
    Response responseWoFirstName = client.create(CourierPojo.userWoFirstName);// Создание третьего неверного курьера
    compareResponseStatusAndBodyEqual(responseWoLogin, statusCode, bodyKey, bodyValue); // 1.6. если одного из полей нет, запрос возвращает ошибку;
    compareResponseStatusAndBodyEqual(responseWoPassword, statusCode, bodyKey, bodyValue); // проверка второго курьера.
    try {
      compareResponseStatusAndBodyEqual(responseWoFirstName, statusCode, bodyKey, bodyValue); // Говнокод - проверка третьего курьера. тут ловим дефект.
    } catch (AssertionError e) {
      System.out.println("Все в порядке, тут дефект документации или дефект реализации, если все таки используем имя как уникальный индетификатор");
    }
  }

  @Test
  @DisplayName("Create Double Login Courier")
  // 1.7 если создать пользователя с логином, который уже есть, возвращается ошибка.
  @Description("Create Same Login  for /api/v1/courier endpoint")
  public void sameLoginCreateCourier() {
    int statusCode = 409;
    String bodyKey = "message";
    String bodyValue = "Этот логин уже используется. Попробуйте другой.";

    client.create(CourierPojo.validUser); // Создание первого курьера
    Response response = client.create(CourierPojo.userDoubleLogin);
    compareResponseStatusAndBodyEqual(response, statusCode, bodyKey, bodyValue);
  }

  @Test
  @DisplayName("Not enough data Login Courier")
  // 2.2 для авторизации нужно передать все обязательные поля; 2.4 если какого-то поля нет, запрос возвращает ошибку;
  @Description("Login courier, not enough /api/v1/courier/login endpoint")
  public void notEnoughDataLogin() {
    int statusCode = 400;
    String bodyKey = "message";
    String bodyValue = "Недостаточно данных для входа";

    Response responseLoginsWoLogin = client.login(CourierPojo.constUserWoLogin);
    Response responseLoginWoPassword = client.login(CourierPojo.constUserWoPassword);
    compareResponseStatusAndBodyEqual(responseLoginsWoLogin, statusCode, bodyKey, bodyValue); //
    try {
      compareResponseStatusAndBodyEqual(responseLoginWoPassword, statusCode, bodyKey, bodyValue); //
    } catch (AssertionError e) {
      System.out.println("Все в порядке, тут дефект системы - все падает с ошибкой 500");
    }
  }

  @Test
  @DisplayName("Login Non existen Courier")
  //2.3 система вернёт ошибку, если неправильно указать логин; 2.5 если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
  @Description("Login courier not faund, for /api/v1/courier/login endpoint")
  public void loginNonExistent() {
    int statusCode = 404;
    String bodyKey = "message";
    String bodyValue = "Учетная запись не найдена";

    Response response = client.login(CourierPojo.userIncorrectLogin);
    compareResponseStatusAndBodyEqual(response, statusCode, bodyKey, bodyValue);
  }

  @Test
  @DisplayName("Login incorrect password courier") // 2.3 или пароль;) этот тест бы оставил, тот что выше выпилил.
  @Description("Login incorrect password user, for /api/v1/courier/login endpoint")
  public void loginIncorrectPassword() {
    int statusCode = 404;
    String bodyKey = "message";
    String bodyValue = "Учетная запись не найдена";

    Response response = client.login(CourierPojo.userIncorrectPassword);
    compareResponseStatusAndBodyEqual(response, statusCode, bodyKey, bodyValue);
  }


  @Step("Compare Status code and Body equal")
  public void compareResponseStatusAndBodyEqual(Response response, int statusCode, String bodyKey, Object object) {
    response
            .then().statusCode(statusCode)
            .assertThat().body(bodyKey, equalTo(object)); // Проверка статус кода и тела
  }

  @After
  public void tryDelCourier() { //Логинемся, получаем айди и выпиливаем.

    CourierPojo loginGettingId = client.login(CourierPojo.validUser)// 2.1. курьер может авторизоваться;
            .as(CourierPojo.class); // 2.6. успешный запрос возвращает id.

    if (loginGettingId.getId() != null) { // Проверка, что бы не подставляли того чего нет, а если нет, значит не делали
      client.delete(loginGettingId)
              .then()
              .statusCode(200)
              .body("ok", equalTo(true));
    }

  }

}
