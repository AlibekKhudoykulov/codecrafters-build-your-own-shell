import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static String[] builtIns = {"echo", "exit", "type"};
    private static String[] paths = System.getenv("PATH").split(File.pathSeparator);


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = sc.nextLine();

            if (input.startsWith("echo")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("exit")) {
                System.exit(0);
            } else if (input.startsWith("type")) {
                getType(input.substring(5));
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }

    public static void getType(String input) {
        for (String builtIn : builtIns) {
            if (builtIn.equalsIgnoreCase(input)) {
                System.out.println(builtIn + " is a shell builtin");
                return;
            }
        }

        for(String path: paths){
            File file = new File(path, input);
            if (file.exists() && file.canExecute()) {
                System.out.println(input + " is " + file.getAbsolutePath());
                return;
            }
        }

        System.out.println(input + ": not found");
    }
}
