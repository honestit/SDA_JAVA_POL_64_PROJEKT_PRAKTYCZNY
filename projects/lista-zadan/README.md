# Projekt 2. Lista zadań

Lista zadań - klasyczna lista zadań dająca możliwość tworzenia własnych zadań do zrobienia oraz aktualizacji ich stanu.

W ramach projektu chcemy zrealizować aplikację posiadającą następującą listę funkcjonalności:
- Tworzenie listy zadań.
- Przeglądanie list zadań.
- Usuwanie list zadań.
- Dodawania zadań do listy.
- Przeglądania zadań z listy.
- Usuwania zadań z listy.
- Oznaczania zadań jako ukończone.
- Przeglądania najbliższych zadań do realizacji.

---

## Wymagania projektowe

Zapoznaj się z poniższymi **obowiązkowymi wymaganiami projektowymi** przed przystąpieniem do jego realizacji:

1. Projekt realizujemy z wykorzytaniem `mavena`.
1. Projekt realizujemy w `Java 11`.
1. Projekt realizujemy z wykorzystaniem bazy `MySQL` lub `Postgres` lub 'H2'
1. Projekt realizujemy z wykorzystaniem biblioteki `Hibernate` w warstwie trwałej aplikacji.
1. Projekt realizujemy z wykorzystaniem `gita`.
1. Projekt realizujemy z wykorzystaniem `slf4j`.

Zapoznaj się dodatkowo z poniższymi **opcjonalnymi wymaganiami projektowymi**:

1. Projekt realizujemy z wykorzystaniem biblioteki testowej `JUnit 5`.
1. Projekt realizujemy z wykorzystaniem biblioteki `lombok`.
1. Nowe funkcjonalności realizujemy w postaci dedykowanych branchy.

> Pamiętaj też, aby realizując projekt często commitować i budować w ten sposób historię swojej aktywności w serwisie github.com

---

## Realizacja projektu

Realizując projekt Lista zadań zakładamy, że projekt Dobre Myśli masz już za sobą (lub jesteś w trakcie), a zdobyte przy jego realizacji doświadczenia będą dla Ciebie dobrym drogowskazem. Dlatego też opisy Twoich zadań będą dużo mniej dokładne.

### Utworzenie i konfiguracja projektu

Zacznij od utworzenia nowego projektu w swoim środowisku deweloperskim (np. IntelliJ). Projekt powinien zostać utworzony w katalogu `lista-zadan`. Czyli wewnątrz tego katalogu powinna powstać już struktura projektowa właściwa dla mavena:

```
lista-zadan
    src
        main
            java
            resources
        test
            java
            resources
    pom.xml
    README.md
```

Przyjmijmy następujące ustawienia mavena:
- nazwa grupy: `projects`
- nazwa artefaktu: `todo-list'
- wymagane zależności:
    - `mysql-connector-java` w wersji `8.0.11` (lub odpowiedni dla `Postgres` lub `H2`
    - `hibernate-core` w wersji `5.4.10.Final`
    - `logback-classic` w wersji `1.2.3`
- właściwości w pliku `pom.xml`:
    - kodowanie znaków na `UTF-8`
    - wersja java: `11` 
    
W katalogu `main\resources` utwórz plik `logback.xml` z następującą zawartością:

```xml
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${user.dir}/logs.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${user.dir}/logs.%d{yyyy-MM-dd}.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <logger name="projects.todolist" level="DEBUG"/>

    <root level="WARN">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```
    
Utwórz pakiet `projects.todolist` i w nim klasę `TodoListApp`. Dodaj w niej metodę `main`, aby dało się tą klasę uruchomić i przetestować działanie aplikacji. Wykorzystaj również loggera z `org.slf4j` do zalogowania czegoś do pliku.

---

### Utworzenie klas modelu

Nasz projekt zakłada model składający się z dwóch klas: `Todos` i `Task`, obie powinny znaleźć się w pakiecie `projects.todolist.model`.

Klasa `Task` posiada następujące pola:
- `name` : `String` _nazwa zadania_
- `createdOn` : `LocalDateTime` _data utworzenia zadania_
- `expectedCompletedOn` : `LocalDateTime` _oczekiwana data zakończenia_
- `completedOn` : `LocalDateTime` _faktyczna data zakończenia_

Klasa `Todos` posiada następujące pola:
- `name` : `String` _nazwa listy zadań_
- `createdOn` : `LocalDateTime` _data utworzenia_

---

### Implementacja głównego interfejsu

Główny interfejs powinien zostać zrealizowany bazując na schemacie z poprzedniego zadania. Przykładowy interfejs główny do realizacji

```
Witamy w Liście Zadań!

Menu:
    1. Listy zadań
    2. Zadania
    3. Pokaż najbliższe zadania do realizacji
    4. Zakończ
    
Twój wybór:
```

### Implementacja obsługi list zadań

Obsługa listy zadań będzie realizowana w klasie `ManageTodosOption`. Przykładowy interfejs tej opcji:

```
Zarządzanie listami zadań

Menu:
    1. Dodaj listę zadań
    2. Pokaż listy zadań
    3. Usuń listę zadań
    4. Wróć
    
Twój wybór:
```

---

### Wdrożenie warstwy trwałej

Na podstawie projektu Dobre Myśli:
- utwórz bazę danych o nazwie `todo_list`,
- przygotuj konfigurację połączenia w pliku `hibernate.cfg.xml`,
- utwórz klasę `DbUtil`, aby móc uzyskać obiekt klasy `Session`,
- rozszerz klasy `Todos` i `Task`, aby stały się poprawnymi encjami.

  > Pamiętaj tutaj o zbudowaniu dwustronnej relacji między klasą `Todos` i `Task`, której właścicielem powinna być klasa `Task`. Czyli obiekt klasy `Todos` posiada listę zadań (obiektów klasy `Task`), a obiekt klasy `Task` posiada pojedynczego właściciela czyli listę zadań (obiekt klasy `Todos`). Właścicielem relacji jest `Task`, więc mapowanie (`mappedBy`) będzie po stronie `Todos`.

---

### Dodawanie nowej listy zadań

Obsługa dodawania nowej listy zadań będzie realizowana w klasie `AddTodosOption`. Opcja ta jest opcją pozbawioną menu, która powinna pobrać podstawowe dane o liście zadań czyli ... aż jej nazwę. Utworzony obiekt `Todos` powinien zostać zapisany do bazy danych.

W celu zapisu utwórz klasę serwisu `TodosService` i w niej metodę `public void save(Todos todos)`.

---

### Wyświetlanie zapisanych list zadań

Obsługa wyświetlanai zapisanych list zadań będzie realizowana w klasie `ShowTodosOption`. Opcja ta jest opcją pozbawioną menu, która powinna pobrać wszystki listy zadań i je wyświetlić.

W celu pobierania list zadań dodaj metodę `public Collection<Todos> findAll()` do klasy `TodosService`.

Przy wyświetlaniu listy zadań wyświetl id listy, nazwę listy, ilość wszystkich zadań, ostatnio zakończone zadanie i najbliższe niezakończone zadanie.

---

### Usuwanie listy zadań

Obsługa usuwania listy zadań będzie realizowana w klasie `DeleteTodosOption`. Opcja ta jest opcją pozbawioną menu, która powinna pobrać od użytkownika id lub nazwę listy do usunięcia.

W celu usunięcia listy dodaj metody `public Todos getTodos(Long id)` i `public Todos getTodos(String name)` do klasy `TodosService`, które będą pobierać z bazy listy na podstawie odpowiednio `id` albo `nazwy`. Dodaj również metodę `public void delete(Todos todos)` do klasy `TodosService`, która będzie odpowiadała za usunięcie listy. 

Po usunięciu listy zadań wyświetl potwierdzenie o usunięcia.

---

### Implementacja obsługi zadań

Obsługa zadań będzie realizowana w klasie `ManageTaskOption`. Przykładowy interfejs tej opcji:

```
Zarządzanie zadaniami

Menu:
    1. Dodaj zadanie do listy
    2. Pokaż zadania z listy
    3. Usuń zadanie z listy
    4. Zakończ zadanie z listy
    5. Wróć
    
Twój wybór:
```

### Dodawanie zadania do listy

Obsługa dodawania zadania do listy będzie realizowana w klasie `AddTaskOption`. Opcja ta jest opcją pozbawioną menu. Żeby dodać nowe zadanie do listy należy podać:
- id lub nazwę listy,
- nazwę zadania,
- oczekiwaną datę zakończenia.

W celu zapisania zadania utwórz metodę `public void save(Task task)` w klasie `TaskService`.

---

### Wyświetlanie zadań z listy

Obsługa wyświetlania zadań z listy będzie realizowana w klasie `ShowTasksOption`. Opcja ta jest opcją pozbawioną menu. Żeby wyświetlić zadania z listy należy podać:
- id lub nazwę listy.

Zadania powinny być wyświetlone od najstarszego do najnowszego. Dla zadań powinno być wyświetlone ich id, nazwa, data utworzenai, planowany termin zakończenia oraz termin zakończenia. Przy wyświetlaniu zadania mogą być oznaczane gdy są zostały zakończone po terminie oraz gdy jeszcze nie zostały zakończone, a ich termin już minął.

---

### Usuwanie zadań z listy

Obsługa usuwania zadań z listy będzie realizowana w klasie `DeleteTaskOption`. Opcja ta jest opcją pozbawioną menu. Żeby usunąć zadanie z listy należy podać:
- id lub nazwę listy,
- id lub nazwę zadania.

W celu usunięcia zadania zaimplementuj metody:
- `public Task getTask(Long id)`,
- `public Task getTask(Long todosId, String name)`,
- `public void delete(Task task)`.

wszystkie metody powinny być dodane do klasy `TaskService`.

Po usunięciu zadania powinno zostać wyświetlone potwierdzenie.

---

### Kończenie zadań

Obsługa kończenia zadań z listy będzie realizowana w klasie `CompleteTaskOption`. Opcja ta jest opcją pozbawioną menu. Żeby zakończyć zadanie należy podać:
- id lub nazwę listy,
- id lub nazwę zadania,

W pierwszej kolejności należy zadanie pobrać, następnie ustawić mu datę zakończenia na bieżącą datę (ewentualnie wprowadzoną przez użytkownika) i zaktualizować zadanie. 

Do pobrania zadania wykorzystaj dostępne już metody z klasy `TaskService`. Do aktualizacji zadania zaimplementuj metodę `public void update(Task task)` w klasie `TaskService`.

---

### Pokaż najbliższe zadania do realizacji

Obsługa wyświetlania najbliższych niezakończonych zadań z każdej listy będzie realizowana w klasie `IncomingUncompleTaskOption`. Opcja ta jest opcją pozbawioną menu. Żeby wyświetlić najbliższe niezakończone zadania należy podać:
- ilość zadań do wyświetlenia.

W celu uzyskania listy zadań nieukończonych dla każdego planu należy zaimplementować metodę `public Map<String, List<Task>> uncompleteTasks(Integer limit)` w klasie `TaskService`. Metoda ta powinna dla każdej z listy pobierać ilość niezakończonych zadań w ilości nie większej niż przekazana wartość `limit`. Zadania powinny być grupowane w mapie, której kluczami są nazwy list zadań. Klucze w mapie powinny być posortowane po dacie utworzenia listy (obiektu `Todos`). Obiekty `Task` na listach powinny być posortowane po oczekiwanej dacie zakończenia rosnąco.

Zadanie powinny być wyświetlane w formacie:

```
Nazwa listy nr 1:
    - oczekiwana data zakończenia: nazwa zadania nr 1
    - ozcekiwana data zakończenia: nazwa zadania nr 2
    ...
Nazwa listy nr 2:
    - oczekiwana data zakończenia: nazwa zadania nr x
    ...
```

---

### Rozszerzenia

Jeżeli spodobał Ci się projekt Lista zadań, to możesz całkiem samodzielnie zrealizować poniższe rozszerzenia:

1. Wyświetlanie zadań zrealizowanych w podanym zakresie dat.
1. Wyświetlanie zadań do zrealizowania w podanym zakresie dat.
1. Wyświetlanie historii zadań dla wybranej listy

   > Dla zadań zrealizowanych powinna być wykorzystana data realizacji, dla zadań niezrealizowanych data planowanej realizacji, a jeżeli jest już przeterminowane to stosowne oznaczenie zadania w historii
   
1. Eksport listy zadań do pliku.

   > Zalecany format CSV, do którego przetworzenia można wykorzystać bibliotekę [OpenCSV](http://opencsv.sourceforge.net/dependency-info.html)
   
1. Import listy zadań z pliku.

   > Można ponownie wykorzystać bibliotekę `OpenCSV`

---

## Gratulacje!

Gratuluję Ci ukończenia projektu! Należą Ci się słowa pochwały:

[Good Job!](https://www.youtube.com/watch?v=S0UvJZmGTsk)