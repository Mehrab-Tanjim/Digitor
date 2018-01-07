package LogicEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StringManipulator {

    private String str;
    private final char[] listOfVars = new char[26];
    private final Map<Character, Character> map = new HashMap<>();

    public StringManipulator() {
        Scanner input = new Scanner(System.in);
        str = input.nextLine();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch>='A' && ch<='Z' && listOfVars[ch - 'A'] == '\0') {
                listOfVars[ch - 'A'] = ch;
            }
        }
        for (int i = 0; i < listOfVars.length; i++) {
            if (listOfVars[i] != '\0') {
                System.out.print(listOfVars[i] + " :");
                int tmp=input.nextInt();
                if(tmp==0) map.put(listOfVars[i], '0');
                else map.put(listOfVars[i], '1');
            }
        }
    }

    public void stringModifier() {
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            str = str.replace(entry.getKey().charValue(), entry.getValue().charValue());
        }
    }

    public void manipulate() {
        while (str.length() != 1) {
            str = str.replace("(0.0)", "0");
            str = str.replace("(0.1)", "0");
            str = str.replace("(1.0)", "0");
            str = str.replace("(1.1)", "1");
            str = str.replace("(0+0)", "0");
            str = str.replace("(0+1)", "1");
            str = str.replace("(1+0)", "1");
            str = str.replace("(1+1)", "1");
            str = str.replace("0'", "1");
            str = str.replace("1'", "0");
        }
    }

    public void printString() {
        System.out.println(str);
    }

}
