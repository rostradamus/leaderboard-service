package com.rostradamus.leaderboardservice.repository;

import com.rostradamus.leaderboardservice.model.LeaderboardRow;
import com.rostradamus.leaderboardservice.util.RankTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class LeaderboardRepository {
    private Map<Integer, LeaderboardRow> leaderboardRowMap = new HashMap<>();
    private RankTree rankTree = new RankTree();

    public Collection<LeaderboardRow> fetchTopN(int n) {
        return rankTree.topN(n);
    }

    public void updateByUserId(int userId, double newScore) {
        synchronized (this) {
            LeaderboardRow leaderboardRow = leaderboardRowMap.get(userId);
            rankTree.remove(leaderboardRow);
            leaderboardRow.setScore(newScore);
            rankTree.insert(leaderboardRow);
        }
    }

    public LeaderboardRow fetchByUserId(int userId) {
        synchronized (this) {
            return leaderboardRowMap.get(userId);
        }
    }

    public LeaderboardRow fetchByRank(int targetRank) {
        synchronized (this) {
            LeaderboardRow leaderboardRow = rankTree.getNth(targetRank - 1);
            leaderboardRow.setRank(targetRank);
            return leaderboardRow;
        }
    }

    public void seedRandomData(int numRows) {
        synchronized (this) {
            for (int i = 0; i < numRows; i++) {
                double randomScore = (double) Math.round(Math.random() * 10000 * 100) / 100;
                createLeaderboardRow(i, randomScore);
            }
            log.info("Seeding " + numRows + " data completed !");
        }
    }

    public void seedTrivialData() {
        createLeaderboardRow(1, 0);
        createLeaderboardRow(2, 30);
        createLeaderboardRow(3, 10);
        createLeaderboardRow(4, 50);
        createLeaderboardRow(5, 0);
        createLeaderboardRow(6, 100);
        createLeaderboardRow(7, 120);
        rankTree.print();
    }

    private void createLeaderboardRow(int userId, double score) {
        LeaderboardRow leaderboardRow = new LeaderboardRow(userId, score);
        leaderboardRowMap.put(leaderboardRow.getUserId(), leaderboardRow);
        rankTree.insert(leaderboardRow);
    }
}
