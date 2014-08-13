package ECF.automacao.suite;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import ECF.automacaoECF.acessoTela.*;
import ECF.automacaoECF.padrao.RecebeParametros;


public class TesteSuite {
	static org.apache.log4j.Logger logger = Logger.getLogger(TesteSuite.class.getName());
	public static String testaTela = new RecebeParametros().testaTela;
	public static String testaCRUD = new RecebeParametros().testaCRUD;
	public static String testaIntegracao = new RecebeParametros().testaIntegracao;
	public static String enviaEmail = new RecebeParametros().enviaEmail;
	public static String destinatariosSuite = new RecebeParametros().destinatariosSuite;

	
	public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		long inicio = System.currentTimeMillis();

		int QtdeFalhas = 0;
		int QtdeSucesso = 0;

		Boolean testaTelaBoolean = Boolean.parseBoolean(testaTela);
		Boolean testaCRUDBoolean = Boolean.parseBoolean(testaCRUD);
		Boolean testaIntegracaoBoolean = Boolean.parseBoolean(testaIntegracao);
		Boolean enviaEmailBoolean = Boolean.parseBoolean(enviaEmail);

		//Class<?>[] testesTela = {TelaAJustesParteBAcesso.class, TelaAliquotasDosTributosAcesso.class, TelaCentralDeCadastrosAcesso.class, TelaCentroDeCustoAcesso.class, TelaComBaseNoSaldoConsolidadoAcesso.class, TelaConsultaDeDarfAcesso.class, TelaContasSpedAssociacaoAcesso.class, TelaContasSpedConsultaAcesso.class, TelaEmpresasAcesso.class, TelaFeriadoAcesso.class, TelaLancamentosContabeisAcesso.class, TelaLucroEstimadoAssociacaoContasLalurAcesso.class, TelaLucroEstimadoContasAcesso.class, TelaLucroRealAssociacaoContasLalurAcesso.class, TelaLucroRealContasAcesso.class, TelaPlanoDeContasAcesso.class, TelaPlanoReferencialPlanoEmpresaAssociacaoAcesso.class, TelaPlanoReferencialPlanoEmpresaConsultaAcesso.class, TelaProcessoEmLoteAcesso.class, TelaResponsaveisAcesso.class, TelaSaldosAcesso.class, TelaScpAcesso.class, TelaSituacaoEspecialAcesso.class, TelaTaxaSelicAcesso.class, TelaTipoDeFeriadoAcesso.class, TelaVencimentoDosTributosAcesso.class};
		Class<?>[] testesTela = {TelaAgrupamentoDeTabelasAcesso.class,TelaAJustesParteBAcesso.class,TelaAliquotasDosTributosAcesso.class,TelaAssociacaoDeTabelasSpedAcesso.class,TelaCentrosDeCustos.class,TelaContasApuracaoAcesso.class,TelaDarfConsultaAcesso.class,TelaDarfProcessamentoAcesso.class,TelaEstabelecimentos.class,TelaFeriadosAcesso.class,TelaGeracaoEcfAcesso.class,TelaImpostosComBaseNoSaldoConsolidadoAcesso.class,TelaImpostosDiferidosComBaseNaMovimentacaoAcesso.class,TelaLancamentosContabeis.class,TelaLucroEstimadoApuracaoCsllAcesso.class,TelaLucroEstimadoApuracaoIrpjAcesso.class,TelaLucroEstimadoContasDaApuracaoAcesso.class,TelaLucroRealApuracaoCsllAcesso.class,TelaLucroRealApuracaoIrpjAcesso.class,TelaLucroRealContasAcesso.class,TelaLucroRealContasDaApuracaoAcesso.class,TelaPerfilEcflAcesso.class,TelaPlanoDeContas.class,TelaPlanoReferencialPlanoEmpresaAssociacao.class,TelaPlanoReferencialPlanoEmpresaConsulta.class,TelaProcessoEmLoteAcesso.class,TelaRelatorioDeEstabelecimentosAcesso.class,TelaRelatoriosCompLucroRealLucroEstimadoAcesso.class,TelaRelatoriosContasLalurAcesso.class,TelaRelatoriosContasLalurContasContabeisAcesso.class,TelaRelatoriosDeCalculosAcesso.class,TelaRelatoriosImpDiferBaseSaldoConsolidAcesso.class,TelaRelatoriosImpDiferidosBaseMovtoAcesso.class,TelaRelatoriosSaldosParteBAcesso.class,TelaResponsaveisAcesso.class,TelaSaldos.class,TelaScp.class,TelaSituacaoEspecialAcesso.class,TelaTaxaSelicAcesso.class,TelaTipoDeFeriadoAcesso.class,TelaVencimentoDosTributosAcesso.class};
		String[] resultadosTestesTela = new String[testesTela.length];
		String[] duracaoTestesTela = new String[testesTela.length];

		Class<?>[] testesCrud = {};
		String[] resultadosTestesCrud = new String[testesCrud.length];
		String[] duracaoTestesCrud = new String[testesCrud.length];

		Class<?>[] testesIntegracao = {};
		String[] resultadosTestesIntegracao = new String[testesIntegracao.length];
		String[] duracaoTestesIntegracao = new String[testesIntegracao.length];

		ResultadosDaSuite auxReultados = new ResultadosDaSuite();

		auxReultados.preparaSuite();
		auxReultados.imprimeTitulo();
		auxReultados.imprimeParametrosGeraisDaSuite();
		auxReultados.imprimeParametrosDaSuite();

		if (testaTelaBoolean == true) {
			int qtdeTestes=testesTela.length;
				
			
			logger.info("------------------------------------------------------");
			logger.info("INICIANDO TESTES DE TELA ("+qtdeTestes+" TESTES)");
			logger.info("------------------------------------------------------");
			for (int i = 0; i < testesTela.length; i++) {

				Result result = new Result();
				long duracaoSuiteTeste = System.currentTimeMillis();
				result = JUnitCore.runClasses(testesTela[i]);
				long duracao = System.currentTimeMillis() - duracaoSuiteTeste;
				String duracaoString = Long.toString(duracao);

				if (result.wasSuccessful() == true) {

					resultadosTestesTela[i] = "SUCESSO!";
					duracaoTestesTela[i] = duracaoString;
					QtdeSucesso++;

				} else {
					resultadosTestesTela[i] = "FALHA!";
					duracaoTestesTela[i] = duracaoString;
					QtdeFalhas++;
				}

			}
		}

		if (testaCRUDBoolean == true) {
			int qtdeTestes=testesTela.length;
			logger.info("------------------------------------------------------");
			logger.info("INICIANDO TESTES DE CRUD ("+qtdeTestes+" TESTES)");
			logger.info("------------------------------------------------------");
			for (int i = 0; i < testesCrud.length; i++) {

				Result result = new Result();
				long duracaoSuiteTeste = System.currentTimeMillis();
				result = JUnitCore.runClasses(testesCrud[i]);
				long duracao = System.currentTimeMillis() - duracaoSuiteTeste;
				String duracaoString = Long.toString(duracao);

				if (result.wasSuccessful() == true) {

					resultadosTestesCrud[i] = "SUCESSO!";
					duracaoTestesCrud[i] = duracaoString;
					QtdeSucesso++;

				} else {
					resultadosTestesCrud[i] = "FALHA!";
					duracaoTestesCrud[i] = duracaoString;
					QtdeFalhas++;

				}

			}
		}

		if (testaIntegracaoBoolean == true) {
			int qtdeTestes=testesTela.length;
			logger.info("------------------------------------------------------");
			logger.info("INICIANDO TESTES DE INTEGRAÇÃO ("+qtdeTestes+" TESTES)");
			logger.info("------------------------------------------------------");
			for (int i = 0; i < testesIntegracao.length; i++) {
				long duracaoSuiteTeste = System.currentTimeMillis();
				Result result = new Result();

				result = JUnitCore.runClasses(testesIntegracao[i]);
				long duracao = System.currentTimeMillis() - duracaoSuiteTeste;
				String duracaoString = Long.toString(duracao);

				if (result.wasSuccessful() == true) {
					resultadosTestesIntegracao[i] = "SUCESSO!";
					duracaoTestesIntegracao[i] = duracaoString;
					QtdeSucesso++;

				} else {
					resultadosTestesIntegracao[i] = "FALHA!";
					duracaoTestesIntegracao[i] = duracaoString;
					QtdeFalhas++;

				}

			}
		}

		String CabecalhoDeResultado = auxReultados.imprimeCabecalhoDeResultado(testesTela, testesCrud, testesIntegracao, testaTelaBoolean, testaCRUDBoolean, testaIntegracaoBoolean, QtdeSucesso, QtdeFalhas);
		String CabecalhoDaTabela = auxReultados.imprimeCabecalhoDaTabelaDeResultados();

		String[] resultadoParaImprimirTela = new String[testesTela.length];
		if (testaTelaBoolean == true)
			resultadoParaImprimirTela = auxReultados.imprimeResultadoNaTela(testesTela, resultadosTestesTela, "TELA", duracaoTestesTela);

		String[] resultadoParaImprimirCrud = new String[testesCrud.length];
		if (testaCRUDBoolean == true)
			resultadoParaImprimirCrud = auxReultados.imprimeResultadoNaTela(testesCrud, resultadosTestesCrud, "CRUD", duracaoTestesCrud);

		String[] resultadoParaImprimirIntegracao = new String[testesIntegracao.length];
		if (testaIntegracaoBoolean == true)
			resultadoParaImprimirIntegracao = auxReultados.imprimeResultadoNaTela(testesIntegracao, resultadosTestesIntegracao, "INTEGRAÇÃO", duracaoTestesIntegracao);

		long duracaoTeste = System.currentTimeMillis() - inicio;
		String rodapeTestes = auxReultados.imprimeDuracaoDaSuite(duracaoTeste);
		String[] parametrosDeTeste = auxReultados.coletaInformacoesGeraisDoTeste();
		File corpoEmail = auxReultados.gravaResultadosTxt(testaTelaBoolean, testaCRUDBoolean, testaIntegracaoBoolean, CabecalhoDeResultado, CabecalhoDaTabela, resultadoParaImprimirTela, resultadoParaImprimirCrud, resultadoParaImprimirIntegracao, rodapeTestes, parametrosDeTeste);

		if (enviaEmailBoolean == true) {
			String destinatarios = destinatariosSuite;
			String assuntoEmail = auxReultados.montaAssuntoEmail(CabecalhoDeResultado);
			String corpoEmailString = auxReultados.montaCorpoEmail(corpoEmail);

			auxReultados.enviaEmailComAnexos(destinatarios, assuntoEmail, corpoEmailString);
		}

	}

}
