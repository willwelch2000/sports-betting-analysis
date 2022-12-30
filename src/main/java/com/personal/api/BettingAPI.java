package com.personal.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.personal.model.Game;

public class BettingAPI {
    final String url = "http://localhost:8080/games";
    final int HOUR_MS = 1000*60*60;
    public List<Game> games;

    public BettingAPI() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        Game newGame = new Game(game);
        games.add(newGame);
    }

    public List<Game> getGamesByLeague(String league) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getByLeagueUrl(league)))
                .headers("Content-Type", "application/json")
                .GET()
                .build();
        
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = response.body();
        return Arrays.asList(mapper.readValue(responseBody, Game[].class));
    }

    private String getByLeagueUrl(String league) {
        return url + "/byLeague/" + league;
    }
}
