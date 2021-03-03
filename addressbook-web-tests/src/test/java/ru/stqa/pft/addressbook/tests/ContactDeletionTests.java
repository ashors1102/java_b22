package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().list().size() == 0) {
                app.group().create(new GroupData().withName("test1"));
            }
            else {
                app.group().modify(0, new GroupData().withId(0).withName("test1"));
            }
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("Aleksey").withLastname("Shorshin").withMobile("+79162267194").withEmail("aleksey.shorshin@yandex.ru").withGroup("test1"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion () throws Exception {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        app.goTo().homePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
