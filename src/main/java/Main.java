import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final String[] builtIns = {"echo", "exit", "type", "pwd"};
    private static final String[] paths = System.getenv("PATH").split(File.pathSeparator);
    private static String currentDirectory = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = sc.nextLine();

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "echo" -> System.out.println(argument);
                case "exit" -> System.exit(0);
                case "type" -> handleType(argument);
                case "pwd" -> System.out.println(currentDirectory);
                case "cd" -> handlePath(argument);
                default -> handleExternal(input);
            }
        }
    }

    private static File findExecutable(String name) {
        for (String path : paths) {
            File file = new File(path, name);
            if (file.exists() && file.canExecute()) return file;
        }
        return null;
    }

    private static void handleType(String argument) {
        for (String builtIn : builtIns) {
            if (builtIn.equals(argument)) {
                System.out.println(argument + " is a shell builtin");
                return;
            }
        }
        File executable = findExecutable(argument);
        if (executable != null) {
            System.out.println(argument + " is " + executable.getAbsolutePath());
        } else {
            System.out.println(argument + ": not found");
        }
    }

    private static void handleExternal(String input) throws IOException, InterruptedException {
        String[] commands = input.split(" ");
        File executable = findExecutable(commands[0]);
        if (executable != null) {
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } else {
            System.out.println(commands[0] + ": command not found");
        }
    }

    private static void handlePath(String absolutePath) {
        Path path = Path.of(absolutePath);
        if (Files.exists(path) && Files.isDirectory(path)) {
            currentDirectory = path.toString();
        } else {
            System.out.println("cd: " + path + ": No such file or directory");
        }
    }
}
