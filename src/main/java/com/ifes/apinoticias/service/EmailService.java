package com.ifes.apinoticias.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import com.ifes.apinoticias.PreferenciaNoticia;
import com.ifes.apinoticias.BancoDados.PessoaRepository;
import com.ifes.apinoticias.models.Pessoa;

@Service
public class EmailService implements IEmailService {

	private final String apiKey = "c798adfd203d40789c7c32d98b3bf950";


	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void disparar(List<Pessoa> pessoaList) throws Exception {

		for (Pessoa pessoa : pessoaList) {
			Date dataHoraAtual = new Date();
			String sysdate = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
			String uri = "https://newsapi.org/v2/everything";
			String paramsGerais = "sortBy=popularity&language=pt&pageSize=10&page=1";

			if (PreferenciaNoticia.DIVERSOS.equals(pessoa.getVldPreferencia())) {

				String uriDiversos = uri + "?apiKey=" + apiKey + "&from=" + sysdate + "&" + paramsGerais + "&q=diversos";

				HttpRequest request = HttpRequest.newBuilder().GET()//
						.uri(URI.create(uriDiversos))//
						.timeout(Duration.ofSeconds(30)) // caso nao tenha resposta - parar a conexao em 10 segundos
						.build();

				HttpClient httpClient = HttpClient.newBuilder().build();

				HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

				//String mensagemEmail = formatMensagemSend(response);
				sendEmail(pessoa, response.body());
			}
			if (PreferenciaNoticia.EDUCACAO.equals(pessoa.getVldPreferencia())) {

				String uriEducacao = uri + "?apiKey=" + apiKey + "&from=" + sysdate + "&" + paramsGerais + "&q=educacao";
				HttpRequest request = HttpRequest.newBuilder()//
						.GET().uri(URI.create(uriEducacao))//
						.timeout(Duration.ofSeconds(30)) // caso nao tenha resposta - parar a conexao em 10 segundos
						.build();

				HttpClient httpClient = HttpClient.newBuilder().build();

				HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

			//	String mensagemEmail = formatMensagemSend(response);

				sendEmail(pessoa, response.body());
			}
		}

	}

	private static String formatMensagemSend(HttpResponse<String> response) throws JSONException {


		JSONObject obj = new JSONObject (response.body());

		JSONObject articles = obj.getJSONObject("articles");
		JSONObject title = articles.getJSONObject("title");
		JSONObject description = articles.getJSONObject("description");
		JSONObject urlToImage = articles.getJSONObject("urlToImage");

		//montando mensagem
		String mensagemEmail = ("Titulo: " + title + "\n\nInformações: " + description
				+ "\n\nLink imagem: " + urlToImage);
		return mensagemEmail;
	}

	private void sendEmail(Pessoa pessoa, String response) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("newsletter@gmail.com");
			message.setTo(pessoa.getEmail());
			message.setSubject("Notícias Diárias");
			message.setText(response);
			emailSender.send(message);

		} catch (MailException e) {
			System.out.println("Error: " + e.getMessage());

		}
	}

}
