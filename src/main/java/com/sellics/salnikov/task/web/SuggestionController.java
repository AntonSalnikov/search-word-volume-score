package com.sellics.salnikov.task.web;

import com.sellics.salnikov.task.model.ScoreResult;
import com.sellics.salnikov.task.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estimate")
@Slf4j
public class SuggestionController {

    private SuggestionService suggestionService;

    @Autowired
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public ScoreResult estimate(@RequestParam(name = "keyword") String keyword) {
        log.debug("Received request to estimate keyword '{}'", keyword);

        return suggestionService.prepare(keyword);
    }
}
