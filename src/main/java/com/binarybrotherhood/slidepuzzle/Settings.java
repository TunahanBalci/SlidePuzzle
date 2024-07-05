package com.binarybrotherhood.slidepuzzle;

import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.Properties;

public class Settings {

    File settingsFile = new File("src/main/resources/settings.properties");
    private Properties settings = new Properties();

    private static KeyCode key_UP = KeyCode.UP;
    private static KeyCode key_DOWN = KeyCode.DOWN;
    private static KeyCode key_RIGHT = KeyCode.RIGHT;
    private static KeyCode key_LEFT = KeyCode.LEFT;
    private static KeyCode key_FULLSCREEN = KeyCode.F11;

    public static KeyCode getKey_UP() {
        return key_UP;
    }

    public static KeyCode getKey_DOWN() {
        return key_DOWN;
    }

    public static KeyCode getKey_RIGHT() {
        return key_RIGHT;
    }

    public static KeyCode getKey_LEFT() {
        return key_LEFT;
    }

    public static KeyCode getKey_FULLSCREEN() {
        return key_FULLSCREEN;
    }

    public void initializeSettings() {
        try {
            FileInputStream configFileReader = new FileInputStream(settingsFile);
            settings.load(configFileReader);
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't read settings file: " + e.getMessage());
        }

        try {
            key_UP = KeyCode.valueOf(settings.getProperty("KEY_UP"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_KEY_UP");
        }

        try {
            key_DOWN = KeyCode.valueOf(settings.getProperty("KEY_DOWN"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_KEY_DOWN");
        }

        try {
            key_RIGHT = KeyCode.valueOf(settings.getProperty("KEY_RIGHT"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_KEY_RIGHT");
        }

        try {
            key_LEFT = KeyCode.valueOf(settings.getProperty("KEY_LEFT"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_KEY_LEFT");
        }

        try {
            key_FULLSCREEN = KeyCode.valueOf(settings.getProperty("KEY_FULLSCREEN"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_KEY_FULLSCREEN");
        }

        try {
            SelectionMenu.animations = Boolean.parseBoolean(settings.getProperty("ANIMATIONS"));
        } catch (Exception e) {
            System.out.println("ERROR: SETTINGS_ANIMATIONS");
        }
    }

    public void createSettings() {
        try {
            settingsFile.getParentFile().mkdir();
            settingsFile.createNewFile();

            settings.setProperty("KEY_UP", key_UP.toString());
            saveChanges();
            settings.setProperty("KEY_DOWN", key_DOWN.toString());
            saveChanges();
            settings.setProperty("KEY_RIGHT", key_RIGHT.toString());
            saveChanges();
            settings.setProperty("KEY_LEFT", key_LEFT.toString());
            saveChanges();
            settings.setProperty("KEY_FULLSCREEN", key_FULLSCREEN.toString());
            saveChanges();
            settings.setProperty("ANIMATIONS", "true");

        } catch (Exception e) {
            System.out.println("ERROR: Couldn't create settings file");
        }
    }

    public void assignKey(String key, KeyCode value) {
        switch (SelectionMenuController.getKeySession()) {
            case "UP":
                key_UP = value;
                registerKeySettings("KEY_UP", value.toString());
                SelectionMenuController.setSession(key, value);
                SelectionMenuController.setIsKeyApproved(true);
                break;
            case "DOWN":
                key_DOWN = value;
                registerKeySettings("KEY_DOWN", value.toString());
                SelectionMenuController.setSession(key, value);
                SelectionMenuController.setIsKeyApproved(true);
                break;
            case "RIGHT":
                key_RIGHT = value;
                registerKeySettings("KEY_RIGHT", value.toString());
                SelectionMenuController.setSession(key, value);
                SelectionMenuController.setIsKeyApproved(true);
                break;
            case "LEFT":
                key_LEFT = value;
                registerKeySettings("KEY_LEFT", value.toString());
                SelectionMenuController.setSession(key, value);
                SelectionMenuController.setIsKeyApproved(true);
                break;
            case "FULLSCREEN":
                key_FULLSCREEN = value;
                registerKeySettings("KEY_FULLSCREEN", value.toString());
                SelectionMenuController.setSession(key, value);
                SelectionMenuController.setIsKeyApproved(true);
                break;
            default:
                System.out.println("ERROR: Couldn't assign key");
                System.exit(-1);
        }
    }

    public void registerKeySettings(String key, String value) {
        try {
            settings.setProperty(key, value);
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't register key in settings.properties");
        }
        saveChanges();
    }

    public void saveChanges() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/settings.properties")) {
            settings.store(out, "Saved changes in settings file");
        } catch (IOException exception) {
            System.out.println("ERROR IN 'SETTINGS.PROPERTIES': \n" + exception.getMessage());
            createSettings();
        }
    }
}
