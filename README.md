# Sports Betting Web Scraper
## Sports Betting App overall
This application is one part of a larger project, an app that
1. Uses Selenium to scrape the web for predictions and betting odds for athletic events.
2. Interfaces with a MySQL database to store and access this data. 
3. Perform analysis on the data to attempt to find successful sports-betting strategies.

## Purpose of this part of the project
This part of the project is designed to perform analysis on several sports-betting strategies, using data from the local MySQL database. 

## Usage
The program will ask the user for a league to do analysis on. As of now, the only supported league is the nba.  
The sports-betting-api services should be running to allow access to local http requests, which access the database.  
The program will retrieve the data for the specified league, and test several strategies. For each strategy the system will
* Print a short summary of what the strategy does
* Print the total amount of money that would have been earned using this strategy on the games in the database, assuming $1 per bet.
* Print the average amount of money earned per game, still assuming $1 bets. 

## Strategies currently being tested
The base Strategy.java class is an abstract class that allows child classes to extend it. The strategies currently being tested are:
* Strategy 1: Using the odds of a game and the predicted percentages, calculate the expected earnings. If the expected earnings is > 0, then bet on the game.
* Strategy 2: Calculate the expected earnings as shown in Strategy 1. For the bet to be taken, the following requirements must be met:
    * The expected earnings is > 0.02.
    * The odds are between -120 and 120 (inclusive), and the percentage is between 40% and 60% (inclusive).
* Strategy 3: Calculate what the expected earnings would be if the percentage was lowered 5%. If the expected earnings are > 0 even with this lowered percentage, take the bet. 