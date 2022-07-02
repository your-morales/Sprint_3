package ru.yandex.api;

public class CourierPojo {
  public CourierPojo(String login, String password, String firstName) { // Конструктор сериализации для Создания клиента
    this.login = login;
    this.password = password;
    this.firstName = firstName;
  }
  public CourierPojo(String login, String password) { // Конструктор сериализации для Логина
    this.login = login;
    this.password = password;
  }
  public CourierPojo(String id) { // Конструктор сериализация для получения id
    this.id = id;
  }
    public CourierPojo() {
    }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


public static CourierPojo ValidUser = new CourierPojo("Kakova","Krasota","PokazhiteRibov"); //Наговнокодил тут клиентов чуть чуть
  public static CourierPojo UserWoLogin = new CourierPojo(null,"Krasota","PokazhiteRibov");
  public static CourierPojo UserWoPassword = new CourierPojo("Kakova",null,"PokazhiteRibov");
  public static CourierPojo UserWoFirstName = new CourierPojo("Kakova","Krasota",null);
  public static CourierPojo UserDoubleLogin = new CourierPojo("Kakova","Prodayte","Ribov");
  public static CourierPojo ConstValidUserWoLogin = new CourierPojo("Bobi","Kotik");
  public static CourierPojo ConstUserWoLogin = new CourierPojo(null,"Kotik");
  public static CourierPojo ConstUserWoPassword = new CourierPojo("Bobi",null);
  public static CourierPojo UserNonExistent = new CourierPojo("Menya","Netu");
  public static CourierPojo UserIncorrectPassword = new CourierPojo("Bobi","KotikIncorrect");
  public static CourierPojo UserIncorrectLogin = new CourierPojo("BobiIncorrect","Kotik");

  private String login;
  private String password;
  private String firstName;
  private String id;
}
