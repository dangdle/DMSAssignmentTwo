package nz.ac.aut.DMS.Hangouts.ServerStuff;

/**
 * Created by Ashellan Edmonds on 8/05/15.
 */
public enum SignUp {
    SUCCESS("Successful Login"), FAILURE("Something went wrong with signup, please try again"), ALREADY_EXISTS("Login already exists"),
    ERROR("Something went wrong with signing up, please try again");

    private CharSequence toast;

    SignUp(String string) {
        toast = string;
    }

    public CharSequence getToast() {
        return toast;
    }
}
