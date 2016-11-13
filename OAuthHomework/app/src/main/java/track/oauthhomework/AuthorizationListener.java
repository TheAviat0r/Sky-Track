package track.oauthhomework;

/**
 * Created by aviator on 11/13/16.
 */

public interface AuthorizationListener {
    void onAuthStarted();

    void onComplete(String token);

    void onError(String error);
}
