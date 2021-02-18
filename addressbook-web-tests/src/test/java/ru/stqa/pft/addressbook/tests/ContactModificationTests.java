package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoGroupPage();
            if (!app.getGroupHelper().isThereAGroup()){
                app.getGroupHelper().createGroup(new GroupData("test1", null, null));
            }
            app.getNavigationHelper().gotoAddNewPage();
            app.getContactHelper().createContact(new ContactData("Aleksey", "Shorshin", "+79162267194", "aleksey.shorshin@yandex.ru", "test1"), true);
            app.getNavigationHelper().gotoHomePage();
        }
    }

    @Test
    public void testContactModification() throws Exception {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Aleksey_edit", "Shorshin_edit", "+7(916)226-71-94", "ashors1102@gmail.com", null), false);
        app.getContactHelper().updateContact();
        app.getNavigationHelper().gotoHomePage();
    }

    @Test
    public void testContactModificationThroughDetails() throws Exception {
        app.getContactHelper().initContactModificationThroughDetails();
        app.getContactHelper().submitModifyContact();
        app.getContactHelper().fillContactForm(new ContactData("Aleksey_edit", "Shorshin_edit", "+7(916)226-71-94", "ashors1102@gmail.com", null), false);
        app.getContactHelper().updateContact();
        app.getNavigationHelper().gotoHomePage();
    }
}
