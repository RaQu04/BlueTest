import pl.blueenergy.document.*;
import pl.blueenergy.organization.User;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammerService {

    public void execute(DocumentDao documentDao) {
        //Miejsce na twój kod:


        List<Document> allDocumentsInDatabase = documentDao.getAllDocumentsInDatabase();
        List<Questionnaire> questionnairesList = new ArrayList<>();
        List<ApplicationForHolidays> applicationForHolidaysList = new ArrayList<>();

            /*
Pobranie wszystkich dokumentów z bazy poprzez DocumentDao. Metoda getAllDocumentsInDatabase zwraca wszystkie dokumenty w jednej liście, dlatego następnie musisz rozdzielić je na dwie osobne listy, po jednej dla każdego z typów dokumentów (ApplicationForHolidays i Questionnaire).
*/

        for (Document document : allDocumentsInDatabase) {
            if (document instanceof Questionnaire) {
                questionnairesList.add((Questionnaire) document);
            } else if (document instanceof ApplicationForHolidays) {
                applicationForHolidaysList.add((ApplicationForHolidays) document);
            }
        }
//        questionnairesList.forEach(System.out::println);
//        System.out.println("*******");
//        applicationForHolidaysList.forEach(System.out::println);


		/*
			1. Policz, ile średnio możliwych odpowiedzi zawierają wszystkie pytania we
			wszystkich kwestionariuszach w systemie.
		 */

        //  exercise1(questionnairesList);

        /*
        2. Stwórz listę wszystkich użytkowników, którzy złożyli wniosek o urlop, a następnie sprawdź czy któryś z nich w loginie zawiera polskie znaki, które mogłyby spowodować błędy w niektórych mechanizmach.
         */

        //  exercise2(applicationForHolidaysList);

        /*
        3. Sprawdź, czy któryś z wniosków urlopowych zawiera niepoprawnie wprowadzony początek i koniec urlopu (tzn. daty w złej kolejności).
         */

        // exercise3(applicationForHolidaysList);


        /*
        4. Stwórz mechanizm (Helper lub Service), który pozwoli w wygodny sposób zapisać obiekt klasy Questionnaire do pliku tekstowego w czytelnym dla użytkownika formacie, czyli np:

Pytanie: Jaki jest Twój ulubiony kolor?
    1. Żółty
    2. Zielony
    3. Niebieski
         */



    }


    private void exercise1(List<Questionnaire> questionnairesList) {
        List<String> questionText = new ArrayList<>();
        List<String> possibleAnswers = new ArrayList<>();


        for (Questionnaire questionnaire : questionnairesList) {
            for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
                questionText.add(questionnaire.getQuestions().get(i).getQuestionText());
            }
        }
        //System.out.println(questionText.size());

        for (Questionnaire questionnaire : questionnairesList) {
            for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
                possibleAnswers.addAll(questionnaire.getQuestions().get(i).getPossibleAnswers());
            }
        }
        // System.out.println(possibleAnswers.size());

        double answer = (possibleAnswers.size() / questionText.size());

        System.out.println("odpowiedz " + answer);
    }

    private void exercise2(List<ApplicationForHolidays> applicationForHolidaysList) {
        List<User> userList = new ArrayList<>();
        List<String> loginList = new ArrayList<>();
        for (ApplicationForHolidays user : applicationForHolidaysList) {
            userList.add(user.getUserWhoRequestAboutHolidays());
        }

        for (User user : userList) {
            loginList.add(user.getLogin());
        }

        System.out.println("***********************");


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
    }

    private void exercise3(List<ApplicationForHolidays> applicationForHolidaysList) {
        Date sinceDate;
        Date toDate;


        for (ApplicationForHolidays applicationForHolidays : applicationForHolidaysList) {
            sinceDate = applicationForHolidays.getSince();
            toDate = applicationForHolidays.getTo();
            if (sinceDate.before(toDate)) {
                System.out.println("porawne daty");
                System.out.println(sinceDate);
                System.out.println(toDate);
            } else {
                System.out.println("Niepoprawne daty");
                System.out.println(sinceDate);
                System.out.println(toDate);
            }
        }
    }
}
