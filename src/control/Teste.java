package control;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Teste {
	public static void main(String[] args) {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\weley\\eclipse-workspace\\Controle\\lib\\chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://pre.ufcg.edu.br:8443/ControleAcademicoOnline/");
		Login login = new Login("", "");
		login.FazerLogin(driver);
		
		List<List<String>> disci = TurmasDoAluno(driver);
		
		DisciplinasDoAluno(driver, disci);
		
		int obrigatorias = 0;
		int optativas = 0;
		driver.get("https://pre.ufcg.edu.br:8443/ControleAcademicoOnline/Controlador?command=AlunoHistorico");
		WebElement historico = driver.findElement(By.xpath("//*[@id=\"integralizacao\"]/table/tbody"));
		String[] disciplinas = historico.getText().split("\n");
		obrigatorias = Integer.parseInt(disciplinas[0].split(" ")[4]) - Integer.parseInt(disciplinas[0].split(" ")[5]);
		optativas = Integer.parseInt(disciplinas[1].split(" ")[4]) - Integer.parseInt(disciplinas[1].split(" ")[5]);
		System.out.println("Faltam "+obrigatorias+" créditos de obrigatorias");
		System.out.println("Faltam "+optativas+" créditos de optativas");
	}
	
	private static void DisciplinasDoAluno(WebDriver driver, List<List<String>> disci) {
		for (int i = 0; i < disci.size() ; i++) {
			driver.get("https://pre.ufcg.edu.br:8443/ControleAcademicoOnline/Controlador?command=AlunoTurmaNotas&codigo="+disci.get(i).get(0)+"&turma="+disci.get(i).get(1)+"&periodo=2018.2");
				
			WebElement nota1 = driver.findElement(By.xpath("//*[@id=\"conteudo\"]/div[4]/table/tbody/tr/td[4]"));
			WebElement nota2 = driver.findElement(By.xpath("//*[@id=\"conteudo\"]/div[4]/table/tbody/tr/td[5]"));
			WebElement nota3 = driver.findElement(By.xpath("//*[@id=\"conteudo\"]/div[4]/table/tbody/tr/td[6]"));
			
			String nota01 = nota1.getText().isEmpty()? "sem nota" : nota1.getText();
			String nota02 = nota2.getText().isEmpty()? "sem nota" : nota2.getText();
			String nota03 = nota3.getText().isEmpty()? "sem nota" : nota3.getText();
			
			System.out.println(disci.get(i).get(2) + "\n   ->nota 1: "+nota01+" \n   ->nota 2: "+nota02+" \n   ->nota 3: "+nota03);
			
			driver.get("https://pre.ufcg.edu.br:8443/ControleAcademicoOnline/Controlador?command=AlunoTurmaFrequencia&codigo="+disci.get(i).get(0)+"&turma="+disci.get(i).get(1)+"&periodo=2018.2");
			WebElement faltas = driver.findElement(By.xpath("//*[@id=\"conteudo\"]/div[4]/table/tbody/tr/td[4]"));
			System.out.println("\n   -> faltas:"+ faltas.getText()+"\n");

			
		}
	}
	
	public static List<List<String>> TurmasDoAluno(WebDriver driver) {
		List<List<String>> disci = new ArrayList<>();
		
		driver.get("https://pre.ufcg.edu.br:8443/ControleAcademicoOnline/Controlador?command=AlunoTurmasListar");
		
		for (int i = 1; i <= 5 ; i++) {
			try {
				WebElement codigo = driver.findElement(By.xpath("//div[@id='conteudo']/table/tbody/tr["+i+"]/td[2]"));
				WebElement turma = driver.findElement(By.xpath("//div[@id='conteudo']/table/tbody/tr["+i+"]/td[4]"));
				WebElement nome = driver.findElement(By.xpath("//div[@id='conteudo']/table/tbody/tr["+i+"]/td[3]"));
				
				List<String> disciplina = new ArrayList<String>();
				disciplina.add(codigo.getText());
				disciplina.add(turma.getText());
				disciplina.add(nome.getText());
				
				disci.add(disciplina);
			}catch(Exception e) {
				break;
			}
		}
		return disci;

	}
}

