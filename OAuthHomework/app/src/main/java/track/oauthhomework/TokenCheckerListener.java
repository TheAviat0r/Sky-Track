package track.oauthhomework;

/**
 * Created by aviator on 11/29/16.
 */

public interface TokenCheckerListener {
    void onSuccessCheck();
    void onFailCheck(String token);
}
