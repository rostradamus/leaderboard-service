package com.rostradamus.leaderboardservice.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class LeaderboardUpdateRequest {
    @NonNull
    private int userId;
    @NonNull
    private double score;
}
