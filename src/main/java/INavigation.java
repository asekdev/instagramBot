public interface INavigation {

    boolean goHome();
    boolean goToProfile();
    boolean goToExplorePage();
    boolean goToUserPage(String username);
    boolean goToHashtag(String hashtag);
}
