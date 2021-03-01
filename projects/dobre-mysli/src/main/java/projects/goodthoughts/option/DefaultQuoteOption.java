package projects.goodthoughts.option;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.goodthoughts.model.Quote;
import projects.goodthoughts.service.QuoteService;

public class DefaultQuoteOption implements Option {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQuoteOption.class);

    private final QuoteService quoteService = new QuoteService();

    public static void main(String[] args) {
        DefaultQuoteOption option = new DefaultQuoteOption();
        option.run();
    }

    @Override
    public void executeOption(String userInput) {
        Quote defaultQuote = quoteService.getDefaultQuote();
        logger.debug("Pobrany cytat: {}", defaultQuote);

        System.out.printf("Cytat na dziś:%n\t \"%s\" (%s)%n", defaultQuote.getContent(), defaultQuote.getAuthor());
    }
}
