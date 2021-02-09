package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class AddNewContactTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    app.getNavigationHelper().gotoAddNewPage();
    app.getContactHelper().fillContactForm(new ContactData("Aleksey", "Shorshin", "+79162267194", "aleksey.shorshin@yandex.ru"));
    app.getContactHelper().submitNewContact();
    app.getNavigationHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }
}
