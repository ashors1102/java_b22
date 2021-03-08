package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @DataProvider
    public Iterator<Object[]> validContacts(){
        List<Object[]> list = new ArrayList<>();
        File photo = new File("src/test/resources/stru.png");
        list.add(new Object[] {new ContactData().withFirstname("Firstname 1")
                                                .withLastname("Lastname 1")
                                                .withMobile("+7(999)-999-99-99")
                                                .withAddress("Address 1")
                                                .withEmail("user1@mail.com")
                                                .withGroup("test1")
                                                .withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstname("Firstname 2")
                                                .withLastname("Lastname 2")
                                                .withMobile("+7(888)-888-88-88")
                                                .withAddress("Address 2")
                                                .withEmail("user2@mail.com")
                                                .withGroup("test1")
                                                .withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstname("Firstname 3")
                                                .withLastname("Lastname 3")
                                                .withMobile("+7(777)-777-77-77")
                                                .withAddress("Address 3")
                                                .withEmail("user3@mail.com")
                                                .withGroup("test1")
                                                .withPhoto(photo)});
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        Contacts before = app.contact().all();
        app.goTo().AddNewPage();
        app.contact().create(contact, true);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
}
