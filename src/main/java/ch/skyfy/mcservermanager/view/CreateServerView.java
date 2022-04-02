package ch.skyfy.mcservermanager.view;

import ch.skyfy.mcservermanager.ServerManagerStage;
import ch.skyfy.mcservermanager.controller.ServerManager;
import ch.skyfy.mcservermanager.model.Server;
import ch.skyfy.mcservermanager.util.FXMLUtils;
import ch.skyfy.mcservermanager.util.UIUtils;
import ch.skyfy.mcservermanager.view.customcontrols.SButton;
import ch.skyfy.mcservermanager.view.customcontrols.SLabel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateServerView extends StackPane implements Initializable {

    @FXML
    private StackPane center_sp, bottom_sp;

    @FXML
    private TextField center_textField;

    @FXML
    private HBox center_hbox;

    private final Stage primaryStage;

    private final double[] primaryStageSize;

    private boolean canceled;

    public CreateServerView(double[] primaryStageSize, Stage primaryStage) {
        this.primaryStageSize = primaryStageSize;
        this.primaryStage = primaryStage;
        canceled = true;
        FXMLUtils.loadFXML(ServerManagerStage.class, "view/createServer.fxml", this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setPrefSize(primaryStageSize[0] * 0.8, primaryStageSize[1] * 0.8);
        createUI();
    }

    private void createUI(){

        final SLabel serverText = new SLabel("Nom du serveur", Font.font("Arial", 8), center_sp);
        serverText.makeResponsive(90, 70, 3,3,3,3);

        StackPane.setAlignment(serverText, Pos.CENTER_RIGHT);
        center_sp.getChildren().add(serverText);

        center_hbox.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            center_textField.setPrefSize(newValue.getWidth() * 0.4, newValue.getHeight() * 0.15);
            center_sp.setPrefWidth(newValue.getWidth() * 0.4);
            center_sp.setPrefHeight(newValue.getHeight() * 0.15);
            double percentWidth = 40d;
            double percentHeight = 15d;
            double width = newValue.getWidth() * (percentWidth / 100d);
            double height = newValue.getHeight() * (percentHeight / 100d);
            double size = UIUtils.getPerkektSize(height, width, "mmmmmmmm", 8);
            center_textField.setFont(Font.font("Arial", size));
        });

        final SButton createServer_btn = new SButton("Créer le serveur", Font.font("Arial", 8), bottom_sp);
        createServer_btn.makeResponsive(40, 60, 5,5,5,5);
        bottom_sp.getChildren().add(createServer_btn);

        createServer_btn.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                ServerManager.getInstance().getServers().add(new Server(center_textField.getText()));
                closeStage(event);
            }
        });

    }

    private void closeStage(MouseEvent event) {
        canceled = false;
        final Node node = (Node) event.getSource();
        ((Stage) node.getScene().getWindow()).close();
    }

}
