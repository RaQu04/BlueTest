import pl.blueenergy.document.*;
import pl.blueenergy.organization.Child;
import pl.blueenergy.organization.User;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgrammerService {

    public void execute(DocumentDao documentDao) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        //Miejsce na twój kod:

        List<Document> allDocumentsInDatabase = documentDao.getAllDocumentsInDatabase();
        new ArrayList<>();

            /*
Pobranie wszystkich dokumentów z bazy poprzez DocumentDao. Metoda getAllDocumentsInDatabase zwraca wszystkie dokumenty w jednej liście, dlatego następnie musisz rozdzielić je na dwie osobne listy, po jednej dla każdego z typów dokumentów (ApplicationForHolidays i Questionnaire).
*/

        List<Questionnaire> questionnairesList = allDocumentsInDatabase.stream()
                .filter(dok -> dok instanceof Questionnaire)
                .map(dok -> (Questionnaire) dok)
                .collect(Collectors.toList());

        List<ApplicationForHolidays> applicationForHolidaysList = allDocumentsInDatabase.stream()
                .filter(dok -> dok instanceof ApplicationForHolidays)
                .map(dok -> (ApplicationForHolidays) dok)
                .collect(Collectors.toList());


		/*
			1. Policz, ile średnio możliwych odpowiedzi zawierają wszystkie pytania we
			wszystkich kwestionariuszach w systemie.
		 */

        exercise1(questionnairesList);

        /*
        2. Stwórz listę wszystkich użytkowników, którzy złożyli wniosek o urlop, a następnie sprawdź czy któryś z nich w loginie zawiera polskie znaki, które mogłyby spowodować błędy w niektórych mechanizmach.
         */

        exercise2(applicationForHolidaysList);

        /*
        3. Sprawdź, czy któryś z wniosków urlopowych zawiera niepoprawnie wprowadzony początek i koniec urlopu (tzn. daty w złej kolejności).
         */

        exercise3(applicationForHolidaysList);


        /*
        4. Stwórz mechanizm (Helper lub Service), który pozwoli w wygodny sposób zapisać obiekt klasy Questionnaire do pliku tekstowego w czytelnym dla użytkownika formacie, czyli np:

Pytanie: Jaki jest Twój ulubiony kolor?
    1. Żółty
    2. Zielony
    3. Niebieski

    ****
    Metoda zapisuje wynik do pliku exercise_4.txt
    ****
         */

        exercise4(questionnairesList);

        /*
        5. Obiekt klasy User zawiera prywatne pole "salary" nie posiadające żadnych publicznych akcesorów. Używając refleksji zmień wartość powyższego pola dla dowolnego użytkownika wnioskującego o urlop.
         */


        exercise5(applicationForHolidaysList);


    }


    private void exercise1(List<Questionnaire> questionnairesList) {

        List<Question> collect = questionnairesList.stream()
                .map(Questionnaire::getQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> possibleAnswers = collect.stream()
                .flatMap(c -> c.getPossibleAnswers().stream())
                .collect(Collectors.toList());

        List<String> questionText = collect.stream()
                .map(Question::getQuestionText)
                .collect(Collectors.toList());


        double answer = (possibleAnswers.size() / questionText.size());
        System.out.println("***  Zadanie 1  ***");
        System.out.println("odpowiedz " + answer);
        System.out.println("******************* \n");
    }

    private void exercise2(List<ApplicationForHolidays> applicationForHolidaysList) {


        List<String> loginList = applicationForHolidaysList.stream()
                .map(ApplicationForHolidays::getUserWhoRequestAboutHolidays)
                .map(User::getLogin)
                .collect(Collectors.toList());


        System.out.println("***  Zadanie 2  ***");

        String NAME_REGEX = "^((?![żźćńąśłęó]).)*$";
        Pattern compiledPattern = Pattern.compile(NAME_REGEX);
        for (String login : loginList) {
            Matcher matcher = compiledPattern.matcher(login);

            if (matcher.find()) {
                System.out.println(login + " - nie posiada polskich znaków");
            } else {
                System.out.println(login + " - UWAGA! - posiada polskie znaki");
            }
        }


        System.out.println("******************* \n");

    }

    private void exercise3(List<ApplicationForHolidays> applicationForHolidaysList) {
        Date sinceDate;
        Date toDate;

        System.out.println("***  Zadanie 3  ***");


        for (ApplicationForHolidays applicationForHolidays : applicationForHolidaysList) {
            sinceDate = applicationForHolidays.getSince();
            toDate = applicationForHolidays.getTo();
            if (toDate.before(sinceDate)) {
                System.out.println("Niepoprawne daty");
                System.out.println("\tSince: " + sinceDate);
                System.out.println("\t  To:  " + toDate);
            } else {
                System.out.println("porawne daty");
                System.out.println("\tSince: " + sinceDate);
                System.out.println("\t  To:  " + toDate);
            }
        }

        System.out.println("******************* \n");

    }

    private void exercise4(List<Questionnaire> questionnairesList) {
        System.out.println("***  Zadanie 4  ***");


        try (PrintWriter printWriter = new PrintWriter("exercise_4.txt")) {
            for (Questionnaire questionnaire : questionnairesList) {
                for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
                    printWriter.println("Pytanie: " + questionnaire.getQuestions().get(i).getQuestionText());

                    for (int j = 0; j < questionnaire.getQuestions().get(i).getPossibleAnswers().size(); j++) {
                        printWriter.println("\t" + (j + 1) + ". " + questionnaire.getQuestions().get(i).getPossibleAnswers().get(j));
                    }
                    printWriter.println("\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Zawartość w pliku exercixe_4.txt");
        System.out.println("******************* \n");

    }

    private void exercise5(List<ApplicationForHolidays> applicationForHolidaysList) throws InstantiationException, IllegalAccessException, NoSuchFieldException {

        /*
        Na potrzeby tego zadania, dodałem do klasy User metodę getSalary()
         */

        System.out.println("***  Zadanie 4  ***");
        Class<?> clazz = Child.class;
        Object cc = clazz.newInstance();

        System.out.println("Before: " + ((Child) cc).getSalary());


        Field f1 = cc.getClass().getSuperclass().getDeclaredField("salary");
        f1.setAccessible(true);
        f1.set(cc, 10_000.00);
        Double str1 = (Double) f1.get(cc);

        System.out.println("After: " + ((Child) cc).getSalary());
    }
}
