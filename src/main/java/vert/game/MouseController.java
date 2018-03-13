package vert.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Administrator on 2018/3/7.
 * Description:
 */
public class MouseController implements EventHandler<MouseEvent> {
    Model model;

    public MouseController(Model model) {
        this.model = model;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

        }
    }
}
