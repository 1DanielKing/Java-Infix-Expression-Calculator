import java.util.*;

public class App {
    public static void main(String[] args) {
        Input input = new Input();
        String operationString = input.getInput();
        List<String> equations = Arrays.asList(operationString);
            //"(5 + 3) * 2");
        
        for(String e: equations)
            System.out.println(solveEquation(e));
    }

    private static int solveEquation(String e) {

        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        Map<Character, Integer> map = new HashMap<>();
        map.put('^', 1);
        map.put('+', 2);
        map.put('-', 2);
        map.put('*', 3);
        map.put('/', 3);

        for (char ch : e.toCharArray()) {

            if(Character.isDigit(ch)){
                operands.push(ch-'0');
            }

            else if (ch == '(')
                operators.push(ch);

            else if (ch == ')') {
                char c = operators.pop();
                while (c != '(') {
                    int n2 = operands.pop();
                    int n1 = operands.pop();
                    operands.push(operation(n1, n2, c));
                    c = operators.pop();
                }
            }
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {

                while (!operators.isEmpty() && operators.peek() != '(' && map.get(ch) <= map.get(operators.peek())) {
                    int n2 = operands.pop();
                    int n1 = operands.pop();
                    operands.push(operation(n1, n2, operators.pop()));
                }
                operators.push(ch);
            }
        }

        while(!operators.isEmpty()){
            int n2 = operands.pop();
            int n1 = operands.pop();
            char ch = operators.pop();
            operands.push(operation(n1, n2, ch));
        }
        return operands.pop();
    }

    private static int operation(int a, int b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return (int) (Math.round(Math.pow(a, b)));
        }
        return 0;
    }
}