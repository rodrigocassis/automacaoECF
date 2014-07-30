package ECF.automacaoECF.padrao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.Assertion;

public class VerificacoesDeTela {
	org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeTela.class.getName());
	public String tempoMedio = new RecebeParametros().tempoMedio;
	int tempoMedioAceitavel = Integer.parseInt(tempoMedio);
	FuncionalidadesUteis utilidade = new FuncionalidadesUteis();

	public String usuario = new RecebeParametros().usuario;
	public String senha = new RecebeParametros().senha;

	private void falha(String mensagem, WebDriver driver, String nomeTeste) throws IOException {

		Random numeroAleatorio = new Random();
		String nomeDoScreenshot = "erro_" + nomeTeste.trim().toLowerCase() + "_" + numeroAleatorio.hashCode();
		File scrsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrsht, new File("./screenshot/" + nomeDoScreenshot + ".png"));
		fail(mensagem + " Screenshot gravado no diretório ./screenshot/ com o nome " + nomeDoScreenshot + ".png");

		encerraNavegador(driver);

	}
	public void aguardaCarregamento(String caminho, String xpathcarregaregistro, String nomeTeste, int tentativas, WebDriver driver) throws InterruptedException, IOException {
		long inicio = System.currentTimeMillis();

		Thread.sleep(1000);

		for (int second = 0;; second++) {
			logger.info("Aguardando o carregamento da tela " + caminho);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathcarregaregistro, driver, nomeTeste);
			try {

				if (driver.findElement(By.xpath(xpathcarregaregistro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		long duracaoCarregamento = System.currentTimeMillis() - inicio;
		logger.info("Tela " + caminho + " acessada com sucesso!!");

		if (duracaoCarregamento <= tempoMedioAceitavel) {
			logger.info("#OK# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		} else {
			logger.info("#ALERTA# Tempo de Carregamento: " + duracaoCarregamento + ", Tempo Aceitável: " + tempoMedioAceitavel);
		}

	}

	public void verificaElementos(int tentativas, String nomeTeste, WebDriver driver, String xpathAbaPesquisa, String xpathAbaResultados, String xpathAbaCadastro) throws InterruptedException, IOException {

		for (int second = 0;; second++) {
			logger.info("Localizando elementos da tela...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathAbaPesquisa + " || " + xpathAbaResultados + " || " + " || " + xpathAbaCadastro, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathAbaPesquisa)).isDisplayed() && driver.findElement(By.xpath(xpathAbaResultados)).isDisplayed() && driver.findElement(By.xpath(xpathAbaCadastro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Elementos localizados com sucesso!");

	}

	public void verificaPresencaCampos(String tela, String nomeTeste, WebDriver driver, int tentativas, String[] campos) throws InterruptedException, IOException {

		logger.info("Verificando campos da tela de " + tela + "...");

		int i = 0;
		while (i < campos.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando campos da tela de " + tela + "..." + campos[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + campos[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(campos[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}
	}

	public void verificaLabels(WebDriver driver, int tentativas, String[] xapthlabelsPesquisa, String[] labelsPesquisa) {

		logger.info("Verificando labels...");

		int i = 0;
		while (i < labelsPesquisa.length) {

			if (driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText().contentEquals(labelsPesquisa[i])) {

				logger.info("#OK# Esperado: " + labelsPesquisa[i] + ", Obtido: " + driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelsPesquisa[i] + ", Obtido: " + driver.findElement(By.xpath(xapthlabelsPesquisa[i])).getText());
			}

			i++;
		}
	}

	public void verificaBotoes(WebDriver driver, String nomeTeste, int tentativas, String[] botoesPesquisa) throws InterruptedException, IOException {

		logger.info("Verificando botoes esperados na tela...");
		int i = 0;
		while (i < botoesPesquisa.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando botoes esperados na tela... " + botoesPesquisa[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + botoesPesquisa[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(botoesPesquisa[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

	}

	public void verificaColunas(WebDriver driver, String caminho, String nomeTeste, int tentativas, String[] xpathColunas, String[] labelcolunas) throws InterruptedException, IOException {

		logger.info("Verificando a presenca das colunas...");
		int i = 0;
		while (i < xpathColunas.length) {
			for (int second = 0;; second++) {
				logger.info("Verificando a presenca das colunas... " + xpathColunas[i]);

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathColunas[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathColunas[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

		logger.info("Verificando Label das colunas...");
		int j = 0;
		while (j < xpathColunas.length) {
			if (driver.findElement(By.xpath(xpathColunas[j])).getText().contentEquals(labelcolunas[j])) {
				logger.info("#OK# Esperado: " + labelcolunas[j] + ", Obtido: " + driver.findElement(By.xpath(xpathColunas[j])).getText());
			} else {
				logger.info("#ALERTA# Esperado: " + labelcolunas[j] + ", Obtido: " + driver.findElement(By.xpath(xpathColunas[j])).getText());
			}
			j++;
		}
	}

	public void efetuaLoginComSucesso(WebDriver driver, int tentativas, String usuario, String senha, String nomeTeste) throws InterruptedException, IOException {

		logger.info("----------------------------------------------------");
		logger.info("ACESSANDO A APLICACAO");
		logger.info("----------------------------------------------------");

		driver.findElement(By.id("j_username")).clear();
		logger.info("Digitando usuario: " + usuario);
		driver.findElement(By.id("j_username")).sendKeys(usuario);
		driver.findElement(By.id("j_password")).clear();
		logger.info("Digitando senha: " + senha);
		driver.findElement(By.id("j_password")).sendKeys(senha);
		logger.info("Clicando no botão 'Entrar'");
		driver.findElement(By.id("taxit_btn_login")).click();

		for (int second = 0;; second++) {
			logger.info("Aguardando o acesso a aplicacao...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado taxit_solution_logo", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("taxit_solution_logo")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Aplicacao acessada com sucesso!");

	}

	public void acessaModuloECF(WebDriver driver, int tentativas, String xpathModulo, String nomeTeste) throws Throwable {
		boolean moduloPresente = false;

		org.apache.log4j.Logger logger = Logger.getLogger(VerificacoesDeTela.class.getName());
		for (int i = 0; i == (tentativas + 1); i++) {

			for (int second = 0; second < tentativas; second++) {
				logger.info(second);
				if (second >= tentativas) {
				}

				try {
					moduloPresente = verificaSeEstaNaTela(driver, xpathModulo);
					if (moduloPresente == true)
						logger.info(moduloPresente);
						break;

				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			
			efetuaLogout(driver, tentativas);
			efetuaLoginComSucesso(driver, tentativas, usuario, senha, nomeTeste);
		
			}

		logger.info("Acessando o módulo ONESOURCE ECF...");
		aguardaCarregamento(nomeTeste, xpathModulo, nomeTeste, tentativas, driver);
		driver.findElement(By.xpath(xpathModulo)).click();
		aguardaCarregamento(nomeTeste, "/html/body/div[3]/div[2]/div/ul/li/a", nomeTeste, tentativas, driver);
		}

		
	

	private boolean verificaSeEstaNaTela(WebDriver driver, String xpathModulo) {
		if (driver.findElement(By.xpath(xpathModulo)).isDisplayed()==true) {
			
			logger.info("true");
			return true;
			
		} else {
			logger.info("false");
			return false;
			
		}
	}
	public void acessaSistema(WebDriver driver, int tentativas, String url, String usuario, String senha, String navegador, String nomeTeste) throws InterruptedException, IOException {

		logger.info("----------------------------------------------------");
		logger.info("URL: " + url);
		logger.info("USUARIO: " + usuario);
		logger.info("SENHA: " + senha);
		logger.info("NAVEGADOR: " + navegador);
		logger.info("TESTE: " + nomeTeste);
		logger.info("----------------------------------------------------");

		logger.info("Acessando aplicação no endereco " + url + ", utilizando o navegador: " + navegador);

		driver.get(url);

		for (int second = 0;; second++) {
			logger.info("Aguardando tela de login ser carregada | Tentativa " + (second + 1) + " de " + (tentativas + 1));

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado j_username", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("j_username")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);

		}
		logger.info("Tela carregada com sucesso!");

	}
	public void acessaTela(WebDriver driver, int tentativas, String xpathmenu1, String xpathmenu2, String xpathtela, String nomeTeste) throws Throwable {

		for (int second = 0;; second++) {
			logger.info("Localizando Menu Principal... ");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado " + xpathmenu1, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathmenu1)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Menu Principal localizado com sucesso!");

		logger.info("Acessando a tela... " + nomeTeste);
		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(xpathmenu1));
		builder.moveToElement(MenuN1).build().perform();
		driver.findElement(By.xpath(xpathmenu2)).click();
		driver.findElement(By.xpath(xpathtela)).click();

	}

	public void acessaTela(WebDriver driver, int tentativas, String xpathmenu1, String xpathtela) {
		logger.info("Acessando a tela...");

		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(xpathmenu1));
		builder.moveToElement(MenuN1).build().perform();

		driver.findElement(By.xpath(xpathtela)).click();

	}

	public void encerraNavegador(WebDriver driver) {

		driver.close();
		driver.quit();
	}

	public void verificaPresencaDosBotoes(String caminho, String nomeTeste, WebDriver driver, int tentativas, String[] xpathBotoesRegistro) throws InterruptedException, IOException {
		logger.info("Verificando botoes esperados na tela...");
		int i = 0;
		while (i < xpathBotoesRegistro.length) {
			for (int second = 0;; second++) {

				if (second >= tentativas)
					falha("Timeout, elemento nao localizado " + xpathBotoesRegistro[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.id(xpathBotoesRegistro[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);

				}

			}
			i++;

		}

	}

	public void verificaTitulos(String caminho, String nomeTeste, WebDriver driver, int tentativas, String[] xpathTitulos) throws InterruptedException, IOException {
		logger.info("Verificando titulos na tela...");
		int i = 0;
		while (i < xpathTitulos.length) {
			for (int second = 0;; second++) {
				if (second >= tentativas)
					falha("timeout, elemento não localizado " + xpathTitulos[i], driver, nomeTeste);
				try {
					if (driver.findElement(By.xpath(xpathTitulos[i])).isDisplayed())
						break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}

			}
			i++;

		}

	}

	public void verificaLabelAba(WebDriver driver, int tentativas, String labelAba, String idAba, String nomeTeste) throws InterruptedException, IOException {
		for (int second = 0;; second++) {
			logger.info("Verificando label da aba...");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado ", driver, nomeTeste);
			try {
				if (driver.findElement(By.id(idAba)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
			if (driver.findElement(By.id(idAba)).getText().contentEquals(labelAba)) {

				logger.info("#OK# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());

			} else {
				logger.info("#ALERTA# Esperado: " + labelAba + ", Obtido: " + driver.findElement(By.id(idAba)).getText());
			}
		}
	}
	public void efetuaLoginComErro(WebDriver driver, String nomeTeste, int tentativas, String url, String usuario, String senha) throws InterruptedException, IOException {
		logger.info("----------------------------------------------------");
		logger.info("ACESSANDO A APLICACAO COM USUARIO E SENHA INVÁLIDOS");
		logger.info("----------------------------------------------------");

		driver.findElement(By.id("j_username")).clear();
		logger.info("Digitando usuario: " + usuario);
		driver.findElement(By.id("j_username")).sendKeys(usuario);
		driver.findElement(By.id("j_password")).clear();
		logger.info("Digitando senha: " + senha);
		driver.findElement(By.id("j_password")).sendKeys("ERRADA");
		logger.info("Clicando no botão 'Entrar'");
		driver.findElement(By.id("taxit_btn_login")).click();

		for (int second = 0;; second++) {
			logger.info("Localizando mensagem de erro 'Usuário e/ou senha inválido(s).'...");
			if (second >= tentativas)
				falha("Timeout, elemento não localizado ", driver, nomeTeste);
			try {
				if (driver.findElement(By.id("outputTextBadCredentials")).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Mensagem de erro localizado com sucesso! - outputTextBadCredentials");

	}

	public void remover(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; ++i) {
				remover(files[i]);
			}
		}
		f.delete();
	}

	public void informaTeste(int i, String caminho, String nomeTeste) {

		if (i == 0) {
			logger.info("#########################################################################");
			logger.info(nomeTeste + " - TESTE INICIADO EM " + new Date());
			logger.info("#########################################################################");
		} else if (i == 1) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE PESQUISA, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 2) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE RESULTADOS, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else if (i == 3) {
			logger.info("----------------------------------------------------");
			logger.info("TELA DE REGISTRO, TELA: " + nomeTeste);
			logger.info("----------------------------------------------------");
		} else {
			logger.info("????????????????????????????????????????????????????");
			logger.info("INVÁLIDO");
			logger.info("????????????????????????????????????????????????????");
		}

	}

	public void verificaElementos(int tentativas, String nomeTeste, WebDriver driver, String xpathAbaPesquisa, String xpathAbaResultados) throws InterruptedException, IOException {
		for (int second = 0;; second++) {
			logger.info("Localizando elementos da tela...");

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathAbaPesquisa + " || " + xpathAbaResultados, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathAbaPesquisa)).isDisplayed() && driver.findElement(By.xpath(xpathAbaResultados)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

	}

	public void acessaAba(WebDriver driver, int tentativas, String xpathRegistroAba2, String nomeTeste, String xpathCaregaRegistro) throws IOException, InterruptedException {

		Actions builder = new Actions(driver);
		WebElement teste = driver.findElement(By.xpath(xpathCaregaRegistro));
		builder.moveToElement(teste).build().perform();

		for (int second = 0;; second++) {
			logger.info("Localizando Aba2... " + xpathRegistroAba2);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathRegistroAba2, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathRegistroAba2)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(xpathRegistroAba2)).click();
	}

	public void moveMouseSobre(WebDriver driver, String caminho, String nomeTeste, int tentativas, String string) throws Throwable {
		for (int second = 0;; second++) {
			logger.info("Localizando Elemento... " + string);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + string, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(string)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		Actions builder = new Actions(driver);
		WebElement MenuN1 = driver.findElement(By.xpath(string));
		builder.moveToElement(MenuN1).build().perform();

	}

	public void pesquisaRegistro(WebDriver driver, String xpathPesquisaCaixaCampo2, String valorPesquisarPesquisaCaixaCampo2, String idBotaoExecutarConsulta) throws InterruptedException {

		driver.findElement(By.xpath(xpathPesquisaCaixaCampo2)).clear();
		logger.info("Digitando valor a ser pesquisado: " + valorPesquisarPesquisaCaixaCampo2);
		driver.findElement(By.xpath(xpathPesquisaCaixaCampo2)).sendKeys(valorPesquisarPesquisaCaixaCampo2);
		logger.info("Clicando no botão pesquisar: " + idBotaoExecutarConsulta);
		driver.findElement(By.id(idBotaoExecutarConsulta)).click();

	}

	public void verificaRegistroIntegrado(WebDriver driver, int tentativas, String nomeTeste, String xpathReferencia, String resultadoReferencia) throws IOException, InterruptedException {
		for (int second = 0;; second++) {
			logger.info("Localizando Registro... " + resultadoReferencia);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + xpathReferencia, driver, nomeTeste);
			try {
				if (driver.findElement(By.xpath(xpathReferencia)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		logger.info("Registro " + resultadoReferencia + " localizado com Sucesso");

	}

	public void acessaAbaPorId(WebDriver driver, int tentativas, String idAbaRegistro, String nomeTeste) throws IOException, InterruptedException {

		Actions builder = new Actions(driver);
		WebElement teste = driver.findElement(By.id(idAbaRegistro));
		builder.moveToElement(teste).build().perform();

		for (int second = 0;; second++) {
			logger.info("Localizando Aba ... " + idAbaRegistro);

			if (second >= tentativas)
				falha("Timeout, elemento nao localizado " + idAbaRegistro, driver, nomeTeste);
			try {
				if (driver.findElement(By.id(idAbaRegistro)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		driver.findElement(By.id(idAbaRegistro)).click();

	}

	public void informaTerminoDoTeste(String nomeTeste, String categoria, long duracaoTeste) {
		logger.info("#########################################################################");
		logger.info("TESTE " + nomeTeste + " ENCERRADO COM SUCESSO EM " + utilidade.formataDuracao(duracaoTeste));
		logger.info("#########################################################################");

	}

	public void acessaTelaPorClick(WebDriver driver, int tentativas, String xpathMenu1, String xpathMenu2, String xpathMenu3, String xpathMenu4, String xpathTela, String nomeTeste, String labelMenu1, String labelMenu2, String labelMenu3, String labelMenu4, String labelTela, int qtdeMenuInt) throws InterruptedException, IOException {
		logger.info("Acessando a Tela...");

		if (qtdeMenuInt == 1) {

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			Thread.sleep(500);

			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 2) {
			
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			
			Thread.sleep(500);
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			Thread.sleep(500);
			

			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 3) {
			
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu3)).getText().contentEquals(labelMenu3)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu3)).click();
			}

			
			Thread.sleep(500);
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		} else if (qtdeMenuInt == 4) {
			logger.info(driver.findElement(By.xpath(xpathMenu1)).getText() + " >");
			Thread.sleep(500);
			
			if (!driver.findElement(By.xpath(xpathMenu1)).getText().contentEquals(labelMenu1)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu1)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu2)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu2)).getText().contentEquals(labelMenu2)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu2)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu3)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu3)).getText().contentEquals(labelMenu3)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu3)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathMenu4)).getText() + " >");
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathMenu4)).getText().contentEquals(labelMenu4)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathMenu4)).click();
			}

			Thread.sleep(500);
			
			logger.info(driver.findElement(By.xpath(xpathTela)).getText());
			Thread.sleep(500);
			if (!driver.findElement(By.xpath(xpathTela)).getText().contentEquals(labelTela)) {
				falha("Tela não está localizada no local correto", driver, nomeTeste);
			} else {
				driver.findElement(By.xpath(xpathTela)).click();
			}

		}

	}
	public void acessaAbaPorXpath(WebDriver driver, int tentativas, String xpathCarregaRegistro, String nomeTeste) throws InterruptedException, IOException {
		aguardaCarregamento(nomeTeste, xpathCarregaRegistro, nomeTeste, tentativas, driver);
		Thread.sleep(500);
		driver.findElement(By.xpath(xpathCarregaRegistro)).click();

	}

	public void acessaModulo(WebDriver driver, int tentativas, String xpathModulo) throws InterruptedException, IOException {

		for (int second = 0;; second++) {
			logger.info("Localizando o link do módulo...");

			if (second >= tentativas)
				//falha("Timeout, módulo não localizado" + xpathModulo, driver, "Acesso ao Módulo");
				efetuaLogoutLogin(driver, tentativas);

			try {

				if (driver.findElement(By.xpath(xpathModulo)).isDisplayed())
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

	}

	private void efetuaLogoutLogin(WebDriver driver, int tentativas) throws InterruptedException, IOException {
		efetuaLogout(driver, tentativas);

		efetuaLoginComSucesso(driver, tentativas, usuario, senha, "Login");

	}

	private void efetuaLogout(WebDriver driver, int tentativas) throws InterruptedException, IOException {
		logger.info("Efetuando Logout...");
		aguardaCarregamento("Logout", "//*[@id='taxit_logoff']/a", "Logout", tentativas, driver);
		driver.findElement(By.xpath("//*[@id='taxit_logoff']/a")).click();
		aguardaCarregamento("Logout", "//*[@id='j_username']", "Logout", tentativas, driver);
		logger.info("Logout efetuado com sucesso!");

	}

}
