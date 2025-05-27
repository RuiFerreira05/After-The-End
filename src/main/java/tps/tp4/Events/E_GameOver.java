package tps.tp4.Events;

import tps.tp4.Colony;

public class E_GameOver extends Event {
    
    public E_GameOver() {
        this.name = "Game Over";
        this.description = "The colony has reached its end. You can no longer continue.";
    }

    @Override
    public String impact(Colony colony, int choice) {
        colony.setGameOver(true);
        return "";
    }
}
