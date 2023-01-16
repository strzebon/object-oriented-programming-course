package agh.ics.oop.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GuiElementBox {
    protected VBox box = new VBox(0);
    protected Label label;

    public GuiElementBox(int number){
        if (number == 0) return;
        String backgroundColor = getBackgroundColor(number);
        String fontColor = getFontColor(number);
        int fontSize = getFontSize(number);
        label = new Label(Integer.toString(number));
        label.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        label.setTextFill(Color.valueOf(fontColor));
        box.setBackground(new Background(new BackgroundFill(Color.valueOf(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        box.getChildren().add(this.label);
        box.setAlignment(Pos.CENTER);
    }

    public VBox getBox(){
        return this.box;
    }

    String getBackgroundColor(int number){
        return switch (number){
            case 2 -> "eee4da";
            case 4 -> "ede0c8";
            case 8 -> "f2b179";
            case 16 -> "f59563";
            case 32 -> "f67c5f";
            case 64 -> "f65e3b";
            case 128 -> "edcf72";
            case 256 -> "edcc61";
            case 512 -> "edc850";
            case 1024 -> "edc53f";
            case 2048 -> "edc22e";
            default -> "3486eb";
        };
    }

    String getFontColor(int number){
        if (number <= 4) return "776e65";
        return "f9f6f2";
    }

    int getFontSize(int number){
        if (number < 100) return 50;
        if (number < 1000) return 40;
        return 30;
    }

}