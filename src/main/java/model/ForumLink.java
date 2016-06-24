package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Dit is een data object voor Forumlinks, threads zonder commentaar,
 * Dit object is geformatteerd zodat het gebruikt kan worden met de orm (object relational mapping) library.
 */

@DatabaseTable(tableName = "thread")
public class ForumLink {

    @DatabaseField(columnName = "idthread")
    private int id;

    @DatabaseField(columnName = "threadname")
    private String title;

    @DatabaseField(columnName = "topic")
    private String desc;

    @DatabaseField(columnName = "iduser")
    private int userId;

    @DatabaseField(columnName = "threadddate")
    java.sql.Date date;

    private int kudos;

    public ForumLink() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getKudos() {
        return kudos;
    }

    public void setKudos(int kudos) {
        this.kudos = kudos;
    }
}
