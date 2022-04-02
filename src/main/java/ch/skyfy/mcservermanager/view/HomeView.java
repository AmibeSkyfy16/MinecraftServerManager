package ch.skyfy.mcservermanager.view;

import ch.skyfy.mcservermanager.ServerManagerStage;
import ch.skyfy.mcservermanager.controller.ServerManager;
import ch.skyfy.mcservermanager.model.Server;
import ch.skyfy.mcservermanager.util.FXMLUtils;
import ch.skyfy.mcservermanager.util.UIUtils;
import ch.skyfy.mcservermanager.view.customcontrols.SButton;
import ch.skyfy.mcservermanager.view.customcontrols.SLabel;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HomeView extends StackPane implements Initializable {

    @FXML
    private Button createServer_btn;

    @FXML
    private StackPane container, bottomLeft_sp;

    @FXML
    private VBox serverList_vbox;

    @FXML
    private ColumnConstraints left_cc;

    @FXML
    private GridPane root_gridPane, right_gridPane;

    @FXML
    private ScrollPane left_scrollPane;

    @FXML
    private VBox bottomRight_vbox, topRight_vbox;

    @FXML
    private TextField command_textField;

    @FXML
    private TextFlow serverLog_textFlow;

    @FXML
    private ScrollPane serverLog_scrollPane;

    private SLabel currentServer;
    private SLabel serverState;

    private SButton startServer_btn;

    private final SLabel.STextGroup sTextGroup;

    private final ServerManager serverManager;

    public HomeView() {

        this.getStylesheets().add(ServerManagerStage.class.getResource("css/home.css").toExternalForm());

        sTextGroup = new SLabel.STextGroup();
        serverManager = ServerManager.getInstance();
        serverManager.getServers().add(new Server("default"));

        FXMLUtils.loadFXML(ServerManagerStage.class, "view/home.fxml", this);
    }

    private void test1() {
        System.out.println
                ("*************Calendar for Year**********");
        try {
            ProcessBuilder pb = new
                    ProcessBuilder("cal", "2022");
            final Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("************************************");


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        createServer_btn.setOnAction(event -> {
//
//            if (count == 1) {
//                try {
//                    OutputStream stdin = serverManager.getServers().get(0).getProcess().getOutputStream();
////                    InputStream stderr = serverManager.getServers().get(0).getProcess().getErrorStream();
////                    InputStream stdout = serverManager.getServers().get(0).getProcess().getInputStream();
//                    String line;
//
////                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(stdin);
//
////                    bufferedOutputStream.write("deop Skyfy16".getBytes());
////                    bufferedOutputStream.flush();
//                    serverManager.getServers().get(0).getProcess().getOutputStream().write("deop Skyfy16\n".getBytes());
//                    serverManager.getServers().get(0).getProcess().getOutputStream().flush();
////                    serverManager.getServers().get(0).getProcess().getOutputStream().write("deop Skyfy16\n\r".getBytes());
////                    serverManager.getServers().get(0).getProcess().getOutputStream().flush();
////                    serverManager.getServers().get(0).getProcess().getOutputStream().write("deop Skyfy16\r\n".getBytes());
////                    serverManager.getServers().get(0).getProcess().getOutputStream().flush();
//
//
////                    bufferedOutputStream.close();
////                    stdin.close();
//
//                    // clean up if any output in stdout
////                    BufferedReader brCleanUp =
////                            new BufferedReader(new InputStreamReader(stdout));
////                    while ((line = brCleanUp.readLine()) != null) {
////                        System.out.println ("[Stdout] " + line);
////                    }
////                    brCleanUp.close();
//
////
////                    // clean up if any output in stderr
////                    brCleanUp =
////                            new BufferedReader(new InputStreamReader(stderr));
////                    while ((line = brCleanUp.readLine()) != null) {
////                        //System.out.println ("[Stderr] " + line);
////                    }
////                    brCleanUp.close();
//
////                    serverManager.getServers().get(0).getProcess().getOutputStream().write("exit".getBytes());
////                    serverManager.getServers().get(0).getProcess().getOutputStream().flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (count == 0) {
//                serverManager.startServerByName("default");
//                count++;
//            }
////            final ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "paper-334.jar", "nogui");
////            processBuilder.directory(finalFile);
////            try {
////                final Process process = processBuilder.start();
////                process.waitFor();
////            } catch (IOException | InterruptedException e) {
////                e.printStackTrace();
////            }
//        });

//        createServer_btn.setOnAction(event -> {
//            String systemProperties = "-Dkey=value";
//            ProcessBuilder pb = new ProcessBuilder( "java", systemProperties, "-jar", "paper-334.jar");
//            pb.directory(finalFile);
//            Process proc = null;
//            try {
//                proc = pb.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            InputStreamConsumerThread inputConsumer =
//                    new InputStreamConsumerThread(proc.getInputStream(), true);
//            InputStreamConsumerThread errorConsumer =
//                    new InputStreamConsumerThread(proc.getErrorStream(), true);
//
//            inputConsumer.start();
//            errorConsumer.start();
//
//            System.out.println( "Job running" );
//            try {
//                proc.waitFor(); // wait until jar is finished
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println( "Job finished" );
//
//            String processOutput = inputConsumer.getOutput();
//            String processError = errorConsumer.getOutput();
//
//            if(!processOutput.isEmpty()){
//                //there were some output
//            }
//        });

//        createServer_btn.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
////                String output = ProcBuilder.run("echo", "Hello World!");
////                System.out.println(" RETURNED : " + output);
//
////                test1();
//
////                if(0 == 0)return;
//
//                URL url2 = ServerManager.class.getResource("executorProcess");
//                File file3 = null;
//                try {
//                    file3 = new File(url2.toURI().getPath());
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
////                File file2 = new File("Z:\\Documents\\Informatique\\Projets\\JavaFX\\2020\\MinecraftServerManager\\src\\main\\resources\\ch\\skyfy\\mcservermanager\\executorProcess\\");
//                File file2 = new File("/Z:/Documents/Informatique/Projets/JavaFX/2020/MinecraftServerManager/build/resources/main/ch/skyfy/mcservermanager/executorProcess/");
//
//
//                try {
//                    ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "start.bat");
//                    pb.directory(file3);
//                    pb.redirectErrorStream(true);
//                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//                    Process process = pb.start();
//                    process.waitFor();
//                    //                    Process process = Runtime.getRuntime().exec("cmd /c start start.bat", null, file3);
////                    process.waitFor();
//                    int i;
//                    char c;
////                    while ((i = process.getInputStream().read()) != -1) {
////                        c = (char) i;
////                        System.out.print(c);
////                    }
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        createBottomLeftUI();
        createRightUI();

        serverManager.getServers().add(new Server("ServerDeBuild1"));
        serverManager.getServers().add(new Server("Server de build #2"));
        serverManager.getServers().add(new Server("Server PVP #1"));
        serverManager.getServers().add(new Server("ServerBUILD"));
//        addServer("ServerDeBuild1 ");
//        addServer("Server de build #2 ");
//        addServer("Server de build #3 ");
//        addServer("Server PVP #1 ");
//        addServer("Server PVP #2 ");
//        addServer("Server PVP #3 ");

    }

    private void createBottomLeftUI() {
        final SButton createServer_btn = new SButton("+", Font.font("Arial", 8), bottomLeft_sp);
        createServer_btn.setId("create-server");
        createServer_btn.makeResponsive(15, 75, 15, 2, 15, 2);
        StackPane.setAlignment(createServer_btn, Pos.CENTER_LEFT);
        StackPane.setMargin(createServer_btn, new Insets(0, 0, 0, 5));
        bottomLeft_sp.getChildren().add(createServer_btn);

        createServer_btn.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                new CreateServerWindow().show((Stage) this.getScene().getWindow());
            }
        });

        serverManager.getServers().addListener((ListChangeListener<? super Server>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    addServer(c.getAddedSubList().get(0).getName());
                }
            }
        });

    }

    private void createRightUI() {

        currentServer = new SLabel("Serveur actuellement séléctionner : AUCUN !", Font.font("Arial", 8), bottomRight_vbox);
        serverState = new SLabel("Etat du serveur : ARRETE", Font.font("Arial", 8), bottomRight_vbox);
        startServer_btn = new SButton("Démarrer le server", Font.font("Arial", 8), bottomRight_vbox);

        currentServer.makeResponsive(80, 20, 5, 5, 5, 5);
        serverState.makeResponsive(80, 20, 5, 5, 5, 5);
        startServer_btn.makeResponsive(60, 25, 5, 5, 5, 5);
        bottomRight_vbox.getChildren().addAll(currentServer, serverState, startServer_btn);

        // Code appellé quand le server aura start
        final Runnable startRunnable = () -> serverState.getText().setText("Etat du serveur : en cours d'exécution");

        // code pour mettre à jour le texte
        final Consumer<String> updateTextConsumer = (stringBuilder) -> Platform.runLater(() -> {
            String line;
            if (serverLog_textFlow.getChildren().size() == 0) line = stringBuilder;
            else line = "\n" + stringBuilder;
            serverLog_textFlow.getChildren().add(new Text(line));
        });

        // AUTOSIZE DU TEXT QUE L'ON SAISIE EN LIGNE DE COMMANDE
        topRight_vbox.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            double percentWidth = 40d;
            double percentHeight = 40d;
            double width = newValue.getWidth() * (percentWidth / 100d);
            double height = newValue.getHeight() * (percentHeight / 100d);
            double size = UIUtils.getPerkektSize(width, height, "mmmmmmmm", 8);
            command_textField.setFont(Font.font("Arial", size));
        });

        // La scrollBar descent automatiquement lorsque du texte est ajouté
        serverLog_textFlow.getChildren().addListener((ListChangeListener<? super Node>) c -> {
            serverLog_scrollPane.layout();
            serverLog_scrollPane.setVvalue(1.0d);
        });

        // Lorsqu'on press sur enter
        command_textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                serverManager.executeCommand(command_textField.getText());
                // TODO ERROR
                command_textField.setText("");
            }
        });

        // Lorsque l'on press sur startServer, on démarre le server actuellement sélectionné
        startServer_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // On récupére le button actuellement sélectionné
            final SButton selected_btn = (SButton) serverList_vbox.getChildren().filtered(node -> node instanceof SButton sButton && sButton.isSelected()).get(0);
            serverManager.startServerByName(selected_btn.getText().getText(), updateTextConsumer, startRunnable);
        });

    }

    private void addServer(String name) {
        final SButton server_btn = new SButton(name, Font.font("Arial", 8), root_gridPane);
        server_btn.getStyleClass().add("server-btn");
        server_btn.makeResponsive(40, 15, 3, 10, 3, 10);
        server_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectedButton(server_btn));
        sTextGroup.addAll(server_btn);
        serverList_vbox.getChildren().add(server_btn);
        Scene scene = this.getScene();
        if(scene != null) {
            scene.getWindow().setWidth(scene.getWindow().getWidth()+1);
        }
    }

    private void selectedButton(SButton selected_btn) {
        serverList_vbox.getChildren().forEach(node -> {
            if (node instanceof SButton sButton) {
                sButton.setSelected(selected_btn.equals(sButton)); // Si le bouton est celui là. Selected est TRUE, SINON TOUS LES AUTRES FALSE
                if (sButton.equals(selected_btn)) {
                    sButton.setSelected(true);
                    serverManager.currentServerProperty().setValue(serverManager.getServerByName(selected_btn.getText().getText()));
                    currentServer.getText().setText("Serveur actuellement sélectionner : " + selected_btn.getText().getText());
                } else {
                    sButton.setSelected(false);
                }
            }
        });
    }

    private void createServerImpl() {

    }

}
