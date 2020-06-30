package com.rostradamus.leaderboardservice.model;

// NOTE: Using Lombok @Data, this class can be more simplified. However, for now, all methods are implemented manually.
public class LeaderboardRow implements Comparable<LeaderboardRow> {
    private static final float DEFAULT_INITIAL_SCORE = 0;
    private int userId;
    private double score;
    private int rank;

    public LeaderboardRow(int userId) {
        this.userId = userId;
        this.score = DEFAULT_INITIAL_SCORE;
        this.rank = -1;
    }
    public LeaderboardRow(int userId, double score) {
        this.userId = userId;
        this.score = score;
        this.rank = -1;
    }

    public int getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public int compareTo(LeaderboardRow o) {
//        if (this.score == o.score) return 0;
        if (this.userId == o.userId) return 0;
        if (this.score == o.score) {
            return this.userId < o.userId ? 1 : -1;
        }

        return this.score < o.score ? 1 : -1;
    }
}
