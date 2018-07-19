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
    // *** modules;
    public static final String LOGON = "logon";
    public static final String DOCUMENT = "document";
    public static final String ATTACHMENT = "attachment";

    // *** document-path;
    public static final String VERSION = "version";
    public static final String FILE = "file";
    public static final String LANGUAGE = "language";
    public static final String TRASH = "trash";
    public static final String UNTRASH = "untrash";
    public static final String ARCHIVE = "archive";
    public static final String UNARCHIVE = "unarchive";
    public static final String FOLLOW = "follow";
    public static final String UNFOLLOW = "unfollow";
    public static final String REVIEW = "review";
    public static final String REVIEW_DECLINE = "reviewdecline";
    public static final String CONVERT = "convert";
    public static final String REMOVAL = "removal";
    public static final String QUEUE = "queue";
    public static final String PDF_CONVERSION = "pdfconversion";
    public static final String SNAP = "snap";

    // *** general params;
    public static final String PARAM_DOCUMENT_ID = "documentId";
    public static final String PARAM_DOCUMENT_LANGUAGE_VERSION_ID = "documentLanguageVersionId";
    public static final String PARAM_ACCOUNT_ID = "accountId";
    public static final String PARAM_CONTACT_ID = "contactId";
    public static final String PARAM_ROLE_ID = "roleId";
    public static final String PARAM_ENTITY_TYPE_ID = "entityTypeId";
    public static final String PARAM_ACTIVE = "active";
    public static final String PARAM_LANGUAGE_KEY = "languageKey";

    public static final String PARAM_MARKDOWN_TO_HTML = "markdownToHtml";
    public static final String PARAM_LOAD_PDF = "loadPdf";
    public static final String PARAM_LOAD_THUMBNAIL = "loadThumbnail";
    public static final String PARAM_LOAD_CATEGORY_IDS = "loadCategoryIds";

    public static final String PARAM_INSTANCE_ID = "instanceId";
    public static final String PARAM_INSTANCE = "instance";
    public static final String PARAM_COUNT = "count";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_ORDER_BY = "orderBy";
    public static final String PARAM_ORDER_DIR = "orderDir";

    // *** document params;
    public static final String PARAM_DOCUMENT_LANGUAGE_VERSION_CAS_ID = "documentLanguageVersionCasId";
    public static final String PARAM_ALL_LANGUAGES = "allLanguages";
    public static final String PARAM_ALL_VERSIONS = "allVersions";
    public static final String PARAM_LANGUAGE_PREFER = "languagePrefer";
    public static final String PARAM_LANGUAGE_EXCLUDE = "languageExclude";
    public static final String PARAM_CATEGORY_IDS_AND = "categoryIdsAnd";
    public static final String PARAM_CATEGORY_IDS_OR = "categoryIdsOr";
    public static final String PARAM_CATEGORY_AGGREGATIONS = "categoryAggregations";
    public static final String PARAM_LANGUAGE_AGGREGATIONS = "languageAggregations";
    public static final String PARAM_LAST_CHANGE_AGGREGATIONS = "lastChangeAggregations";
    public static final String PARAM_READ = "read";
    public static final String PARAM_UPDATE_READ_TIME = "updateReadTime";
    public static final String PARAM_RELATED_DOCUMENT_ID = "relatedDocumentId";
    public static final String PARAM_TEMPLATE = "template";
    public static final String PARAM_SHOW_ARCHIVED = "showArchived";
    public static final String PARAM_SHOW_REMOVED = "showRemoved";
    public static final String PARAM_SHOW_ARCHIVED_ONLY = "showArchivedOnly";
    public static final String PARAM_SHOW_REMOVED_ONLY = "showRemovedOnly";
    public static final String PARAM_DOCUMENT_VALID = "documentValid";
    public static final String PARAM_FOLLOWER_ACCOUNT_ID = "followerAccountId";
    public static final String PARAM_FOLLOWING = "following";
    public static final String PARAM_CATEGORY_FOLLOWER_ACCOUNT_ID = "categoryFollowerAccountId";
    public static final String PARAM_CATEGORY_FOLLOWING = "categoryFollowing";
    public static final String PARAM_MARKED_HELPFUL_BY_CONTACT_ID = "markedHelpfulByContactId";
    public static final String PARAM_LAST_CHANGED_SINCE_DAYS = "lastChangedSinceDays";
    public static final String PARAM_LAST_CHANGED_SINCE = "lastChangedSince";
    public static final String PARAM_RESPONSIBLE_CONTACT_ID = "responsibleContactId";
    public static final String PARAM_AUTHORSHIP_ACCOUNT_ID = "authorshipAccountId";
    public static final String PARAM_AUTHORSHIP = "authorship";
    public static final String PARAM_DOCUMENT_EXPIRES_IN_DAYS = "documentExpiresInDays";
    public static final String PARAM_DOCUMENT_EXPIRES_ON = "documentExpiresOn";
    public static final String PARAM_DOCUMENT_EXPIRES = "documentExpires";
    public static final String PARAM_DOCUMENT_HAS_ARCHIVED_BEGIN = "documentHasArchivedBegin";
    public static final String PARAM_SHOW_LANGUAGE_VERSION_REVIEWED_ONLY = "showLanguageVersionReviewedOnly";
    public static final String PARAM_HIDE_LANGUAGE_VERSION_REVIEWED = "hideLanguageVersionReviewed";
    public static final String PARAM_SHOW_LANGUAGE_VERSION_READY_ONLY = "showLanguageVersionReadyOnly";
    public static final String PARAM_HIDE_LANGUAGE_VERSION_READY = "hideLanguageVersionReady";
    public static final String PARAM_SHOW_LANGUAGE_VERSION_REVIEW_REQUESTED_ONLY = "showLanguageVersionReviewRequestedOnly";
    public static final String PARAM_HIDE_LANGUAGE_VERSION_REVIEW_REQUESTED = "hideLanguageVersionReviewRequested";
    public static final String PARAM_ADDITIONAL_PROPERTY_ID = "additionalPropertyId";
    public static final String PARAM_ADDITIONAL_PROPERTY_TYPE = "additionalPropertyType";
    public static final String PARAM_SHOW_SNAP_ONLY = "showSnapOnly";
    public static final String PARAM_HIDE_SNAP = "hideSnap";
    public static final String PARAM_MIME_TYPE_ID = "mimeTypeId";
    public static final String PARAM_MIME_TYPE = "mimeType";
    public static final String PARAM_MENTIONED_ACCOUNT_ID_IN_COMMENT = "mentionedAccountIdInComment";
    public static final String PARAM_MENTIONED_ACCOUNT_ID_IN_COMMENT_UNREAD = "mentionedAccountIdInCommentUnread";
    public static final String PARAM_DISABLE_RIGHTS_CHECK = "disableRightsCheck";
    public static final String PARAM_LOAD_RESPONSIBLES = "loadResponsibles";
    public static final String PARAM_LOAD_AUTHORS = "loadAuthors";
    public static final String PARAM_LOAD_LAST_AUTHOR_ONLY = "loadLastAuthorOnly";
    public static final String PARAM_LOAD_FOLLOWERS = "loadFollowers";
    public static final String PARAM_LOAD_AM_I_FOLLOWING = "loadAmIFollowing";
    public static final String PARAM_LOAD_MARKED_AS_HELPFUL_BY = "loadMarkedAsHelpfulBy";
    public static final String PARAM_LOAD_ATTACHMENTS = "loadAttachments";
    public static final String PARAM_LOAD_LANGUAGE_ATTACHMENTS = "loadLanguageAttachments";
    public static final String PARAM_LOAD_DOCUMENT_ATTACHMENTS = "loadDocumentAttachments";
    public static final String PARAM_LOAD_RELATED_DOCUMENT_IDS = "loadRelatedDocumentIds";
    public static final String PARAM_LOAD_ADDITIONAL_PROPERTIES = "loadAdditionalProperties";
    public static final String PARAM_LOAD_EXTENSION_VALUES = "loadExtensionValues";
    public static final String PARAM_LOAD_FIRST_READ_TIMES = "loadFirstReadTimes";
    public static final String PARAM_LOAD_SNAP_FLAG = "loadSnapFlag";
    public static final String PARAM_LOAD_ROLE_RIGHTS = "loadRoleRights";
    public static final String PARAM_REGENERATE = "regenerate";
    public static final String PARAM_IGNORE_REMOVE_TIME = "ignoreRemoveTime";
    public static final String PARAM_PUBLISH_REQUEST = "publishRequest";
    public static final String PARAM_VALID_BEGIN_IN_SECONDS_REQUEST = "validBeginInSecondsRequest";
    public static final String PARAM_ARCHIVED_BEGIN_IN_SECONDS_REQUEST = "archivedBeginInSecondsRequest";

    protected String basePath;

    public MtrRestClient(String basePath) {
        this.basePath = basePath;
    }

    public LoginData login(String userName, String password) {
        String path = this.basePath + APPLICATION_PATH+"/" + LOGON;
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
        String path = this.basePath + APPLICATION_PATH + "/" + LOGON;
        logger.trace("logout() - -- LOGOUT ------------");
        try(Response resp = this.deleteRequestPlain(path);) {
            boolean loggedOut = resp.readEntity(Boolean.class);
            logger.trace("logout() - loggedOut: " + loggedOut);
        }
    }

    public ListResult<Document> loadDocuments() {
        String path = this.basePath + APPLICATION_PATH+"/" + DOCUMENT;
        logger.debug("loadDocuments()");
        // *** load metadata of all default-documents;
        ListResult<Document> lr = null;
        try(Response resp = this.get(path+
                 "?"+PARAM_ALL_LANGUAGES+"=true"+
                 "&"+PARAM_LOAD_ADDITIONAL_PROPERTIES+"=true"+
                 "&"+PARAM_LOAD_ATTACHMENTS+"=true"+
                 "&"+PARAM_LIMIT+"=10"+
                 "&"+PARAM_COUNT+"=true")) {
            lr = resp.readEntity(new GenericType<ListResult<Document>>() {});
        }
        return lr;
    }

    public String loadDocumentContent(long documentId, String languageKey) {
        String path = this.basePath + APPLICATION_PATH+"/" + DOCUMENT;
        logger.debug("loadDocumentContent({}, {})", documentId, languageKey);
        // *** load content;
        String content = null;
        try(Response resp = this.getNothing(path+"/"+documentId+"/"+FILE+"?"+PARAM_LANGUAGE_KEY+"="+languageKey)) {
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
        String path = this.basePath + APPLICATION_PATH+"/" + DOCUMENT;
        logger.debug("loadAttachmentContent({}, {}, {})", documentId, documentLanguageVersionId, attachmentId);
        // *** load content;
        Response resp = this.getNothing(path+"/"+documentId+"/"+
                                        VERSION+"/"+documentLanguageVersionId+"/"+
                                        ATTACHMENT+"/"+attachmentId);
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
