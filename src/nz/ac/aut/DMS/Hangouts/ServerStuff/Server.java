package nz.ac.aut.DMS.Hangouts.ServerStuff;

import nz.ac.aut.DMS.Hangouts.User;
import nz.ac.aut.DMS.Hangouts.WebUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ashellan Edmonds on 5/05/15.
 */
public enum Server {;

    public static ServerHangoutResponse Hangout(String username, String lng, String lat, String date){
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("long", lng));
        pairs.add(new BasicNameValuePair("lat", lat));
        pairs.add(new BasicNameValuePair("date", date));
        String s = WebUtil.start().post().setUrl("http://dangdle.com/dmshangout.php").setEntity(pairs).addHeaders(pairs).finishUnzipped();
        return ServerHangoutResponse.get(s);
    }

    public static List<User> getHangoutUsers(){
        String s = WebUtil.start().post().setUrl("http://dangdle.com/dmsgethangouts.php").finishUnzipped();
        final String[] split = s.replaceAll("<tr>", "").split("</tr>");
        List<User> list = new LinkedList<>();
        for(String s2 : split){
            final String[] split1 = s2.replaceAll("<td>", "").split("</td>");
            list.add(new User() {{
                List<NameValuePair> pairs = new LinkedList<>();
                pairs.add(new BasicNameValuePair("query", "SELECT ph FROM MyGuests WHERE username='"+split1[0]+"'"));
                String s1 = WebUtil.start().post().setUrl("http://dangdle.com/dmssql.php").setEntity(pairs).addHeaders(pairs).finishUnzipped();
                setUsername(split1[0]);
                setLng(split1[1]);
                setLat(split1[2]);
                setDate(split1[3]);
                setPhone(s1.replaceAll("(<td>)|(</td>)|(<tr>)|(</tr>)", ""));
            }});

        }
        return list;
    }

    public static SignUp signUp(String username, String password, String ph) {
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));
        pairs.add(new BasicNameValuePair("phone", ph));
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

    public static SignIn signIn(String username, String password) {
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


}
