package com.crick.apis.controllers;


import com.crick.apis.entities.Match;
import com.crick.apis.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {

    private MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/live")
    public ResponseEntity<List<Match>> getAllLiveMatches(){
        return new ResponseEntity<>(matchService.getAllLiveMatches(), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches(){
       return new ResponseEntity<>(this.matchService.getAllMatches(), HttpStatus.OK);
    }

    @GetMapping("/point-table")
    public ResponseEntity<List<List<String>>> getPointTable(){
        return new ResponseEntity<>(this.matchService.getCWC2023PointTable(), HttpStatus.OK);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<Map<String, String>>> getScheduledMatches(){
        return new ResponseEntity<>(this.matchService.scheduledMatches(), HttpStatus.OK);
    }
}
