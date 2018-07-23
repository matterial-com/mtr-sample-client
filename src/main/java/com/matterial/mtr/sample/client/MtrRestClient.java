package com.matterial.mtr.sample.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matterial.mtr.api.Api;
import com.matterial.mtr.api.object.AdditionalProperty;
import com.matterial.mtr.api.object.Attachment;
import com.matterial.mtr.api.object.Document;
import com.matterial.mtr.api.object.ListResult;
import com.matterial.mtr.api.object.LoginData;
import com.matterial.mtr.api.object.Logon;

/**
 * <strong>MtrRestClient</strong>
 */
public class MtrRestClient extends RestClient {

    private static final Logger logger = LoggerFactory.getLogger(MtrRestClient.class);

    public static final String APPLICATION_PATH = "api";

    protected String basePath;

    public MtrRestClient(String basePath) {
        this.basePath = basePath;
    }

    public LoginData login(String userName, String password) {
        String path = this.basePath + APPLICATION_PATH+"/" + Api.LOGON;
        logger.trace("login()");
        Logon logon = new Logon(userName, password);
        LoginData loginData = null;
        try(Response resp = this.post(path, logon)) {
            this.updateSession(resp);
            loginData = resp.readEntity(LoginData.class);
            logger.trace("login() - sessionId: " + this.sessionId);
        }
        return loginData;
    }

    public void logout() {
        String path = this.basePath + APPLICATION_PATH + "/" + Api.LOGON;
        logger.trace("logout() - -- LOGOUT ------------");
        try(Response resp = this.deleteRequestPlain(path);) {
            boolean loggedOut = resp.readEntity(Boolean.class);
            logger.trace("logout() - loggedOut: " + loggedOut);
        }
    }

    public ListResult<Document> loadDocuments() {
        String path = this.basePath + APPLICATION_PATH+"/" + Api.DOCUMENT;
        logger.debug("loadDocuments()");
        // *** load metadata of all default-documents;
        ListResult<Document> lr = null;
        try(Response resp = this.get(path+
                 "?"+Api.PARAM_ALL_LANGUAGES+"=true"+
                 "&"+Api.PARAM_LOAD_ADDITIONAL_PROPERTIES+"=true"+
                 "&"+Api.PARAM_LOAD_ATTACHMENTS+"=true"+
                 "&"+Api.PARAM_LIMIT+"=10"+
                 "&"+Api.PARAM_COUNT+"=true")) {
            lr = resp.readEntity(new GenericType<ListResult<Document>>() {});
        }
        return lr;
    }

    public String loadDocumentContent(long documentId, String languageKey) {
        String path = this.basePath + APPLICATION_PATH+"/" + Api.DOCUMENT;
        logger.debug("loadDocumentContent({}, {})", documentId, languageKey);
        // *** load content;
        String content = null;
        try(Response resp = this.getNothing(path+"/"+documentId+"/"+Api.FILE+"?"+Api.PARAM_LANGUAGE_KEY+"="+languageKey)) {
            logger.trace("loadDocumentContent() - mediaType: " + resp.getMediaType());
            logger.trace("loadDocumentContent() - length: " + resp.getLength());
            try(InputStream in = resp.readEntity(InputStream.class)) {
                if(in != null) {
                    content = IOUtils.toString(in, CharEncoding.UTF_8);
                }
            }
            catch (IOException e) {
                logger.error("loadDocumentContent() - ", e);
            }
        }
        return content;
    }

    @SuppressWarnings("resource")
    public InputStream loadAttachmentContent(long documentId, long documentLanguageVersionId, long attachmentId) {
        String path = this.basePath + APPLICATION_PATH+"/" + Api.DOCUMENT;
        logger.debug("loadAttachmentContent({}, {}, {})", documentId, documentLanguageVersionId, attachmentId);
        // *** load content;
        Response resp = this.getNothing(path+"/"+documentId+"/"+
                                        Api.VERSION+"/"+documentLanguageVersionId+"/"+
                                        Api.ATTACHMENT+"/"+attachmentId);
        logger.trace("loadAttachmentContent() - mediaType: " + resp.getMediaType());
        logger.trace("loadAttachmentContent() - length: " + resp.getLength());
        return resp.readEntity(InputStream.class);
    }

    /**
     * sample-usage;
     */
    public static void main(String[] args) {
        String basePath = "http://localhost:8080/mtr-backend/";
        logger.info("USING: "+basePath);

        MtrRestClient client = new MtrRestClient(basePath);
        LoginData ld = client.login("user", "xxx");
        logger.info("CURRENT DS: "+ld.getCurrentDataSource().getDisplayName());

        ListResult<Document> lr = client.loadDocuments();
        List<Document> docs = lr.getResults();
        long count = lr.getTotalHits();
        logger.info("COUNT OF DOCUMENTS: " + count);
        for (Document document : docs) {
            logger.info("DOCUMENT FOUND: "+document.getId()+"/"+document.getLanguageVersionLanguageKey()+" => "+document.getLanguageVersionTitle());
            for (AdditionalProperty ap : document.getAdditionalProperties()) {
                logger.info("ADDITIONAL PROPERTY: id: "+ap.getId()+", type: "+ap.getPropertyType()+", name: "+ap.getName());
            }
            List<Attachment> atts = document.getAttachments();
            for (Attachment att : atts) {
                logger.info("ATTACHMENT: "+att.getId()+" => "+att.getName());
                try(InputStream attStream = client.loadAttachmentContent(document.getId(), document.getLanguageVersionId(), att.getId())) {
                    System.out.println("ATTACHMENT: "+attStream);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String content = client.loadDocumentContent(document.getId(), document.getLanguageVersionLanguageKey());
            logger.info("DOCUMENT CONTENT: \n" + content+"\n*********************************************************\n\n");
        }

        client.logout();
    }

}
