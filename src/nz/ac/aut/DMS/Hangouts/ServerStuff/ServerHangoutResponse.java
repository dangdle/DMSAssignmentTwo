package nz.ac.aut.DMS.Hangouts.ServerStuff;

import android.util.Log;

/**
 * Created by Ashellan Edmonds on 8/05/15.
 */
public enum ServerHangoutResponse {
    SUCCESS("success"), FAILURE("fail"), INVALID("false");

    private String success;

    ServerHangoutResponse(String success) {
        this.success = success;
    }

    public static ServerHangoutResponse get(String string) {
        for (ServerHangoutResponse response : values()) {
            if (response.success.equals(string)) {
                return response;
            }
        }
        Log.d("my", string);
        return null;
    }
}
