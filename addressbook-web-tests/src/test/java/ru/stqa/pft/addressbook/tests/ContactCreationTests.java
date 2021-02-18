package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void checkForExistingGroup() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
    }

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().gotoAddNewPage();
        app.getContactHelper().createContact(new ContactData("Aleksey", "Shorshin", "+79162267194", "aleksey.shorshin@yandex.ru", "test1"), true);
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}
