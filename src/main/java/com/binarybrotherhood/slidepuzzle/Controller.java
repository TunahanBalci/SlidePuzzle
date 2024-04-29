package com.binarybrotherhood.slidepuzzle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Controller {


    public Controller(){
        System.out.println("Controller created");

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
    protected void showHoverText(){

        System.out.println("TEST");

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

        System.out.println("DEBUG: Previous Game Mode");

        if (SelectionMenu.gameIndex == 0){



        } else{
            SelectionMenu.gameIndex =- 1;
        }

    }

    @FXML
    protected void gameMode_NEXT(){

        System.out.println("DEBUG: Next Game Mode");


        if (SelectionMenu.gameIndex == 0){

        } else{
            SelectionMenu.gameIndex =- 1;
        }

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

    @FXML
    protected void showPlayText(){
        playImage_label1.setVisible(true);
        playImage_label1.setOpacity(1);

        playImage_label2.setVisible(true);
        playImage_label2.setOpacity(1);

    }

    @FXML
    protected void hidePlayText(){

        playImage_label1.setVisible(false);

        playImage_label2.setVisible(false);
    }

    @FXML
    protected void startGame(){

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
}