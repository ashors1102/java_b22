package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void checkForExistingGroup() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
// Черновой вариант проверки непустого списка групп на наличие группы с нужным именем
//        else {
//            Set<GroupData> elements = app.group().all();
//            for (GroupData element : elements){
//                if (!element.getName().equals("test1")) {
//                    app.group().create(new GroupData().withName("test1"));
//                }
//                break;
//            }
//        }
        app.goTo().homePage();
    }

    @Test
    public void testContactCreation() throws Exception {
        Contacts before = app.contact().all();
        app.goTo().AddNewPage();
        ContactData contact = new ContactData().withFirstname("Aleksey").withLastname("Shorshin").withMobile("+79162267194").withEmail("aleksey.shorshin@yandex.ru").withGroup("test1");
        app.contact().create(contact, true);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
}
