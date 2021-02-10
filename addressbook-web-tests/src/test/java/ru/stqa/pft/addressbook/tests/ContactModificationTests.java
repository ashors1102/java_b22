package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() throws Exception {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Aleksey_edit", "Shorshin_edit", "+7(916)226-71-94", "ashors1102@gmail.com"));
        app.getContactHelper().updateContact();
        app.getNavigationHelper().gotoHomePage();
    }
}
