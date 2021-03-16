package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    File photo = new File("src/test/resources/stru.png");

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.db().contacts().size() == 0) {
            if (app.db().groups().size() == 0){
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1").withHeader("header_new").withFooter("footer_new"));
            }
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("Firstname_new")
                                                  .withLastname("Lastname_new")
                                                  .withMobile("+79162267194")
                                                  .withHomePhone("+79162267195")
                                                  .withWorkPhone("+79162267196")
                                                  .withAddress("peace")
                                                  .withEmail("email")
                                                  .withEmail2("email2")
                                                  .withEmail3("email3")
                                                  .withGroup("test1")
                                                  .withPhoto(photo), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                                               .withFirstname("Aleksey_edit1")
                                               .withLastname("Shorshin_edit1")
                                               .withMobile("+7(916)226-71-94")
                                               .withHomePhone("+7(916)226-71-90")
                                               .withWorkPhone("+7(916)226-71-99")
                                               .withAddress("qwerty")
                                               .withEmail("email_edit11")
                                               .withEmail2("email_edit21")
                                               .withEmail3("email_edit31")
                                               .withPhoto(photo);
        app.contact().modify(contact);
        app.goTo().homePage();
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }

    @Test
    public void testContactModificationThroughDetails() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                                               .withFirstname("Aleksey_edit2")
                                               .withLastname("Shorshin_edit2")
                                               .withMobile("+7(916)226-71-94")
                                               .withHomePhone("+7(916)226-71-91")
                                               .withWorkPhone("+7(916)226-71-92")
                                               .withAddress("qwerty1")
                                               .withEmail("email_edit21")
                                               .withEmail2("email_edit22")
                                               .withEmail3("email_edit23")
                                               .withPhoto(photo);
        app.contact().modifyThroughDetails(contact);
        app.goTo().homePage();
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
