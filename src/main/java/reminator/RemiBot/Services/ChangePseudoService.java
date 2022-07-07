package reminator.RemiBot.Services;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reminator.RemiBot.bot.RemiBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChangePseudoService {

    private final ChromeDriver driver;

    public ChangePseudoService() throws InterruptedException, FileNotFoundException {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        driver.get("https://www.messenger.com/t/4326115330795163");
        System.out.println(driver.getCurrentUrl());
        new File("page.html");



        try {
            System.out.println("avant cookie1");
            PrintWriter printWriter = new PrintWriter("page.html");
            printWriter.write(driver.getPageSource());
            printWriter.close();
            WebElement cookie = driver.findElement(By.xpath("//button[@title='Only allow essential cookies']"));
            new Actions(driver)
                    .pause(Duration.ofMillis(500))
                    .click(cookie)
                    .perform();
        } catch (NotFoundException ignored){
            System.out.println("not find 1");
        }

        try {
            System.out.println("avant cookie2");
            Thread.sleep(5000);
            PrintWriter printWriter = new PrintWriter("page.html");
            printWriter.write(driver.getPageSource());
            printWriter.close();
            WebElement cookie = driver.findElement(By.xpath("//button[@title='Only allow essential cookies']"));
            System.out.println("après find cookie 2");
            System.out.println(cookie.getText());
            new Actions(driver)
                    .pause(Duration.ofMillis(500))
                    .click(cookie)
                    .perform();
        } catch (NotFoundException ignored){
            System.out.println("not find 2");
        }


        System.out.println("avant mail");
        PrintWriter printWriter = new PrintWriter("page.html");
        printWriter.write(driver.getPageSource());
        printWriter.close();
        WebElement emailElem = driver.findElement(By.id("email"));
        System.out.println("avant pass");
        printWriter = new PrintWriter("page.html");
        printWriter.write(driver.getPageSource());
        printWriter.close();
        WebElement passElem = driver.findElement(By.id("pass"));

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .sendKeys(emailElem, "rlaborie2000@gmail.com")
                .perform();

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .sendKeys(passElem, RemiBot.mdpFB)
                .sendKeys(Keys.ENTER)
                .perform();

/*
        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(buttonLogin)
                .perform();
*/



        Thread.sleep(5000);
        System.out.println(driver.getCurrentUrl());
        printWriter = new PrintWriter("page.html");
        printWriter.write(driver.getPageSource());
        printWriter.close();



        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'b20td4e0 muag1w35')]")));

        } catch (TimeoutException e) {
            new Actions(driver)
                    .click(driver.findElement(By.xpath("//div[contains(@class, 'j9ispegn pmk7jnqg k4urcfbm datstx6m b5wmifdl kr520xx4 mdpwds66 b2cqd1jy n13yt9zj eh67sqbx')]")))
                    .perform();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'b20td4e0 muag1w35')]")));
        }


        WebElement elements = driver.findElement(By.xpath("//div[contains(@class, 'b20td4e0 muag1w35')]"));
        WebElement personaliserElemContainer = elements.findElements(By.className("k4urcfbm")).get(0);
        List<WebElement> personaliserElems = personaliserElemContainer.findElements(By.xpath("//div[contains(@class, 'scb9dxdr dflh9lhu')]"));

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(personaliserElems.get(0))
                .perform();

        WebElement modifierElem = personaliserElems
                .get(1)
                .findElements(By.xpath("//div[contains(@class, 'oajrlxb2 gs1a9yip g5ia77u1 mtkw9kbi tlpljxtp qensuy8j ppp5ayq2 goun2846 ccm00jje s44p3ltw mk2mc5f4 rt8b4zig n8ej3o3l agehan2d sk4xxmp2 rq0escxv nhd2j8a9 mg4g778l pfnyh3mw p7hjln8o tgvbjcpo hpfvmrgz jb3vyjys qt6c0cv9 l9j0dhe7 i1ao9s8h esuyzwwr f1sip0of du4w35lb btwxx1t3 abiwlrkh p8dawk7l lzcic4wl a8c37x1j kvgmc6g5 cxmmr5t8 oygrvhab hcukyx3x beltcj47 p86d2i9g aot14ch1 kzx2olss dflh9lhu scb9dxdr')]"))
                .get(4);

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(modifierElem)
                .perform();
    }


    public void start() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div/div[3]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div/div[1]")));
                WebElement elements = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div/div[1]"));


                for (WebElement personne : elements.findElements(By.tagName("div"))) {

                    List<WebElement> nomsTanguy = personne.findElements(By.tagName("div"));

                    for (WebElement element : nomsTanguy) {
                        try {

                            WebElement elem = element.findElement(By.tagName("span"));
                            String name = elem.getText();

                            if (name.contains("Matéo Gat")) {
                                changePseudo(elem, "Le meilleur délégué");
                            }

                            if (name.contains("Tanguy Veyrenc de Lavalette")) {
                                changePseudo(elem, "Traitre numéro 1");
                            }

                            if (name.contains("Baptiste Pomarelle")) {
                                changePseudo(elem, "Traitre numéro 2");
                            }
                        } catch (NoSuchElementException | NullPointerException ignored) {}
                    }
                }


                //changePseudo("Tanguy Veyrenc de Lavalette", "/html/body/div[1]/div/div/div/div[3]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div/div[1]/div[22]/div[1]/div/div[2]/div/div/div", "Traitre numéro 1");
            }
        }, 0, 1000 * 5);
    }

    private void changePseudo(WebElement elem, String surnom) {

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(elem)
                .pause(Duration.ofMillis(500))
                .keyDown(Keys.CONTROL)
                .sendKeys("A")
                .keyUp(Keys.CONTROL)
                .pause(Duration.ofMillis(500))
                .sendKeys(surnom)
                .pause(Duration.ofMillis(500))
                .sendKeys(Keys.ENTER)
                .perform();
    }
}
