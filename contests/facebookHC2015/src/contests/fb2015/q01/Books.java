package contests.fb2015.q01;
/*
 Facebook Hacker Cup 2015 Qualification Round
 Problem 1: Cooking the Books
 author: Galina Khayut
 Date: 01/09/2015
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Books {

    static String[] minMax(String s) {
        String[] result = new String[2];
        result[0] = result[1] = s;

        /* Sort the digits - ascending order gives the minimal value */
        char[] sorted = Arrays.copyOf(s.toCharArray(), s.length());
        Arrays.sort(sorted);

        boolean doneMin, doneMax; doneMin = doneMax = false;

        /* Compare actual digits positions to their respective min/max permutations.
        Swap accordingly in order of significance */
        for (int i = 0; !(doneMin && doneMax) && i < s.length(); i++) {
            int c = s.charAt(i) - '0';
            if (!doneMin) {
                if (c > sorted[i] - '0') {
                    int swapIndex = findNextMin(s, i + 1);
                    if (swapIndex != i) {
                        result[0] = swap(s, i, swapIndex);
                        doneMin = true;
                    }
                }
            }
            if (!doneMax) {
                if (c < sorted[sorted.length - i -1] - '0') {
                    int swapIndex = findNextMax(s, i + 1);
                    if (swapIndex != i) {
                        result[1] = swap(s, i, swapIndex);
                        doneMax = true;
                    }
                }
            }
        }
        return result;
    }

    /* In case there are duplicates of the minimal digit, get its rightmost index
    * (so the greater digit is swapped to the least significant position) */
    static int findNextMin(String s, int startFrom) {
        int result = startFrom - 1;
        int startChar = s.charAt(result) - '0';
        int min = startChar;
        for (int i = startFrom; i < s.length(); i++) {
            int c = s.charAt(i) - '0';
            if (c <= min && c != startChar) {
                //skip if the first position is 0
                if (c == 0 && startFrom == 1) continue;
                min = c;
                result = i;
            }
        }
        return result;
    }

    /* Find the rightmost occurrence of the maximal digit */
    static int findNextMax(String s, int startFrom) {
        int result = startFrom - 1;
        int startChar = s.charAt(result) - '0';
        int max = startChar;
        for (int i = startFrom; i < s.length(); i++) {
            int c = s.charAt(i) - '0';
            if (c >= max & c != startChar) {
                max = c;
                result = i;
            }
        }
        return result;
    }

    private static String swap(String input, int pos1, int pos2) {
        if (pos1 < 0 || pos1 == pos2) return input;
        char[] chars = Arrays.copyOf(input.toCharArray(), input.length());
        char tmp = chars[pos1];
        chars[pos1] = chars[pos2];
        chars[pos2] = tmp;
        return String.valueOf(chars);
    }

    public static void main(String[] args) throws Exception {
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

        int entries = 0;
        while (sc.hasNextLine()) {
            //skip first line
            if (entries == 0) {
                entries = 1;
                continue;
            }
            String amount = sc.nextLine().trim();

            String[] minMax = minMax(amount);

            String s = String.format("Case #%d: %s %s", entries++, minMax[0], minMax[1]);
            pw.println(s);
            System.out.println(s);
        }
        sc.close();
        pw.close();
    }
}
