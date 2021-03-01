package projects.goodthoughts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.goodthoughts.model.Quote;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    public Quote getDefaultQuote() {
        Quote quote = new Quote();
        quote.setContent("Programista nie kopiuje kodu, programista pisze metody");
        quote.setAuthor("Mistrz Yoda");

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(URI.create("https://theysaidso.com/quote-of-the-day/"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            String body = response.body();
            logger.trace("Pobrana treść strony: {}", body);
            String quoteFragment = getQuoteFragment(body, "Inspiring Quote of the day");
            logger.debug("Fragment z cytatem: {}", quoteFragment);
            String content = getQuoteContent(quoteFragment);
            String author = getQuoteAuthor(quoteFragment);
            quote.setContent(content);
            quote.setAuthor(author);
            quote.setCategory("Inspiring");
            logger.debug("Pobrany cytat: {}", quote);
        } catch (IOException | InterruptedException e) {
            logger.info("Błąd przy pobieraniu cytatu", e);
        }

        logger.debug("Pobrany cytat: {}", quote);
        return quote;
    }


    private String getQuoteFragment(String body, String phrase) {
        int indexOfTitle = body.indexOf("title=\"" + phrase + "\"");
        body = body.substring(indexOfTitle);
        int indexOfLead = body.indexOf("<p class=\"lead\">");
        body = body.substring(indexOfLead);
        return body;
    }

    private String getQuoteAuthor(String content) {
        int indexOfSpan = content.indexOf("<span itemprop=\"name\">");
        int indexOfSpanClosed = content.indexOf("</span>", indexOfSpan);
        int spanLength = "<span itemprop=\"name\">".length();
        return content.substring(indexOfSpan + spanLength, indexOfSpanClosed);
    }

    private String getQuoteContent(String body) {
        int indexOfSpan = body.indexOf("<span itemprop=\"text\">");
        int indexOfSpanClosed = body.indexOf("</span>", indexOfSpan);
        int spanLength = "<span itemprop=\"text\">".length();
        return body.substring(indexOfSpan + spanLength, indexOfSpanClosed);
    }
}
