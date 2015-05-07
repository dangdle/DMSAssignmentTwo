package nz.ac.aut.DMS.Hangouts;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import java.util.List;

/**
 * Created by Ashellan Edmonds on 7/04/15.
 */
public enum WebInterface {
;
    public interface Construct{
        SetUrl get();

         
        SetUrl post();

    }

    public interface SetUrl{
         
        setup setUrl(String url);
    }

    public interface setup{
         
        setup addHeader(String name, String value);

         
        setup addHeaders(List<? extends NameValuePair> pairs);

         
        setup setEntity(List<? extends NameValuePair> pairs);

         
        setup setClient(HttpClient client);

         

         
        HttpResponse finish();

         
        String finishUnzipped();
    }
}
