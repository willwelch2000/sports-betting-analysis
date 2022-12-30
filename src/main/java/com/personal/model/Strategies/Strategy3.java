package com.personal.model.Strategies;

import java.util.List;

import com.personal.model.Game;

public class Strategy3 extends Strategy {
    
    public Strategy3() {
        super("Expected total greater than 0 even with the percentage lowered 5%", 0.0, 1.0, -5000, 5000, 0.0);
    }

    @Override
    public boolean includeGame(Game game) {
        Game modifiedGame = new Game(game); // percentage will be 5% less
        modifiedGame.setPercentage(game.getPercentage() - 0.05);
        return getExpectedTotal(modifiedGame) >= 0;
    }

}
