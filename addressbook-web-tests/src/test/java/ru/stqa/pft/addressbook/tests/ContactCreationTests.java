package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddNewPage();
    app.getContactHelper().fillContactForm(new ContactData("Aleksey", "Shorshin", "+79162267194", "aleksey.shorshin@yandex.ru", "test1"), true);
    app.getContactHelper().submitNewContact();
    app.getNavigationHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }
}
