package ch.skyfy.mcservermanager.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class InputStreamConsumerThread extends Thread {

    private final InputStream is;

    private final boolean sysout;

    private final StringBuilder output = new StringBuilder();

    private final Consumer<String> consumer;

    public InputStreamConsumerThread(InputStream is, boolean sysout, Consumer<String> consumer) {
        this.is = is;
        this.sysout = sysout;
        this.consumer = consumer;
    }

    public void run() {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (sysout)
                    System.out.println(line);
                consumer.accept(line);
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}