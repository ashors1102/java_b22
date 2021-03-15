package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase{

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
                    .withMobile("+79162267194")
                    .withAddress("Советский_союз")
                    .withGroup("test1"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
    }

    public static String cleaned(String address){
        return address.replaceAll("\\n", "").replaceAll("\\s", "");
    }
}
