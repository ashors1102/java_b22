package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().list().size() == 0){
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
    public void testContactModification() throws Exception {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData().withId(before.get(before.size()-1).getId()).withFirstname("Aleksey_edit").withLastname("Shorshin_edit").withMobile("+7(916)226-71-94").withEmail("ashors1102@gmail.com");
        app.contact().modify(index, contact);
        app.goTo().homePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

    @Test
    public void testContactModificationThroughDetails() throws Exception {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("Aleksey_edit").withLastname("Shorshin_edit").withMobile("+7(916)226-71-94").withEmail("ashors1102@gmail.com");
        app.contact().modifyThroughDetails(index, contact);
        app.goTo().homePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
