package controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import model.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Deze klasse is verantwoordelijk voor het ophalen van data uit de database, en het vertalen naar objecten.
 * Naar dit doel wordt de orm (object relational mapping) library gebruikt.
 */
public class ForumThreadService {

    /**
     * Deze methode wordt gebruikt om de informatie van een thread op te halen samen met 20 comments.
     * De comments die op worden gehaald hangen af van het postId dat wordt meegegeven.
     * @param id identifier van de thread
     * @param postId nummer vanaf waar de comments opgehaald moeten worden.
     * @return Geeft een thread object met een lijst van 20 geordende comments terug
     */
    public ForumThread getThread(int id, int postId) {
        Dao<ForumThread, Integer> threadDao;
        Dao<ThreadUpvote, Integer> upvoteDao;
        Dao<Comment, Integer> commentDao;
        try {
            threadDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ForumThread.class);
            ForumThread forumThread = threadDao.queryForId(id);

            upvoteDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ThreadUpvote.class);
            forumThread.setKudos((int) upvoteDao.queryBuilder().where().eq("idthread", id).countOf());

            commentDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), Comment.class);
            QueryBuilder<Comment, Integer> qb = commentDao.queryBuilder();
            qb.where().eq("idthread", id);
            qb.orderBy("postdate", true);
            forumThread.setComments(qb.query().subList(postId, postId + 19));

            return forumThread;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Deze methode geeft de nieuwste threads terug.
     * @param start het begin van de sublijst.
     * @return geeft een lijst van 20 threads terug geordend op datum, met de nieuwste thread eerst.
     */
    public List<ForumLink> getNewThreads(int start) {
        Dao<ForumLink, Integer> forumLinkDao;
        try {
            forumLinkDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ForumLink.class);
            QueryBuilder<ForumLink, Integer> qb = forumLinkDao.queryBuilder();
            qb.orderBy("date", false);
            List<ForumLink> forumLinks = qb.query().subList(start, start + 19);
            for (ForumLink forumLink : forumLinks) {
                Dao<ThreadUpvote, Integer> upvoteDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ThreadUpvote.class);
                forumLink.setKudos((int) upvoteDao.queryBuilder().where().eq("idthread", forumLink.getId()).countOf());
            }
            return forumLinks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Deze methode geeft de meest populaire threads terug.
     * @param start het begin van de sublijst.
     * @return geeft een lijst van 20 threads terug geordend op populariteit (upvotes), met de meest populaire thread eerst.
     */
    public List<ForumLink> getPopThreads(int start) {
        try {
            Dao<ForumLink, Integer> forumLinkDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ForumLink.class);
            QueryBuilder<ForumLink, Integer> qb = forumLinkDao.queryBuilder();
            List<ForumLink> forumLinks = qb.query();
            for (ForumLink forumLink : forumLinks) {
                Dao<ThreadUpvote, Integer> upvoteDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ThreadUpvote.class);
                forumLink.setKudos((int) upvoteDao.queryBuilder().where().eq("idthread", forumLink.getId()).countOf());
            }
            Collections.sort(forumLinks, new KudosComparator());
            return forumLinks.subList(start, start + 19);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deze methode geeft de threads van een bepaalde gebruiker terug.
     * @param start het begin van de sublijst.
     * @param userId de gebruiker
     * @return geeft een lijst van 20 threads terug geordend op datum, met de nieuwste thread eerst.
     */
    public List<ForumLink> getMyThreads(int start, int userId) {
        try {
            Dao<ForumLink, Integer> forumLinkDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ForumLink.class);
            QueryBuilder<ForumLink, Integer> qb = forumLinkDao.queryBuilder();
            qb.where().eq("iduser", userId);
            qb.orderBy("date", false);
            return qb.query().subList(start, start + 19);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deze methode plaatst een comment in de gespecificeerde thread.
     * @param threadId het thread
     * @param userId de gebruiker die het commentaar plaatst
     * @param content de inhoud van het commentaar
     * @return succes-staat
     */
    public static String postComment(int threadId, int userId, String content) {
        try {
            Dao<Comment, Integer> commentDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), Comment.class);

            Comment c = new Comment();
            c.setContent(content);
            c.setCreatedOn(new java.sql.Date(new java.util.Date().getTime()));
            c.setPosterId(userId);
            c.setThreadId(threadId);

            commentDao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failure";
        }
        return "succes";
    }

    /**
     * Deze methode maakt een nieuwe thread aan.
     * @param title de titel van de thread
     * @param subject het onderwerp van de thread
     * @param userId de gebruiker die de thread aanmaakt
     * @return succes-staat
     */
    public static String postThread(String title, String subject, String userId) {
        try {
            Dao<ForumThread, Integer> threadDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ForumThread.class);

            ForumThread forumThread = new ForumThread();
            forumThread.setTitle(title);
            forumThread.setDesc(subject);
            forumThread.setUserId(Integer.parseInt(userId));
            forumThread.setDate(new java.sql.Date(new java.util.Date().getTime()));

            threadDao.create(forumThread);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failure";
        }
        return "succes";
    }

    /**
     * Deze methode verzorgt het upvoten van een comment
     * @param commentId het commentaar dat kudos krijgt
     * @param userId de gebruiker die kudos geeft
     * @return succes-staat
     */
    public static String upVoteComment(int commentId, int userId) {
        try {
            Dao<CommentUpvote, Integer> commentUpvoteDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), CommentUpvote.class);

            CommentUpvote commentUpvote = new CommentUpvote();
            commentUpvote.setUserId(userId);
            commentUpvote.setCommentId(commentId);

            commentUpvoteDao.create(commentUpvote);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failure";
        }

        return "succes";
    }

    /**
     * Deze methode verzorgt het upvoten van een comment
     * @param threadId het thread dat kudos krijgt
     * @param userId de gebruiker die kudos geeft
     * @return succes-staat
     */
    public static String upVoteThread(int threadId, int userId) {
        try {
            Dao<ThreadUpvote, Integer> threadUpvoteDao = DaoManager.createDao(DatabaseHandler.getInstance().getConnectionSource(), ThreadUpvote.class);

            ThreadUpvote t = new ThreadUpvote();
            t.setThreadId(threadId);
            t.setUserId(userId);

            threadUpvoteDao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failure";
        }
        return "succes";
    }

    /**
     * Een comparator om de populairiteit van threads met elkaar te kunnen vergelijken.
     */
    private class KudosComparator implements Comparator<ForumLink> {
        @Override
        public int compare(ForumLink o1, ForumLink o2) {
            return o1.getKudos() < o2.getKudos() ? -1 : o1.getKudos() == o2.getKudos() ? 0 : 1;
        }
    }
}


