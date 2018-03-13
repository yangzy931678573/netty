/*
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package javafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.random;

public class ColorfulCircles extends Application {

    @Override
    public void start(Stage primaryStage) {
        //根群
        Group root = new Group();
        //场景
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        primaryStage.setScene(scene);
        //圆所在群
        Group circles = new Group();
        //生成30个实心圆
        for (int i = 0; i < 30; i++) {
            //透明度0.01的实心圆
            Circle circle = new Circle(150, Color.web("white", 0.01));
            //圆的边
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.50));
            circle.setStrokeWidth(4);

            circles.getChildren().add(circle);
        }
        //彩色矩阵
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                //线性渐变,即从(0,1)到(1,0)填充颜色
                new LinearGradient(0f, 1f, 1f, 0f,
                        true,//按比例
                        CycleMethod.NO_CYCLE,//不循环
                        new Stop[]{
                                new Stop(0, Color.web("#f8bd55")),
                                new Stop(0.14, Color.web("#c0fe56")),
                                new Stop(0.28, Color.web("#5dfbc1")),
                                new Stop(0.43, Color.web("#64c2f8")),
                                new Stop(0.57, Color.web("#be4af7")),
                                new Stop(0.71, Color.web("#ed5fc2")),
                                new Stop(0.85, Color.web("#ef504c")),
                                new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());

        Group blendModeGroup =
                new Group(new Group(
                        //黑色背景矩阵
                        new Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK),
                        //圆所在群
                        circles),
                        //色彩矩阵所在群
                        colors);
        //设置蒙板混合
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);

        //盒式模糊(Box Blur)效果
        //模糊范围设置为10像素高*10像素宽,模糊迭代次数设置为3;
        // 它产生了的效果与高斯模糊类似.这种模糊生成技术在圆圈的边缘处产生了一种平滑的效果
        circles.setEffect(new BoxBlur(10, 10, 3));


        Timeline timeline = new Timeline();
        //遍历圆所在群,设置帧和时间线有关的函数
        for (Node circle : circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    //每个圆在第0秒出现在随机位置
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(circle.translateXProperty(), random() * 800),
                            new KeyValue(circle.translateYProperty(), random() * 600)),
                    //每个圆在第40秒出现在随机位置
                    new KeyFrame(new Duration(40000),
                            new KeyValue(circle.translateXProperty(), random() * 800),
                            new KeyValue(circle.translateYProperty(), random() * 600)));
        }
        timeline.play();//让时间线按指定帧函数播放40s

        primaryStage.show();
    }
}
