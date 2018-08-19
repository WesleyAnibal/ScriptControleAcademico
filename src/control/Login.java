package control;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
	
	private String matricula;
	private String senha;

	public Login(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}
	
	public void FazerLogin(WebDriver driver) {
		WebElement matricula = driver.findElement(By.name("login"));
		WebElement senha = driver.findElement(By.name("senha"));
		matricula.sendKeys(this.matricula);
		senha.sendKeys(this.senha);
		senha.submit();
	}
}
