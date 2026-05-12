import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = sc.nextLine();

            if (input.startsWith("echo")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("exit")) {
                System.exit(0);
            }if (input.startsWith("type")) {
                String command = input.substring(5);
                if (command.equals("echo") || command.equals("exit") || command.equals("type")) {
                    System.out.println(command + " is a built-in command");
                } else {
                    System.out.println(command + " is not a built-in command");
                }
            }else {
                System.out.println(input + ": command not found");
            }


        }

    }
}
