package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CardanoCLIJava {
    public static void main(String[] args) {
        try {
            // Path to the cardano-cli executable within your project directory
            String cliPath = "cardano-cli"; // Adjust the path to the cardano-cli executable

            // First command to execute
            String[] command1 = {cliPath, "address", "key-gen", "--verification-key-file", "b.vkey",
                    "--signing-key-file", "b.skey"};

            // Second command to execute
            String[] command2 = {cliPath, "address", "build",
                    "--payment-verification-key-file", "b.vkey", "--testnet-magic", "2",
                    "--out-file", "b.addr"};

            // Start both processes
            ProcessBuilder pb1 = new ProcessBuilder(command1);
            ProcessBuilder pb2 = new ProcessBuilder(command2);

            Process process1 = pb1.start();
            Process process2 = pb2.start();

            // Read the output and error streams of the first process
            readOutput(process1.getInputStream(), process1.getErrorStream());

            // Read the output and error streams of the second process
            readOutput(process2.getInputStream(), process2.getErrorStream());

            // Wait for both processes to finish
            int exitCode1 = process1.waitFor();
            System.out.println("Exited with error code for command1: " + exitCode1);

            int exitCode2 = process2.waitFor();
            System.out.println("Exited with error code for command2: " + exitCode2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void readOutput(InputStream inputStream, InputStream errorStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

        // Read the output of the process
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // Read the error output of the process
        while ((line = errorReader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
