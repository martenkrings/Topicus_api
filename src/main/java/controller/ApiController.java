package controller;

import exception.ResponseError;
import model.ForumThread;

import java.util.List;

import static myjson.JsonUtil.json;
import static myjson.JsonUtil.toJson;

import static spark.Spark.*;

/**
 *  Deze klasse is verantwoordelijk voor het afhandelen van request aan de server,
 *  Het aanroepen van de corresponderende methode, en het vertalen van de objecten naar een json bestand.
 */
public class ApiController {

    public ApiController(final ForumThreadService threadService) {
        get("/thread/:id", (req, res) -> {
            String id = req.params(":id");
            ForumThread thread = threadService.getThread(Integer.parseInt(id), 0);
            if (thread != null) {
                return thread;
            }
            res.status(400);
            return new ResponseError("No thread with id '%s' found", id);
        }, json());

        get("/thread/:id/post/:postId", (req, res) -> {
            String id = req.params(":id");
            String postId = req.params(":postId");
            ForumThread thread = threadService.getThread(Integer.parseInt(id), Integer.parseInt(postId));
            if (thread != null) {
                return thread;
            }
            res.status(400);
            return new ResponseError("No thread with id '%s' found", id);
        }, json());

        get("/threads/all", (req, res) -> {
            List forumLinks = threadService.getNewThreads(0);
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        get("/threads/all/from/:iteratorStart", (req, res) -> {
            String iteratorStart = req.params(":iteratorStart");
            List forumLinks = threadService.getNewThreads(Integer.parseInt(iteratorStart));
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        get("/threads/pop", (req, res) -> {
            List forumLinks = threadService.getPopThreads(0);
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        get("/threads/pop/from/:iteratorStart", (req, res) -> {
            String iteratorStart = req.params(":iteratorStart");
            List forumLinks = threadService.getPopThreads(Integer.parseInt(iteratorStart));
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        get("/threads/:user", (req, res) -> {
            int userId = Integer.parseInt(req.params(":user"));
            List forumLinks = threadService.getMyThreads(0, userId);
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        get("/threads/:user/from/:iteratorStart", (req, res) -> {
            String iteratorStart = req.params(":iteratorStart");
            int userId = Integer.parseInt(req.params(":user"));
            List forumLinks = threadService.getMyThreads(Integer.parseInt(iteratorStart), userId);
            if (forumLinks != null) {
                return forumLinks;
            }
            res.status(400);
            return new ResponseError("threads not found");
        }, json());

        /* /users?name=john&email=john@foobar.com" */
        post("/comment", (req, res) ->
                ForumThreadService.postComment(
                        Integer.parseInt(req.queryParams("threadId")),
                        Integer.parseInt(req.queryParams("userId")),
                        req.queryParams("content")
                ), json());

        post("/thread/post", (req, res) ->
                ForumThreadService.postThread(
                        req.queryParams("title"),
                        req.queryParams("subject"),
                        req.queryParams("userId")
                ), json());

        post("/comment/upvote", (req, res) ->
                ForumThreadService.upVoteComment(
                        Integer.parseInt(req.queryParams("commentId")),
                        Integer.parseInt(req.queryParams("userId"))
                ), json());

        post("/thread/upvote", (req, res) ->
                ForumThreadService.upVoteThread(
                        Integer.parseInt(req.queryParams("threadId")),
                        Integer.parseInt(req.queryParams("userId"))
                ), json());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
