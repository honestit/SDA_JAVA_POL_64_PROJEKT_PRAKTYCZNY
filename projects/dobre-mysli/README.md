# Projekt 1. Dobre Myśli

Dobre myśli - prosty projekt działający w konsoli pozwalający pobierać cytaty z internetu oraz tworzyć właśną bazę ulubionych cytatów.

W ramach projektu chcemy zrealizować aplikację posiadającą następującą listę funkcjonalności:
- Pobieranie domyślnego cytatu z internetu.
- Pobieranie wybranego cytatu z internetu.
- Generowanie tłumaczenia cytatu.
- Zapisywanie cytatu do bazy danych.
- Przeglądanie zapisanych cytatów.
- Usuwanie zapisanych cytatów.
- Dodawanie własnych cytatów.

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
1. Projekt realizujemy z wykorzystaniem biblioteki `jsoup`.
1. Projekt realizujemy z wykorzystaniem biblioteki `lombok`.
1. Nowe funkcjonalności realizujemy w postaci dedykowanych branchy.

> Pamiętaj też, aby realizując projekt często commitować i budować w ten sposób historię swojej aktywności w serwisie github.com

---

## Realizacja projektu

Projekt realizujemy w postaci kolejnych kroków, które zostały poniżej sprecyzowane.

### Utworzenie i konfiguracja projektu

Zacznij od utworzenia nowego projektu w swoim środowisku deweloperskim (np. IntelliJ). Projekt powinien zostać utworzony w katalogu `dobre-mysli`. Czyli wewnątrz tego katalogu powinna powstać już struktura projektowa właściwa dla mavena:

```
dobre-mysli
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
- nazwa artefaktu: `good-thoughts'
- wymagane zależności:
    - `mysql-connector-java` w wersji `8.0.11` (lub inny sterownik dla `Postgres` lub `H2`)
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
    
    <logger name="projects.goodthoughts" level="DEBUG"/>

    <root level="WARN">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```
    
Utwórz pakiet `projects.goodthoughts` i w nim klasę `GoodThoughtsApp`. Dodaj w niej metodę `main`, aby dało się tą klasę uruchomić i przetestować działanie aplikacji. Wykorzystaj również loggera z `org.slf4j` do zalogowania czegoś do pliku.

---

### Utworzenie klas modelu

Nasz projekt zakłada bardzo prosty model bazowy składający się z pojedynczej klasy reprezentującej cytat: `Quote`.

Utwórz klasę `Quote` w pakiecie `projects.goodthoughts.model`.

Klasa powinna posiadać następujące pola:
- `content` : `String` _treść cytatu_
- `translation` : `String` _treść cytatu po polsku_
- `author` : `String` _autor cytatu_
- `category` : `String` _kategoria cytatu_
- `createdOn` : `LocalDateTime` _data utworzenia lub pobrania cytatu`

Pamiętaj, aby w klasie zadbać również o poprawną implementację metody `equals`, `hashCode` oraz `toString`.

> W metodzie `equals` i `hashCode` wybierz pola, które Twoim zdaniem dobrze definiują unikalność cytatu.

**TEST:** Utwórz w klasie `GoodThoughtsApp` dwa różne obiekty `Quote` i wyświetl informacje o nich na konsoli.

---

### Utworzenie bazowego interfejsu użytkownika

Przejdziemy teraz do utworzenia bazowego interfejsu naszej aplikacji czyli tzw. pętli aplikacyjnej.

W klasie `GoodThoughtsApp` utwórz nową metodę `public void run(Object... args)`, która będzie służyła do uruchomienia naszej aplikacji. Wewnątrz metody należy zaimplementować logikę interfejsu użytkownika.

Przyjmij następujące założenia:
- na start wyświetlany jest komunikat powitalny,
- aplikacja rozpoczyna działanie w nieskończonej pętli,
- użytkownikowi prezentowane jest menu z wyborem opcji,
- użytkownik wprowadza wybraną opcję menu,
- należy weryfikować czy użytkownik wybrał poprawną opcję,
- poprawnie wybrana opcja jest przekazywana do dedykowanej metody jej obsługi,
- program kończy swoje działanie gdy wybranał opcję zakończenia,
- użytkownik otrzymuje komunikat gdy wybrał niepoprawną opcję,
- na zakończenie programu wyświetlany jest komunikat pożegnalny.

> Większość z tych założeń możesz zaimplementować w postaci dedykowanych metod.

Zaimplementuj opcję zakończenia pracy z aplikacją.

**TEST:** Przetestuj działanie programu tworząc nowy obiekt `GoodThoughtsApp` w metodzie `main` i uruchamiając go metodą `run`.

---

### Pobieranie ustalonego cytatu z internetu

Realizujemy teraz konkretną funkcjonalność pobierania cytatu. Utwórz w tym celu klasę `DefaultQuoteOption` w pakiecie `projects.goodthoughts.option`.

Klasa powinna posiadać pojedynczą metodę `public void run(Object... args)`, którą można uruchomić z poziomu klasy `GoodThoughtsApp`.

> Pełna implementacja metody powinna być identyczna jak w klasie `GoodThoughtsApp`, ale poszczególne kroki będą inaczej działać.

Implementacja:
- będzie posiadać wiadomość powitalną informująco o pobieraniu cytatu,
- nie będzie posiadać menu,
- nie będzie pobierać opcji od użytkownika,
- nie będzie walidować opcji użytkownika,
- automatycznie kończy się po wykonaniu działania,
- w ramach wykonania akcji:
  - pobierze treść strony z cytatami dnia z serwisu [theysaidso.com](https://theysaidso.com/quote-of-the-day/)
  - sparsuje treść, aby uzyskać z niej cytat oraz autora,
  - wyświetli cytat i autora,
  
  > Bezpośrednie pobieranie cytatu zrealizuj w osobnej klasie: `QuoteService` z pakietu `projects.goodthoughts.service` i metodzie `public Quote getDefaultQuote()`.
  
- nie będzie wyświetlać wiadomości pożegnalnej.

> Do pobrania cytatu możesz wykorzystać klasę [HttpClient](https://openjdk.java.net/groups/net/httpclient/intro.html) lub skorzystać z biblioteki [jsoup](https://jsoup.org/).

Na zakończenie stwórz obiekt klasy `DefaultQuoteOption` i użyj go w głównej klasie aplikacji przy odpowiednim wyborze użytkownika. Rozbuduj menu klasy głównej o obsługę wyświetlania domyślnego cytatu.

**TEST:** Przetestuj działanie całej aplikacji.

--- 

### Pobieranie wybranego cytatu z internetu

Pobieranie wybranego cytatu z internetu realizujemy ponownie w formie dedykowanej klasy `ChosenQuoteOption` o analogicznej struktorze do klasy `DefaultQuoteOption` i w tym samym pakiecie.

Zacznijmy od stworzenia wyliczenia (`enum`) definiującego dostępne kategorie cytatów: `QuoteCategory`. Utwórz je w pakiecie `projects.goodthoughts.model`. Wyliczenie powinno zawierać po jednej wartości dla każdej kategorii cytatu dostępnej w serwisie theysaidso.com

Implementacja klasy `ChosenQuoteOption` powinna:
- nie będzie posiadać wiadomości powitalnej,
- wyświetli po jednej opcji menu dla każdego wyliczenia z klasy `QuoteCategory`,
- będzie walidować poprawnie wprowadzone opcje,
- pobierze cytat o wskazanej kategorii i wyświetli ten cytat,
- zakończy działanie przy każdej z wybranych opcji,
- poinformuje o niepoprawnie wybranej opcji,
- nie będzie posiadać wiadomości pożegnalnej.
    
> Funkcjonalność pobierania cytatu zaimplementuj jako metodę `public Quote getQuote(QuoteCategory category)` w klasie `QuoteService`.

Na zakończenie rozbuduj główne menu aplikacji, aby była możliwość pobrania cytatu z wybranej kategorii i połącz obsługę tej możliwości z klasą `ChosenQuoteOption`.

**TEST:** Przetestuj działanie całej aplikacji.

---

### Generowanie tłumaczenia cytatu

Generowanie tłumaczenia dla cytatu będzie rozszerzeniem funkcjonalności klas `DefaultQuoteOption` i `ChosenQuoteOption`. 

Implementację tłumaczenia zrealizuj w dedykowanej klasie `TranslationService`:
- utwórz klasę `TranslationService` w pakiecie `projects.goodthoughts.service`,
- utwórz w niej metodę `public void translate(Quote quote)`,
- w metodzie wyślij żądanie na adres: https://www.tlumaczangielskopolski.pl/wp-content/themes/translatica/inc/translate/translator.php?from=en&to=pl&text={cytat} gdzie pod `{cytat}` podstawisz treść cytatu.

  > Jeżeli cytat może zawierać znaki specjalne lub spoza podstawowego zakresu, to należy również użyć `URLEncoder.encode(quote.getContent(), "UTF-8");`
  
- w wyniku otrzymamy wartość w formacie:

  ```
  {"code":"0","lang":"pl-en","text":["tłumaczenie"]}
  ```
  
  z wartości tej należy uzyskać tłumaczenie i zaktualizować przekazany parametr `quote`.
  
Po zaimplementowaniu usługi `TranslationService` należy rozbudować klasy `DefaultQuoteOption` i `ChosenQuoteOption` o uzyskanie tłumaczenia cytatu i wyświetlanie cytatu z tłumaczeniem.
  
**TEST:** Przetestuj działanie całej aplikacji w obu opcjach: dla domyślnego cytatu oraz dla cytatu z wybranej kategorii.

---

### Zapisywanie cytatu do bazy danych

Zapisywanie cytatu do bazy danych będzie rozszerzeniem funkcjonalności klas `DefaultQuoteOption` i `ChosenQuoteOption`.

Po wyświetleniu cytatu (z tłumaczeniem lub bez) użytkownik powinien zostać zapytany czy zapisać cytat do bazy danych. Jeżeli wybierze opcję zapisania, to cytat powinien znaleźć się w bazie danych.

Realizacja zapisu do bazy danych będzie wymagała od nas kilku większych kroków:
1. Utworzenia bazy danych dla naszej aplikacji.
1. Utworzenia konfiguracji połączenia bazy danych w Hibernate.
1. Utworzenie klasy zarządzającej sesją Hibernate.
1. Zmodyfikowanie klasy `Quote`, aby stała się pełnoprawną encją.
1. Dodanie metody `public void save(Quote quote)` w klasie `QuoteService`, która wykona zapis do bazy danych.

#### Utworzenie bazy danych dla naszej aplikacji

Nową bazę danych stworzymy poleceniem: `CREATE DATABASE good_thoughts CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`

> Połączenie do bazy danych możesz utworzyć w Twoim środowisku deweloperskim.

#### Utworzenie konfiguracji połączenia bazy danych w Hibernate

Konfigurację połączenia stworzymy jako plik `hibernate.cfg.xml` w katalogu `resources` projektu. 

Przeanalizuj poniższą konfigurację i wykorzystaj ją w swoim projekcie:

```xml
<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

    <session-factory>
        <property name="hbm2ddl.auto">update</property>
        <property name="show_sql">true</property>
        <property name="dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <property name="connection.url">jdbc:mysql://localhost/good_thoughts?useSSL=false&amp;characterEncoding=utf8&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=TRUE</property>
        <property name="connection.username">username</property> <!-- To do zmiany -->
        <property name="connection.password">password</property> <!-- To do zmiany -->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>

        <property name="hibernate.c3p0.acquire_increment">1</property>
        <property name="hibernate.c3p0.idle_test_period">60</property>
        <property name="hibernate.c3p0.min_size">1</property>
        <property name="hibernate.c3p0.max_size">2</property>
        <property name="hibernate.c3p0.max_statements">50</property>
        <property name="hibernate.c3p0.timeout">0</property>
        <property name="hibernate.c3p0.acquireRetryAttempts">1</property>
        <property name="hibernate.c3p0.acquireRetryDelay">250</property>

        <mapping class="projects.goodthoughts.model.Quote"/>
        <!-- Tutaj dopisujemy kolejne klasy encji -->
    </session-factory>

</hibernate-configuration>
```

#### Utworzenie klasy zarządzającej sesją Hibernate

Zarządzanie sesją Hibernate przekażemy do klasy `DbUtil` w pakiecie `projects.goodthoughts.config`. Klasa będzie dostarczała pojedynczą metodę `public Session getSession()` umożliwiającą rozpoczęcie pracy z sesją Hibernate.

Przeanalizuj poniższą klasę i wykorzystaj ją w swoim projekcie:

```java
package projects.goodthoughts.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbUtil {

    private final SessionFactory sessionFactory;

    private DbUtil() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public static Session getSession() {
        return DbUtilWrapper.INSTANCE.getSessionFactory().openSession();
    }

    private static class DbUtilWrapper {

        private static final DbUtil INSTANCE = new DbUtil();
    }
}
```

#### Modyfikacja klasy Quote

Do klasy `Quote` musimy dodać wymagane adnotacje oraz brakujące pola:
- adnotację `Entity`, która uczyni z naszej klasy encję,
- adnotację `Table`, w której określimy nazwę tabeli `quotes`,
- pole `id` o typie `Long`, które będzie pełniło rolę klucza głównego i będzie generowane automatycznie (adnotacje `Id` + `GeneratedValue`),
- metodę z adnotacją `PrePersist`, która zagwarantuje ustawienie daty utworzenia przed zapisaniem do bazy.

#### Implementacja usługi zapisu cytatu do bazy danych

Klasę `QuoteService` rozszerzamy o wspomnianą metodę `public void save(Quote quote)`. Metoda powinna uzyskać obiekt sesji `Session` oraz w ramach transakcji wykonać zapis encji `Quote`. Zapisana encja będzie miała automatycznie ustawione pole `id`.

Wykorzystaj zaimplementowaną metodę w klasach `DefaultQuoteOption` i `ChosenQuoteOption`, aby zakończyć prace nad tą funkcjonalnością.

**TEST:** Przetestuj działanie całej aplikacji w obu opcjach: dla domyślnego cytatu oraz dla cytatu z wybranej kategorii.

---

### Przeglądanie zapisanych cytatów

Przeglądanie zapisanych cytatów będzie nową funkcjonalnością realizowaną w klasie `ReviewQuotesOption` w pakiecie `projects.goodthoughts.option`. Ponownie możemy wykorzystać znany nam schemat obsługi użytkownika z wyświetlaniem menu i realizacją opcji.

Na początek należy zaimplementować metodę `public Collection<Quote> findAll()` w metodzie `QuoteService`, która będzie pobierała z bazy wszystkie cytaty posortowane po danie utworzenia i zwracała je.
  
Założenia dla klasy `ReviewQuotesOption`:
- posiada wiadomość powitalną,
- posiada menu z opcjami:
  - wyświetlenia listy cytatów,
  - powrót,
- pobiera opcje od użytkownika,
- waliduje poprawność wprowadzonej opcji,
- wykonanie opcji wyświetlenia cytatów:
  - pobiera cytaty przy użyciu `QuoteService`,
  - wyświetla kolejne cytaty (id, autor i treść oraz tłumaczenie gdy jest dostępne),
- kończy działanie przy wybraniu opcji powrotu,
- wyświetla informację o niepoprawnie wybranej opcji,
- nie ma wiadomości pożegnalnej.

Rozszerz główne menu aplikacji (`GoodThoughtsApp`) o opcję wyświetlania listy cytatów.

**TEST:** Przetestuj działanie całej aplikacji.

---

### Usuwanie cytatów z bazy

Usuwanie cytatów z bazy będzie nową funkcjonalnością wymagającą implementacji klasy `ManageQuoteOption` w pakiecie `projects.goodthoughts.option`.

Zacznij od zaimplementowania metody `public void delete(Quote quote)` oraz `public Quote getQuote(Long id)` w klasie `QuoteService`. Cel metod powinien być oczywisty.

Klasa `ManageQuoteOption` ma następujące założenia:
- nie ma wiadomości powitalnej,
- posiada menu z opcjami:
  - usuń cytat,
  - powrót,
- pobiera opcje od użytkownika,
- waliduje poprawność wprowadzonej opcji,
- wykonanie opcji usunięcia cytatu:
  - pobiera `id` cytatu do usunięcia,
  - pobiera cytat do usunięcia na podstawie `id`,
  - jeżeli cytat o takim `id` istnieje to go usuwa, a w przeciwnym razie wyświetla komunikat błędu.
- kończy działanie przy wybraniu opcji powrotu,
- wyświetla informację o niepoprawnie wybranej opcji,
- nie ma wiadomości pożegnalnej.

Rozszerz menu klasy `ReviewQuotesOption` o opcję zarządzania cytatami.

**TEST:** Przetestuj działanie całej aplikacji.

---

### Dodawanie własnych cytatów

Dodawanie cytatów będzie wymagało rozszerzenia menu klasy `ManageQuoteOption` o opcję dodawania nowego cytatu.

Implementacja obsługi tej opcji wymaga pobrania od użytkownika:
- autora cytatu,
- treści cytatu,
- tłumaczenia,
i zapisania cytatu przy użyciu metody `save(Quote)` z klasy `QuoteService`.

**TEST:** Przetestuj działanie całej aplikacji.

---

### Rozszerzenia

Jeżeli spodobał Ci się projekt Dobre Myśli, to możesz całkiem samodzielnie zrealizować poniższe rozszerzenia:

1. Rozbudowanie wyświetlania listy cytatów o opcje wyświetlania tylko wybranej kategorii.
1. Dodanie opcji edytowania cytatu (np. poprawianie tłumaczenia).
1. Dodanie opcji wysyłki cytatu mailem.

---

## Gratulacje!

Gratuluję Ci ukończenia projektu! Należą Ci się słowa pochwały:

[Good Job!](https://www.youtube.com/watch?v=S0UvJZmGTsk)