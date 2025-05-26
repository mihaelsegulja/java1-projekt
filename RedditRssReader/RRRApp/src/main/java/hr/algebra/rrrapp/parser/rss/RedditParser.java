/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.parser.rss;

import hr.algebra.rrrapp.parser.model.Entry;
import hr.algebra.dao.model.Author;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 *
 * @author miki
 */
public class RedditParser {
    
    private static final String DIR = "assets";
    private static final Author DELETED_AUTHOR = new Author("u/deleted", "https://www.reddit.com/user/deleted");
    
    private RedditParser() { }
    
    public static List<Entry> parse(String rssUrl) throws IOException, XMLStreamException {
        List<Entry> entries = new ArrayList<>();
        
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(rssUrl);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            
            Optional<TagType> tagType = Optional.empty();
            
            Entry entry = null;
            StartElement startElement = null;
            Author author = null;
            
            boolean insideContent = false;
            StringBuilder contentBuilder = new StringBuilder();
            
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);

                        if (tagType.isPresent()) {
                            switch (tagType.get()) {
                                case ENTRY -> {
                                    entry = new Entry();
                                    entries.add(entry);
                                }
                                case AUTHOR -> {
                                    author = new Author();
                                }
                                case MEDIA_THUMBNAIL -> {
                                    if (entry != null && entry.getThumbnailLink() == null) {
                                        Attribute atr = startElement.getAttributeByName(new QName("url"));
                                        if (atr != null) {
                                            handlePicture(entry, atr.getValue());
                                        }
                                    }
                                }
                                case CONTENT -> {
                                    insideContent = true;
                                    contentBuilder.setLength(0); // reset buffer
                                }
                                case LINK -> {
                                    if (entry != null) {
                                        Attribute hrefAttr = startElement.getAttributeByName(new QName("href"));
                                        if (hrefAttr != null) {
                                            entry.setLink(hrefAttr.getValue());
                                        }
                                    }
                                }
                                case CATEGORY -> {
                                    if (entry != null) {
                                        Attribute labelAttr = startElement.getAttributeByName(new QName("label"));
                                        if (labelAttr != null) {
                                            entry.setSubredditName(labelAttr.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && entry != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            
                            if (insideContent) {
                                contentBuilder.append(event.asCharacters().getData());
                            }
                            
                            switch(tagType.get()){
                                case AUTHOR_NAME -> {
                                    if (!data.isEmpty()) {
                                        author.setName(data);
                                    }
                                }
                                case AUTHOR_URI -> {
                                    if (!data.isEmpty()) {
                                        author.setLink(data);
                                    }
                                }
                                case ID -> {
                                    if (!data.isEmpty()) {
                                        entry.setRedditId(data);
                                    }
                                }
                                case TITLE -> {
                                    if (!data.isEmpty()) {
                                       entry.setTitle(data);
                                    }
                                }
                                case UPDATED -> {
                                    if (!data.isEmpty()) {
                                        entry.setUpdatedDate(OffsetDateTime.parse(data, Entry.DATE_FORMATTER));
                                    }
                                }
                                case PUBLISHED -> {
                                    if (!data.isEmpty()) {
                                        entry.setPublishedDate(OffsetDateTime.parse(data, Entry.DATE_FORMATTER));
                                    }
                                }
                            }
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        String endTag = event.asEndElement().getName().getLocalPart();
                        Optional<TagType> endTagType = TagType.from(endTag);
                        
                        if (endTagType.isPresent()) {
                            switch (endTagType.get()) {
                                case AUTHOR -> {
                                    if (entry != null && author != null) {
                                        entry.setAuthor(author);
                                        author = null;
                                    }
                                }
                                case ENTRY -> {
                                    if (entry != null && entry.getAuthor() == null) {
                                        entry.setAuthor(DELETED_AUTHOR);
                                    }
                                }
                                case CONTENT -> {
                                    if (entry != null) {
                                        String rawHtml = contentBuilder.toString().trim();
                                        String cleanContent = Jsoup.clean(rawHtml, Safelist.relaxed());
                                        entry.setContent(cleanContent);
                                        insideContent = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return entries;
    }
    
    private static void handlePicture(Entry entry, String pictureUrl) {
        try {
            // Remove the query params
            String cleanUrl = pictureUrl.split("\\?")[0];

            String ext = ".jpg"; // fallback
            int dotIndex = cleanUrl.lastIndexOf('.');
            if (dotIndex != -1 && dotIndex < cleanUrl.length() - 1) {
                String candidate = cleanUrl.substring(dotIndex).toLowerCase();
                if (candidate.matches("\\.(jpg|jpeg|png|gif)")) {
                    ext = candidate;
                }
            }

            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            entry.setThumbnailLink(localPicturePath);

        } catch (IOException ex) {
            Logger.getLogger(RedditParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private enum TagType {
        ENTRY("entry"),
        AUTHOR("author"),
        AUTHOR_NAME("name"),
        AUTHOR_URI("uri"),
        CATEGORY("category"),
        CONTENT("content"),
        ID("id"),
        MEDIA_THUMBNAIL("thumbnail"),
        LINK("link"),
        UPDATED("updated"),
        PUBLISHED("published"),
        TITLE("title");
        
        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
