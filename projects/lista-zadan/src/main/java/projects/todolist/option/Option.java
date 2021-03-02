package projects.todolist.option;

public interface Option {

    default void run(Object... args) {
        showWelcomeMessage();
        while (true) {
            showOptions();
            String option = getUserOption();
            if (validateOption(option)) {
                executeOption(option);
                if (checkIsExitOption(option)) {
                    break;
                }
            } else {
                executeInvalidOption(option);
            }
        }
        showExitMessage();
    }

    void showWelcomeMessage();

    void showOptions();

    String getUserOption();

    boolean validateOption(String option);

    void executeOption(String option);

    boolean checkIsExitOption(String option);

    void executeInvalidOption(String option);

    void showExitMessage();
}
