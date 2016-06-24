import controller.ApiController;
import controller.ForumThreadService;

/**
 * Deze Klasse zet de api klaar voor gebruik.
 */
public class Main {
    public static void main(String[] args) {
        new ApiController(new ForumThreadService());
    }
}