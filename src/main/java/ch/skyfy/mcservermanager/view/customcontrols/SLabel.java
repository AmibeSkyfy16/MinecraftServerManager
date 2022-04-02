package ch.skyfy.mcservermanager.view.customcontrols;

import ch.skyfy.mcservermanager.ServerManagerStage;
import ch.skyfy.mcservermanager.util.UIUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.function.Supplier;

public class SLabel extends Control {

    private final StackPane sp;

    private final Text text;

    private final Region region;

    private final SimpleObjectProperty<Insets> marginProperty;

    private Supplier<String> longestString; // permet de récupérer le chaine de caractère la plus longue (voir STextGroup)


    public SLabel(String str, Font font, Region region) {
        sp = new StackPane();
        this.text = new Text(str) {{
            setBoundsType(TextBoundsType.VISUAL);
            setFont(font);
        }};
        this.region = region;
        this.marginProperty = new SimpleObjectProperty<>(new Insets(0));

        sp.getChildren().add(text);
        this.getChildren().add(sp);

        getStyleClass().add(this.getClass().getSimpleName().toLowerCase());
    }

    @Override
    protected void layoutChildren() {
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = getWidth() - (snappedLeftInset() + snappedRightInset());
        final double h = getHeight() - (snappedTopInset() + snappedBottomInset());
        sp.resizeRelocate(x, y, w, h);
        this.requestLayout(); // TODO : RESOUT LE BUG GRAPHIC DE REDIMENSIONNEMENT
        request(this);
    }

    public void request(Parent parent) {
        parent.requestLayout();
        final Parent parent1 = parent.getParent();
        if (parent1 != null) request(parent1);
    }

    public void makeResponsive(double percentWidth, double percentHeight,
                               double percentMarginTop, double percentMarginRight,
                               double percentMarginBottom, double percentMarginLeft) {

        //----------- SIZE (WIDTH AND HEIGHT) -----------\\
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);

        region.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getWidth() == 0 || newValue.getHeight() == 0) return;
            this.setPrefSize(newValue.getWidth() * (percentWidth / 100), newValue.getHeight() * (percentHeight / 100));

           final Insets margin = new Insets(
                    newValue.getHeight() * (percentMarginTop / 100),
                    newValue.getWidth() * (percentMarginRight / 100),
                    newValue.getHeight() * (percentMarginBottom / 100),
                    newValue.getWidth() * (percentMarginLeft / 100));

            marginProperty.set(margin);
        });

        //----------- RESIZE TEXT IN SBUTTON -----------\\
        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> resizeText(newValue));
    }

    public void resizeText(Bounds bounds) {
        final double width = bounds.getWidth() - marginProperty.get().getLeft() - marginProperty.get().getRight();
        final double height = bounds.getHeight() - marginProperty.get().getTop() - marginProperty.get().getBottom();
        final double size = UIUtils.getPerkektSize(height, width, (longestString == null) ? text.getText() : longestString.get(), 8); // SI higherString est null, ça veut dire que notre sbutton n'est membre d'aucun STextGroup
        if (size != -1) {
            text.setFont(Font.font("Arial", size));
        }
    }

    private void setLongestString(Supplier<String> longestString) {
        this.longestString = longestString;
    }

    public Text getText() {
        return text;
    }

    @Override
    public String getUserAgentStylesheet() {
        return ServerManagerStage.class.getResource("css/control/"+this.getClass().getSimpleName().toLowerCase()+".css").toExternalForm();
    }

    @Override
    protected double computeMinWidth(double height) {
        return sp.minWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        return sp.minHeight(width);
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
        return sp.prefWidth(height) +
                snappedLeftInset() +
                snappedRightInset();
    }

    @Override
    protected double computePrefHeight(double width) {
        return sp.prefHeight(width) +
                snappedTopInset() +
                snappedBottomInset();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SLabelSkin(this);
    }

    /**
     * Cette classe regroupe plusieurs SLabel ou SButton afin
     * de calculer la taille de police la plus adapté
     */
    public static class STextGroup {

        private int maxLenght = 0;
        private String text;

        private final Supplier<String> longestString = () -> text;

        public STextGroup(SLabel... sLabels) {
            addAll(sLabels);
        }

        public void addAll(SLabel... sLabels) {
            for (SLabel sLabel : sLabels) {
                sLabel.setLongestString(longestString);
                int size = sLabel.getText().getText().length();
                if (size > maxLenght) {
                    maxLenght = size;
                    text = sLabel.getText().getText();
                }
            }
        }

    }

}
