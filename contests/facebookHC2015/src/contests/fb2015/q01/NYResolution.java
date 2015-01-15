package contests.fb2015.q01;
/*
 Facebook Hacker Cup 2015 Qualification Round
 Problem 2: New Year Resolution
 Date: 01/09/2015
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class NYResolution {

    static class Meal {
        int proteins;
        int carbs;
        int fats;

        public Meal() {}

        public Meal(int p, int c, int f) {
            proteins = p;
            carbs = c;
            fats = f;
        }

        public Meal add(Meal m) {
            proteins += m.proteins;
            carbs += m.carbs;
            fats += m.fats;
            return this;
        }

        @Override
        public String toString() {
            return proteins + " " + carbs + " " + fats;
        }
    }

    static class Case {
        Meal target;
        List<Meal> meals;
        public Case(Meal mt, List<Meal> ml) {
            target = mt;
            meals = ml;
        }

        @Override
        public String toString() {
            String result = "";
            result += target + ":\n";
            for (Meal m : meals) {
                result += "\t" + m + "\n";
            }
            return result;
        }
    }

    static List<Case> doInput(String fileName) throws Exception {
        File in = new File(System.getProperty("user.dir") +"/" + fileName);
        System.out.println(in.getAbsolutePath());
        Scanner sc = null;
        List<Case> cases = new ArrayList<Case>();

        try {
            sc = new Scanner(in);
            int mealCount = 0;
            int proteins, carbs, fats;
            Meal target = null; List<Meal> menu = null;
            sc.nextLine(); //skip the first line
            while (sc.hasNextLine()) {
                String[] s = sc.nextLine().trim().split("\\s+");
                if (s.length == 3) {
                    proteins = Integer.parseInt(s[0]);
                    carbs = Integer.parseInt(s[1]);
                    fats = Integer.parseInt(s[2]);

                    if (mealCount == 0) {
                        target = new Meal(proteins, carbs, fats);
                        menu = new ArrayList<Meal>();
                    } else {
                        if (menu == null) throw new RuntimeException("Malformed input file");
                        if (menu.size() < mealCount) {
                            Meal meal = new Meal(proteins, carbs, fats);
                            menu.add(meal);
                        } else {
                            cases.add(new Case(target, menu));
                            mealCount = 0;
                            target = new Meal(proteins, carbs, fats);
                            menu = new ArrayList<Meal>();
                        }
                    }
                } else if (s.length == 1) {
                    mealCount = Integer.parseInt(s[0]);
                }
            }
            Case ccase = new Case(target, menu);
            cases.add(ccase);
        } finally {
            sc.close();
        }
        return cases;
    }

    static boolean isPossible(Case c) {
        int total = c.meals.size();
        //Generate a powerset of all meals per case represented as a binary string
        //1 - include the meal as a possible candidate, 0 - do not include
        for (int i = 1; i < Math.pow(2, total); i++) {
            String s = Integer.toBinaryString(i);
            String newS = "";
            //left-pad with zeros
            for (int j = 0; j < total - s.length(); j++)
                newS += '0';
            s = newS + s;

            Meal m = new Meal();
            char[] arr = s.toCharArray();
            System.out.println(s);
            System.out.println();
            in:for (int j = 0; j < arr.length; j++) {
                if (arr[j]  == '1') {
                    m.add(c.meals.get(total - j - 1));
                }
                if (m.proteins > c.target.proteins ||
                                m.carbs > c.target.carbs ||
                                m.fats > c.target.fats) {
                    break in;
                }

            }
            if (m.proteins == c.target.proteins && m.carbs == c.target.carbs
                            && m.fats == c.target.fats) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
         testmain(args);
    }

    static void testmain(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide input file name as first argument");
            return;
        }

        List<Case> cases = doInput(args[0]);
        File outf = new File(System.getProperty("user.dir") + "/"  + args[0] +".out");
        PrintWriter out = new PrintWriter(new FileWriter(outf));

        try {
            int caseNo = 0;
            for (Case c : cases) {
                caseNo++;
                //System.out.println(caseNo);
                if (caseNo != 48) continue;
                System.out.println("Case #" + caseNo + "\n" + c);
                boolean isPossible = isPossible(c);

                out.printf("Case #%d: %s\n", caseNo, isPossible ? "yes" : "no");
                System.out.printf("Case #%d: %s\n\n", caseNo, isPossible ? "yes" : "no");

            }
        } finally {
            out.close();
        }
    }
}
