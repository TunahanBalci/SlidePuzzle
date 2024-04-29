package com.binarybrotherhood.slidepuzzle;

import javafx.scene.input.KeyCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Settings {

    private static final Properties settings = new Properties();

    public ArrayList<KeyCode> existingKeys  = new ArrayList<KeyCode>();


    private static KeyCode key_UP = KeyCode.UP;
    private static KeyCode key_DOWN = KeyCode.DOWN;
    private static KeyCode key_RIGHT = KeyCode.RIGHT;
    private static KeyCode key_LEFT = KeyCode.LEFT;
    private static KeyCode key_FULLSCREEN = KeyCode.F11;


    public static KeyCode getKey_UP(){
        return key_UP;
    }
    public static KeyCode getKey_DOWN(){
        return key_DOWN;
    }
    public static KeyCode getKey_RIGHT(){
        return key_RIGHT;
    }
    public static KeyCode getKey_LEFT(){
        return key_LEFT;
    }
    public static KeyCode getKey_FULLSCREEN(){
        return key_FULLSCREEN;
    }






    public static void initializeSettings(){

        settings.putIfAbsent("KEY_UP", key_UP);
        settings.putIfAbsent("KEY_DOWN", key_DOWN);
        settings.putIfAbsent("KEY_RIGHT", key_RIGHT);
        settings.putIfAbsent("KEY_LEFT", key_LEFT);
        settings.putIfAbsent("KEY_FULLSCREEN", key_FULLSCREEN);
    }

    // Example accessor:
    public static String getSettingValue(String settingType, String defaultValue) {


        return settings.getProperty(settingType, defaultValue);
    }

    public static void assignKey(String type, KeyCode key){

        System.out.println("ASSIGNKEY");

        System.out.println("TYPE: " + Controller.getKeySession() + " KEY: " + key);

        switch (Controller.getKeySession()){

            case "UP":

                key_UP = key;
                registerKeySettings("KEY_SLIDE_UP", key.toString());
                Controller.setSession(type, key);
                Controller.setIsKeyApproved(true);
                break;

            case "DOWN":

                key_DOWN = key;
                registerKeySettings("KEY_SLIDE_DOWN", key.toString());
                Controller.setSession(type, key);
                Controller.setIsKeyApproved(true);
                break;

            case "RIGHT":

                key_RIGHT = key;
                registerKeySettings("KEY_SLIDE_RIGHT", key.toString());
                Controller.setSession(type, key);
                Controller.setIsKeyApproved(true);
                break;

            case "LEFT":

                key_LEFT = key;
                registerKeySettings("KEY_SLIDE_LEFT", key.toString());
                Controller.setSession(type, key);
                Controller.setIsKeyApproved(true);
                break;

            case "FULLSCREEN":

                key_FULLSCREEN = key;
                registerKeySettings("KEY_SLIDE_FULLSCREEN", key.toString());
                Controller.setSession(type, key);
                Controller.setIsKeyApproved(true);
                break;

            default:
                System.out.println("ERROR: Couldn't assign key");
                System.exit(-1);
        }
    }



    public static void registerKeySettings(String key, String value){

        settings.setProperty(key, value);
    }



    public static void saveChanges(){

        try (FileOutputStream out = new FileOutputStream("settings.properties")) {

            settings.store(out, "Saved changes in settings file");

        } catch (IOException exception) {

            System.out.println("ERROR IN 'SETTINGS.PROPERTIES': " + exception);
            System.exit(-1);
        }
    }
}
