package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase  {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitNew() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("address"), contactData.getAddress());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void select(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelected() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void closeAlert() {
        wd.switchTo().alert().accept();
    }

    public void initModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void initModificationById(int id) {
        wd.findElement(By.xpath("//a[@href='edit.php?id=" + id +"']")).click();
    }

    public void update() {
        click(By.name("update"));
    }

    public void initModificationThroughDetailsById(int id) {
        wd.findElement(By.xpath("//a[@href='view.php?id=" + id +"']")).click();
    }

    public void submitModify() {
        click(By.name("modifiy"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void create(ContactData contact, boolean creation) {
        fillForm(contact, true);
        submitNew();
        contactCache = null;
    }

    public void modify(ContactData contact) {
        initModificationById(contact.getId());
        fillForm(contact, false);
        update();
        contactCache = null;
    }

    public void delete(int index) {
        select(index);
        deleteSelected();
        closeAlert();
        pauseAfterDeletion();
    }

    public void delete(ContactData contact) {
        selectById(contact.getId());
        deleteSelected();
        closeAlert();
        pauseAfterDeletion();
        contactCache = null;
    }

    public void modifyThroughDetails(ContactData contact) {
        initModificationThroughDetailsById(contact.getId());
        submitModify();
        fillForm(contact, false);
        update();
        contactCache = null;
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void pauseAfterDeletion() {
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.cssSelector("td:nth-child(1) input")).getAttribute("value"));
            String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            contacts.add(new ContactData().withId(id)
                                          .withFirstname(firstname)
                                          .withLastname(lastname));
        }
        return contacts;
    }

    private Contacts contactCache;

    public Contacts all() {
        if (contactCache != null){
            return new Contacts(contactCache);
        }
        Contacts contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List <WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            contactCache.add(new ContactData().withId(id)
                                              .withFirstname(firstname)
                                              .withLastname(lastname)
                                              .withAllPhones(allPhones)
                                              .withAddress(address)
                                              .withAllEmails(allEmails));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId())
                                .withFirstname(firstname)
                                .withLastname(lastname)
                                .withMobile(mobile)
                                .withHomePhone(home)
                                .withWorkPhone(work)
                                .withAddress(address)
                                .withEmail(email)
                                .withEmail2(email2)
                                .withEmail3(email3);
    }
}
