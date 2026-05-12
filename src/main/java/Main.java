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
            } else {
                System.out.println(input + ": command not found");
            }


        }

    }
}
