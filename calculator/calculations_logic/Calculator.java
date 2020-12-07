package com.barracuda.tasks.politeh.practice_04_12_2020.calc.calculator.calculations_logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private boolean divideByZero;
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

        //если у нас есть " ("в выражении: проверьте, есть ли скобки, внутри них должна быть правильная подстановка (содержащая по крайней мере 2 числа с одним знаком +-*/ между ними;.
        //не работает должным образом: может найти последнее правильное утверждение и вернуть true; до этого могут быть неправильные утверждения;
        // String statement = "-1.0+((5/8*9)-(44+5.55555)/888.888)-(0.33+)";
        // избегаю этой ситуации^ (0.33+)
        // избегаю этой ситуации^ (0.33)
        // разрешить такую ситуацию (-1.033)
        if(statement.contains("(")){
            boolean correctStatementWithinParenthesis = false;
            boolean correctStatementWithinParenthesis1 = false;
            boolean correctStatementWithinParenthesis2 = false;


            Pattern p4 = Pattern.compile("\\((\\+|\\-)?\\d+([^\\+\\-\\*\\/]*)?(\\+|\\-|\\*|\\/)\\d+(.*)?\\)");
            Matcher m4 = p4.matcher(statement);
            while(m4.find()){
                correctStatementWithinParenthesis = true;
            }
            //избегаю этой ситуации: (0.33)
            //избегаю этой ситуации: (0.33+)
            //избегаю этой ситуации: (+0.33)
            //избегаю этой ситуации: (*0.33)
            //избегаю этой ситуации: (/0.33)
            //разрешаю: (-1.033)
            Pattern p42 = Pattern.compile("\\([\\+\\*\\/]*\\d+(\\.\\d+)?[\\+\\-\\*\\/]*\\)");
            Matcher m42 = p42.matcher(statement);
            while(m42.find()){
                correctStatementWithinParenthesis = false;
            }

            if(!correctStatementWithinParenthesis) return false;
        }

        //проверка что у нас нет несколько знаков +-*/ или . идущих подряд:
        Pattern p5 = Pattern.compile("[\\+\\-\\*\\/\\.]{2,}");
        Matcher m5 = p5.matcher(statement);
        if(m5.find()){
            return false;
        }

        //проверка что нет такой ситуации: 24.555.43
        Pattern p6 = Pattern.compile("\\d*\\.\\d*\\.");
        Matcher m6 = p6.matcher(statement);
        if(m6.find()){
            return false;
        }

        //проверка: что только цифры могут быть рядом с точкой с двух сторон.
        //не работает корректно. Корректен вариант далее в этом методе...
        if(statement.contains(".")){
            Pattern p7 = Pattern.compile("\\d\\.\\d");
            Matcher m7 = p7.matcher(statement);
            if(!m7.find()){
                return false;
            }
        }


        //проверка что . или / или * не являются первыми знаками в выражении:
        if(statement.charAt(0) == '.' || statement.charAt(0) == '*' || statement.charAt(0) == '/' ){
            return false;
        }

        //проверка что скобка такая  ( и такая  ) создают корректные пары. избегаю ситуации:  (   )  ) ( (   )
        //счетчик открывающих скобок не может быть меншье нуля (
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

        //избегаю ситуации 1(  стоят вместе или  )55 стоят вместе:
        Pattern p8 = Pattern.compile("(\\)\\d|\\d\\()");
        Matcher m8 = p8.matcher(statement);
        if(m8.find()){
            return false;
        }

        //избегаю ситуации: .( точка и скобка вместе или  ). вместе:
        Pattern p9 = Pattern.compile("(\\)\\.|\\.\\()");
        Matcher m9 = p9.matcher(statement);
        if(m9.find()){
            return false;
        }

        return true;
    }

    //Этот метод разделяет входную строку (выражение) на токены:
    private static List<Object> stringToTokens(String statement){
        //создаю мапу цифр и помещаю в нее все цифры из входной строки:
        Map<Integer, Object> mapOfNumbers= new HashMap<>();
        //add to the pattern minus sign when not a beginning of the line and not after (
        //добавить паттерну знак минус если не начало линии и не после скобки.
        Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher m = p.matcher(statement);
        while(m.find()){
            BigDecimal bigDecimal = new BigDecimal(m.group());
            mapOfNumbers.put(m.start(), bigDecimal);
        }
        //////////////////////////////////////////

        //создаю мапу знаком и все знаки складываю в нее.
        Map<Integer, Object> mapOfSigns = new HashMap<>();
        Pattern p2 = Pattern.compile("[\\(\\)\\+\\-\\*\\/]");
        Matcher m2 = p2.matcher(statement);
        while(m2.find()){
            mapOfSigns.put(m2.start(), m2.group());
        }
        /////////////////////////////////////////////
        //объединяю эти две мапы в одну:
        mapOfNumbers.putAll(mapOfSigns);
        /////////////////////////////////////////////
        //создаю List основанный на этой общей мапе
        List<Object> listOfTokens = new ArrayList<>();
        for(int i = 0; i < statement.length(); i++){
            if(mapOfNumbers.containsKey(i)) {
                listOfTokens.add(mapOfNumbers.get(i));
            }
            else{
                listOfTokens.add("?"); //<---фейковый токен
            }
        }
        ////////////////////////////////////////////

        //удаляю фейковые токены ("?") из листа
        for(int i = 0; i < listOfTokens.size(); i++){
            if(listOfTokens.get(i) instanceof String) {
                if (((String) listOfTokens.get(i)).equals("?")) {
                    listOfTokens.remove(i);
                    i--;
                }
            }
        }

        //решаю "проблему знака минус"- когда минус идет в начале выражения.:
        //"-1.5+1.2+(-1)"

        for(int i = 0; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(0) instanceof String) {
                if (((String) listOfTokens.get(0)).equals("-")) {
                    listOfTokens.remove(0);
                    i--;
                    //следующее число умножаю на -1;
                    if(listOfTokens.get(i+1) instanceof BigDecimal){
                        BigDecimal b = (BigDecimal)listOfTokens.get(i+1);
                        listOfTokens.set(i+1, b.multiply(new BigDecimal(-1)));
                    }
                    break;
                }
            }
        }


        //адаптирую лист к проблеме, когда минус идет непосредственно после скобок:
        for(int i = 0; i < listOfTokens.size(); i++) {
            if (listOfTokens.get(i) instanceof String) {
                if (((String) listOfTokens.get(i)).equals("(")) {

                    if(listOfTokens.get(i + 1) instanceof String) {
                        if (((String) listOfTokens.get(i + 1)).equals("-")) {
                            //delete this object of minus from list
                            listOfTokens.remove(i + 1);
                            i--;

                            //следующее число множаю на -1
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
    //этот метод делает одно маленькое математическое вычисление: забирает знак, два числа, выполняет действие, ответ складывает в стек с числами.
    private void doAction(){
        String upperSign = stackOfSigns.pop();
        BigDecimal last = stackOfBigDecimals.pop();
        BigDecimal preLast = stackOfBigDecimals.pop();
        if(upperSign.equals("*")){
            BigDecimal result = preLast.multiply(last);
            stackOfBigDecimals.push(result);

        }
        else if(upperSign.equals("/")){
            try {
                BigDecimal result = preLast.divide(last, RoundingMode.HALF_UP);
                stackOfBigDecimals.push(result);
            }catch(ArithmeticException a){
                divideByZero = true;
            }
        }else if(upperSign.equals("+")){
            BigDecimal result = preLast.add(last);
            stackOfBigDecimals.push(result);

        }else if(upperSign.equals("-")){
            BigDecimal result = preLast.subtract(last);
            stackOfBigDecimals.push(result);

        }

    }


    //этот метод используется когда достигается конец выражения во время итерации - как последнее действие с остатками стеков:
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
            try {
                BigDecimal result = preLast.divide(last, RoundingMode.HALF_UP);
                answer = stackOfBigDecimals.push(result);
            }catch(ArithmeticException a){
                divideByZero = true;
            }

        }else if(upperSign.equals("+")){
            BigDecimal result = preLast.add(last);
            answer = stackOfBigDecimals.push(result);
        }else if(upperSign.equals("-")){
            BigDecimal result = preLast.subtract(last);
            answer = stackOfBigDecimals.push(result);
        }
        return answer;
    }


    //этот рекурсивный метод для действий со скобками:
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
            if(divideByZero){
                return;
            }
            recursive1(sign);
        }
    }


    //этот рекурсивный метод - для приоритета действий.
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
            if(divideByZero){
                return;
            }
            recursive2(sign);
        }
    }

    //главный метод класса - открытый интерфейс.
    public String evaluate(String statement) {

        try {
            //проверка, что выражение валидно. Если не валидно - вернуть... null? или что-то еще?
            if (!validityCheck(statement)) return "неверное выражение";

            //получить лист токенов:
            List<Object> listOfTokens = stringToTokens(statement);

            //итерация по листу токенов:
            for (int i = 0; i < listOfTokens.size(); i++) {
//            /////////////////////проверочка///////////////////
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
//            ///////////////конец проверки///////////////////
                if (listOfTokens.get(i) instanceof String) {
                    String sign = (String) listOfTokens.get(i);

                    //если stackOfSigns пустой ---> push этот listOfTokens.get(i)в stackOfSigns.
                    if (stackOfSigns.size() == 0) {
                        stackOfSigns.push(sign);
                    }
                    //если stackOfSigns не  пустой,
                    //если sign  == "(" -------> push этот sign в stackOfSigns

                    else if (sign.equals("(")) {
                        stackOfSigns.push(sign);
                    }
                    //если sign  == ")"  ------->
                    else if (sign.equals(")")) {
                        //если последний элемент в stackOfSign является "(" - нужно его pop  "(" из стека.
                        recursive1(sign);
                    }
                    //если sign в выражении == "*" или "/", и stackOfSigns.peek() == "+" или "-"   --->
                    //push этот sign в stackOfSigns
                    else if (sign.equals("+") || sign.equals("-") || sign.equals("*") || sign.equals("/")) {
                        recursive2(sign);
                    }
                } else if (listOfTokens.get(i) instanceof BigDecimal) {
                    //если цифра  ---> пушнуть ее в  stackOfDoubles.
                    BigDecimal d = (BigDecimal) listOfTokens.get(i);
                    stackOfBigDecimals.push(d);

                }

            }

            //финальное действие в отдельной логике вот здесь::
            while (!stackOfSigns.empty()) {
                doAction();
                if(divideByZero){
                    return "деление на ноль...";
                }
            }

            BigDecimal answer = new BigDecimal(0);
            if (stackOfSigns.size() == 1 && stackOfBigDecimals.size() == 2) {
                answer = doLastAction();
            } else if (stackOfSigns.empty() && stackOfBigDecimals.size() == 1) {
                answer = stackOfBigDecimals.pop();
            }


//        /////////////////////проверка///////////////////
//        System.out.println("ФИНАЛ - Содержимое стека с числами: ");
//        for(BigDecimal b : stackOfBigDecimals){
//            System.out.println(b.toString());
//        }
//        System.out.println("\nФИНАЛ - Содержимое стека со знаками: ");
//        for(String s : stackOfSigns){
//            System.out.println(s);
//        }
//        ///////////////конец проверки///////////////////


            //НЕ ЗАБЫТЬ ОКРУГЛИТЬ ОТВЕТ

            //Округление делаю до 4 значящих цифр, округляю только конечный результат. Пример: 102.12356 -> 102.1236
            answer = answer.setScale(4, RoundingMode.HALF_UP);

            return divideByZero? "деление на ноль" : answer.toString();

        }catch(ArithmeticException n){
            return "деление на ноль!";
        }
    }

//финальная проверка:
//    public static void main(String[] args) {
//        Calculator c = new Calculator();
//        String answer = c.evaluate("3+2=");
//        System.out.println(answer);
//
//    }
}
