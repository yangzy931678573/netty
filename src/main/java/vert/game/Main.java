package vert.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Administrator on 2018/3/7.
 * Description:
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final Model model = new Model();
        final View view = new View();
        final MouseController controller = new MouseController(model);

        new AnimationTimer() {
            long time = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - time < 1000 / 60) {
                    try {
                        Thread.sleep(1000 / 60 - (System.currentTimeMillis() - time));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                model.update();
                view.update();
                time = System.currentTimeMillis();
            }
        }.start();
        view.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, controller);
    }
    /*
    public static void main(String[] args) {
        launch(args);
    }*/
}
