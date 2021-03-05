package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().all().size() == 0){
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
    public void testContactModification() throws Exception {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Aleksey_edit").withLastname("Shorshin_edit").withMobile("+7(916)226-71-94").withEmail("ashors1102@gmail.com");
        app.contact().modify(contact);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }

    @Test
    public void testContactModificationThroughDetails() throws Exception {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Aleksey_edit").withLastname("Shorshin_edit").withMobile("+7(916)226-71-94").withEmail("ashors1102@gmail.com");
        app.contact().modifyThroughDetails(contact);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
