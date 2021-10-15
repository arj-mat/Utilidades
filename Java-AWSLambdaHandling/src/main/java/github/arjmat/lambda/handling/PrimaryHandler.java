package github.arjmat.lambda.handling;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class PrimaryHandler implements RequestHandler<PrimaryHandler.Event, Object> {
    public static class Event {
        public String method;
        public String resourcePath;
        public String remoteIp;
        public Object body;
        public Map<String, String> queryParams;
        public Map<String, String> pathParams;
        public Map<String, String> headers;
    }

    @Override
    public Object handleRequest(Event event, Context context) {
        return null;
    }
}
