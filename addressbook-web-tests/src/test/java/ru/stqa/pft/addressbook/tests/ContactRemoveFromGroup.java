package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

public class ContactRemoveFromGroup extends TestBase{

    @BeforeClass
    public void checkForExistingPreconditions() {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test"));
        }
        if (contacts.size() == 0) {
            File photo = new File("src/test/resources/stru.png");
            app.goTo().AddNewPage();
            app.contact().create(new ContactData().withFirstname("TestFirstName")
                                                  .withLastname("TestLastNAme")
                                                  .withPhoto(photo)
                                                  .inGroup(groups.iterator().next()), true);
            app.goTo().homePage();
        }
        if (!contacts.contains(groups)) {
            app.goTo().homePage();
            ContactData contactData = app.db().contactWithoutGroup();
            app.contact().selectContactWithoutGroup(contactData);
            GroupData group = groups.iterator().next();
            app.contact().selectGroup(group);
            app.contact().pushAddToGroup();
        }
    }

    @Test
    public void contactRemoveFromGroupTest() {
            app.goTo().homePage();
            ContactData contactData = app.db().contactWithGroup();
            GroupData groupData = contactData.getGroups().iterator().next();
            app.contact().getGroupData(groupData);
            app.contact().pushRemoveFromGroup();
            app.goTo().homePage();
            ContactData contactData1 = app.db().contactById(contactData.getId());
            Assert.assertTrue(contactData1.getGroups().contains(groupData));
    }
}
