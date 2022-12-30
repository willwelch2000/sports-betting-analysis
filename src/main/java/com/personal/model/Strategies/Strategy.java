package com.personal.model.Strategies;

import java.util.ArrayList;
import java.util.List;

import com.personal.model.Game;

public abstract class Strategy {
    // Default game picking--expected total, odds, and percentage are all within bounds
        // If oddsOrPercentage is true, only the odds OR percentage must be in bounds

    private String description = "";
    private Double percentageMin;
    private Double percentageMax;
    private int oddsMin;
    private int oddsMax;
    private Double expectedTotalMin;
    private List<Game> games;
    private boolean oddsOrPercentage;

    public Strategy(String description, Double percentageMin, Double percentageMax, int oddsMin, int oddsMax,
            Double expectedTotalMin) {
        this(description, percentageMin, percentageMax, oddsMin, oddsMax, expectedTotalMin, false);
    }

    public Strategy(String description, Double percentageMin, Double percentageMax, int oddsMin, int oddsMax,
            Double expectedTotalMin, boolean oddsOrPercentage) {
        this.description = description;
        this.percentageMin = percentageMin;
        this.percentageMax = percentageMax;
        this.oddsMin = oddsMin;
        this.oddsMax = oddsMax;
        this.expectedTotalMin = expectedTotalMin;
        this.oddsOrPercentage = oddsOrPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setGames(List<Game> games) {
        this.games = new ArrayList<>();
        for (Game game : games) {
            this.games.add(game);
        }
    }

    public StrategyResults getResults() {
        List<Game> filteredGames = filterGames();
        Double total = 0.0;
        int count = 0;
        for (Game game : filteredGames) {
            Integer odds = game.getOdds();
            Double earnings = -1.0;
            if (game.getResult().equals("Y"))
                earnings = odds > 0? (odds/100.0) : (-100.0/odds);
            if (!game.getResult().equals("U")) {
                total += earnings;
                count++;
            }
        }
        Double average = total/count;
        StrategyResults results =  new StrategyResults();
        results.total = total;
        results.average = average;
        return results;
    }

    private List<Game> filterGames() {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            if (includeGame(game))
                filteredGames.add(game);
        }
        return filteredGames;
    }

    public boolean includeGame(Game game) {
        if (!oddsOrPercentage && getExpectedTotal(game) >= expectedTotalMin && game.getPercentage() >= percentageMin && 
                game.getPercentage() <= percentageMax && game.getOdds() >= oddsMin && game.getOdds() <= oddsMax) {
            return true;
        } else if (oddsOrPercentage && getExpectedTotal(game) >= expectedTotalMin && ((game.getPercentage() >= percentageMin && 
                game.getPercentage() <= percentageMax) || (game.getOdds() >= oddsMin && game.getOdds() <= oddsMax))) {
            return true;
        }
        return false;
    }

    protected Double getExpectedTotal(Game game) {
        Integer odds = game.getOdds();
        Double percentage = game.getPercentage();

        Double expectedEarnings;
        if (odds > 0) {
            expectedEarnings = odds*percentage/100;
        } else {
            expectedEarnings = -percentage*100/odds;
        }
        Double expectedLoss = 1-percentage;
        return expectedEarnings - expectedLoss;
    }
}
