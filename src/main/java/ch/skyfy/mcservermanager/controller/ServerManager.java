package ch.skyfy.mcservermanager.controller;

import ch.skyfy.mcservermanager.model.Server;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ServerManager {

    private static ServerManager instance;

    private final ObservableList<Server> servers;

    private final ObjectProperty<Server> currentServer;


    /**
     * Java Singleton Pattern Implementation
     * @see https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
     * @return ServerManager
     */
    public static ServerManager getInstance(){
        if(instance == null){
            synchronized (ServerManager.class){
                if(instance == null){
                    instance = new ServerManager();
                }
            }
        }
        return instance;
    }

    private ServerManager() {
        servers = FXCollections.observableArrayList();
        currentServer = new SimpleObjectProperty<>(null);
    }

    public void startServerByName(String name, Consumer<String> runnable, Runnable startRunnable) {
        servers.stream().filter(server -> server.getName().equals(name)).findFirst().ifPresent(server -> server.start(runnable, startRunnable));
    }

    public ObservableList<Server> getServers() {
        return servers;
    }

    public Server getServerByName(String name) {
        Optional<Server> optionalServer = servers.stream().filter(server -> server.getName().equals(name)).findFirst();
        if (!optionalServer.isEmpty()) {
            return optionalServer.get();
        }
        return null;
    }

    public boolean executeCommand(String command) {
        final Server server1 = currentServerProperty().get();
        if (server1 == null) return false;
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(server1.getProcess().getOutputStream());
        try {
            bufferedOutputStream.write((command + "\n").getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ObjectProperty<Server> currentServerProperty() {
        return currentServer;
    }

}
