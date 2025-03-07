package entity;

import java.text.DecimalFormat;
import java.util.*;

public class Score {
    private int dailyScore;
    private int midtermScore;
    private int experimentScore;
    private int finalScore;
    private double totalScore;
    private Date date;

    public Score() {
        generateScores();
    }

    public void generateScores() {
        this.dailyScore = getRandomScore(40, 100);
        this.midtermScore = getRandomScore(30, 100);
        this.experimentScore = getRandomScore(50, 100);
        this.finalScore = getRandomScore(40, 100);
        this.totalScore = calculateTotalScore();
        this.date = new Date();
    }

    private int getRandomScore(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public double calculateTotalScore() {
    double totalScore = dailyScore * 0.4 + midtermScore * 0.2 + experimentScore * 0.2 + finalScore * 0.2;
    DecimalFormat df = new DecimalFormat("#.00"); // 格式化为两位小数
    return Double.parseDouble(df.format(totalScore)); // 格式化并返回
}

    public double getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return "Score{" + "dailyScore=" + dailyScore + ", midtermScore=" + midtermScore + ", experimentScore=" + experimentScore + ", finalScore=" + finalScore + ", totalScore=" + totalScore + ", date=" + date + '}';
    }
}
