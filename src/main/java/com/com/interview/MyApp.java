package com.com.interview;

class MyApp {
    public static void main(String[] args) {
        System.out.println(reverse("Hello Java", 0));
    }

    public static String reverse(String string, int index) {
       if (string.length() == index) {
           return "";
       }

       return reverse(string, index + 1) + string.charAt(index);
    }
}