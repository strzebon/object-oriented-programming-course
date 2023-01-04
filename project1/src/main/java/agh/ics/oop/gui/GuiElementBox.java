package agh.ics.oop.gui;
import agh.ics.oop.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class GuiElementBox {
    private ImageView imageView;

    public GuiElementBox(IMapElement element){
        try {
            Image image = new Image(new FileInputStream(element.getImagePath()));
            this.imageView = new ImageView(image);
            this.imageView.setFitHeight(20);
            this.imageView.setFitWidth(20);

        } catch (FileNotFoundException e){
            throw new RuntimeException("File was not found.");
        }
    }

    public ImageView getImageView(){
        return this.imageView;
    }
}
