package Interfaces;

public interface IFollower {

    boolean followerUser(String username);
    boolean unfollowUsers(int numUsers);
    boolean followUsers(TypeStrategy t, int numUsers);
}
