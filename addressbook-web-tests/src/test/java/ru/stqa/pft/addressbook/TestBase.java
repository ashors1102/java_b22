package ru.stqa.pft.addressbook;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase extends ApplicationManager {

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        init();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        stop();
    }

}
