package com.newscraper;

import java.util.List;

/**
 * Demo application showing how to use the NewsHeadlineScraper
 */
public class Main {

    public static void main(String[] args) {
        NewsHeadlineScraper scraper = new NewsHeadlineScraper();

        System.out.println("========================================");
        System.out.println("    News Headline Scraper Demo");
        System.out.println("========================================\n");

        // Example 1: BBC News
        System.out.println("📰 Scraping BBC News...");
        List<String> bbcHeadlines = scraper.scrapeHeadlines(
                "https://www.bbc.com/news",
                "h2" // BBC uses h2 tags for headlines
        );
        displayHeadlines(bbcHeadlines, 5);

        System.out.println("\n" + "=".repeat(40) + "\n");

        // Example 2: Hacker News
        System.out.println("📰 Scraping Hacker News...");
        List<NewsHeadlineScraper.Headline> hnHeadlines = scraper.scrapeHeadlinesWithLinks(
                "https://news.ycombinator.com",
                "span.titleline > a" // Hacker News structure
        );
        displayHeadlinesWithLinks(hnHeadlines, 10);

        System.out.println("\n" + "=".repeat(40) + "\n");

        // Example 3: The Guardian
        System.out.println("📰 Scraping The Guardian...");
        List<String> guardianHeadlines = scraper.scrapeHeadlines(
                "https://www.theguardian.com/international",
                "h3.card-headline" // Guardian uses h3 with card-headline class
        );
        displayHeadlines(guardianHeadlines, 5);

        System.out.println("\n========================================");
        System.out.println("Done!");
        System.out.println("========================================");
    }

    /**
     * Display headlines (simple version)
     */
    private static void displayHeadlines(List<String> headlines, int limit) {
        if (headlines.isEmpty()) {
            System.out.println("  No headlines found.");
            return;
        }

        int count = Math.min(headlines.size(), limit);
        for (int i = 0; i < count; i++) {
            System.out.println("  " + (i + 1) + ". " + headlines.get(i));
        }

        if (headlines.size() > limit) {
            System.out.println("  ... and " + (headlines.size() - limit) + " more");
        }
    }

    /**
     * Display headlines with links
     */
    private static void displayHeadlinesWithLinks(List<NewsHeadlineScraper.Headline> headlines, int limit) {
        if (headlines.isEmpty()) {
            System.out.println("  No headlines found.");
            return;
        }

        int count = Math.min(headlines.size(), limit);
        for (int i = 0; i < count; i++) {
            NewsHeadlineScraper.Headline h = headlines.get(i);
            System.out.println("  " + (i + 1) + ". " + h.getText());
            if (h.getUrl() != null && !h.getUrl().isEmpty()) {
                System.out.println("     → " + h.getUrl());
            }
        }

        if (headlines.size() > limit) {
            System.out.println("  ... and " + (headlines.size() - limit) + " more");
        }
    }
}
