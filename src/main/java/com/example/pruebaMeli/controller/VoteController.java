package com.example.pruebaMeli.controller;

import com.example.pruebaMeli.service.VoteService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
@Validated
public class VoteController {

    private final VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public int getVotes(@RequestParam @NotBlank String cityName, @RequestParam @Min(0) int estimatedCost) {
        return voteService.getVoteCount(cityName, estimatedCost);
    }
}
