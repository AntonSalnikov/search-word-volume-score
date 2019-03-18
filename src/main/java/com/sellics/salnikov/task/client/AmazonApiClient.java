package com.sellics.salnikov.task.client;


import com.netflix.hystrix.HystrixCommand;
import com.sellics.salnikov.task.model.amazon.SuggestionResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Used for HTTP requests to AWS in following cases:
 * <ul>
 * <li>Retrieve {@link SuggestionResult} for specified prefix
 * </ul>
 *
 * @author @author anton.salnikov
 */
@FeignClient(name = "amazon", url = "${feign.client.config.amazon.service}")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface AmazonApiClient {


    @RequestMapping(method = RequestMethod.GET, value = "/api/2017/suggestions")
    HystrixCommand<SuggestionResult> getSuggestionResult(@RequestParam(name = "mid") String mid,
                                                         @RequestParam(name = "alias") String alias,
                                                         @RequestParam(name = "prefix") String prefix);
}
