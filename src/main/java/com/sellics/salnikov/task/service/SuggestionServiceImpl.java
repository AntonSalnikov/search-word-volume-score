package com.sellics.salnikov.task.service;

import com.netflix.hystrix.HystrixCommand;
import com.sellics.salnikov.task.client.AmazonApiClient;
import com.sellics.salnikov.task.model.ScoreResult;
import com.sellics.salnikov.task.model.amazon.Suggestion;
import com.sellics.salnikov.task.model.amazon.SuggestionResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SuggestionServiceImpl implements SuggestionService {

    //TODO: anton.salnikov - move this magic strings to properties
    private static final String MID_VALUE = "ATVPDKIKX0DER";
    private static final String ALIAS_VALUE = "aps";

    private AmazonApiClient amazonApiClient;

    @Autowired
    public SuggestionServiceImpl(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") AmazonApiClient amazonApiClient) {
        this.amazonApiClient = amazonApiClient;
    }

    @Override
    public ScoreResult prepare(final String prefix) {
        log.debug("Performing score calculation for prefix {}", prefix);

        if(StringUtils.isBlank(prefix)) {
            return new ScoreResult(prefix, 0);
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ScoreResult result = new ScoreResult(prefix, calculateScore(prefix));
        log.debug("Result is received: {}. Operation took {} ms.", result, stopWatch.getTime());
        return result;
    }

    private List<HystrixCommand<SuggestionResult>> prepareCommands(final String prefix) {

        List<HystrixCommand<SuggestionResult>> commands = new ArrayList<>();
        for(int i = 1; i <= prefix.length(); i++) {
            HystrixCommand<SuggestionResult> command = amazonApiClient.getSuggestionResult(MID_VALUE, ALIAS_VALUE, prefix.substring(0, i));
            commands.add(command);
        }

        return commands;
    }

    private int calculateScore(final String prefix) {
        //Process HystrixCommands and extract suggestions
        List<SuggestionResult> results = new ArrayList<>();
        //TODO: anton.salnikov - move magic number to config
        Observable.from(prepareCommands(prefix)).take(9, TimeUnit.SECONDS)
                .subscribe(
                        s -> results.add(s.execute()),
                        e -> log.error("Error appeared", e),
                        () -> log.debug("Processing of commands is finished. Sent '{}' requests.", results.size())
                );

        if (results.size() == 0) {
            return 0;
        }

        //Create pattern for keyword with word boundaries
        Pattern pattern = Pattern.compile("\\b"+prefix+"\\b");
        long numberOfMatches = results.stream()
                .map(SuggestionResult::getSuggestions)
                .flatMap(Collection::stream)
                .map(Suggestion::getValue)
                .filter(StringUtils::isNotBlank)
                //perform search in value from beginning
                .filter(f -> pattern.matcher(f).find(0))
                .count();

        return (int) Math.round((double) numberOfMatches/(results.size() * 10) * 100);
    }
}
