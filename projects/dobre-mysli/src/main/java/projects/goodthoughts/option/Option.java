package projects.goodthoughts.option;

public interface Option {

    void executeOption(String userInput);

    default void run(Object... args) {
        showWelcomeMessage();
        while (true) {
            showMenu();
            String userInput = getUserInput();
            boolean isValidOption = validateOption(userInput);
            if (isValidOption) {
                executeOption(userInput);
                boolean isExitOption = checkIsExitOption(userInput);
                if (isExitOption) {
                    break;
                }
            } else {
                showInvalidOptionMessage(userInput);
            }
        }
        showGoodbyeMessage();
    }

    default void showGoodbyeMessage() {
    }

    default void showInvalidOptionMessage(String userInput) {
    }

    default boolean checkIsExitOption(String userInput) {
        return true;
    }

    default boolean validateOption(String userInput) {
        return true;
    }

    default String getUserInput() {
        return "";
    }

    default void showMenu() {
    }

    default void showWelcomeMessage() {
    }
}
