package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Dit is een data object voor thread upvotes,
 * Dit object is geformatteerd zodat het gebruikt kan worden met de orm (object relational mapping) library.
 */
@DatabaseTable(tableName = "thread_has_upvotes")
public class ThreadUpvote {

    @DatabaseField(columnName = "idthread")
    int threadId;

    @DatabaseField(columnName = "iduser")
    int userId;

    public ThreadUpvote() {
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
