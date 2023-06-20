package com.ifes.apinoticias.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ifes.apinoticias.enums.PreferenciaNoticia;
import com.ifes.apinoticias.models.Pessoa;

@Service
public class EmailService implements IEmailService {

	private final String apiKey = "c798adfd203d40789c7c32d98b3bf950";

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void disparar(List<Pessoa> pessoaList) throws Exception {

		for (Pessoa pessoa : pessoaList) {
			// preparando parametros para consumir api
			Date dataHoraAtual = new Date();
			String sysdate = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
			String uri = "https://newsapi.org/v2/everything";
			String paramsGerais = "sortBy=popularity&language=pt&pageSize=10&page=1";

			if (PreferenciaNoticia.DIVERSOS.equals(pessoa.getVldPreferencia())) {

				String uriDiversos = uri + "?apiKey=" + apiKey + "&from=" + sysdate + "&" + paramsGerais + "&q=diversos";

				HttpRequest request = HttpRequest.newBuilder().GET()//
						.uri(URI.create(uriDiversos))//
						.timeout(Duration.ofSeconds(60))//
						.build();

				HttpClient httpClient = HttpClient.newBuilder().build();

				HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

				List<String> mensagemEmail = formatMensagemSend(response, sysdate);
				sendEmail(pessoa, mensagemEmail.toString(), sysdate);
			}

			if (PreferenciaNoticia.EDUCACAO.equals(pessoa.getVldPreferencia())) {

				String uriEducacao = uri + "?apiKey=" + apiKey + "&from=" + sysdate + "&" + paramsGerais + "&q=educacao";

				HttpRequest request = HttpRequest.newBuilder()//
						.GET().uri(URI.create(uriEducacao))//
						.timeout(Duration.ofSeconds(30)).build();

				HttpClient httpClient = HttpClient.newBuilder().build();

				HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

				List<String> mensagemEmail = formatMensagemSend(response, sysdate);

				sendEmail(pessoa, mensagemEmail.toString(), sysdate);

			}else {
				throw new Exception("Preferencia do tipo de notícia inválida");
			}
		}

	}

	private static List<String> formatMensagemSend(HttpResponse<String> response, String sysdate) throws JSONException {

		String mensagemEmail = "";
		List<String> listMensagem = new ArrayList<>();

		JSONObject obj = new JSONObject(response.body());

		try {
			JSONArray articlesList = obj.getJSONArray("articles");
			mensagemEmail = "Principais Noticias em " + sysdate;
			for (int i = 0; i < articlesList.length(); i++) {
				JSONObject article = articlesList.getJSONObject(i);

				String title = (String) article.get("title");
				String description = article.getString("description");
				String url = article.getString("url");

				mensagemEmail = ("\n" + "\nTitulo: " + title + "\nResumo: " + description + "\n" + url + "\n\n");
				listMensagem.add(mensagemEmail);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return listMensagem;

	}

	private void sendEmail(Pessoa pessoa, String response, String sysdate) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("newsletter@gmail.com");
			message.setTo(pessoa.getEmail());
			message.setSubject("Notícias Diárias - " + sysdate);
			message.setText(response);
			emailSender.send(message);

		} catch (MailException e) {
			System.out.println("Error: " + e.getMessage());

		}
	}

}
