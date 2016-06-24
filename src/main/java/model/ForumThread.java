package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Dit is een data object voor threads,
 * Dit object is geformatteerd zodat het gebruikt kan worden met de orm (object relational mapping) library.
 */
@DatabaseTable(tableName = "thread")
public class ForumThread extends ForumLink implements Serializable {

    private List<Comment> comments;

    public ForumThread() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
