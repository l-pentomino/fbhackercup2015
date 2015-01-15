package contests.fb2015.q01;
/*
 Facebook Hacker Cup 2015 Qualification Round
 Problem 1: Cooking the Books
 Date: 01/09/2015
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Books {

    static String[] minMax(String s, int radix) {
        char[] ch = s.toCharArray();

        char[] minString = Arrays.copyOf(ch, ch.length);
        char[] maxString = Arrays.copyOf(ch, ch.length);

        for (int i = 0; i < minString.length - 1; i++) {
            int minPos = i;
            int localMin = minString[i] - '0';
            for (int j = i + 1; j < minString.length; j++) {
               int digit = minString[j] - '0';
               if (digit <= localMin) {
                   if (i == 0 && digit == 0) continue;
                   localMin = digit;
                   minPos = j;
               }
            }
            if (minPos != i && localMin != minString[i] - '0') {
                swap(minString, i, minPos);
                break;
            }
        }

        for (int i = 0; i < maxString.length -1; i++) {
            int minPos = i;
            int localMin = maxString[i] - '0';
            for (int j = i + 1; j < maxString.length; j++) {
                int digit = maxString[j] - '0';
                if (digit >= localMin) {
                    localMin = digit;
                    minPos = j;
                }
            }
            if (minPos != i && localMin != maxString[i] - '0') {
                swap(maxString, i, minPos);
                break;
            }
        }
        String[] result = new String[2];
        result[0] = String.valueOf(minString);
        result[1] = String.valueOf(maxString);
        return result;
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) throws Exception {
        testmain(args);
        //test();
    }

    static void test() {
        String in = "100\n" +
                                "31524\n" +
                                "897\n" +
                                "123\n" +
                                "10\n" +
                                "5\n" +
                                "999999999\n" +
                                "0\n" +
                                "10\n" +
                                "9990999\n" +
                                "939214502\n" +
                                "773452111\n" +
                                "223456093\n" +
                                "604231672\n" +
                                "857412048\n" +
                                "287689159\n" +
                                "422931895\n" +
                                "728154034\n" +
                                "926288077\n" +
                                "380045176\n" +
                                "869841756\n" +
                                "472956328\n";
        String[] s = in.split("\\n");
        for (String amt : s) {
            String[] mm = minMax(amt, 10);
            System.out.println(amt + ": " + mm[0] + "/ " + mm[1]);
        }
    }

    static void testmain(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Must provide input file name as the first argument");
            System.out.println(System.getProperty("user.dir"));
            return;
        }

        String fileName = args[0];
        File inputFile = new File(System.getProperty("user.dir") +"/" + fileName);
        File outputFile = new File(System.getProperty("user.dir") + "/" + fileName + ".out");
        PrintWriter pw = new PrintWriter(new FileWriter(outputFile));

        Scanner sc = new Scanner(inputFile);

        int entries = 1;
        sc.nextLine();
        while (sc.hasNextLine()) {
            String amount = sc.nextLine().trim();

            String[] mm = minMax(amount,10);


            String s = String.format("Case #%d: %s %s", entries++, mm[0], mm[1]);
            pw.println(s);
            System.out.println("Entry " + entries + " " + amount + ": " + mm[0] + " / " + mm[1]);

        }
        sc.close();
        pw.close();
    }
}
