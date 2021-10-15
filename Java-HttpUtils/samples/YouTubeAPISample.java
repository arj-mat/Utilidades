/*
 * httputils - 14/10/2021
 * @author Mateus Araújo - https://github.com/arj-mat
 */

import github.arjmat.httputils.HttpUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class YouTubeAPISample {
    @Test
    public void test() {
        final String YOUTUBE_API_KEY = "YOUR_API_KEY"; // https://console.cloud.google.com/apis/credentials
        final String VIDEO_ID = "gjBLo7cDFSw";

        System.out.println( "Fetching video information..." );

        Map<String, String> headers = new HashMap<>();
        headers.put( "Accept", "application/json" );

        var result = HttpUtils.<YoutubeVideoDTO>get(
                YoutubeVideoDTO.class,
                "https://youtube.googleapis.com/youtube/v3/videos?part=snippet&id=" + VIDEO_ID + "&key=" + YOUTUBE_API_KEY,
                headers
        );

        System.out.printf( "Response status: %d\n", result.status );

        if ( result.data.isPresent() ) {
            var videoInfo = result.data.get();

            System.out.printf(
                    "\tTitle: %s\n\tThumbnail: %s\n\tAuthor: %s\n",
                    videoInfo.getItems().get( 0 ).snippet.title,
                    videoInfo.getItems().get( 0 ).snippet.thumbnails.high.url,
                    videoInfo.getItems().get( 0 ).snippet.channelTitle
            );
        } else {
            System.out.println( "Failed to load data." );
        }
    }
}
