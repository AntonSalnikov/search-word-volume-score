package com.sellics.salnikov.task.model.amazon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter@Setter
@NoArgsConstructor
@ToString
public class Suggestion {

    private String suggType;
    private String type;
    private String value;
    private String refTag;
    private Boolean ghost;
    private Boolean help;
    private Boolean xcatOnly;
    private Boolean fallback;
    private Boolean spellCorrected;
    private Boolean blackListed;
}
