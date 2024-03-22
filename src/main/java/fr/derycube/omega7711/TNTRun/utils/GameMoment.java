package fr.derycube.omega7711.TNTRun.utils;

public enum GameMoment {
    WAITING_FOR_PLAYERS(3600),
    STARTING(45),
    ROUND(60),
    GAME_OVER(20);
    final int timebeforenext;
    GameMoment(int timebeforenext) {
        this.timebeforenext = timebeforenext;
    }
    public int getTimeinSeconds() {
        return this.timebeforenext;
    }
    public int getTimeinTicks() {
        return this.timebeforenext*20;
    }
}
