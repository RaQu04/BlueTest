Treść zadania:

Projekt zawiera system, który przechowuje dane w obiektach zwanych dokumentami. Aktualnie w systemie istnieją 2 typy dokumentów: ApplicationForHolidays oraz Questionnaire, jednak w przyszłości może być ich więcej. Dokumenty zapisywane są w bazie danych, a dostęp do bazy zapewnia obiekt typu DocumentDao. Ze względu na treningowy charakter projektu, obiekt ten nie nie łączy się rzeczywiście z bazą danych, ale zwraca zamockowane obiekty.

Po uruchomieniu klasy Main system zainstancjonuje obiekt klasy ProgrammerService i wywoła na tej instancji metodę execute, której dostarczy jako argument obiekt typu DocumentDao. Tam powinien rozpocząć się twój kod. Możesz tworzyć dodatkowe klasy, jeżeli tylko masz ochotę. Wszystko zależy od Twojej inwencji.

Twoim pierwszym zadaniem jest pobranie wszystkich dokumentów z bazy poprzez DocumentDao. Metoda getAllDocumentsInDatabase zwraca wszystkie dokumenty w jednej liście, dlatego następnie musisz rozdzielić je na dwie osobne listy, po jednej dla każdego z typów dokumentów (ApplicationForHolidays i Questionnaire).

Następnie:

1. Policz, ile średnio możliwych odpowiedzi zawierają wszystkie pytania we wszystkich kwestionariuszach w systemie.

2. Stwórz listę wszystkich użytkowników, którzy złożyli wniosek o urlop, a następnie sprawdź czy któryś z nich w loginie zawiera polskie znaki, które mogłyby spowodować błędy w niektórych mechanizmach.

3. Sprawdź, czy któryś z wniosków urlopowych zawiera niepoprawnie wprowadzony początek i koniec urlopu (tzn. daty w złej kolejności).

4. Stwórz mechanizm (Helper lub Service), który pozwoli w wygodny sposób zapisać obiekt klasy Questionnaire do pliku tekstowego w czytelnym dla użytkownika formacie, czyli np:

Pytanie: Jaki jest Twój ulubiony kolor?
    1. Żółty
    2. Zielony
    3. Niebieski

5. Obiekt klasy User zawiera prywatne pole "salary" nie posiadające żadnych publicznych akcesorów. Używając refleksji zmień wartość powyższego pola dla dowolnego użytkownika wnioskującego o urlop.
