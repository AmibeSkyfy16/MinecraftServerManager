package ch.skyfy.mcservermanager.view.customcontrols;

import ch.skyfy.mcservermanager.ServerManagerStage;
import ch.skyfy.mcservermanager.controller.ServerManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.function.Supplier;
import java.util.zip.CheckedOutputStream;

public class JavaOneButton extends Control {

    private static final PseudoClass PSEUDO_CLASS_ARMED = PseudoClass.getPseudoClass("armed");

    private final Text textLabel;
    private final StackPane stackPane;
    private final SimpleObjectProperty<Insets> marginProperty;
    private Supplier<String> longestString; // permet de récupérer le chaine de caractère la plus longue (voir STextGroup)

    public JavaOneButton(String text) {
        stackPane = new StackPane() {
            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                this.requestLayout(); // TODO : RESOUT LE BUG GRAPHIC DE REDIMENSIONNEMENT
                request(this);
            }
        };
        this.marginProperty = new SimpleObjectProperty<>(new Insets(0));
        getStyleClass().add("javaone-button");
        setFocusTraversable(true);
        this.textLabel = new Text(text) {{
            setBoundsType(TextBoundsType.VISUAL);
            setFont(Font.font("Arial", 20));
        }};
        stackPane.getChildren().add(textLabel);
        getChildren().add(stackPane);

//        String id = (this.getClass() == SLabel.class) ? "slabel" : "sbutton";
//        this.getStylesheets().add(ServerManagerStage.class.getResource("css/control/sbutton.css").toExternalForm());
//        stackPane.getStyleClass().add("javaone-button");

//        this.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(1), new Insets(12))));

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            pseudoClassStateChanged(PSEUDO_CLASS_ARMED, true);
            requestFocus();
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            pseudoClassStateChanged(PSEUDO_CLASS_ARMED, false);
        });

    }
    public void request(Parent parent) {
        parent.requestLayout();
        Parent parent1 = parent.getParent();
        if (parent1 != null) request(parent1);
    }

    // --- text
    private StringProperty textProperty = new SimpleStringProperty(this, "text");

    public final StringProperty textProperty() {
        return textProperty;
    }

    public final String getText() {
        return textProperty.get();
    }


    @Override
    protected double computeMinWidth(double height) {
        return stackPane.minWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        return stackPane.minHeight(width);
    }

    @Override
    protected double computeMaxWidth(double height) {
        return computePrefWidth(height);
    }

    @Override
    protected double computeMaxHeight(double width) {
        return computePrefHeight(width);
    }

    @Override
    protected double computePrefWidth(double height) {
        return stackPane.prefWidth(height) +
                snappedLeftInset() +
                snappedRightInset();
    }

    @Override
    protected double computePrefHeight(double width) {
        return stackPane.prefHeight(width) +
                snappedTopInset() +
                snappedBottomInset();
    }

    @Override
    protected void layoutChildren() {
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = getWidth() - (snappedLeftInset() + snappedRightInset());
        final double h = getHeight() - (snappedTopInset() + snappedBottomInset());
        stackPane.resizeRelocate(x, y, w, h);
    }

    @Override
    public String getUserAgentStylesheet() {
        return ServerManagerStage.class.getResource("css/javaone-button.css").toExternalForm();
    }


    @Override
    protected Skin<?> createDefaultSkin() {
        return new JavaOneButtonSkin(this);
    }
}
