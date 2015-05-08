package nz.ac.aut.DMS.Hangouts.ServerStuff;

/**
 * Created by Ashellan Edmonds on 8/05/15.
 */
public enum SignIn {
    SUCCESS("Welcome Back!"), FAILURE("Invalid Username/Password"), ERROR("Something went wrong with signing in, please try again");
    private CharSequence toast;

    SignIn(String string) {
        toast = string;
    }

    public CharSequence getToast() {
        return toast;
    }
}
