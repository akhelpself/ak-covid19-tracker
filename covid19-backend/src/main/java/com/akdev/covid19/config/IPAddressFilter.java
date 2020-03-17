package com.akdev.covid19.config;

import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.services.impl.RawDBGeoIPLocationService;
import com.akdev.covid19.utils.SpringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class IPAddressFilter implements Filter {

    private Logger logger = LogManager.getLogger(IPAddressFilter.class);

    @Autowired
    public RawDBGeoIPLocationService rawDBGeoIPLocationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession httpSession = request.getSession();
        GeoIP geoIP = (GeoIP) httpSession.getAttribute("location");
        if (geoIP == null) {
            //check cookie
            Cookie[] cookies = request.getCookies();
            if("/".equals(request.getRequestURI())) {
                try {
                    String ipAddress = SpringUtils.getClientIp(request);
                    geoIP = rawDBGeoIPLocationService.getLocation(ipAddress);
                } catch (Exception e) {
                    logger.error("Cannot find IP Address, {}", e.getMessage());
                }
            }

            if (geoIP == null) {
                geoIP = new GeoIP("USA", "US", null, "New York", "40.721092", "-73.999789");
            }
            geoIP.setType(1);
            httpSession.setAttribute("location", geoIP);
        }

        //call next filter in the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
