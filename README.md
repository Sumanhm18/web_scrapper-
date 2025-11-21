# News Headline Scraper

A simple Java web scraper that fetches news headlines from websites using Jsoup.

## Features

✅ **Browser-like headers** - Mimics real browser requests to avoid being blocked  
✅ **Flexible selectors** - Works with different website structures using CSS selectors  
✅ **Error handling** - Gracefully handles connection issues and timeouts  
✅ **Link extraction** - Can scrape both headlines and their URLs

## Requirements

- Java 17+
- Maven

## Dependencies

- Jsoup 1.17.1 - HTML parser and web scraper
- SLF4J - Logging framework

## Quick Start

### 1. Build the project

```bash
mvn clean compile
```

### 2. Run the demo

```bash
mvn exec:java -Dexec.mainClass="com.newscraper.Main"
```

## Usage

### Basic headline scraping

```java
NewsHeadlineScraper scraper = new NewsHeadlineScraper();

// Scrape headlines using CSS selector
List<String> headlines = scraper.scrapeHeadlines(
    "https://www.bbc.com/news",
    "h2"  // CSS selector for headlines
);

// Display results
headlines.forEach(System.out::println);
```

### Scrape headlines with links

```java
List<NewsHeadlineScraper.Headline> headlines = scraper.scrapeHeadlinesWithLinks(
    "https://news.ycombinator.com",
    "span.titleline > a"
);

for (NewsHeadlineScraper.Headline h : headlines) {
    System.out.println(h.getText());
    System.out.println("Link: " + h.getUrl());
}
```

## Finding CSS Selectors

1. Open the website in your browser
2. Right-click on a headline → Inspect Element
3. Note the tag name and classes
4. Create a CSS selector:
   - `h2` - all h2 tags
   - `.article-title` - elements with class "article-title"
   - `h3.card-headline` - h3 tags with class "card-headline"
   - `span.titleline > a` - anchor tags inside span.titleline

## Examples

The demo includes examples for:

- **BBC News** - `h2`
- **Hacker News** - `span.titleline > a`
- **The Guardian** - `h3.card-headline`

## Anti-Blocking Features

The scraper includes several headers to appear like a real browser:

- User-Agent (Chrome on macOS)
- Accept headers
- Accept-Language
- Connection keep-alive
- Timeout handling (10 seconds)

## Notes

⚠️ **Always check the website's robots.txt and terms of service before scraping**  
⚠️ **Be respectful with request frequency to avoid overloading servers**  
⚠️ **Website structures change - selectors may need updates**

## Tech Stack

- **Java 17**
- **Jsoup** - HTML parsing and web scraping
- **Maven** - Dependency management and build tool
