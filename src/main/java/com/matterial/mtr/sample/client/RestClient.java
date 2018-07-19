package com.matterial.mtr.sample.client;

import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>RestClient</strong>
 */
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public static final String SESSION_COOKIE_NAME = "JSESSIONID";

    public static final int REQUEST_NOTHING = 0;
    public static final int REQUEST_XML = 1;
    public static final int REQUEST_JSON = 2;
    public static final int REQUEST_PLAIN = 3;
    public static final int REQUEST_HTML = 4;

    protected String sessionId;


    protected Response getNothing(String path) {
        Response resp = this.createClient(path, REQUEST_NOTHING).get();
        logger.trace("get() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response getPlain(String path) {
        Response resp = this.createClient(path, REQUEST_PLAIN).get();
        logger.trace("getPlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response get(String path) {
        Response resp = this.createClient(path, REQUEST_JSON).get();
        logger.trace("get() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putNothing(String path) {
        Response resp = this.createClient(path, REQUEST_JSON).put(null);
        logger.trace("putNothing() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putNothingRequestPlain(String path) {
        Response resp = this.createClient(path, REQUEST_PLAIN).put(null);
        logger.trace("putNothingRequestPlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putNothingRequestNothing(String path) {
        Response resp = this.createClient(path, REQUEST_NOTHING).put(null);
        logger.trace("putNothingRequestNothing() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putPlainRequestNothing(String path, String entity) {
        Response resp = this.createClient(path, REQUEST_NOTHING).put(Entity.text(entity));
        logger.trace("putPlainRequestNothing() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putPlainRequestPlain(String path, String entity) {
        Response resp = this.createClient(path, REQUEST_PLAIN).put(Entity.text(entity));
        logger.trace("putPlainRequestPlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putPlain(String path, String entity) {
        Response resp = this.createClient(path, REQUEST_JSON).put(Entity.text(entity));
        logger.trace("putPlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putRequestNothing(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_NOTHING).put(Entity.json(entity));
        logger.trace("putRequestNothing() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response putRequestPlain(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_PLAIN).put(Entity.json(entity));
        logger.trace("putRequestPlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response put(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_JSON).put(Entity.json(entity));
        logger.trace("put() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response post(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_JSON).post(Entity.json(entity));
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response postNothing(String path) {
        Response resp = this.createClient(path, REQUEST_JSON).post(null);
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response postPlain(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_JSON).post(Entity.text(entity));
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response postPlainRequestPlain(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_PLAIN).post(Entity.text(entity));
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response postPlainRequestHtml(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_HTML).post(Entity.text(entity));
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response postRequestNothing(String path, Object entity) {
        Response resp = this.createClient(path, REQUEST_NOTHING).post(Entity.json(entity));
        logger.trace("post() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response delete(String path) {
        Response resp = this.createClient(path, REQUEST_JSON).delete();
        logger.trace("delete() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response deleteRequestPlain(String path) {
        Response resp = this.createClient(path, REQUEST_PLAIN).delete();
        logger.trace("deletePlain() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Response deleteRequestNothing(String path) {
        Response resp = this.createClient(path, REQUEST_NOTHING).delete();
        logger.trace("deleteNothing() - STATUS: " + resp.getStatus());
        return resp;
    }

    protected Builder createClient(String path, int acceptedResponse) {
        Client client = ResteasyClientBuilder.newClient();
        WebTarget target = client.target(path);
        Builder builder = null;
        if(acceptedResponse == REQUEST_XML) {
            builder = target.request(MediaType.APPLICATION_XML_TYPE);
        }
        else if(acceptedResponse == REQUEST_JSON) {
            builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        }
        else if(acceptedResponse == REQUEST_PLAIN) {
            builder = target.request(MediaType.TEXT_PLAIN_TYPE);
        }
        else if(acceptedResponse == REQUEST_HTML) {
            builder = target.request(MediaType.TEXT_HTML_TYPE);
        }
        else {
            builder = target.request();
        }
        builder.cookie(SESSION_COOKIE_NAME, this.getSessionId());
        return builder;
    }

    /**
     * generate a random session id
     */
    protected String getSessionId() {
        if(this.sessionId == null) {
            this.sessionId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().concat(".node1");
        }
        return this.sessionId;
    }

    protected void updateSession(Response resp) {
        Map<String, NewCookie> cookies = resp.getCookies();
        NewCookie sessionCookie = cookies.get(SESSION_COOKIE_NAME);
        if(sessionCookie != null) {
            String sessionId = sessionCookie.getValue();
            this.sessionId = sessionId;
            logger.trace("updateSession() - SESSION_ID UPDATED...");
        }
        logger.trace("updateSession() - USING SESSION: {} ", this.sessionId);
    }

}
