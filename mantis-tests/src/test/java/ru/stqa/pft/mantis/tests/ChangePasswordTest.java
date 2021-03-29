package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.MantisUser;

import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase {
    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }

    @Test
    public void testLogin() throws Exception {
        app.registration().login("administrator", "root");
        app.registration().showUserList();
        Thread.sleep(10000);
        MantisUser mantisUser = app.db().mantisUser();
        app.registration().selectUser(String.valueOf(mantisUser.getId()));
        app.registration().clickChangePassword();

        String userName = mantisUser.getUsername();
        boolean userExist = app.james().doesUserExist(userName);
        if (!userExist) {
            app.james().createUser(userName,"password");
        }
        else {
            app.james().drainEmail(userName, "password");
        }

        List<MailMessage> mailMessages = app.james().waitForMail(userName,"password",60000);
        String confirmationLink = findConfirmationLink(mailMessages, userName + "@localhost");
        app.registration().changePassword(confirmationLink, "password1");

        assertTrue(app.newSession().login(userName,"password1"));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email){
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore(). build();
        return regex.getText(mailMessage.text);
    }
}
