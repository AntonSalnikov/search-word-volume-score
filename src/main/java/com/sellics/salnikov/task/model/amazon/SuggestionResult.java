package com.sellics.salnikov.task.model.amazon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter@Setter
@NoArgsConstructor@ToString
public class SuggestionResult {

    private String alias;
    private String prefix;
    private String suffix;

    private List<Suggestion> suggestions;

    private String responseId;
    private Boolean shuffled;
}
