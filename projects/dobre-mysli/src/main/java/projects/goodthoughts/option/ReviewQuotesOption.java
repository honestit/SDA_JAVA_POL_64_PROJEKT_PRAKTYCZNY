package projects.goodthoughts.option;

import projects.goodthoughts.model.Quote;
import projects.goodthoughts.service.QuoteService;

import java.util.Collection;

public class ReviewQuotesOption implements Option {

    private QuoteService quoteService = new QuoteService();

    @Override
    public void showWelcomeMessage() {
        System.out.println("\nTwoje zapisane cytat:\n");
    }

    @Override
    public void executeOption(String userInput) {
        Collection<Quote> quotes = quoteService.findAll();
        // |                  James: 'Cytat'                                                |
        quotes.forEach(quote -> {
            System.out.printf("| %30s: '%-140s' |%n",
                    quote.getAuthor(),
                    quote.getContent());
        });
    }
}
