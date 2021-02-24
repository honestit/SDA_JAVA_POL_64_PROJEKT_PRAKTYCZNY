package projects.goodthoughts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.goodthoughts.model.Quote;

import java.time.LocalDateTime;

public class GoodThoughtsApp {

    private static final Logger logger = LoggerFactory.getLogger(GoodThoughtsApp.class);

    public static void main(String[] args) {
        System.out.println("Witaj");
        logger.info("Uruchamianie aplikacji ...");

        Quote quote1 = new Quote("Life is beautiful", "Życie jest piękne",
                "Anonim", "Życiowe", LocalDateTime.now());

        Quote quote2 = new Quote("Life is brutal and full of zasadzkasz",
                "Życie jest brutalne i pełne zasadzek", "Wujek dobra rada", "Życiowe",
                LocalDateTime.now().minusMonths(3));

        logger.debug("Test cytatu 1: " + quote1 + " - koniec cytatu");
        logger.debug("Test cytatu 2: {} - koniec cytatu", quote2);
        logger.debug(String.format("Oba cytaty, 1: %s, 2: %s", quote1, quote2));
    }
}
