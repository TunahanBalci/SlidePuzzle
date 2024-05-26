package com.binarybrotherhood.slidepuzzle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LeaderBoardController {

    @FXML
    private Label player1_name;
    @FXML
    private Label player1_time;

    @FXML
    private Label player2_name;
    @FXML
    private Label player2_time;

    @FXML
    private Label player3_name;
    @FXML
    private Label player3_time;

    @FXML
    private Label player4_name;
    @FXML
    private Label player4_time;

    @FXML
    private Label player5_name;
    @FXML
    private Label player5_time;

    @FXML
    protected void mainMenu() {
        Stage old_stage = (Stage) player1_name.getScene().getWindow();
        old_stage.hide();

        Platform.runLater(() -> {
            try {
                Utilities.windowPos_X = old_stage.getX();
                Utilities.windowPos_Y = old_stage.getY();

                Application application = SelectionMenu.class.newInstance();
                Stage newStage = new Stage();
                application.start(newStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static String parseTime(int timeCounter) {
        String output;

        if (timeCounter > 3600000) {
            int hour = (timeCounter - (timeCounter % 3600000)) / 3600000;
            int minute = (timeCounter - (timeCounter % 60000)) / 60000 - hour * 60;
            int second = (timeCounter - (timeCounter % 1000)) / 1000 - minute * 60;
            output = (hour + "h " + minute + "m " + second + "." + timeCounter % 1000 + "s");

        } else if (timeCounter > 60000) {
            int minute = (timeCounter - (timeCounter % 60000)) / 60000;
            int second = (timeCounter - (timeCounter % 1000)) / 1000 - minute * 60;
            output = (minute + "m " + second + "." + timeCounter % 1000 + "s");

        } else {
            int second = (timeCounter - (timeCounter % 1000)) / 1000;
            output = (second + "." + timeCounter % 1000 + "s");
        }
        return output;
    }

    public void initialize() {
        LeaderBoard leaderBoard = new LeaderBoard().getLeaderBoard();
        player1_name.setText(leaderBoard.getName()[0]);
        player1_time.setText(parseTime(leaderBoard.getTime()[0]));

        player2_name.setText(leaderBoard.getName()[1]);
        player2_time.setText(parseTime(leaderBoard.getTime()[1]));

        player3_name.setText(leaderBoard.getName()[2]);
        player3_time.setText(parseTime(leaderBoard.getTime()[2]));

        player4_name.setText(leaderBoard.getName()[3]);
        player4_time.setText(parseTime(leaderBoard.getTime()[3]));

        player5_name.setText(leaderBoard.getName()[4]);
        player5_time.setText(parseTime(leaderBoard.getTime()[4]));
    }
}
