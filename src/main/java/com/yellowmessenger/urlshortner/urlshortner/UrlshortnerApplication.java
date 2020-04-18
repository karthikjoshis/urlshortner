package com.yellowmessenger.urlshortner.urlshortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * Main application class where program begins.
 */
@SpringBootApplication
public class UrlshortnerApplication {

	public static void main(String[] args)
	{
	    List<Object> objects = new LinkedList<>();
	    objects.add("Today");
	    objects.add(123.13);

	//	SpringApplication.run(UrlshortnerApplication.class, args);

        Map<Integer, List<Object>> map = new LinkedHashMap<>();
        map.put(1,objects);
        map.put(2,objects);
        System.out.println("");

        System.out.println("added changes");

        System.out.println(map.get(1).get(0));

        for (Map.Entry<Integer,List<Object>> m : map.entrySet())
        {
            System.out.println("value is:- " + m.getKey() + "-> " + m.getValue());
        }
	}

}
