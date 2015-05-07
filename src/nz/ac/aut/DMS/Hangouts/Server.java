package nz.ac.aut.DMS.Hangouts;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ashellan Edmonds on 5/05/15.
 */
public class Server {

    public SignUp signUp(String username, String password) {
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));
        String s = WebUtil.start().post().setUrl("http://dangdle.com/dmssignup.php").setEntity(pairs).addHeaders(pairs).finishUnzipped();
        if("success".equals(s)){
            return SignUp.SUCCESS;
        }
        if("fail".equals(s)) {
            return SignUp.ALREADY_EXISTS;
        }
        if("not found".equals(s)){
            return SignUp.ERROR;
        }
        return SignUp.FAILURE;
    }

    public SignIn signIn(String username, String password) {
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));
        String s = WebUtil.start().post().setUrl("http://dangdle.com/dmssignin.php").setEntity(pairs).addHeaders(pairs).finishUnzipped();
        if("success".equals(s)){
            return SignIn.SUCCESS;
        }
        if("not found".equals(s)){
            return SignIn.ERROR;
        }

        return SignIn.FAILURE;
    }

    public enum SignIn {
        SUCCESS("Welcome Back!"), FAILURE("Invalid Username/Password"), ERROR("Something went wrong with signing in, please try again")
        ;
        private CharSequence toast;

        SignIn(String string){
            toast = string;
        }

        public CharSequence getToast() {
            return toast;
        }
    }


    public enum SignUp{
        SUCCESS("Successful Login"), FAILURE("Something went wrong with signup, please try again"), ALREADY_EXISTS("Login already exists"),
        ERROR("Something went wrong with signing up, please try again");

        private CharSequence toast;

        SignUp(String string){
            toast = string;
        }

        public CharSequence getToast() {
            return toast;
        }
    }
}
