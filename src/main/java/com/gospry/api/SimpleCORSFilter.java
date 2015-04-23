package com.gospry.api;

/**
 * Filter to support cross origin requests (CORS)
 *
 * http://www.html5rocks.com/en/tutorials/cors/
 * https://spring.io/guides/gs/rest-service-cors/
 * https://spring.io/understanding/CORS
 */

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String origin = req.getHeader("Origin");

        //comma separated list of all allowed domains excl. this host
        res.setHeader("Access-Control-Allow-Origin", origin);

        //comma separated list of all allowed cross domain request methods
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        //force to use cache to minimize number of preflight requests
        res.setHeader("Access-Control-Max-Age", "3600");

        //comma separated list of all allowed request headers (case sensitive!)
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Accept, Access-Control-Request-Method, Content-Type, Authorization");

        //uncomment following line if you need cookie support
        //response.setHeader("Access-Control-Allow-Credentials", "true");

        if (req.getMethod().equals("OPTIONS")) {
            try {
                res.getWriter().print("OK");
                res.getWriter().flush();
            } catch (IOException e) {
                //todo log
            }
        }else{
            chain.doFilter(req, res);
        }
    }



}
