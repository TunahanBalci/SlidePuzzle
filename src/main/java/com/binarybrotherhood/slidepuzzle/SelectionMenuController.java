package com.binarybrotherhood.slidepuzzle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SelectionMenuController {

    private static boolean anim = true;
    private static boolean isSelectingKey = false;
    private static boolean isKeyApproved = false;
    private static String keySession = "NONE";
    private static KeyCode keySession_VALUE = KeyCode.ESCAPE;

    public static void setSession(String type, KeyCode value) {
        keySession = type;
        keySession_VALUE = value;
    }

    @FXML
    private Label puzzleName, playImage_label1, playImage_label2, threeDots, slideUpLabel, slideDownLabel,
            slideRightLabel, slideLeftLabel, fullscreenLabel, assignKeybindHeader, controlsHeader;

    @FXML
    private Button animationsButton, slideUpButton, slideDownButton, slideRightButton, slideLeftButton,
            fullscreenButton, selectImageButton, button_help, button_leaderboard;

    @FXML
    private TextField playerName;

    @FXML
    private ImageView gameImage;

    @FXML
    protected void selectImage() {
        SelectionMenu.setIsSelectingImage(true);
    }

    @FXML
    protected void toggleAnimations() {
        if (anim) {
            animationsButton.setText("ANIMATIONS: OFF");
            animationsButton.blendModeProperty().set(BlendMode.OVERLAY);
            anim = false;
        } else {
            animationsButton.setText("ANIMATIONS: ON");
            animationsButton.blendModeProperty().set(BlendMode.SRC_OVER);
            anim = true;
        }
    }

    @FXML
    protected void gameMode_PREVIOUS() {
        switch (SelectionMenu.gameModeIndex) {
            case 2:
                SelectionMenu.gameModeIndex = 1;
                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                try {
                    gameImage.setImage(new Image("/icon_4x4.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 4x4 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("4X4 NUMBER PUZZLE");
                selectImageButton.setVisible(false);
                break;
            case 1:
                SelectionMenu.gameModeIndex = 0;
                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                try {
                    gameImage.setImage(new Image("/icon_3x3.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 NUMBER PUZZLE");
                selectImageButton.setVisible(false);
                break;
            case 0:
                SelectionMenu.gameModeIndex = 2;
                playImage_label1.setTextFill(Color.web("#ffffff"));
                playImage_label2.setTextFill(Color.web("#ffffff"));
                try {
                    gameImage.setImage(new Image("/icon_3x3_picture.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 picture puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 PICTURE PUZZLE");
                selectImageButton.setVisible(true);
                break;
            default:
                System.out.println("ERROR: COULDNT SELECT GAME MODE (GAMEMODE_PREVIOUS)");
        }
        System.out.println("DEBUG: Previous Game Mode");
    }

    @FXML
    protected void gameMode_NEXT() {
        switch (SelectionMenu.gameModeIndex) {
            case 0:
                SelectionMenu.gameModeIndex = 1;
                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                try {
                    gameImage.setImage(new Image("/icon_4x4.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 4x4 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("4X4 NUMBER PUZZLE");
                selectImageButton.setVisible(false);
                break;
            case 1:
                SelectionMenu.gameModeIndex = 2;
                playImage_label1.setTextFill(Color.web("#ffffff"));
                playImage_label2.setTextFill(Color.web("#ffffff"));
                try {
                    gameImage.setImage(new Image("/icon_3x3_picture.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 picture puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 PICTURE PUZZLE");
                selectImageButton.setVisible(true);
                break;
            case 2:
                SelectionMenu.gameModeIndex = 0;
                playImage_label1.setTextFill(Color.web("#213443"));
                playImage_label2.setTextFill(Color.web("#213443"));
                try {
                    gameImage.setImage(new Image("/icon_3x3.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 NUMBER PUZZLE");
                selectImageButton.setVisible(false);
                break;
            default:
                System.out.println("ERROR: COULDNT SELECT GAME MODE (GAMEMODE_NEXT)");
        }
        System.out.println("DEBUG: Next Game Mode");
    }

    @FXML
    protected void changeButton_UP() {
        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();
        isSelectingKey = !isSelectingKey;
        keySession = "UP";
        toggleElements();
        keySelect_animatedDots();
    }

    @FXML
    protected void changeButton_DOWN() {
        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();
        isSelectingKey = !isSelectingKey;
        keySession = "DOWN";
        toggleElements();
        keySelect_animatedDots();
    }

    @FXML
    protected void changeButton_RIGHT() {
        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();
        isSelectingKey = !isSelectingKey;
        keySession = "RIGHT";
        toggleElements();
        keySelect_animatedDots();
    }

    @FXML
    protected void changeButton_LEFT() {
        keyChangeListener.setCycleCount(Animation.INDEFINITE);
        keyChangeListener.stop();
        keyChangeListener.play();
        isSelectingKey = !isSelectingKey;
        keySession = "LEFT";
        toggleElements();
        keySelect_animatedDots();
    }

    @FXML
    protected void changeButton_FULLSCREEN() {
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
    protected void showPlayText() {
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
    protected void hidePlayText() {
        removeBlur.setWidth(0);
        removeBlur.setHeight(0);
        removeBlur.setIterations(0);
        gameImage.setEffect(removeBlur);
        playImage_label1.setVisible(false);
        playImage_label2.setVisible(false);
    }

    @FXML
    protected void startGame() {
        SelectionMenu.animations = anim;
        Stage old_stage = (Stage) fullscreenButton.getScene().getWindow();
        old_stage.close();

        switch (SelectionMenu.gameModeIndex) {
            case 0:
                try {
                    gameImage.setImage(new Image("/icon_3x3.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 4x4 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 NUMBER PUZZLE");

                Platform.runLater(() -> {
                    try {
                        Utilities.setUsername(playerName.getText());
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
                Platform.runLater(() -> {
                    try {
                        Utilities.setUsername(playerName.getText());
                        SelectionMenu.setGridSize(4);
                        Application application = NumbersPuzzle.class.newInstance();
                        Stage newStage = new Stage();
                        application.start(newStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                try {
                    gameImage.setImage(new Image("/icon_4x4.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 4x4 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("4X4 NUMBER PUZZLE");
                break;

            case 2:
                try {
                    gameImage.setImage(new Image("/icon_3x3_picture.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 picture puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                playImage_label1.setTextFill(Color.web("#ffffff"));
                puzzleName.setText("3X3 PICTURE PUZZLE");

                Platform.runLater(() -> {
                    try {
                        Utilities.setUsername(playerName.getText());
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
        if (isSelectingKey) {
            if (threeDots.getText().equals(" ")) {
                threeDots.setText(".");
            } else if (threeDots.getText().equals(".")) {
                threeDots.setText("..");
            } else if (threeDots.getText().equals("..")) {
                threeDots.setText("...");
            } else {
                threeDots.setText(" ");
            }
        } else {
            threeDots.setText(" ");
        }
    }));

    Timeline keyChangeListener = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
        if (isKeyApproved) {
            switch (keySession) {
                case "UP":
                    slideUpButton.setText(Settings.getKey_UP().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "DOWN":
                    slideDownButton.setText(Settings.getKey_DOWN().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "RIGHT":
                    slideRightButton.setText(Settings.getKey_RIGHT().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "LEFT":
                    slideLeftButton.setText(Settings.getKey_LEFT().toString());
                    setSession("NONE", KeyCode.ESCAPE);
                    isSelectingKey = false;
                    isKeyApproved = false;
                    toggleElements();
                    break;

                case "FULLSCREEN":
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
    protected void keySelect_animatedDots() {
        keySelectTimeline.setCycleCount(Animation.INDEFINITE);
        if (isSelectingKey) {
            keySelectTimeline.play();
        } else {
            keySelectTimeline.stop();
        }
    }

    public static boolean getIsSelectingKey() {
        return isSelectingKey;
    }

    public static void setIsKeyApproved(boolean value) {
        isKeyApproved = value;
    }

    public static String getKeySession() {
        return keySession;
    }

    protected void toggleElements() {
        if (isSelectingKey) {
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
            controlsHeader.setVisible(false);
            assignKeybindHeader.setVisible(true);
        } else {
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
            controlsHeader.setVisible(true);
            assignKeybindHeader.setVisible(false);
        }
    }

    @FXML
    protected void openMenu_HELP() {
        SelectionMenu.setScene_Help((Stage) button_leaderboard.getScene().getWindow());
    }

    @FXML
    protected void openMenu_LEADERBOARD() {
        SelectionMenu.setScene_LeaderBoard((Stage) button_leaderboard.getScene().getWindow());
    }

    public static void textLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textField.getText().length() > maxLength) {
                    String s = textField.getText().substring(0, maxLength);
                    textField.setText(s);
                }
            }
        });
    }

    public void initialize() {
        Utilities.setUsername(playerName.getText());
        if (anim) {
            animationsButton.blendModeProperty().set(BlendMode.SRC_OVER);
            animationsButton.setText("ANIMATIONS: ON");
        } else {
            animationsButton.blendModeProperty().set(BlendMode.OVERLAY);
            animationsButton.setText("ANIMATIONS: OFF");
        }
        textLimiter(playerName, 10);
        slideUpButton.setText(Settings.getKey_UP().toString());
        slideDownButton.setText(Settings.getKey_DOWN().toString());
        slideRightButton.setText(Settings.getKey_RIGHT().toString());
        slideLeftButton.setText(Settings.getKey_LEFT().toString());
        fullscreenButton.setText(Settings.getKey_FULLSCREEN().toString());

        switch (SelectionMenu.gameModeIndex) {
            case 0:
                try {
                    gameImage.setImage(new Image("/icon_3x3.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("3X3 NUMBER PUZZLE");
                break;
            case 1:
                try {
                    gameImage.setImage(new Image("/icon_4x4.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 4x4 number puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                puzzleName.setText("4X4 NUMBER PUZZLE");
                break;
            case 2:
                try {
                    gameImage.setImage(new Image("/icon_3x3_picture.png"));
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't load 3x3 picture puzzle icon: " + e.getMessage());
                    gameImage.setImage(Utilities.createPlaceholderImage(128).getImage());
                }
                playImage_label1.setTextFill(Color.web("#ffffff"));
                selectImageButton.setVisible(true);
                puzzleName.setText("3X3 PICTURE PUZZLE");
                break;
            default:
                System.out.println("ERROR: IN CONTROLLER INITIALIZE METHOD (IMAGEVIEW SWITCH-CASE)");
        }
    }
}
