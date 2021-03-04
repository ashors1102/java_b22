package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().all().size() == 0) {
                app.group().create(new GroupData().withName("test1"));
            }
// Черновой вариант проверки непустого списка групп на наличие группы с нужным именем
//            else {
//                Set<GroupData> elements = app.group().all();
//                for (GroupData element : elements){
//                    if (!element.getName().equals("test1")) {
//                        app.group().create(new GroupData().withName("test1"));
//                    }
//                    break;
//                }
//            }
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("Aleksey").withLastname("Shorshin").withMobile("+79162267194").withEmail("aleksey.shorshin@yandex.ru").withGroup("test1"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion () throws Exception {
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().delete(deletedContact);
        app.goTo().homePage();

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
/**
 * Сортировка списков при помощи lambda-выражения
 * */
/*
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
 */
        Assert.assertEquals(before, after);
    }
}
