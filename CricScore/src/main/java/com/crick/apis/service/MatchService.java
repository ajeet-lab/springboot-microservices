package com.crick.apis.service;

import com.crick.apis.entities.Match;

import java.util.List;
import java.util.Map;

public interface MatchService {
    // Get all matched
    List<Match> getAllMatches();

    // Get live matches
    List<Match> getAllLiveMatches();

    // Get CWC2023 point table
    List<List<String>> getCWC2023PointTable();

    List<Map<String, String>> scheduledMatches();
}
