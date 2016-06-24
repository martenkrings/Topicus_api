package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Dit is een data object voor comment upvotes,
 * Dit object is geformatteerd zodat het gebruikt kan worden met de orm (object relational mapping) library.
 */
@DatabaseTable(tableName = "comment_has_upvotes")
public class CommentUpvote {

    @DatabaseField(columnName = "idpost")
    int commentId;

    @DatabaseField(columnName = "iduser")
    int userId;

    public CommentUpvote() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int threadId) {
        this.commentId = threadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
