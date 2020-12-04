package com.barracuda.tasks.politeh.practice_04_12_2020.calc.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    //Создаю стек для всех цифр в переданной строке:
    private Stack<BigDecimal> stackOfBigDecimals = new Stack<>();
    //Создаю второй стек для всех знаков типа ( ) + - * /
    private Stack<String> stackOfSigns = new Stack<>();

    //Создаю мапу приоритетов выполнения арифметических действий:
    private static Map<String, Integer> priorities = new HashMap<>();
    static {
        priorities.put("+", 1);
        priorities.put("-", 1);
        priorities.put("*", 2);
        priorities.put("/", 2);
    }

    //создаю метод для проверки валидности переданного математического выражения:
    private boolean validityCheck(String statement){

        //проверяю, содержит ли строка неразрешенные символы:
        if(!statement.matches("[0-9\\+\\-\\*/\\(\\)\\.]+")){
            return false;
        }

        //проверка, содаржит ли выражение скобку ")"перед скобкой "("
        if(statement.matches("[^(]*\\)+.*")){
            return false;
        }

        //проверка что количество скобок открывающих ( соответствует количеству закрывающих скобок )
        int countOpeningParenthesis = 0;
        int countClosingParenthesis = 0;
        Pattern p1 = Pattern.compile("\\(");
        Pattern p2 = Pattern.compile("\\)");
        Matcher m1 = p1.matcher(statement);
        Matcher m2 = p2.matcher(statement);
        while(m1.find()){
            countOpeningParenthesis++;
        }
        while(m2.find()){
            countClosingParenthesis++;
        }
        if(countClosingParenthesis!=countOpeningParenthesis){
            return false;
        }

        //проверка не идут ли скобки такая  ( и такая  ) одна за другой:
        Pattern p3 = Pattern.compile("\\(\\)");
        Matcher m3 = p3.matcher(statement);

        boolean areTogether = false;
        while(m3.find()){
            areTogether = true;
        }
        if(areTogether){
            return false;
        }

        //if we have "(" in the statement: check if there are parenthesis, there should be correct substatement within (containing at least 2 numbers with one +-*/ sign in between;.
        //doesn't work properly: can find last correct statement and return true; Before that can be uncorrect statements;
        //String statement = "-1.0+((5/8*9)-(44+5.55555)/888.888)-(0.33+)";
        //avoid this situation^ (0.33+)
        //avoid this situation^ (0.33)
        //permit situation (-1.033)

        if(statement.contains("(")){
            boolean correctStatementWithinParenthesis = false;
            boolean correctStatementWithinParenthesis1 = false;
            boolean correctStatementWithinParenthesis2 = false;


            Pattern p4 = Pattern.compile("\\((\\+|\\-)?\\d+([^\\+\\-\\*\\/]*)?(\\+|\\-|\\*|\\/)\\d+(.*)?\\)");
            Matcher m4 = p4.matcher(statement);
            while(m4.find()){
                correctStatementWithinParenthesis = true;
            }
            //avoid this situation: (0.33)
            //avoid this situation: (0.33+)
            //avoid this situation: (+0.33)
            //avoid this situation: (*0.33)
            //avoid this situation: (/0.33)
            //permit situation (-1.033)
            Pattern p42 = Pattern.compile("\\([\\+\\*\\/]*\\d+(\\.\\d+)?[\\+\\-\\*\\/]*\\)");
            Matcher m42 = p42.matcher(statement);
            while(m42.find()){
                correctStatementWithinParenthesis = false;
            }

            if(!correctStatementWithinParenthesis) return false;
        }

        //check that we don't have several signs of +-*/ or . going together:
        Pattern p5 = Pattern.compile("[\\+\\-\\*\\/\\.]{2,}");
        Matcher m5 = p5.matcher(statement);
        if(m5.find()){
            return false;
        }

        //check if we do not have the situation: 24.555.43
        Pattern p6 = Pattern.compile("\\d*\\.\\d*\\.");
        Matcher m6 = p6.matcher(statement);
        if(m6.find()){
            return false;
        }

        //check : only digits can be around the dot.
        //Doesn't work correct. Corrected in later logic of this method.
        if(statement.contains(".")){
            Pattern p7 = Pattern.compile("\\d\\.\\d");
            Matcher m7 = p7.matcher(statement);
            if(!m7.find()){
                return false;
            }
        }


        //check if . or / or * isn't the first sign in the statement:
        if(statement.charAt(0) == '.' || statement.charAt(0) == '*' || statement.charAt(0) == '/' ){
            return false;
        }

        //check if ( and ) create correct pairs. Avoid situation: (   )  ) ( (   )
        //counter of opening ( never can be less than 0
        String onlyParenthesis = statement.replaceAll("[^\\(\\)]", "");
        int counter = 0;
        char[] cArray = statement.toCharArray();
        for(int i = 0; i < cArray.length; i++){
            if(cArray[i] == '('){
                counter++;
            }else if(cArray[i] == ')'){
                counter--;
            }
            if(counter < 0){
                return false;
            }
        }

        //avoid this situation: 1(  together or )55 together:
        Pattern p8 = Pattern.compile("(\\)\\d|\\d\\()");
        Matcher m8 = p8.matcher(statement);
        if(m8.find()){
            return false;
        }

        //avoid this situation: .(  together or ). together:
        Pattern p9 = Pattern.compile("(\\)\\.|\\.\\()");
        Matcher m9 = p9.matcher(statement);
        if(m9.find()){
            return false;
        }

        //avoid this situation^ (0.33+)
        //avoid this situation^ (0.33)



        return true;
    }

    //this method splits the given statement to tokens:
    private static List<Object> stringToTokens(String statement){
        //create map for numbers and put here all the numbers from the String statement
        Map<Integer, Object> mapOfNumbers= new HashMap<>();
        //add to the pattern minus sign when not a beginning of the line and not after (
        Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher m = p.matcher(statement);
        while(m.find()){
            BigDecimal bigDecimal = new BigDecimal(m.group());
            mapOfNumbers.put(m.start(), bigDecimal);
        }
        //////////////////////////////////////////
        //create map for the signs and put all the signs into this map.
        Map<Integer, Object> mapOfSigns = new HashMap<>();
        Pattern p2 = Pattern.compile("[\\(\\)\\+\\-\\*\\/]");
        Matcher m2 = p2.matcher(statement);
        while(m2.find()){
            mapOfSigns.put(m2.start(), m2.group());
        }
        /////////////////////////////////////////////
        //unite all pair within one map
        mapOfNumbers.putAll(mapOfSigns);
        /////////////////////////////////////////////
        //create list based on this common map
        List<Object> listOfTokens = new ArrayList<>();
        for(int i = 0; i < statement.length(); i++){
            if(mapOfNumbers.containsKey(i)) {
                listOfTokens.add(mapOfNumbers.get(i));
            }
            else{
                listOfTokens.add("?");
            }
        }
        ////////////////////////////////////////////
        //delete fake tokens ("?") from list

        for(int i = 0; i < listOfTokens.size(); i++){
            if(listOfTokens.get(i) instanceof String) {
                if (((String) listOfTokens.get(i)).equals("?")) {
                    listOfTokens.remove(i);
                    i--;
                }
            }
        }

        //solve "minus sign problem" when minus goes in the beginning of the statement:
        //"-1.5+1.2+(-1)"

        for(int i = 0; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(0) instanceof String) {
                if (((String) listOfTokens.get(0)).equals("-")) {
                    listOfTokens.remove(0);
                    i--;
                    //next number multiply by -1;
                    if(listOfTokens.get(i+1) instanceof BigDecimal){
                        BigDecimal b = (BigDecimal)listOfTokens.get(i+1);
                        listOfTokens.set(i+1, b.multiply(new BigDecimal(-1)));
                    }
                    break;
                }
            }
        }

        //adapt list to the problem when the minus goes immediately after parenthesis:
        for(int i = 0; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(i) instanceof String) {
                if (((String) listOfTokens.get(i)).equals("(")) {

                    if(listOfTokens.get(i + 1) instanceof String) {
                        if (((String) listOfTokens.get(i + 1)).equals("-")) {
                            //delete this object of minus from list
                            listOfTokens.remove(i + 1);
                            i--;
                            //next number multiply by -1;
                            if (listOfTokens.get(i + 2) instanceof BigDecimal) {
                                BigDecimal b = (BigDecimal) listOfTokens.get(i + 2);
                                listOfTokens.set(i + 2, b.multiply(new BigDecimal(-1)));
                            }
                        }
                    }

                }
            }
        }

        return listOfTokens;
    }

    //this method calculates single action: pop sign, pop two numbers, calculate them and push the result to the stackOfBigDecimals.
    private void doAction(){
        String upperSign = stackOfSigns.pop();
        BigDecimal last = stackOfBigDecimals.pop();
        BigDecimal preLast = stackOfBigDecimals.pop();
        if(upperSign.equals("*")){
            BigDecimal result = preLast.multiply(last);
            stackOfBigDecimals.push(result);
        }
        else if(upperSign.equals("/")){
            BigDecimal result = preLast.divide(last, RoundingMode.HALF_UP);
            stackOfBigDecimals.push(result);
        }else if(upperSign.equals("+")){
            BigDecimal result = preLast.add(last);
            stackOfBigDecimals.push(result);
        }else if(upperSign.equals("-")){
            BigDecimal result = preLast.subtract(last);
            stackOfBigDecimals.push(result);
        }
    }

    //this method is used when the end of the statement is reached during the iteration for the last action with the rest of stacks.
    private BigDecimal doLastAction(){
        BigDecimal answer = new BigDecimal(0);
        String upperSign = stackOfSigns.pop();
        BigDecimal last = stackOfBigDecimals.pop();
        BigDecimal preLast = stackOfBigDecimals.pop();
        if(upperSign.equals("*")){
            BigDecimal result = preLast.multiply(last);
            answer = stackOfBigDecimals.push(result);
        }
        else if(upperSign.equals("/")){
            BigDecimal result = preLast.divide(last, RoundingMode.HALF_UP);
            answer = stackOfBigDecimals.push(result);
        }else if(upperSign.equals("+")){
            BigDecimal result = preLast.add(last);
            answer = stackOfBigDecimals.push(result);
        }else if(upperSign.equals("-")){
            BigDecimal result = preLast.subtract(last);
            answer = stackOfBigDecimals.push(result);
        }
        return answer;
    }

    //this recursive method is for actions within the parenthesis:
    //(2+2)
    private void recursive1(String sign){
        if(stackOfSigns.empty()){
            stackOfSigns.push(sign);
            return;
        }
        String upperSign = stackOfSigns.peek();
        if(upperSign.equals("(")){
            stackOfSigns.pop();
        }
        else if(upperSign.equals("+") || upperSign.equals("-") || upperSign.equals("*") || upperSign.equals("/")){
            doAction();
            recursive1(sign);
        }
    }

    //this recursive method is used for priorities of actions;
    private void recursive2(String sign){
        if(stackOfSigns.empty()){
            stackOfSigns.push(sign);
            return;
        }
        String upperSign = stackOfSigns.peek();
        if(priorities.containsKey(sign) && priorities.containsKey(upperSign) && priorities.get(upperSign) < priorities.get(sign)){
            stackOfSigns.push(sign);
        }
        else if(upperSign.equals("(") || upperSign.equals(")")){
            stackOfSigns.push(sign);
        }
        else if(priorities.containsKey(sign) && priorities.containsKey(upperSign) && priorities.get(upperSign) >= priorities.get(sign)){
            doAction();
            recursive2(sign);
        }
    }

    public String evaluate(String statement) {
        // TODO: Implement the logic here
        //01. Check if string is valid. Use regex. Return null if not valid.
        if(!validityCheck(statement)) return null;
        //02. Parse the string. ---> DONE via stringToTokens(String statement);
        //03. Use BigDecimal class to calculate --->DONE;
        //04. Round the result ---> DONE;
        //05. convert result to the String and return it. ---> DONE;


        //getting list of tokens:
        List<Object> listOfTokens = stringToTokens(statement);

        //iterating through the listOfTokens:
        for(int i = 0; i < listOfTokens.size(); i++){
//            /////////////////////CHECK///////////////////
//            System.out.println(i + "-я итерация:");
//            System.out.println("Содержимое стека с числами: ");
//            for(BigDecimal b : stackOfBigDecimals){
//                System.out.println(b.toString());
//            }
//            System.out.println();
//            System.out.println("Содержимое стека со знаками: ");
//            for(String s : stackOfSigns){
//                System.out.println(s);
//            }
//            System.out.println("--------------------------------------------------");
//            ///////////////END OF CHECK///////////////////
            if(listOfTokens.get(i) instanceof String){
                String sign = (String)listOfTokens.get(i);
                //if stackOfSigns is empty, ---> push this listOfTokens.get(i) to the stackOfSigns.
                if(stackOfSigns.size() == 0){
                    stackOfSigns.push(sign);
                }
                //if stackOfSigns is not empty,
                //if sign  == "(" -------> push this sign to the stackOfSigns
                else if(sign.equals("(")){
                    stackOfSigns.push(sign);
                }
                //if sign  == ")"  ------->
                else if(sign.equals(")")){
                    //if lhe last item in stackOfSign is "(" - we should pop this "(" from stack.
                    recursive1(sign);
                }
                //if sign in the statement == "*" or "/", and stackOfSigns.peek() == "+" or "-"   --->
                //push this sign to the stackOfSigns
                else if(sign.equals("+") || sign.equals("-") || sign.equals("*") || sign.equals("/")){
                    recursive2(sign);
                }
            }else if(listOfTokens.get(i) instanceof BigDecimal){
                //if number ---> push it to the stackOfDoubles.
                BigDecimal d = (BigDecimal) listOfTokens.get(i);
                stackOfBigDecimals.push(d);

            }

        }

        //FINAL ACTION IN THE SEPARATE LOGIC HERE:
        while(!stackOfSigns.empty()){
            doAction();
        }

        BigDecimal answer = new BigDecimal(0);
        if(stackOfSigns.size() == 1 && stackOfBigDecimals.size() == 2){
            answer = doLastAction();
        }
        else if(stackOfSigns.empty() && stackOfBigDecimals.size() == 1){
            answer = stackOfBigDecimals.pop();
        }


//        /////////////////////CHECK///////////////////
//        System.out.println("ФИНАЛ - Содержимое стека с числами: ");
//        for(BigDecimal b : stackOfBigDecimals){
//            System.out.println(b.toString());
//        }
//        System.out.println("\nФИНАЛ - Содержимое стека со знаками: ");
//        for(String s : stackOfSigns){
//            System.out.println(s);
//        }
//        ///////////////END OF CHECK///////////////////


        //DO NOT FORGET TO ROUND THE ANSWER
        //Rounding is to be performed to 4 significant digits, only the final result is to be rounded. Example: 102.12356 -> 102.1236
        answer = answer.setScale(4, RoundingMode.HALF_UP);

        return answer.toString();
    }


//    public static void main(String[] args) {
//        Calculator c = new Calculator();
//        String answer = c.evaluate("3+2=");
//        System.out.println(answer);
//
//    }
}
