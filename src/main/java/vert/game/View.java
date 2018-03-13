package vert.game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Administrator on 2018/3/7.
 * Description:
 */
public class View extends Stage {
    private Canvas canvas = new Canvas();

    public View() {
        Group root = new Group();
        root.getChildren().add(canvas);
        canvas.widthProperty().bind(widthProperty());//绑定canvas的长宽，保持与stage的长宽一致
        canvas.heightProperty().bind(heightProperty());
        this.setScene(new Scene(root));

        this.setWidth(900);//设置宽度为900
        this.setHeight(500);//设置高度为540
        this.show();//显示
    }

    public void update() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().drawImage(new Image("羽毛球.png"),11.0,11.0);//这里开始绘制图形
    }
}
