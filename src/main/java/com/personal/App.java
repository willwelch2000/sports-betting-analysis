package com.personal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.personal.api.BettingAPI;
import com.personal.model.Game;
import com.personal.model.Strategies.Strategy;
import com.personal.model.Strategies.Strategy1;
import com.personal.model.Strategies.Strategy2;
import com.personal.model.Strategies.Strategy3;
import com.personal.model.Strategies.StrategyResults;

public final class App {
    private static String[] supportedLeagues = {"nba"};
    private static Strategy[] strategies = {new Strategy1(), new Strategy2(), new Strategy3()};
    
    private static String getLeague() {
        // Request league from user
        System.out.print("Select a league\nSupported leagues: ");
        for (String supportedLeague : supportedLeagues) {
            System.out.print(supportedLeague + "   ");
        }
        System.out.println();
        Scanner input = new Scanner(System.in);
        String league = "";
        while (true) {
            league = input.next().toLowerCase();
            if (Arrays.asList(supportedLeagues).contains(league)) {
                System.out.println("Success!");
                break;
            } else {
                System.out.print("League not supported. Try again: ");
            }
        }
        input.close();
        return league;
    }

    public static void main( String[] args ) {
        String league = getLeague();
        BettingAPI bettingAPI = new BettingAPI();

        // Get games
        List<Game> games = new ArrayList<>();
        try {
            games = bettingAPI.getGamesByLeague(league);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test each strategy
        for (Strategy strategy : strategies) {
            strategy.setGames(games);
            System.out.println("\nTesting stratgy: " + strategy.getDescription());
            StrategyResults results = strategy.getResults();
            System.out.println("Total: " + results.total);
            System.out.println("Average: " + results.average);
        }
    }
}
