/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.parser.rss;

import hr.algebra.dao.model.Author;
import hr.algebra.dao.model.Post;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
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

/**
 *
 * @author miki
 */
public class RedditParser {
    
    private static final String DIR = "assets";
    
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
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);

                        if (tagType.isPresent() && tagType.get().equals(TagType.ENTRY)) {
                            entry = new Entry();
                            entries.add(entry);
                        }
                        if (tagType.isPresent() && tagType.get().equals(TagType.AUTHOR)) {
                            author = new Author();
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && entry != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
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
                                case CATEGORY -> { 
                                    if (startElement != null) {
                                        Attribute atr = startElement.getAttributeByName(new QName("label"));
                                        if (atr != null) {
                                            entry.setSubredditName(atr.getValue());
                                        }
                                    }
                                }
                                case CONTENT -> {
                                    if (!data.isEmpty()) {
                                        entry.setContent(data);
                                    }
                                }
                                case ID -> {
                                    if (!data.isEmpty()) {
                                        entry.setRedditId(data);
                                    }
                                }
                                case LINK -> { 
                                    if (startElement != null) {
                                        Attribute atr = startElement.getAttributeByName(new QName("href"));
                                        if (atr != null) {
                                            entry.setLink(atr.getValue());
                                        }
                                    }
                                }
                                case MEDIA_THUMBNAIL -> {
                                    if (startElement != null && entry.getThumbnailLink() == null) {
                                        Attribute atr = startElement.getAttributeByName(new QName("url"));
                                        if (atr != null) {
                                            handlePicture(entry, atr.getValue());
                                        }
                                    }
                                }
                                case TITLE -> {
                                    if (!data.isEmpty()) {
                                       entry.setTitle(data);
                                    }
                                }
                                case UPDATED -> {
                                    if (!data.isEmpty()) {
                                        entry.setUpdatedDate(LocalDateTime.parse(data, Entry.DATE_FORMATTER));
                                    }
                                }
                                case PUBLISHED -> {
                                    if (!data.isEmpty()) {
                                        entry.setPublishedDate(LocalDateTime.parse(data, Entry.DATE_FORMATTER));
                                    }
                                }
                            }
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if (tagType.isPresent() && tagType.get().equals(TagType.AUTHOR)) {
                            entry.setAuthor(author);
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
            
            String ext = cleanUrl.matches("\\.(jpg|jpeg|png|gif)") ?
                    cleanUrl.substring(cleanUrl.lastIndexOf('.')) :
                    ".jpg";

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
        MEDIA_THUMBNAIL("media:thumbnail"),
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
