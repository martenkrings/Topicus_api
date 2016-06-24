package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Dit is een data object voor comments,
 * Dit object is geformatteerd zodat het gebruikt kan worden met de orm (object relational mapping) library.
 */
@DatabaseTable(tableName = "comment")
public class Comment {

    @DatabaseField(columnName = "idpost")
    private int id;

    @DatabaseField(columnName = "postcontent")
    private String content;

    @DatabaseField(columnName = "postdate")
    private java.sql.Date createdOn;

    @DatabaseField(columnName = "iduser")
    private int posterId;

    @DatabaseField(columnName = "idthread")
    private int threadId;

    private int kudos;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getKudos() {
        return kudos;
    }

    public void setKudos(int kudos) {
        this.kudos = kudos;
    }
}
