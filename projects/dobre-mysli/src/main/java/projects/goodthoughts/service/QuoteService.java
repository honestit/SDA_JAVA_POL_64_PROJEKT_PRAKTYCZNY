package projects.goodthoughts.service;

import projects.goodthoughts.model.Quote;

import java.time.LocalDateTime;

public class QuoteService {

    public Quote getDefaultQuote() {
        return new Quote("Life is beautiful", "Życie jest piękne",
                "Anonim", "Życiowe", LocalDateTime.now());
    }
}
