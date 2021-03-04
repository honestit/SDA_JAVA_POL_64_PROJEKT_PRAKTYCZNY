package projects.goodthoughts.option;

import projects.goodthoughts.model.Quote;
import projects.goodthoughts.service.QuoteService;

import java.util.Scanner;

public class DeleteQuoteOption implements Option {

    private QuoteService quoteService = new QuoteService();

    @Override
    public void executeOption(String userInput) {
        System.out.print("Podaj id cytatu do usunięcia: ");
        Scanner scanner = new Scanner(System.in);

        Long id = 0L;
        while (id <= 0) {
            while (!scanner.hasNextLong()) {
                scanner.nextLine();
                System.out.print("\033[0;31mNiepoprawna wartość. Podaj id cytatu do usunięcia: \033[0m");
            }
            id = scanner.nextLong();
            if (id <= 0) {
                System.out.print("\033[0;31mNiepoprawna wartość. Podaj id cytatu do usunięcia: \033[0m");
            }
        }

        Quote quote = quoteService.getQuote(id);
        if (quote != null) {
            quoteService.remove(quote);
            System.out.println("\033[0;32mUsunięto cytat\033[0m");
        } else {
            System.out.println("\033[0;31mNie znaleziono wskazanego cytatu\033[0m");
        }


    }

    @Override
    public void showWelcomeMessage() {
        System.out.println("\nUsuwanie cytatu");
    }
}
