package Interfaces;

public interface INavigation {

    boolean goHome();
    boolean goToProfile();
    boolean goToExplorePage();
    boolean goToUserPageToLike(String username);
    boolean goToUserPageToFollow(String username);
    boolean goToHashtag(String hashtag);
}
