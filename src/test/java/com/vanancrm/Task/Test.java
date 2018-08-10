package com.vanancrm.Task;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test  {

   // public class Test implements Comparator  {
    public static void main(String args[]) {
        /*ArrayList<String> al = new ArrayList<String>();
        al.add("a3");
        al.add("b2");
        al.add("c4");
        al.add("d1");
        al.add("e5");

        /* Collections.sort method is sorting the
        elements of ArrayList in ascending order. */
        //Collections.sort(al);

        // Let us print the sorted list
        /*System.out.println("List after the use of" +
                " Collection.sort() :\n" + al);*/
        //String[] alphaNumericStringArray = new String[] { "a3",
                //"b2", "c4", "d1", "e5"};

        /*
         * Arrays.sort method can take an unsorted array and a comparator
         * to give a final sorted array.
         *
         * The sorting is done according to the comparator that we have
         * provided.
         */
        //Arrays.sort(alphaNumericStringArray, new Test());

       /* for (int i = 0; i < alphaNumericStringArray.length; i++) {
            System.out.println(alphaNumericStringArray[i]);
        }*/
        final Pattern p = Pattern.compile("^\\d+");
        String[] examples = {
                "3a", "5b", "4c", "2d", "1e"};
        String number = "";
        String letter = "";
        for (int i = 0; i < examples[0].length(); i++) {
            char a = examples[0].charAt(i);
            if (Character.isDigit(a)) {
                number = number + a;

            } else {
                letter = letter + a;

            }
        }
        System.out.println("Alphates in string:"+letter);
        System.out.println("Numbers in String:"+number);
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                Matcher m = p.matcher(object1);
                Integer number1 = null;
                if (!m.find()) {
                    return object1.compareTo(object2);
                }
                else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2);
                    if (!m.find()) {
                        return object1.compareTo(object2);
                    }
                    else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        }
                        else {
                            return object1.compareTo(object2);
                        }
                    }
                }
            }
        };
        List<String> examplesList = new ArrayList<String>(Arrays.asList(examples));
        Collections.sort(examplesList, c);
        System.out.println(examplesList);
    }

   /* public int compare(Object firstObjToCompare, Object secondObjToCompare) {
        String firstString = firstObjToCompare.toString();
        String secondString = secondObjToCompare.toString();

        if (secondString == null || firstString == null) {
            return 0;
        }

        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();

        int index1 = 0;
        int index2 = 0;

        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);

            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];

            int loc1 = 0;
            int loc2 = 0;

            do {
                space1[loc1++] = ch1;
                index1++;

                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));

            do {
                space2[loc2++] = ch2;
                index2++;

                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));

            String str1 = new String(space1);
            String str2 = new String(space2);

            int result;

            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                Integer firstNumberToCompare = new Integer(Integer
                        .parseInt(str1.trim()));
                Integer secondNumberToCompare = new Integer(Integer
                        .parseInt(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            } else {
                result = str1.compareTo(str2);
            }

            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
    }*/

}
