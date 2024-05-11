package com.binarybrotherhood.slidepuzzle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class SelectionMenuController {

    public SelectionMenuController(){
        System.out.println("SelectionMenuController created");

    }


    private static boolean isSelectingKey = false;
    private static boolean isKeyApproved = false;

    private static String keySession = "NONE";

    private static KeyCode keySession_VALUE = KeyCode.ESCAPE;

    public static void setSession(String type, KeyCode value){

        keySession = type;
        keySession_VALUE = value;
    }

    @FXML
    private Label puzzleName;
    @FXML
    private Label playImage_label1;
    @FXML
    private Label playImage_label2;
    @FXML
    private Label threeDots;
    @FXML
    private Label slideUpLabel;
    @FXML
    private Label slideDownLabel;
    @FXML
    private Label slideRightLabel;
    @FXML
    private Label slideLeftLabel;
    @FXML
    private Label fullscreenLabel;
    @FXML
    private Label assignKeybindHeader;
    @FXML
    private Label controlsHeader;


    @FXML
    private Button animationsButton;
    @FXML
    private Button slideUpButton;
    @FXML
    private Button slideDownButton;
    @FXML
    private Button slideRightButton;
    @FXML
    private Button slideLeftButton;
    @FXML
    private Button fullscreenButton;
    @FXML
    private Button selectImageButton;


    @FXML
    private ImageView gameImage;


    @FXML
    protected void showHoverText(){

        System.out.println("TEST");

    }

    @FXML
    protected void selectImage() {

        SelectionMenu.setIsSelectingImage(true);
    }

    @FXML
    protected void toggleAnimations(){

        String onOff = "ON";

        if (SelectionMenu.animations){
            onOff = "OFF";
            animationsButton.blendModeProperty().set(BlendMode.OVERLAY);
        } else {
            onOff = "ON";
            animationsButton.blendModeProperty().set(BlendMode.SRC_OVER);
        }

        animationsButton.setText("Animations: " + onOff);

        SelectionMenu.animations = !SelectionMenu.animations;

    }

    @FXML
    protected void gameMode_PREVIOUS(){



        switch(SelectionMenu.gameModeIndex){
            case 2:
                SelectionMenu.gameModeIndex = 1;

                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                gameImage.setImage(new Image("/icon_4x4.png"));
                puzzleName.setText("4X4 NUMBER PUZZLE");

                selectImageButton.setVisible(false);
                break;
            case 1:
                SelectionMenu.gameModeIndex = 0;

                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                gameImage.setImage( new Image("/icon_3x3.png"));
                puzzleName.setText("3X3 NUMBER PUZZLE");

                selectImageButton.setVisible(false);
                break;
            case 0:
                SelectionMenu.gameModeIndex = 2;

                playImage_label1.setTextFill(Color.web("#ffffff"));
                playImage_label2.setTextFill(Color.web("#ffffff"));
                gameImage.setImage( new Image("/icon_3x3_picture.png"));
                puzzleName.setText("3X3 PICTURE PUZZLE");

                selectImageButton.setVisible(true);
                break;
            default:
                System.out.println("ERROR: COULDNT SELECT GAME MODE (GAMEMODE_PREVIOUS)");;
        }

        System.out.println("DEBUG: Previous Game Mode");

    }

    @FXML
    protected void gameMode_NEXT(){



        switch(SelectionMenu.gameModeIndex){
            case 0:
                SelectionMenu.gameModeIndex = 1;

                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                gameImage.setImage(new Image("/icon_4x4.png"));
                puzzleName.setText("4X4 NUMBER PUZZLE");

                selectImageButton.setVisible(false);
                break;
            case 1:
                SelectionMenu.gameModeIndex = 2;

                playImage_label1.setTextFill(Color.web("#ffffff"));
                playImage_label2.setTextFill(Color.web("#ffffff"));
                gameImage.setImage( new Image("/icon_3x3_picture.png"));
                puzzleName.setText("3X3 PICTURE PUZZLE");

                selectImageButton.setVisible(true);
                break;
            case 2:
                SelectionMenu.gameModeIndex = 0;

                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                gameImage.setImage( new Image("/icon_3x3.png"));
                puzzleName.setText("3X3 NUMBER PUZZLE");

                selectImageButton.setVisible(false);
                break;
            default:
                System.out.println("ERROR: COULDNT SELECT GAME MODE (GAMEMODE_NEXT)");;
        }

        System.out.println("DEBUG: Next Game Mode");
    }

    @FXML
    protected void changeButton_UP(){

        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();

        isSelectingKey = !isSelectingKey;
        keySession = "UP";

        toggleElements();

        keySelect_animatedDots();

    }

    @FXML
    protected void changeButton_DOWN(){

        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();

        isSelectingKey = !isSelectingKey;
        keySession = "DOWN";


        toggleElements();

        keySelect_animatedDots();


    }

    @FXML
    protected void changeButton_RIGHT(){

        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();

        isSelectingKey = !isSelectingKey;
        keySession = "RIGHT";


        toggleElements();

        keySelect_animatedDots();

    }

    @FXML
    protected void changeButton_LEFT(){

        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();

        isSelectingKey = !isSelectingKey;
        keySession = "LEFT";

        toggleElements();

        keySelect_animatedDots();

    }

    @FXML
    protected void changeButton_FULLSCREEN(){

        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();

        isSelectingKey = !isSelectingKey;
        keySession = "FULLSCREEN";


        toggleElements();

        keySelect_animatedDots();

    }
    BoxBlur blurImage = new BoxBlur();
    BoxBlur removeBlur = new BoxBlur();

    @FXML
    protected void showPlayText(){
        playImage_label1.setVisible(true);

        blurImage.setWidth(8);
        blurImage.setHeight(8);
        blurImage.setIterations(8);



        gameImage.setEffect(blurImage);

        playImage_label1.setOpacity(1);

        playImage_label2.setVisible(true);
        playImage_label2.setOpacity(1);

    }

    @FXML
    protected void hidePlayText(){

        removeBlur.setWidth(0);
        removeBlur.setHeight(0);
        removeBlur.setIterations(0);

        gameImage.setEffect(removeBlur);

        playImage_label1.setVisible(false);

        playImage_label2.setVisible(false);
    }

    @FXML
    protected void startGame(){

        Stage old_stage = (Stage) fullscreenButton.getScene().getWindow();



        old_stage.close();

        switch(SelectionMenu.gameModeIndex) {
            case 0:


                gameImage.setImage(new Image("/icon_3x3.png"));
                puzzleName.setText("3X3 NUMBER PUZZLE");

                Platform.runLater(()->{

                    try {

                        SelectionMenu.setGridSize(3);
                        Application application = NumbersPuzzle.class.newInstance();

                        Stage newStage = new Stage();
                        application.start(newStage);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                });

                break;

            case 1:

                Platform.runLater(()->{

                    try {

                        SelectionMenu.setGridSize(4);
                        Application application = NumbersPuzzle.class.newInstance();

                        Stage newStage = new Stage();
                        application.start(newStage);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                });

                gameImage.setImage(new Image("/icon_4x4.png"));
                puzzleName.setText("4X4 NUMBER PUZZLE");
                break;

            case 2:

                gameImage.setImage(new Image("/icon_3x3_picture.png"));

                playImage_label1.setTextFill(Color.web("#ffffff"));
                puzzleName.setText("3X3 PICTURE PUZZLE");

                Platform.runLater(()->{

                    try {

                        SelectionMenu.setGridSize(3);
                        Application application = PicturePuzzle.class.newInstance();

                        Stage newStage = new Stage();
                        application.start(newStage);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                });

                break;
            default:
                System.out.println("ERROR: IN CONTROLLER INITIALIZE METHOD (IMAGEVIEW SWITCH-CASE)");
        }

        System.out.println("DEBUG: Start game");
    }

    Timeline keySelectTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

        if (isSelectingKey){

            if (threeDots.getText().equals(" ")){

                threeDots.setText(".");

            } else if (threeDots.getText().equals(".")){

                threeDots.setText("..");

            } else if (threeDots.getText().equals("..")){

                threeDots.setText("...");

            } else {

                threeDots.setText(" ");
            }
        } else {
            threeDots.setText(" ");
        }
    }));

    Timeline keyChangeListener = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

        if (isKeyApproved){

            System.out.println("true!");

            switch (keySession){

                case "UP":

                    System.out.println("CASE UP");

                    slideUpButton.setText(Settings.getKey_UP().toString());
                    System.out.println("WORKINGGGG");
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "DOWN":

                    System.out.println("CASE DOWN");

                    slideDownButton.setText(Settings.getKey_DOWN().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "RIGHT":

                    System.out.println("CASE RIGHT");

                    slideRightButton.setText(Settings.getKey_RIGHT().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "LEFT":

                    System.out.println("CASE LEFT");

                    slideLeftButton.setText(Settings.getKey_LEFT().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "FULLSCREEN":

                    System.out.println("CASE FULLSCREEN");

                    fullscreenButton.setText(Settings.getKey_FULLSCREEN().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;
                default:
                    System.out.println("WAITING...");
            }
        }
    }));

    @FXML
    protected void keySelect_animatedDots(){

        keySelectTimeline.setCycleCount(Animation.INDEFINITE);

        if (isSelectingKey){

            keySelectTimeline.play();


        } else {


            keySelectTimeline.stop();
        }
    }

    public static boolean getIsSelectingKey(){
        return isSelectingKey;
    }

    public static void setIsSelectingKey(boolean value){
        isSelectingKey = value;
    }

    public static boolean getIsKeyAproved(){
        return isKeyApproved;
    }

    public static void setIsKeyApproved(boolean value){
        isKeyApproved = value;
    }

    public static String getKeySession(){
        return keySession;
    }

    public static void setKeySession(String value){
        keySession = value;
    }

    protected void toggleElements(){

        if (isSelectingKey){

            threeDots.setVisible(true);
            keySelectTimeline.setCycleCount(Animation.INDEFINITE);
            keySelectTimeline.play();

            slideUpLabel.setVisible(false);
            slideDownLabel.setVisible(false);
            slideLeftLabel.setVisible(false);
            slideRightLabel.setVisible(false);
            fullscreenLabel.setVisible(false);

            slideUpButton.setVisible(false);
            slideDownButton.setVisible(false);
            slideRightButton.setVisible(false);
            slideLeftButton.setVisible(false);
            fullscreenButton.setVisible(false);
            animationsButton.setTranslateX(assignKeybindHeader.getTranslateX() - (controlsHeader.getWidth()) / 3);



            controlsHeader.setVisible(false);
            assignKeybindHeader.setVisible(true);

        } else{

            threeDots.setVisible(false);
            threeDots.setText(" ");
            keySelectTimeline.stop();

            slideUpLabel.setVisible(true);
            slideDownLabel.setVisible(true);
            slideLeftLabel.setVisible(true);
            slideRightLabel.setVisible(true);
            fullscreenLabel.setVisible(true);

            slideUpButton.setVisible(true);
            slideDownButton.setVisible(true);
            slideRightButton.setVisible(true);
            slideLeftButton.setVisible(true);
            fullscreenButton.setVisible(true);
            animationsButton.setTranslateX(controlsHeader.getTranslateX());

            controlsHeader.setVisible(true);
            assignKeybindHeader.setVisible(false);

        }

    }


    public void initialize(){
        //System.out.println("INITIALIZED"); //DEBUG
        playImage_label1.getStyleClass().add("stroke");
        playImage_label2.getStyleClass().add("stroke");

        switch(SelectionMenu.gameModeIndex) {
            case 0:


                gameImage.setImage(new Image("/icon_3x3.png"));
                puzzleName.setText("3X3 NUMBER PUZZLE");
                break;

            case 1:

                gameImage.setImage(new Image("/icon_4x4.png"));
                puzzleName.setText("4X4 NUMBER PUZZLE");
                break;

            case 2:

                gameImage.setImage(new Image("/icon_3x3_picture.png"));

                playImage_label1.setTextFill(Color.web("#ffffff"));

                selectImageButton.setVisible(true);
                puzzleName.setText("3X3 PICTURE PUZZLE");
                break;
            default:
                System.out.println("ERROR: IN CONTROLLER INITIALIZE METHOD (IMAGEVIEW SWITCH-CASE)");

        }
    }
}