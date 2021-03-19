package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase{

    @BeforeMethod
    public void checkForExistingPreconditions() {
        if (app.db().contacts().size() == 0) {
            if (app.db().groups().size() == 0) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1"));
            }
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("Aleksey")
                    .withLastname("Shorshin")
                    .withEmail("aaa-a@email.ru")
                    .withEmail2("123456@email.com")
                    .withEmail3("aaa123@email.рф"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}
