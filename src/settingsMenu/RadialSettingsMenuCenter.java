package settingsMenu;


import java.util.HashMap;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

public class RadialSettingsMenuCenter extends Group {

    private final Map<Object, Node> centerGraphics = new HashMap<Object, Node>();
    private FadeTransition hideTransition;
    private FadeTransition showTransition;
    private Group showTransitionGroup = new Group();
    private Group hideTransitionGroup = new Group();

    RadialSettingsMenuCenter() {
	getChildren().add(hideTransitionGroup);
	getChildren().add(showTransitionGroup);
	showTransition = FadeTransitionBuilder.create()
		.duration(Duration.millis(400)).node(showTransitionGroup)
		.fromValue(0.0).toValue(1.0).build();
	hideTransition = FadeTransitionBuilder.create()
		.duration(Duration.millis(400)).node(hideTransitionGroup)
		.fromValue(1.0).toValue(0.0).build();
    }

    void addCenterItem(final Object key, final Node centerGraphic) {
	centerGraphics.put(key, centerGraphic);
	centerGraphic.setTranslateX(-centerGraphic.getBoundsInLocal()
		.getWidth() / 2.0);
	centerGraphic.setTranslateY(-centerGraphic.getBoundsInLocal()
		.getHeight() / 2.0);
    }

    void displayCenter(final Object key) {
	final Node node = centerGraphics.get(key);
	showTransitionGroup.getChildren().setAll(node);
	showTransition.playFromStart();
    }

    void hideCenter(final Object key) {
	final Node node = centerGraphics.get(key);
	hideTransitionGroup.getChildren().setAll(node);
	hideTransition.playFromStart();
    }
}