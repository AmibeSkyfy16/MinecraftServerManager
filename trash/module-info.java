module ch.skyfy.mcservermanager {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires nuprocess;
    requires java.desktop;

    exports ch.skyfy.mcservermanager to javafx.graphics;

    opens ch.skyfy.mcservermanager.view to  javafx.fxml;

    requires jproc;

}