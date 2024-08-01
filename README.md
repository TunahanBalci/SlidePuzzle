SLIDE PUZZLE

    Slide puzzle is a sliding puzzle game written in Java and a tiny bit of CSS. The main goal is to align initally randomized puzzle pieces by sliding them into an ultimate correct order.
    
    FEATURES: 
        Timer,
        Selecting user name,
        Leaderboards (top 5 scores),
        Sliding animations,
        Sliding sounds and background music,
        Options menu:
            Change key binds, toggle animations, enter user name, select game mode.
        picture puzzle (select any picture), 
        error handling,
    
    USED LIBRARIES:
        JavaFX (Gluon),
        Java AWT

    HOW TO COMPILE?
        1. Download source code
        2. Ensure maven dependencies are correct
        3. Compile using maven

    WHY NO RELEASE?
        Due to an issue between JavaFX and Maven, the game requires entire JavaFX library inside its source directory.
        Otherwise, the game needs to be launched through JVM arguments to launch it with the required libraries each time.
        For that reason, I decided to keep the game as is.
