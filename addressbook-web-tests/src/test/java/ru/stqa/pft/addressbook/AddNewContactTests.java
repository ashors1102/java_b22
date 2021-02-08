package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class AddNewContactTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    gotoAddNewPage();
    fillContactForm(new ContactData("Aleksey", "Shorshin", "+79162267194", "aleksey.shorshin@yandex.ru"));
    submitNewContact();
    gotoHomePage();
    logout();
  }
}
