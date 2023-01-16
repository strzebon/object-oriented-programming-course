package agh.ics.oop.gui;

import agh.ics.oop.Board;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application{

    private final int rows = 4;
    private final int cols = 4;
    private final int size = 100;
    private final Board board = new Board(rows, cols);
    private final int[][] arr = board.getBoard();
    private final GridPane gridPane = new GridPane();
    private int[] position;
    private VBox generatedBox;
    private final BorderPane pane = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        pane.getChildren().add(gridPane);
        draw();
        Scene scene = new Scene(pane, cols*size, rows*size);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    if (board.up()){
                        position = board.generateSquare();
                        draw();
                        if (board.isGameOver()) gameOver();
                    }
                }
                case DOWN -> {
                    if (board.down()){
                        position = board.generateSquare();
                        draw();
                        if (board.isGameOver()) gameOver();
                    }
                }
                case RIGHT -> {
                    if (board.right()){
                        position = board.generateSquare();
                        draw();
                        if (board.isGameOver()) gameOver();
                    }
                }
                case LEFT -> {
                    if (board.left()){
                        position = board.generateSquare();
                        draw();
                        if (board.isGameOver()) gameOver();
                    }
                }
            }
        });
    }

    public void draw(){
        this.gridPane.getChildren().clear();
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.setGridLinesVisible(false);

        for (int i=0; i<rows; i++) gridPane.getRowConstraints().add(new RowConstraints(size));
        for (int j=0; j<cols; j++) gridPane.getColumnConstraints().add(new ColumnConstraints(size));

        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                GuiElementBox element = new GuiElementBox(arr[i][j]);
                VBox box = element.getBox();
                GridPane.setHalignment(box, HPos.CENTER);
                gridPane.add(box, j, i);
                if (position != null && i == position[0] && j == position[1]) generatedBox = box;
            }
        }

        gridPane.setGridLinesVisible(true);
        if (position != null){
            ScaleTransition scale = new ScaleTransition();
            scale.setFromX(0);
            scale.setFromY(0);
            scale.setToX(1);
            scale.setToY(1);
            scale.setDuration(Duration.millis(200));
            scale.setNode(generatedBox);
            scale.play();
        }

    }

    void gameOver(){
        VBox box = new VBox(0);
        Label label = new Label("GAME OVER");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        Color color = new Color(1,1,1, 0.5);
        Background background = new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
        box.setBackground(background);
        box.getChildren().add(label);
        box.setAlignment(Pos.CENTER);
        pane.setCenter(box);
    }
}
