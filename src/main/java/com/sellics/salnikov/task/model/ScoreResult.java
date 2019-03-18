package com.sellics.salnikov.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
@NoArgsConstructor@AllArgsConstructor
public class ScoreResult {

    /**
     * Keyword received from client
     *
     */
    //TODO: anton.salnikov - I guess it is typo in assignment. But lets keep upercase before clarification
    @JsonProperty(value = "Keyword")
    private String keyword;

    /**
     * The score should be in the range [0 → 100] and represent the estimated search-volume (how often Amazon customers
     * search for that exact keyword). A score of 0 means that the keyword is practically never searched for,
     * 100 means that this is one of the hottest keywords in all of amazon.com right now
     */
    private int score;
}
