package ch.skyfy.mcservermanager.model;

import ch.skyfy.mcservermanager.ServerManagerStage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class Server {

    private String name;

    private Thread thread;

    ProcessBuilder processBuilder;

    Process process;

    private InputStreamConsumerThread inputConsumer, errorConsumer;


    public Server(String name) {
        this.name = name;
    }

    public void start(Consumer<String> runnable, Runnable startRunnable) {
        thread = new Thread(() -> {
            // CHECK IF FILE EXIST
            try {
                final File serverFolder = new File(ServerManagerStage.class.getResource("servers/" + name.trim()).toURI().getPath());
                if (!serverFolder.exists()) {
                    if (!serverFolder.mkdir()) {
                        System.out.println("ERREUR DOSSIER PAS CREER");
                        return;
                    }
                }
                processBuilder = new ProcessBuilder("java", "-jar", "paper-334.jar");
                processBuilder.redirectErrorStream(true);
//                processBuilder.redirectOutput(ProcessBuilder.Redirect.DISCARD)
                processBuilder.directory(serverFolder);
                try {
                    process = processBuilder.start();
                    startRunnable.run();

                    inputConsumer = new InputStreamConsumerThread(process.getInputStream(), false, runnable);
                    errorConsumer = new InputStreamConsumerThread(process.getErrorStream(), false, runnable);

                    inputConsumer.start();
                    errorConsumer.start();

                    process.waitFor();
                    System.out.println("END !");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }) {{
            setDaemon(true);
        }};
        thread.start();
    }

    public String getName() {
        return name;
    }

    public Thread getThread() {
        return thread;
    }

    public ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }

    public Process getProcess() {
        return process;
    }

    public InputStreamConsumerThread getErrorConsumer() {
        return errorConsumer;
    }

    public InputStreamConsumerThread getInputConsumer() {
        return inputConsumer;
    }
}
