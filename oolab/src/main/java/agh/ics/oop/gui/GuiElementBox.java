package agh.ics.oop.gui;

import agh.ics.oop.*;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    protected Image image;
    protected IMapElement mapElement;
    protected VBox box = new VBox(4);
    protected Label label;

    public GuiElementBox(IMapElement mapElement){
        this.mapElement = mapElement;

        try {
            this.image = new Image(new FileInputStream(mapElement.getImage()));
            ImageView imageView = new ImageView(this.image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);

            this.box.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.label = new Label(this.mapElement.toCaption());
        this.box.getChildren().add(this.label);
        this.box.setAlignment(Pos.CENTER);

    }

    public VBox getBox(){
        return this.box;
    }

}