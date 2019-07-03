package Interfaces;

public interface INavigation {

    boolean goHome();
    boolean goToProfile();
    boolean goToExplorePage();
    boolean goToUserPage(String username, String actionType);
    boolean goToHashtag(String hashtag);
}
