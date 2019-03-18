package com.sellics.salnikov.task.service;

import com.netflix.hystrix.HystrixCommand;
import com.sellics.salnikov.task.model.ScoreResult;

/**
 * This service allows its clients calculate score of Amazon search result for specified prefix
 *
 * @author anton.salnikov
 */
@FunctionalInterface
public interface SuggestionService {

    /**
     * Calculates score for specified keyword. Takes received keyword and performs subsequent requests for keyword
     * substrings. Algorithm of score calculation is:
     * <p/>
     * <ol>
     *   <li>Create list of possible substring starting from first letter till the whole string</li>
     *   <li>Perform subsequent requests to Amazon completion API to retrieve lists of suggestions</li>
     *   <li>Calculate score dividing number of keyword matches in suggestions with total number of received suggestions</li>
     * </ol>
     * <p/>
     * To fulfill requirement SLA of 10 seconds for a request round-trip list of {@link HystrixCommand} before execute is wrapped in
     * {@link rx.Observable} with 9 seconds take_time value.
     *
     * @param keyword provided by service clients
     *
     * @return prepared [@link ScoreResult]
     */
    ScoreResult prepare(String keyword);
}
