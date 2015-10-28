package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    /**
     * <pre>
     * Input :
     * 1st arg = your rank at the start of the season
     * 2nd arg = your number of stars at the start of the season
     * 3rd arg = the winrate of the deck you'll use up to rank 5 (from 0.0 to 1.0)
     * 4th arg = the average time of a match up to rank 5(in minutes)
     * 5th arg = the winrate of the deck you'll use after rank 5 (from 0.0 to 1.0)
     * 6th arg = the average time of a match after rank 5(in minutes)
     * 7th arg = the number of simulations you want to do
     * </pre>
     */
    
    public static void main(String[] args) {
        Random random = new Random();
        int startingRank = Integer.parseInt(args[0]);
        int startingStars = Integer.parseInt(args[1]);
        double winrateBefore = Double.parseDouble(args[2]);
        int durationBefore = Integer.parseInt(args[3]);
        double winrateAfter = Double.parseDouble(args[4]);
        int durationAfter = Integer.parseInt(args[5]);
        int nbSim = Integer.parseInt(args[6]);

        List<Long> rankFives = new ArrayList<Long>();
        List<Long> legends = new ArrayList<Long>();
        
        for (int i = 0; i < nbSim; i++) {
            long[] game = runOneGame(startingRank, startingStars, winrateBefore, durationBefore, winrateAfter, durationAfter, random);
            rankFives.add(game[0]);
            legends.add(game[1]);
        }
        
        System.out.println("Average time to get rank 5 : " + average(rankFives) + " / Time to get legend : " + average(legends));

    }
    
    private static long average(List<Long> list) {
        long ans = 0;
        for (Long l : list) {
            ans += l;
        }
        return ans / list.size();
    }
    
    private static long[] runOneGame(int startingRank, int startingStars, double winrateBefore, int durationBefore, double winrateAfter,
        int durationAfter, Random random) {
        HearthstoneSeason season = new HearthstoneSeason(startingRank, startingStars);
        long durationFive = 0;
        long totalduration = 0;
        boolean rankFive = season.getRank() <= 5;
        
        while (season.getRank() != 0) {
            double winrate = (season.getRank() > 5) ? winrateBefore : winrateAfter;
            int duration = (season.getRank() > 5) ? durationBefore : durationAfter;
            if (random.nextDouble() < winrate) {
                season.winGame();
            } else {
                season.loseGame();
            }
            totalduration += duration;
            if (!rankFive) {
                durationFive += duration;
                if (season.getRank() <= 5) {
                    rankFive = true;
                }
            }
        }
        
        long[] ans = {durationFive, totalduration};
        return ans;
    }
}