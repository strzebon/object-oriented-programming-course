package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class App extends Application implements IRefreshObserver {

    private AbstractWorldMap map;
    private final GridPane gridPane = new GridPane();
    private Thread threadEngine;
    private RefreshSimulationEngine engine;


    @Override
    public void init() throws Exception {
        super.init();
//        Parameters parameters = getParameters();
//        String[] args = parameters.getRaw().toArray(new String[0]);

        try {
            this.map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            engine = new RefreshSimulationEngine(this.map, positions, 300);
            engine.addObserver(this);
            System.out.println(map);
        } catch(IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox();
        TextField textField = new TextField();
        Button button = new Button("Start");

        button.setOnAction((event) -> {
            String[] moves = textField.getCharacters().toString().split(" ");
            MoveDirection[] directions = new OptionsParser().parse(moves);
            engine.setDirections(directions);
            threadEngine = new Thread(engine);
            threadEngine.start();
        });

        vBox.getChildren().addAll(textField, button);
        vBox.getChildren().add(gridPane);

        draw();

        Scene scene = new Scene(vBox, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
//        Scene scene = new Scene(gridPane, (maxX - minX + 2) * width, (maxY - minY + 2) * height);
    }

    private void draw() {
        int width = 40;
        int height = 40;

        int minY = this.map.checkLowerLeft().y;
        int minX = this.map.checkLowerLeft().x;
        int maxY = this.map.checkUpperRight().y;
        int maxX = this.map.checkUpperRight().x;

        Label xyLabel = new Label("y\\x");
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        gridPane.getRowConstraints().add(new RowConstraints(height));
        gridPane.add(xyLabel, 0, 0, 1, 1);

        for (int i = minY; i < maxY+1; i++) {
            Label label = new Label(Integer.toString(i));
            gridPane.getRowConstraints().add(new RowConstraints(height));
            gridPane.add(label, 0, maxY - i +1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = minX; i < maxX+1; i++) {
            Label label = new Label(Integer.toString(i));
            gridPane.add(label, i-minX+1, 0, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int x = minX; x < maxX+1; x++) {
            for (int y = minY; y < maxY+1; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {
                    IMapElement object = (IMapElement) this.map.objectAt(position);
                    GuiElementBox element = new GuiElementBox(object);
                    VBox box = element.getBox();
                    GridPane.setHalignment(box, HPos.CENTER);
                    gridPane.add(box, position.x - minX + 1, maxY - position.y + 1, 1, 1);
                }
            }
        }
        gridPane.setGridLinesVisible(true);
    }
    @Override
    public void refresh() {
        Platform.runLater( () -> {
            this.gridPane.getChildren().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.getRowConstraints().clear();
            this.gridPane.setGridLinesVisible(false);
            draw();
        });
    }
}
