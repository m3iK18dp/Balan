package com.kiem.at16;

import java.util.Iterator;
import java.util.Stack;

//Chuyen tu hau to sang trung to
public class Balan {

    public static void main(String[] args) {
        // System.out.println(TienToHau(TrungToTien("E*(A+F)-(B+C)/D")));
        // System.out.println(TrungToHau("E*(A+F)-(B+C)/D"));
        // System.out.println(TinhTrungTo("( 90.4 - 4 ) + ( 6 / 3 )"));
        // System.out.println(TinhTienTo("+ - 8 4 / 6 3"));
        System.out.println(TinhHauTo("8 4 - 6 3 / +"));
    }

    public static String TienToTrung(String s) {
        Stack<String> st = new Stack<String>();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                st.push("(" + st.pop() + c + st.pop() + ")");
            } else
                st.push("" + c);
        }
        return st.pop();
    }

    static int uT(char c) {
        return c == '+' || c == '-' ? 1 : c == '*' || c == '/' ? 2 : 0;
    }

    public static String TrungToTien(String s) {
        Stack<Character> stack = new Stack<Character>();
        String str = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                str = s.charAt(i) + str;
            else if (s.charAt(i) == ')')
                stack.push(s.charAt(i));
            else if (s.charAt(i) == '(') {
                char x = stack.pop();
                while (x != ')') {
                    str = x + str;
                    x = stack.pop();
                }
            } else {
                while (stack.size() > 0 && uT(s.charAt(i)) < uT(stack.peek()))
                    str = stack.pop() + str;
                stack.push(s.charAt(i));
            }
        }
        while (stack.size() > 0)
            str = stack.pop() + str;
        return str;
    }

    public static String HauToTrung(String s) {
        Stack<String> st = new Stack<String>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                String a = st.pop();
                st.push("(" + st.pop() + c + a + ")");
            } else
                st.push("" + c);
        }
        return st.pop();
    }

    public static String TrungToHau(String s) {
        Stack<Character> stack = new Stack<>();
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                str += s.charAt(i);
            else if (s.charAt(i) == '(')
                stack.push(s.charAt(i));
            else if (s.charAt(i) == ')') {
                char x;
                while ((x = stack.pop()) != '(')
                    str += x;
            } else {
                while (stack.size() > 0 && uT(s.charAt(i)) <= uT(stack.peek()))
                    str += stack.pop();
                stack.push(s.charAt(i));
            }
        }
        while (stack.size() > 0)
            str += stack.pop();
        return str;
    }

    public static String HauToTien(String s) {
        Stack<String> st = new Stack<String>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                String a = st.pop();
                st.push(c + st.pop() + a);
            } else
                st.push("" + c);
        }
        return st.pop();
    }

    public static String TienToHau(String s) {
        Stack<String> st = new Stack<String>();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                st.push(st.pop() + st.pop() + c);
            } else
                st.push("" + c);
        }
        return st.pop();
    }

    public static double tinh(double m, String xa, double n) {
        switch (xa.charAt(0)) {
            case '+':
                System.out.println("Phep toan: " + n + " + " + m);
                return n + m;
            case '-':
                System.out.println("Phep toan: " + n + " - " + m);
                return n - m;
            case '*':
                System.out.println("Phep toan: " + n + " * " + m);
                return n * m;
            default:
                System.out.println("Phep toan: " + n + " / " + m);
                return n / m;
        }
    }

    static int uT(String c) {
        return c.equals("+") || c.equals("-") ? 1 : c.equals("*") || c.equals("/") ? 2 : 0;
    }

    static double TinhTrungTo(String s) {
        String[] a = s.split("\\s+");
        Stack<Double> st1 = new Stack<Double>();
        Stack<String> st2 = new Stack<>();
        st2.push("(");
        print(a, st1, st2, 0);
        for (int i = 0; i < a.length; i++) {
            if (!"+-*/()".contains(a[i]))
                st1.push(Double.parseDouble(a[i]));
            else if (a[i].equals("("))
                st2.push(a[i]);
            else if ("+-*/".contains(a[i])) {
                while (uT(st2.peek()) > uT(a[i]))
                    st1.push(tinh(st1.pop(), st2.pop(), st1.pop()));
                st2.push(a[i]);
            } else if (a[i].equals(")")) {
                while (!st2.peek().equals("("))
                    st1.push(tinh(st1.pop(), st2.pop(), st1.pop()));
                st2.pop();
            }
            print(a, st1, st2, i + 1);
        }
        while (!st2.peek().equals("(")) {
            st1.push(tinh(st1.pop(), st2.pop(), st1.pop()));
            print(a, st1, st2, a.length);
        }
        return st1.pop();
    }

    public static void print(String[] a, Stack<Double> st1, Stack<String> st2, int i) {
        if (i >= a.length)
            System.out.print("Bieu thuc:  Empty");
        else {
            System.out.print("Bieu thuc:  ");
            for (int j = i; j < a.length; j++)
                System.out.print(a[j] + " ");
        }
        System.out.println();
        Iterator iterator = st1.iterator();
        if (st1.size() == 0)
            System.out.print("Stack1: Empty");
        else {
            System.out.print("Stack1: ");
            while (iterator.hasNext())
                System.out.print(iterator.next() + " ");
        }
        System.out.println();
        iterator = st2.iterator();
        if (st2.size() == 0)
            System.out.print("Stack2: Empty");
        else {
            System.out.print("Stack2: ");
            while (iterator.hasNext())
                System.out.print(iterator.next() + " ");
        }
        System.out.println("\n" + (i + 1) + "----------------------");
    }

    public static double TinhTienTo(String s) {
        String[] a = s.split("\\s+");
        Stack<String> st = new Stack<>();
        printTien(a, st, a.length - 1);
        for (int i = a.length - 1; i >= 0; i--) {
            if (!"+-*/".contains(a[i]))
                st.push(a[i]);
            else {
                double value = Double.parseDouble(st.pop());
                st.push("" + tinh(Double.parseDouble(st.pop()), a[i], value));
            }
            printTien(a, st, i - 1);
        }
        return Double.parseDouble(st.pop());
    }

    public static void printTien(String[] a, Stack<String> st, int i) {
        if (i < 0)
            System.out.print("Bieu thuc:    Empty");
        else {
            System.out.print("Bieu thuc:   ");
            for (int j = i; j >= 0; j--)
                System.out.print(a[j] + " ");
        }
        System.out.println();
        Iterator iterator = st.iterator();
        if (st.size() == 0)
            System.out.print("Stack:    Empty");
        else {
            System.out.print("Stack:    ");
            while (iterator.hasNext())
                System.out.print(iterator.next() + " ");
        }
        System.out.println("\n" + (a.length - i + 1) + "-----------------");
    }

    public static double TinhHauTo(String s) {
        String[] a = s.split("\\s+");
        System.out.println(a.length);
        Stack<String> st = new Stack<>();
        printHau(a, st, 0);
        for (int i = 0; i < a.length; i++) {
            if (!"+-*/".contains(a[i]))
                st.push(a[i]);
            else
                st.push("" + tinh(Double.parseDouble(st.pop()), a[i], Double.parseDouble(st.pop())));
            printHau(a, st, i + 1);
        }
        return Double.parseDouble(st.pop());
    }

    public static void printHau(String[] a, Stack<String> st, int i) {

        if (i < 0)
            System.out.print("Bieu thuc:    Empty");
        else {
            System.out.print("Bieu thuc:   ");
            for (int j = i; j < a.length; j++)
                System.out.print(a[j] + " ");
        }
        System.out.println();
        Iterator iterator = st.iterator();
        if (st.size() == 0)
            System.out.print("Stack:    Empty");
        else {
            System.out.print("Stack:    ");
            while (iterator.hasNext())
                System.out.print(iterator.next() + " ");
        }
        System.out.println("\n" + (i + 1) + "-----------------");
    }
}
