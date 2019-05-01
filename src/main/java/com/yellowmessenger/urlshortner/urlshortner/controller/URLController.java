package com.yellowmessenger.urlshortner.urlshortner.controller;

import com.yellowmessenger.urlshortner.urlshortner.commons.URLValidator;
import com.yellowmessenger.urlshortner.urlshortner.services.RateLimitingService;
import com.yellowmessenger.urlshortner.urlshortner.dto.RateInput;
import com.yellowmessenger.urlshortner.urlshortner.dto.ShortenRequest;
import com.yellowmessenger.urlshortner.urlshortner.services.URLConverterService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author karthikjoshi
 * This class has two API method:- 1)Which returns a SHORTENED URL for the given url input.
 *                                 2)Re-directs User to the url request which was made in the first api.
 */
@RestController
public class URLController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);

    @Autowired
    private URLConverterService urlConverterService;

    @Autowired
    private RateLimitingService rateLimitingService;

    @ApiOperation("Api to get shortened url for the given input")
    @RequestMapping(value = "/shortener", method=RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception
    {
        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
        List<RateInput> inputs = new ArrayList<RateInput>();
        inputs.add(new RateInput(request.getRemoteAddr(),System.currentTimeMillis()));

        if (!inputs.isEmpty())
        {
            for (RateInput input : inputs)
            {
                boolean result = RateLimitingService.hitAPI(input.clientId,input.timeOfHit);
                /**
                 * Throws an exception if there are more than specific number of hits per second from a user session.
                 * In this example restriction has been made for 30 per minute(which can be changed accordingly).
                 */
                if (!result)
                {
                    throw new Exception("Rate limit exceeded");
                }
            }
        }
        String longUrl = shortenRequest.getUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl))
        {
            String localURL = request.getRequestURL().toString();
            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
            LOGGER.info("Shortened url to: " + shortenedUrl);
            return buildResponse(shortenedUrl,"Shortened URL returned successfully");
        }
        throw new Exception("Please enter a valid URL");
    }

    @ApiOperation("Api to redirect to the original URL")
    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception
    {
        LOGGER.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        LOGGER.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }
}



