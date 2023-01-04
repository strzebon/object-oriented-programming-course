package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class App extends Application implements IRefreshObserver{
    private TextField heightTextField;
    private TextField widthTextField;
    private TextField startedEnergyTextField;
    private TextField dailyEnergyConsumptionTextField;
    private TextField grassProfitTextField;
    private TextField dailyGrassSpawnTextField;
    private TextField startedAmountOfAnimalTextField;
    private TextField startedAmountOfGrassTextField;
    private TextField refreshTimeTextField;
    private TextField mapVariantTextField;
    private TextField mutationVariantTextField;
    private TextField reproductionCostTextField;
    private TextField fullStomachTextField;
    private TextField minMutationTextField;
    private TextField maxMutationTextField;
    private TextField genotypeLengthTextField;
    private final GridPane mapGridPane = new GridPane();
    private int gridHeight;
    private int gridWidth;
    private WorldMap mapSimulation;
    private VBox generalVBox;
    private VBox optionsVBox;
    private LineChart<Number, Number> mapLineChart;
    private XYChart.Series<Number, Number> mapDataSeries1 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> mapDataSeries2 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> mapDataSeries3 = new XYChart.Series<>();
    private XYChart.Series<Number, int[]> mapDataSeries4 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> mapDataSeries5 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> mapDataSeries6 = new XYChart.Series<>();
    private Thread threadEngine;
    private VBox plotAndGenotypeVBox;
    private VBox sumarizeTextPlotVBox;
    private HBox sumarizePlotHBox;
    private HBox genotypeHBox;
    private boolean mapWaitingThread = false;
    private SimulationEngine engine;
    private boolean flag = false;

    @Override
    public void start(Stage primaryStage) {
        try {
        primaryStage.setTitle("Evolution Simulator");
        HBox welcomeText = createHeadLineText("Welcome in Darwin evolution.", 24);

        // Map settings
        HBox mapProperties = createHeadLineText("Map settings, type your parameters", 16);
        Text heightText = createText("Map height:");
        this.heightTextField = new TextField("10");
        HBox heightBox = createHBox(heightText, this.heightTextField);

        Text widthText = createText("Map width:");
        this.widthTextField = new TextField("10");
        HBox widthBox = createHBox(widthText, this.widthTextField);

        // Energy settings
        HBox energySettings = createHeadLineText("Energy settings, type your parameters", 16);
        Text startedEnergyText = createText("Animal starting energy:");
        this.startedEnergyTextField = new TextField("30");
        HBox startedEnergyBox = createHBox(startedEnergyText, this.startedEnergyTextField);

        Text fullStomachText = createText("Minimum energy required to copulation:");
        this.fullStomachTextField = new TextField("50");
        HBox fullStomachBox = createHBox(fullStomachText, this.fullStomachTextField);

        Text dailyEnergyUsageText = createText("Daily energy usage:");
        this.dailyEnergyConsumptionTextField = new TextField("1");
        HBox dailyEnergyConsumption = createHBox(dailyEnergyUsageText, this.dailyEnergyConsumptionTextField);

        Text grassProfitText = createText("Grass profit:");
        this.grassProfitTextField = new TextField("10");
        HBox grassProfitBox = createHBox(grassProfitText, this.grassProfitTextField);

        // Spawning settings
        HBox spawningSettings = createHeadLineText("Spawning settings, type your parameters", 16);
        Text startedAmountOfAnimalsText = createText("Animals spawned at the start of simulation:");
        this.startedAmountOfAnimalTextField = new TextField("10");
        HBox startedAmountOfAnimalsBox = createHBox(startedAmountOfAnimalsText, this.startedAmountOfAnimalTextField);

        Text startedAmoutOfGrassText = createText("Grass spawned at the start of simulation:");
        this.startedAmountOfGrassTextField = new TextField("10");
        HBox startedAmountOfGrassBox = createHBox(startedAmoutOfGrassText, this.startedAmountOfGrassTextField);

        Text reproductionCostText = createText("Reproduction cost of animals:");
        this.reproductionCostTextField = new TextField("30");
        HBox reproductionCostBox = createHBox(reproductionCostText, this.reproductionCostTextField);

        Text dailyGrassSpawnText = createText("Daily spawning grass:");
        this.dailyGrassSpawnTextField = new TextField("1");
        HBox dailyGrassSpawnBox = createHBox(dailyGrassSpawnText, this.dailyGrassSpawnTextField);

        // World variant
        HBox mapVariant = createHeadLineText("World variant - type 0 if you want 'globe' or 1 if you want 'infernal portal'", 16);
        Text mapVariantText = createText("World variant:");
        this.mapVariantTextField = new TextField("0");
        HBox mapVariantBox = createHBox(mapVariantText, this.mapVariantTextField);

        // Mutation variant
        HBox mutationVariant = createHeadLineText("Mutation variant - type 0 if you want 'full randomness' or 1 if you want 'slight correction'", 16);
        Text mutationVariantText = createText("Mutation variant:");
        this.mutationVariantTextField = new TextField("0");
        HBox mutationVariantBox = createHBox(mutationVariantText, this.mutationVariantTextField);

        // Refresh settings
        HBox refreshTime = createHeadLineText("Refresh time for animals and grass", 16);
        Text refreshTimeText = createText("Refresh time in ms:");
        this.refreshTimeTextField = new TextField("500");
        HBox refreshTimeBox = createHBox(refreshTimeText, this.refreshTimeTextField);

        // Minimum, maximum and length of genes to mutation
        HBox changeMutation = createHeadLineText("Minimum and maximum genes to mutation and length of genotype", 16);
        Text minMutationText = createText("Minimum mutation:");
        this.minMutationTextField = new TextField("0");
        HBox minMutationBox = createHBox(minMutationText, this.minMutationTextField);

        Text maxMutationText = createText("Maximum mutation:");
        this.maxMutationTextField = new TextField("1");
        HBox maxMutationBox = createHBox(maxMutationText, this.maxMutationTextField);

        Text genotypeLength = createText("Length of genotype");
        this.genotypeLengthTextField = new TextField("4");
        HBox genotypeLengthBox = createHBox(genotypeLength, this.genotypeLengthTextField);

        // Start Button
        HBox startButton = createStartButton();
        this.optionsVBox = new VBox(welcomeText, mapProperties, heightBox, widthBox, energySettings,
                startedEnergyBox, fullStomachBox, dailyEnergyConsumption, grassProfitBox, startedAmountOfAnimalsBox,
                startedAmountOfGrassBox, reproductionCostBox, dailyGrassSpawnBox, mapVariant,
                mapVariantBox, mutationVariant, mutationVariantBox, refreshTime, refreshTimeBox,
                changeMutation, minMutationBox, maxMutationBox, genotypeLengthBox, startButton);
        this.optionsVBox.setSpacing(20);
        this.optionsVBox.setAlignment(Pos.CENTER);

        this.generalVBox = new VBox(this.optionsVBox);
        this.generalVBox.setSpacing(50);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinX());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        Scene scene = new Scene(this.generalVBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void draw(WorldMap map, boolean stop){
        this.mapGridPane.setGridLinesVisible(false);
        this.mapGridPane.getColumnConstraints().clear();
        this.mapGridPane.getRowConstraints().clear();
        this.mapGridPane.getChildren().clear();
        this.mapGridPane.setGridLinesVisible(true);
        createGrid(map);
        if(!stop){
            placeAnimal(map);
        }
        updateLineChart(map);
        updateTextPlot();
        updateDominantGenotype();
    }
    @Override
    public void refresh(){
        Platform.runLater(() -> {
//            this.app.refresh(this.map, false);
            draw(mapSimulation, false);
        });
    }

    private HBox createHeadLineText(String text, int size){
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.BOLD, size));
        HBox hBox = new HBox(newText);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    private Text createText(String text){
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        return newText;
    }
    private HBox createHBox(Text text, TextField textField){
        HBox hBox = new HBox(text, textField);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        return hBox;
    }

    private HBox createStartButton() {
        Button startButton = new Button("Start");
        startButton.setOnAction((action) -> {
            this.mapSimulation = new WorldMap(
                    Integer.parseInt(this.widthTextField.getText()),
                    Integer.parseInt(this.heightTextField.getText()),
                    Integer.parseInt(this.mapVariantTextField.getText()),
                    Integer.parseInt(this.grassProfitTextField.getText()),
                    Integer.parseInt(this.fullStomachTextField.getText()),
                    Integer.parseInt(this.reproductionCostTextField.getText()),
                    Integer.parseInt(this.minMutationTextField.getText()),
                    Integer.parseInt(this.maxMutationTextField.getText()),
                    Integer.parseInt(this.mutationVariantTextField.getText()));

            this.engine = new SimulationEngine(
                    this.mapSimulation,
                    this,
                    Integer.parseInt(this.refreshTimeTextField.getText()),
                    Integer.parseInt(this.startedAmountOfGrassTextField.getText()),
                    Integer.parseInt(this.dailyGrassSpawnTextField.getText()),
                    Integer.parseInt(this.startedAmountOfAnimalTextField.getText()),
                    Integer.parseInt(this.startedEnergyTextField.getText()),
                    Integer.parseInt(this.genotypeLengthTextField.getText()));
            this.engine.addObserver((IRefreshObserver) this);

            this.generalVBox.getChildren().remove(this.optionsVBox);

            // Options in text field
//            this.sumarizeTextPlotVBox = createTextPlot(this.mapSimulation);

            // Plot
            VBox mapSimulationVBox = createPlot(this.mapSimulation);
//            mapSimulationVBox.getChildren().add(this.sumarizeTextPlotVBox);
            HBox plotHBox = new HBox(mapSimulationVBox);
            plotHBox.setSpacing(100); // to check if needed
            plotHBox.setAlignment(Pos.TOP_RIGHT);

            // Dominant genotype
            VBox mapSimulationGenotypeVBox = createDominantGenotype(this.mapSimulation);
            this.genotypeHBox = new HBox(mapSimulationGenotypeVBox);
            this.genotypeHBox.setSpacing(30); // to check if neeeded
            this.genotypeHBox.setAlignment(Pos.CENTER);
            this.plotAndGenotypeVBox = new VBox(plotHBox, this.genotypeHBox);

            // Text from plot
            VBox textFromPlotVBox = createTextPlot(this.mapSimulation);
            this.sumarizePlotHBox = new HBox(textFromPlotVBox);
            this.sumarizePlotHBox.setSpacing(30); // to check if neeeded
            this.sumarizePlotHBox.setAlignment(Pos.TOP_RIGHT);
            this.sumarizeTextPlotVBox = new VBox(this.sumarizePlotHBox);

            // Options
            VBox optionButtons = createOptionButtons();
            HBox gridPaneHBox = new HBox(this.mapGridPane, optionButtons, sumarizeTextPlotVBox, mapSimulationVBox, plotAndGenotypeVBox);
            gridPaneHBox.setSpacing(10);
            gridPaneHBox.setAlignment(Pos.TOP_LEFT);


            this.generalVBox.getChildren().addAll(gridPaneHBox, this.plotAndGenotypeVBox);

            this.generalVBox.setSpacing(10);
            this.threadEngine = new Thread(engine);
            this.threadEngine.start();
        });
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox hBox = new HBox(startButton);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    // Tworzenie siatki z kolorami jako tło dla stepów i dżungli
    private void createGrid(WorldMap map){
        this.mapGridPane.setGridLinesVisible(true);
        int mapRight = map.getUpperRight().x;
        int mapUpper = map.getUpperRight().y;
        Vector2d jungleLowerLeft = map.getJungleLowerLeft();
        Vector2d jungleUpperRight = map.getJungleUpperRight();
        this.gridWidth = 800 / Integer.parseInt(this.widthTextField.getText());
        this.gridHeight = 800 / Integer.parseInt(this.heightTextField.getText());

        for (int i = 0; i <= mapRight; i++) {
            this.mapGridPane.getColumnConstraints().add(new ColumnConstraints(this.gridWidth));
        }

        for (int i = 0; i <= mapRight; i++) {
            this.mapGridPane.getRowConstraints().add(new RowConstraints(this.gridHeight));
        }

        VBox newVBox;
        for(int i = 0; i <= mapRight; i++){
            for(int j = 0; j <= mapUpper; j++){
                Vector2d newPosition = new Vector2d(i, j);
                newVBox = new VBox();
                if (newPosition.follows(jungleLowerLeft) && newPosition.precedes(jungleUpperRight))
                    newVBox.setStyle("-fx-background-color: #0C825C");
                else
                    newVBox.setStyle("-fx-background-color: #825735");
                this.mapGridPane.add(newVBox, i, j, 1, 1);
            }
        }
    }

    private void placeAnimal(WorldMap map){
        ArrayList<Vector2d> animalsAndGrasses = getAnimalsAndGrasses(map);
//        for (int i = 0; i < map.getUpperRight().x; i++){
//            for (int j = 0; j < map.getUpperRight().y; i++){
//                Vector2d newPosition = new Vector2d(i, j);
//                if ((IMapElement) map.objectAt(newPosition) != null){
//                    VBox vBox;
//                    GuiElementBox guiElementBox = new GuiElementBox((IMapElement) map.objectAt(newPosition));
//                    ImageView imageView = guiElementBox.getImageView();
//                    imageView.setFitWidth(this.gridWidth);
//                    imageView.setFitHeight(this.gridHeight);
//                    vBox = new VBox(imageView);
//                    if (map.objectAt(newPosition) instanceof Animal){
//                        vBox.setOnMouseClicked((action) -> createWindowWithAnimalInfo(map, (Animal) map.objectAt(newPosition)));
//                    }
//                    this.mapGridPane.add(vBox, newPosition.x, newPosition.y, 1, 1);
//                }
//            }
//        }



        for (Vector2d position : animalsAndGrasses){
            if (map.objectAt(position) != null) {
                VBox vBox = new VBox();
                GuiElementBox guiElementBox = new GuiElementBox((IMapElement) map.objectAt(position));
                ImageView imageView = guiElementBox.getImageView();
                imageView.setFitWidth(this.gridWidth);
                imageView.setFitHeight(this.gridHeight);
                vBox = new VBox(imageView);


//                IMapElement objectToCheck;
//                objectToCheck = (IMapElement) map.objectAt(position);
//                if (objectToCheck instanceof Animal)
//                    vBox.setStyle(((Animal) objectToCheck).getColorImage());
//                if (map.objectAt(position) instanceof Grass)
//                    vBox.setStyle("-fx-background-color: #00FF66");
//

                if (map.objectAt(position) instanceof Animal) {
                    vBox.setOnMouseClicked((action) -> createWindowWithAnimalInfo(map, (Animal) map.objectAt(position)));
                }
                this.mapGridPane.add(vBox, position.x, position.y, 1, 1);
            }
        }
    }

    private ArrayList<Vector2d> getAnimalsAndGrasses(WorldMap map){
        LinkedList<Animal> animals = map.getAnimals();
        LinkedList<Grass> grasses = map.getGrasses();
        ArrayList<Vector2d> animalsAndGrasses = new ArrayList<>();
//        Vector2d[] animalsAndGrasses = new Vector2d[animals.size() + map.getNumberOfGrasses()];
        int i = 0;
        for (Animal animal : animals){
            animalsAndGrasses.add(animal.getPosition());
        }
        for (Grass grass : grasses){
            animalsAndGrasses.add(grass.getPosition());
        }

        return animalsAndGrasses;
    }

    private void createWindowWithAnimalInfo(WorldMap map, Animal animal){
        Stage window = new Stage();
        VBox animalInfoVBox = new VBox();
        window.setTitle("Info of animal on the map.");
        createAnimalInfoVBox(map, animalInfoVBox, animal, window);
        StackPane stackPane = new StackPane(animalInfoVBox);
        Scene newScene = new Scene(stackPane, 600,600);
        window.setScene(newScene);
        if (this.mapWaitingThread)
            window.show();
    }

    private void createAnimalInfoVBox(WorldMap map, VBox animalInfoVBox, Animal animal, Stage window){
        animalInfoVBox.getChildren().clear();

        // Animal image
        Label imageViewInfoLabel = new Label("Animal image");
        imageViewInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GuiElementBox guiElementBox = new GuiElementBox(animal);
        ImageView imageView = guiElementBox.getImageView();
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        // Animal genotype
        Label genotypeInfoLabel = new Label("Animal genotype");
        genotypeInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int[] animalGenes = animal.getGenotype();
        Label genotypeLabel = new Label(Arrays.toString(animalGenes));

        // Animal active gene
        Label activeGeneInfoLabel = new Label("Active gene of animal");
        activeGeneInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int activeGene = animal.getActiveGene();
        Label activeGeneLabel = new Label(String.valueOf(activeGene));

        // Animal current energy
        Label currentEnergyInfoLabel = new Label("Current amount of energy");
        currentEnergyInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int amountOfEnergy = animal.getEnergy();
        Label currentEnergyLabel = new Label(String.valueOf(amountOfEnergy));

        // Amount of grass eaten by animal
        Label eatenGrassInfoLabel = new Label("Current amount of eaten grass");
        eatenGrassInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int eatenGrass = animal.getEatenGrass();
        Label eatenGrassLabel = new Label(String.valueOf(eatenGrass));

        // Amount of children having by animal
        Label amountOfChildrenInfoLabel = new Label("Amount of children having by animal");
        amountOfChildrenInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int amountOfChildren = animal.getChildren();
        Label amountOfChildrenLabel = new Label(String.valueOf(amountOfChildren));

        // Animal amount of energy
        Label animalEnergyInfoLabel = new Label("Animal energy");
        animalEnergyInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label animalEnergyLabel;
        int animalEnergy;
        if (animal.isDead()) animalEnergyLabel = new Label("Animal is dead so energy equals 0");
        else {
            animalEnergy = animal.getEnergy();
            animalEnergyLabel = new Label(String.valueOf(animalEnergy));
        }

        // Animal death
        Label animalDeathInfoLabel = new Label("Animal death age");
        animalDeathInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label animalDeathLabel;
        int animalDeathAge;
        if (animal.isDead()){
            animalDeathAge = animal.getAge();
            animalDeathLabel = new Label(String.valueOf(animalDeathAge));
        }
        else
            animalDeathLabel = new Label("Animal is still alive.");

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction((action) -> {
            window.close();
        });
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        animalInfoVBox.getChildren().addAll(imageViewInfoLabel, imageView, genotypeInfoLabel, genotypeLabel,
                activeGeneInfoLabel, activeGeneLabel, currentEnergyInfoLabel, currentEnergyLabel,
                eatenGrassInfoLabel, eatenGrassLabel, amountOfChildrenInfoLabel, amountOfChildrenLabel,
                animalEnergyInfoLabel, animalEnergyLabel, animalDeathInfoLabel, animalDeathLabel, closeButton);
        animalInfoVBox.setSpacing(5);
        animalInfoVBox.setAlignment(Pos.TOP_CENTER);
    }

    private VBox createPlot(WorldMap map){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        this.mapLineChart = new LineChart<>(xAxis, yAxis);
        this.mapDataSeries1.setName("Number of all animals");
        this.mapDataSeries2.setName("Number of all grasses");
        this.mapDataSeries3.setName("Number of empty fields");
//        this.mapDataSeries4.setName("Most popular genotype");
        this.mapDataSeries5.setName("Average energy if living animals");
        this.mapDataSeries6.setName("Average lifespan of dead animals");
        updateLineChart(map);
        return new VBox(this.mapLineChart);
    }

    private VBox createTextPlot(WorldMap map){
        VBox sumarizeVBox;

        Text widthText1 = createText("Number of all animals: ");
        Text answerText1 = createText(String.valueOf(map.getNumberOfAnimals()));
        HBox widthBox1 = new HBox(widthText1, answerText1);

        Text widthText2 = createText("Number of all grasses: ");
        Text answerText2 = createText(String.valueOf(map.getNumberOfGrasses()));
        HBox widthBox2 = new HBox(widthText2, answerText2);

        Text widthText3 = createText("Number of empty fields: ");
        Text answerText3 = createText(String.valueOf(map.getNumberOfUncoveredSquares()));
        HBox widthBox3 = new HBox(widthText3, answerText3);

        Text widthText4 = createText("Average energy if living animals: ");
        Text answerText4 = createText(String.valueOf(map.getAverageEnergyOfAliveAnimals()));
        HBox widthBox4 = new HBox(widthText4, answerText4);

        Text widthText5 = createText("Average lifespan of dead animals: ");
        Text answerText5 = createText(String.valueOf(map.getAverageLifetimeOfDeadAnimals()));
        HBox widthBox5 = new HBox(widthText5, answerText5);

        sumarizeVBox = new VBox(widthBox1, widthBox2, widthBox3, widthBox4, widthBox5);
        sumarizeVBox.setSpacing(10);
        return sumarizeVBox;
    }

    private void updateLineChart(WorldMap map){
        this.mapDataSeries1.getData().add(new XYChart.Data<>(map.getDay(), map.getNumberOfAnimals()));
        this.mapDataSeries2.getData().add(new XYChart.Data<>(map.getDay(), map.getNumberOfGrasses()));
        this.mapDataSeries3.getData().add(new XYChart.Data<>(map.getDay(), map.getNumberOfUncoveredSquares()));
//        this.mapDataSeries4.getData().add(new XYChart.Data<>(map.getDay(), map.getMostPopularGenotype()));
        this.mapDataSeries5.getData().add(new XYChart.Data<>(map.getDay(), map.getAverageEnergyOfAliveAnimals()));
        this.mapDataSeries6.getData().add(new XYChart.Data<>(map.getDay(), map.getAverageLifetimeOfDeadAnimals()));
        if (this.flag) {
            try {
                this.mapLineChart.getData().add(this.mapDataSeries1);
                this.mapLineChart.getData().add(this.mapDataSeries2);
                this.mapLineChart.getData().add(this.mapDataSeries3);
//            this.mapLineChart.getData().add(this.mapDataSeries4);
                this.mapLineChart.getData().add(this.mapDataSeries5);
                this.mapLineChart.getData().add(this.mapDataSeries6);
            } catch (IllegalArgumentException e) {
                return;
            }
        }

    }

    private VBox createOptionButtons(){
        String mapFilePath = "src/main/resources/map.csv";

        // Stop button
        Button stopButtonMap = new Button("Stop map");
        stopButtonMap.setOnAction((action) -> {
            if (!this.mapWaitingThread){
                this.engine.stop();
                this.mapWaitingThread = true;
            }
        });
        stopButtonMap.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        stopButtonMap.setMaxWidth(Double.MAX_VALUE);

        // Resume button
        Button resumeButtonMap = new Button("Resume map");
        resumeButtonMap.setOnAction((action) -> {
            if (this.mapWaitingThread) {
                this.engine.start();
                this.mapWaitingThread = false;
            }
        });
        resumeButtonMap.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        resumeButtonMap.setMaxWidth(Double.MAX_VALUE);

        VBox mapOptions = new VBox(stopButtonMap, resumeButtonMap);
        mapOptions.setSpacing(15);

        // Exit button
        Button exitButtonMap = new Button("Exit");
        exitButtonMap.setOnAction((action) -> {
            try {
                saveFileToCSV(this.mapSimulation, mapFilePath, this.mapSimulation.getDay());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Platform.exit();
            System.exit(0);
        });
        exitButtonMap.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        exitButtonMap.setMaxWidth(Double.MAX_VALUE);

        VBox vBox = new VBox(mapOptions, exitButtonMap);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        return vBox;
    }

    private VBox createDominantGenotype(WorldMap map){
        int[] dominantGenotype = map.getMostPopularGenotype();
        String genotype = Arrays.toString(dominantGenotype);
        Text text = new Text("Map dominant genotype");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label genotypeLabel = new Label(genotype);
        genotypeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        VBox mapDominantGenotypeBox = new VBox(text, genotypeLabel);
        mapDominantGenotypeBox.setAlignment(Pos.CENTER);
        return mapDominantGenotypeBox;
    }

    private void updateDominantGenotype(){
        this.plotAndGenotypeVBox.getChildren().remove(this.genotypeHBox);
        VBox mapGenotypeVBox = createDominantGenotype(this.mapSimulation);
        this.genotypeHBox = new HBox(mapGenotypeVBox);
        this.genotypeHBox.setSpacing(20); // check if needed
        this.genotypeHBox.setAlignment(Pos.CENTER);
        this.plotAndGenotypeVBox.getChildren().add(this.genotypeHBox);
    }
    private void updateTextPlot(){
        this.sumarizeTextPlotVBox.getChildren().remove(this.sumarizePlotHBox);
        VBox textFromPlot = createTextPlot(this.mapSimulation);
        this.sumarizePlotHBox = new HBox(textFromPlot);
        this.sumarizePlotHBox.setSpacing(20);
        this.sumarizePlotHBox.setAlignment(Pos.CENTER);
        this.sumarizeTextPlotVBox.getChildren().add(this.sumarizePlotHBox);
    }
    private void saveFileToCSV(WorldMap map, String filePath, int day) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filePath)){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Date | ");
            stringBuilder.append("Number of all animals | ");
            stringBuilder.append("Number of all grasses | ");
            stringBuilder.append("Number of empty fields | ");
            stringBuilder.append("Average energy of living animals | ");
            stringBuilder.append("Average lifespan of dead animals");
            stringBuilder.append("\n");

            XYChart.Data animals;
            XYChart.Data grasses;
            XYChart.Data emptyFields;
            XYChart.Data averageEnergy;
            XYChart.Data averageLifespan;

            for (int i = 0; i < day; i++) {
                stringBuilder.append(i + 1 + " | ");
                animals = ( XYChart.Data) this.mapDataSeries1.getData().get(i);
                stringBuilder.append(animals.getYValue() + " | ");
                grasses = (XYChart.Data) this.mapDataSeries2.getData().get(i);
                stringBuilder.append(grasses.getYValue() + " | ");
                emptyFields = (XYChart.Data) this.mapDataSeries3.getData().get(i);
                stringBuilder.append(emptyFields.getYValue() + " | ");
                averageEnergy = (XYChart.Data) this.mapDataSeries5.getData().get(i);
                stringBuilder.append(averageEnergy.getYValue() + " | ");
                averageLifespan = (XYChart.Data) this.mapDataSeries6.getData().get(i);
                stringBuilder.append(averageLifespan.getYValue());
                stringBuilder.append("\n");
            }

            writer.write(stringBuilder.toString());
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}
