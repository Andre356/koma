package savoy2019;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTests {
    public static void main(String[] args) {
//        Example1_TestIsMatch();
//        Example2_TestMatch();
//        Example3_Test_Matches();
//        Example4_TestNameGroups();
        Example5_TekstReplace();
    }


    private static void Example1_TestIsMatch() {

        String input1 = "12345";
        String input2 = "12345s";
        String input3 = "dąb";

        Pattern pattern1 = Pattern.compile("^\\d+$");   // czy tekst składa się tylko z cyfer
        Pattern pattern2 = Pattern.compile("^\\w+$", Pattern.UNICODE_CHARACTER_CLASS);   // czy tekst składa się tylko z liter

        System.out.println(pattern1.matcher(input1).matches());
        System.out.println(pattern1.matcher(input2).matches());
        System.out.println(pattern2.matcher(input3).matches());
    }

    private static void Example2_TestMatch() {

        String input4 = "Człowiek człowiekowi wilkiem, a zombie zombie zombie";

        Pattern pattern3 = Pattern.compile("człowiek");
        Matcher matcher3 = pattern3.matcher(input4);

        if (matcher3.find()) {
            // metoda find inicjuje klasę matcher, znajduje pierwsze dopasowanie i uzupełnia klasę matcher informacjami
            // o tym dopasowaniu. Jak nic nie znajdzie to rzuci false, dlatego pozostałe met. wywołujemy tylko jak true
            WriteMatch(matcher3);
        }

        Pattern pattern4 = Pattern.compile("człowiek", Pattern.CASE_INSENSITIVE);
        Matcher matcher4 = pattern4.matcher(input4);
        if (matcher4.find()) {
            WriteMatch(matcher4);
        }
    }

// Wszystkie dopasowania ----------------------------------------------------------------------------------------------
    private static void Example3_Test_Matches(){
        String input5 = "Człowiek człowiekowi wilkiem, a zombie zombie zombie";

        // wyrazy zaczynające się od 'z'
        Pattern pattern5 = Pattern.compile("\\bz\\w*\\b");
        Matcher matcher5 = pattern5.matcher(input5);
        while (matcher5.find()) {
            WriteMatch(matcher5);
        }
    }
// Grupy nazwane ------------------------------------------------------------------------------------------------------
    private static void Example4_TestNameGroups(){
        String input = "13:51 13:52 13:53, 8:02 25:25";
        Pattern pattern = Pattern.compile("\\b(?<HOUR>\\d+):(?<MINUTE>\\d\\d)\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()){
            WriteMatch(matcher);
            WriteGroup(matcher, "HOUR", 23);
            WriteGroup(matcher, "MINUTE", 59);
            System.out.println();
        }
    }

// Zamiana tekstu -----------------------------------------------------------------------------------------------------
    private static void Example5_TekstReplace(){
        String input = "Człowiek człowiekowi wilkiem, a troll trollowi trollem.";
        Pattern pattern = Pattern.compile("\\b(?<TWORD>t\\w*)\\b", Pattern.UNICODE_CHARACTER_CLASS);  //słowa na 't'
        Matcher matcher = pattern.matcher(input);

        String output = matcher.replaceAll("xxx ");
        System.out.println(output);
    }

    private static void WriteMatch(Matcher matcher) {
        int start = matcher.start();
        int end = matcher.end();
        String value = matcher.group();     // domyślnie 0, więc grupa 0 - całe dopasowanie, można wywołać konkretne
        // lub wywołać stringa czyli po nazwie
        System.out.println("Position: " + start + "-" + end + "\tValue: " + value);
    }
    private static void WriteGroup(Matcher matcher, String name, int maxValue) {
        int start = matcher.start(name);
        int end = matcher.end(name);
        String value = matcher.group(name);

        if (Integer.parseInt(value) <= maxValue) {
            System.out.println("Position: " + start + "-" + end + "\tValue: " + value);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}
