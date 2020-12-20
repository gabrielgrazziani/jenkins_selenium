package dev.gabrielgrazziani;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TaskTest {

    private WebDriver web() throws MalformedURLException {
        ChromeOptions cap = new ChromeOptions();
        WebDriver drive = new RemoteWebDriver(new URL("http://192.168.0.22:4444/wd/hub/"),cap);
        drive.navigate().to("http://192.168.0.22:8001/tasks/");
        drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return drive;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException{
        WebDriver drive = web();
        try {
            drive.findElement(By.id("addTodo")).click();
            drive.findElement(By.id("task")).sendKeys("teste selenium");
            drive.findElement(By.id("dueDate")).sendKeys("30/12/2030");
            drive.findElement(By.id("saveButton")).click();
            String message = drive.findElement(By.id("message")).getText();
            assertEquals("Success!", message);
        } finally {
            drive.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException{
        WebDriver drive = web();
        try {
            drive.findElement(By.id("addTodo")).click();
            drive.findElement(By.id("task")).sendKeys("teste selenium");
            drive.findElement(By.id("dueDate")).sendKeys("30/12/2010");
            drive.findElement(By.id("saveButton")).click();
            String message = drive.findElement(By.id("message")).getText();
            assertEquals("Due date must not be in past", message);
        } finally {
            drive.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException{
        WebDriver drive = web();
        try {
            drive.findElement(By.id("addTodo")).click();
            drive.findElement(By.id("task")).sendKeys("teste selenium");
            drive.findElement(By.id("saveButton")).click();
            String message = drive.findElement(By.id("message")).getText();
            assertEquals("Fill the due date", message);
        } finally {
            drive.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException{
        WebDriver drive = web();
        try {
            drive.findElement(By.id("addTodo")).click();
            drive.findElement(By.id("dueDate")).sendKeys("30/12/2030");
            drive.findElement(By.id("saveButton")).click();
            String message = drive.findElement(By.id("message")).getText();
            assertEquals("Fill the task description", message);
        } finally {
            drive.quit();
        }
    }
}
