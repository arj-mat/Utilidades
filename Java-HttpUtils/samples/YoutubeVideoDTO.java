/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import lombok.Data;
import java.util.List;

// Generated with https://json2csharp.com/json-to-pojo

@Data
public class YoutubeVideoDTO {
    @Data
    public static class Default {
        public String url;
        public int width;
        public int height;
    }

    @Data
    public static class Medium {
        public String url;
        public int width;
        public int height;
    }

    @Data
    public static class High {
        public String url;
        public int width;
        public int height;
    }

    @Data
    public static class Standard {
        public String url;
        public int width;
        public int height;
    }

    @Data
    public static class Maxres {
        public String url;
        public int width;
        public int height;
    }

    @Data
    public static class Thumbnails {
        public Medium medium;
        public High high;
        public Standard standard;
        public Maxres maxres;
    }

    @Data
    public static class Localized {
        public String title;
        public String description;
    }

    @Data
    public static class Snippet {
        public String publishedAt;
        public String channelId;
        public String title;
        public String description;
        public Thumbnails thumbnails;
        public String channelTitle;
        public List<String> tags;
        public String categoryId;
        public String liveBroadcastContent;
        public String defaultLanguage;
        public Localized localized;
        public String defaultAudioLanguage;
    }

    @Data
    public static class Item {
        public String kind;
        public String etag;
        public String id;
        public Snippet snippet;
    }

    @Data
    public static class PageInfo {
        public int totalResults;
        public int resultsPerPage;
    }

    private String kind;
    private String etag;
    private List<Item> items;
    private PageInfo pageInfo;
}
