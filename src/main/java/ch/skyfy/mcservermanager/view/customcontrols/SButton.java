package ch.skyfy.mcservermanager.view.customcontrols;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

public class SButton extends SLabel {

    private static final PseudoClass PSEUDO_CLASS_ARMED = PseudoClass.getPseudoClass("armed");
    private static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");


    private final BooleanProperty selected = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, get());
        }

        @Override
        public Object getBean() {
            return SButton.this;
        }

        @Override
        public String getName() {
            return "exploding";
        }
    };

    public SButton(String str, Font font, Region region) {
        super(str, font, region);

        initFocus();
    }

    /**
     * Gère le focus
     * https://static.rainfocus.com/oracle/oow16/sess/1462484351438001p6a1/ppt/Custom%20Controls.pdf$
     * https://guigarage.com/2016/02/javafx-and-css-pseudo-classes/
     */
    private void initFocus() {
        setFocusTraversable(true);
        addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            pseudoClassStateChanged(PSEUDO_CLASS_ARMED, true);
            requestFocus();
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            pseudoClassStateChanged(PSEUDO_CLASS_ARMED, false);
        });
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }


}
