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

    private static final String HTTP_BASE_URL = "http://dangdle.com/DMS/";

    public static ServerHangoutResponse Hangout(String username, String lng, String lat, String date){
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("long", lng));
        pairs.add(new BasicNameValuePair("lat", lat));
        pairs.add(new BasicNameValuePair("date", date));
        String s = getFromUrl(pairs, "dmshangout.php");
        return ServerHangoutResponse.get(s);
    }

    private static String getFromUrl(List<NameValuePair> pairs, String endUrl) {
        return WebUtil.start().post().setUrl(HTTP_BASE_URL + endUrl).setEntity(pairs).addHeaders(pairs).finishUnzipped();
    }

    private static String getFromUrl(String endUrl) {
        return WebUtil.start().post().setUrl(HTTP_BASE_URL + endUrl).finishUnzipped();
    }

    private static String getFromSql(List<NameValuePair> query){
        return getFromUrl(query, "dmssql.php");
    }


    public static List<User> getHangoutUsers(){
        //gets list of all people in locations database
        String s = getFromUrl("dmsgethangouts.php");
        //users are between <tr></tr>'s
        final String[] split = s.replaceAll("<tr>", "").split("</tr>");
        List<User> list = new LinkedList<>();
        //for each user string
        for(String s2 : split){
            //columns are inbetween <td></td>'s
            final String[] split1 = s2.replaceAll("<td>", "").split("</td>");
            list.add(new User() {{
                List<NameValuePair> pairs = new LinkedList<>();
                //query database for user ph number, inefficient but works
                pairs.add(new BasicNameValuePair("query", "SELECT ph FROM MyGuests WHERE username='"+split1[0]+"'"));
                String s1 = getFromSql(pairs);
                setUsername(split1[0]);
                setLng(split1[1]);
                setLat(split1[2]);
//                setDate(split1[3]);
                //only 1 column/row, so just remove all td/tr's
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
        String s = getFromUrl(pairs, "dmssignup.php");
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
        String s = getFromUrl(pairs, "dmssignin.php");
        if("success".equals(s)){
            return SignIn.SUCCESS;
        }
        if("not found".equals(s)){
            return SignIn.ERROR;
        }

        return SignIn.FAILURE;
    }

    public static AddFriendResult addFriend(String user1, String user2){
        {
            List<NameValuePair> pairs = new LinkedList<>();
            //query database for user ph number, inefficient but works
            pairs.add(new BasicNameValuePair("query", "INSERT INTO Friends VALUES('" + getId(user1) + "','" + getId(user2) + "')"));
            String s1 = getFromSql(pairs);
            if(s1.equals(AddFriendResult.FAIL.s)) return AddFriendResult.FAIL;
        }
        return AddFriendResult.SUCCEES;
    }
    private static int getId(String username){
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("query", "SELECT id FROM MyGuests WHERE username='" + username + "'"));
        String s1 = getFromSql(pairs);
        return Integer.valueOf(s1.replaceAll("(<td>)|(</td>)|(<tr>)|(</tr>)", ""));
    }


    public static List<User> getFriendsList(String username){
        List<NameValuePair> pairs = new LinkedList<>();
        int id = getId(username);
        final String[] friendsId;
        {
            pairs.add(new BasicNameValuePair("query", "SELECT friend_id FROM Friends where user_id='" + id + "'"));
            String s = getFromSql(pairs);
            friendsId = s.replaceAll("<tr>", "").split("</tr>");
            for (int i = 0; i < friendsId.length; i++) {
                friendsId[i] = friendsId[i].replaceAll("(<td>)|(</td>)", "");
            }
            pairs.clear();
        }


        List<User> friends = new LinkedList<>();
        for(String friendid : friendsId) {
            try {
                int friendsintid = Integer.valueOf(friendid);
                pairs.add(new BasicNameValuePair("query", "SELECT * FROM MyGuests WHERE id='" + friendsintid + "'"));
                String friendString = getFromSql(pairs);
                String[] split = friendString.replaceAll("<tr>", "").split("</tr>");
                final String friendName = split[0].replaceAll("<td>", "").split("</td>")[1];
                final String ph = split[0].replaceAll("<td>", "").split("</td>")[3];
                friends.add(new User() {{
                    setUsername(friendName);
                    setPhone(ph);
                }});
            }catch (Exception e){};
        }
        return friends;
    }

}
