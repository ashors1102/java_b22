package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroup extends TestBase{

    @BeforeClass
    public void checkForExistingPreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test"));
        }
        if (app.db().contacts().size() == 0) {
            File photo = new File("src/test/resources/stru.png");
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("TestFirstName")
                                                  .withLastname("TestLastNAme")
                                                  .withPhoto(photo), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void contactAddToGroupTest(){
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        System.out.println(groups);
        for (ContactData contact : contacts) {
            System.out.println(contact.getId());
            System.out.println(contact.getGroups());
            System.out.println(groups.equals(contact.getGroups()));
            if (!groups.equals(contact.getGroups())){
                app.contact().selectById(contact.getId());
                app.contact().selectGroup(contacts);
                app.contact().addContactToGroup();
            }
            else {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1")
                                                  .withHeader("header_new")
                                                  .withFooter("footer_new"));
            }
            break;
        }
        Contacts contacts_after_add = app.db().contacts();
        assertThat(contacts.iterator().next(), equalTo(contacts_after_add.iterator().next()));
    }
}
