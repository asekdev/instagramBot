import Interfaces.ILiker;
import Interfaces.LikeStrategy;

public class BotLiker implements ILiker {

    public boolean likePhotos(LikeStrategy strategy, int numPhotos) {
        boolean photosLiked = strategy.likePhotos(numPhotos);

        return photosLiked;
    }
}
