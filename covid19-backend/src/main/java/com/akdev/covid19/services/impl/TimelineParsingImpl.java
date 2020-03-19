package com.akdev.covid19.services.impl;

import com.akdev.covid19.model.Timeline;
import com.akdev.covid19.services.TimelineParsing;
import com.akdev.covid19.utils.Spy;
import com.fasterxml.jackson.core.type.TypeReference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimelineParsingImpl implements TimelineParsing {

    private static final String TIMELINE_DATA = "TIMELINE_DATA";

    private CacheManager cacheManager;

    public TimelineParsingImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Timeline> getData() throws Exception {
        if (cacheManager.invalid(TIMELINE_DATA)) {
            return cacheManager.get(TIMELINE_DATA, new TypeReference<List<Timeline>>() {});
        }
        List<Timeline> results = new ArrayList<>();
        Document document = Jsoup.connect("https://bnonews.com/index.php/2020/02/the-latest-coronavirus-cases/").get();
        int flag = 0;
        for (Element e: document.getElementById("mvp-content-main").getAllElements()) {
            if (Spy.outerContextContains(e, "h4#; div#left relative; div#left relative;")) {
                Timeline t = new Timeline();
                t.setTitle(true);
                t.setContent(e.text());
                results.add(t);
                flag ++;
            }

            if (flag > 0
                    && Spy.outerContextContains(e, "li#; ul#; div#left relative; div#left relative; div#left relative;")
                    && "li".equals(e.tagName())) {
                Timeline t = new Timeline();
                t.setTitle(false);
                t.setContent(e.ownText().replaceAll("\\(.*?\\)", ""));

                String href = e.getElementsByTag("a").attr("href");
                t.setSource(href);
                results.add(t);
            }
        }
        cacheManager.put(TIMELINE_DATA, results);
        return results;
    }
}
