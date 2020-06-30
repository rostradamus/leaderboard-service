package com.rostradamus.leaderboardservice.controller;

import com.rostradamus.leaderboardservice.controller.request.LeaderboardUpdateRequest;
import com.rostradamus.leaderboardservice.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaderboardController {
    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top/{n}")
    public ResponseEntity<?> getTopN(@PathVariable int n) {
        return ResponseEntity.ok(leaderboardRepository.fetchTopN(n - 1));
    }

    @GetMapping("/by-id/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(leaderboardRepository.fetchByUserId(userId));
    }

    @GetMapping("/by-rank/{targetRank}")
    public ResponseEntity<?> getByRank(@PathVariable int targetRank) {
        return ResponseEntity.ok(leaderboardRepository.fetchByRank(targetRank));
    }

    @PostMapping("/by-id/{userId}")
    public ResponseEntity<?> updateByUserId(@PathVariable int userId,
                                            @RequestBody LeaderboardUpdateRequest leaderboardUpdateRequest) {
        leaderboardRepository.updateByUserId(userId, leaderboardUpdateRequest.getScore());
        return ResponseEntity.ok(leaderboardRepository.fetchByUserId(userId));
    }
}
