package main;

public class HearthstoneSeason {
    private static int[] maxStars = {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2};
    
    private int rank;
    private int stars;
    private int winStreak;

    public int getRank() {
        return this.rank;
    }

    public HearthstoneSeason(int rank, int stars) {
        this.rank = rank;
        this.stars = stars;
        this.winStreak = 0;
    }
    
    public void loseGame() {
        this.winStreak = 0;
        if (this.rank <= 20) {
            this.stars--;
            if (this.stars == -1) {
                if (this.rank == 20) {
                    this.stars = 0;
                } else {
                    this.rank++;
                    this.stars = this.maxStars[this.rank] - 1;
                }
            }
        }
    }
    
    private void winStar() {
        this.stars++;
        if (this.stars == (maxStars[this.rank] + 1)) {
            this.rank--;
            this.stars = 1;
        }
    }
    
    public void winGame() {
        this.winStreak++;
        this.winStar();
        if ((this.rank > 5) && (this.winStreak >= 3)) {
            this.winStar();
        }
    }

    public void debug() {
        System.err.println("Rank : " + this.rank + " / Stars : " + this.stars + " / Win Streak : " + this.winStreak);
    }
}