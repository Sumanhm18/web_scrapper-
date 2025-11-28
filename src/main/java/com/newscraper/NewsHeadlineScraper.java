package com.newscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple news headline scraper using Jsoup
 * Handles different website structures and mimics browser requests
 */
public class NewsHeadlineScraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int TIMEOUT = 10000; // 10 seconds

    /**
     * Scrape headlines from a website using CSS selectors
     * 
     * @param url              The website URL
     * @param headlineSelector CSS selector for headlines (e.g., "h2",
     *                         ".article-title")
     * @return List of headlines
     */
    public List<String> scrapeHeadlines(String url, String headlineSelector) {
        List<String> headlines = new ArrayList<>();

        try {
            // Connect with browser-like headers to avoid being blocked
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .header("Upgrade-Insecure-Requests", "1")
                    .timeout(TIMEOUT)
                    .get();

            // Extract headlines using the provided selector
            Elements headlineElements = doc.select(headlineSelector);

            for (Element element : headlineElements) {
                String headline = element.text().trim();
                if (!headline.isEmpty()) {
                    headlines.add(headline);
                }
            }

            System.out.println("✓ Successfully scraped " + headlines.size() + " headlines from " + url);

        } catch (IOException e) {
            System.err.println("✗ Error scraping " + url + ": " + e.getMessage());
        }

        return headlines;
    }

    /**
     * Scrape headlines with link URLs
     * 
     * @param url              The website URL
     * @param headlineSelector CSS selector for headline elements
     * @return List of headline objects with text and URL
     */
    public List<Headline> scrapeHeadlinesWithLinks(String url, String headlineSelector) {
        List<Headline> headlines = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .timeout(TIMEOUT)
                    .get();

            Elements headlineElements = doc.select(headlineSelector);

            for (Element element : headlineElements) {
                String text = element.text().trim();
                String link = element.attr("abs:href"); // Get absolute URL

                if (!text.isEmpty()) {
                    headlines.add(new Headline(text, link));
                }
            }

            System.out.println("✓ Successfully scraped " + headlines.size() + " headlines with links");

        } catch (IOException e) {
            System.err.println("✗ Error scraping: " + e.getMessage());
        }

        return headlines;
    }
//scrapes the header into the headline object
    /**
     * Simple class to hold headline data
     */
    public static class Headline {
        private String text;
        private String url;

        public Headline(String text, String url) {
            this.text = text;
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return text + (url != null && !url.isEmpty() ? " [" + url + "]" : "");
        }
    }
}
