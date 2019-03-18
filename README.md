# Sellics test assignments

## Algorithm description

#### 1. What assumptions did you make?

By itself Amazon completion API does not return information about keyword search volume. 
But it returns 10 suggestion for each prefix input. Main assumption is that these 10 suggested items 
are the top used keywords by other customers who also performs searching and it is the only one criteria which may be used
for scoring. 

#### 2. How does your algorithm work?

1. User provides a keyword
2. Service creates a list of substrings for specified keywords starting from the 1st letter. 
If keyword is `iphone` in result we receive list with 6 substrings - `[i, ip, iph, ...]`
3. Service creates list of request commands and performs requests to Amazon completion API:
    ```text
      https://completion.amazon.com/api/2017/suggestions?prefix=i
      https://completion.amazon.com/api/2017/suggestions?prefix=ip
      https://completion.amazon.com/api/2017/suggestions?prefix=iph
      .
      .
      .
      https://completion.amazon.com/api/2017/suggestions?prefix=iphone%20charger
    ```
4. This serial operation is wrapped in `Observable` to make possible control duration of processing and break it at some time point
5. From response the suggested value is retrieved and compared with initial keyword. Is the whole keyword present in suggestion or not.
6. Score is calculated by dividing the number of matches in suggestion with the total number of suggestions. In this case
total `number of suggestions` is calculated by multiplying `number of performed requests` to `10`.   

#### 3. Do you think the (*hint) that we gave you earlier is correct and if so - why?

Yes, I assume that returned order comparatively is insignificant. It may be Amazon strategy to obfuscate real search volume results.
At least it is difficult to say that such order makes sense. 

#### 4. How precise do you think your outcome is and why?

The result of score calculation is really crude. It depends on many factors: 
1. How correctly the list of suggested 10 items was created by Amazon API
2. The search volume calculates for specified time interval and it may differ depending on searching window. 
3. You own request might also impact the whole picture :)

## Project description

To build project use command - `$ ./gradlew clean build`

To run project without IDE: 
1. Go to ./build/libs
2. run `$ java -jar test-task-0.0.1.jar`

To run project from IDEA: 

1. Open project in IDE
2. So as lombok is used do not forget to enable annotation processing
3. Run `TestTaskApplication().main()` 


