package com.yellowmessenger.urlshortner.urlshortner.services;

import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author karthikjoshi
 * This class is to validate and restrict the number of requests per user(s) session for given time.
 */
@Service
public class RateLimitingService {

    static Map<String, Queue<Long>> holder = new HashMap<String, Queue<Long>>();
    static int TIME_LIMIT = 1000;
    static int MAX_HITS = 2;

    public static boolean hitAPI(String clientId, long timeOfHit)
    {
        boolean result = isAllowed(clientId, timeOfHit);
        if(result)
        {
            System.out.println("hit @ " + timeOfHit + "ms");
            return true;
        }
        else
            System.out.println("blocked @ " + timeOfHit + "ms");
            return false;
    }

    public static boolean isAllowed(String clientId, long timeOfHit) {
        boolean result = false;
        if(holder.containsKey(clientId)) {
            //client has hit before, keep polling the queue till you reach a time whose diff with timeOfHit is less than 1000
            Queue<Long> timings = holder.get(clientId);
            while(!timings.isEmpty() && timeOfHit-timings.peek() >= TIME_LIMIT) {
                timings.poll();
            }
            //the remaining queue is the number of hits within the 1000 ms
            if(timings.size() < MAX_HITS) {
                //add the timeOfHit
                timings.add(timeOfHit);
                //update the holder
                holder.put(clientId, timings);
                result = true;
            }
        } else {
            //client hitting first time, simply put it in holder
            Queue<Long> timings = new LinkedList<Long>();
            timings.add(timeOfHit);
            holder.put(clientId, timings);
            result = true;
        }
        return result;
    }
}

