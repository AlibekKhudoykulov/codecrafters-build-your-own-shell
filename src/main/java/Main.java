import java.io.File;
import java.io.IOException;
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
            } else if (input.startsWith("type")) {
                getType(input.substring(5));
            } else if (input.startsWith("exit")) {
                System.exit(0);
            } else {
                boolean executableProgram = getExecutableProgram(input);
                if(!executableProgram) {
                    System.out.println(input + ": command not found");
                }
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

    public static boolean getExecutableProgram(String input) throws IOException, InterruptedException {
        String[] commands = input.split(" ");
        for(String path: paths){
            File file = new File(path, commands[0]);
            if (file.exists() && file.canExecute()) {
                ProcessBuilder pb = new ProcessBuilder(commands);
                Process process = pb.start();
//                System.out.println("Program was passed "+ commands.length +" args (including program name).");
//                System.out.println("Arg #0 (program name): "+ commands[0]);
//                printCommands(commands);
//                System.out.println("Program Signature: " + process.pid());
                return true;
            }
        }
        return false;
    }

    public static void printCommands(String[] commands){
        for(int i = 1; i < commands.length; i++){
            System.out.println("Arg #"+i+": "+commands[i]);
        }
    }
}
