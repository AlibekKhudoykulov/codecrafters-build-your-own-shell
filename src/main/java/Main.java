import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String[] paths = System.getenv("PATH").split(File.pathSeparator);
            String input = sc.nextLine();

            if (input.startsWith("echo")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("exit")) {
                System.exit(0);
            } else if (input.startsWith("type")) {
                String command = input.substring(5);
                if (command.equals("echo") || command.equals("exit") || command.equals("type")) {
                    System.out.println(command + " is a shell builtin");
                } else {
                    boolean found = false;
                    for(String directory: paths) {
                        File file = new File(directory, command);
                        if( file.exists() && file.canExecute()) {
                            System.out.println(command + " is " + file.getAbsolutePath());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println(command + ": not found");
                    }
                }
            }else {
                System.out.println(input + ": command not found");
            }


        }

    }
}
