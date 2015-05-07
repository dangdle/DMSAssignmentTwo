package nz.ac.aut.DMS.Hangouts;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static nz.ac.aut.DMS.Hangouts.WebInterface.*;


/**
 * Created by Ashellan Edmonds on 7/04/15.
 * Fluent web connection
 * start().(get()/post()).setUrl().setheaders/setentity.finish
 *
 */
public class WebUtil implements Construct, SetUrl, setup {
    private static final String UTF8 = "UTF-8";
      
    private HttpPost post;
      
    private HttpGet get;
    private HttpClient client;
     
    private sw postOrGet;

    private WebUtil() {
        postOrGet = sw.GET;
    }

     
    public static Construct start(){
         
        return new WebUtil();
    }


     
    public static HttpClient getDefaultClient(){
        return new DefaultHttpClient();
    }

    @Override
     
    public SetUrl get() {
         client = getDefaultClient();
        postOrGet = sw.GET;
         
        return this;
    }


    @Override
     
    public SetUrl post() {
        client = getDefaultClient();
        postOrGet = sw.POST;
         
        return this;
    }

    @Override
     
    public setup setUrl(  final String url) {
         
        switch (postOrGet){
            case POST:
                post = new HttpPost(url);
                break;
            case GET:
                get = new HttpGet(url);
                break;
        }
        return this;
    }

    @Override
     
    public setup addHeader(  final String name,   final String value) {
        switch (postOrGet){
            case POST:
                assert null != post;
                post.setHeader(name, value);
                break;
            case GET:
                assert null != get;
                get.setHeader(name, value);
                break;
        }
        return this;
    }

    @Override
     
    public setup addHeaders(  final List<? extends NameValuePair> pairs) {
        for(NameValuePair p : pairs){
            switch (postOrGet){
                case POST:
                    assert null != post;
                    post.setHeader(p.getName(), p.getValue());
                    break;
                case GET:
                    assert null != get;
                    get.setHeader(p.getName(), p.getValue());
                    break;
            }
        }
        return this;
    }

    @Override
     
    public setup setEntity(  final List<? extends NameValuePair> pairs) {
        switch (postOrGet){
            case POST:
                try {
                    assert null != post;
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                } catch (final UnsupportedEncodingException e) {
                     
                }
                break;
            case GET:
                break;
        }
        return this;
    }

    @Override
     
    public setup setClient(  final HttpClient client) {
         
        this.client = client;
        return this;
    }

    @Override
     
    public HttpResponse finish() {
        HttpResponse resp = null;
        switch (postOrGet){
            case POST:
                try {
                    resp = client.execute(post);
                } catch (final ClientProtocolException e) {
                    System.out.println(e);
                     e.printStackTrace();
                } catch (final IOException e) {
                    System.out.println(e);
                     e.printStackTrace();
                } catch (Exception e){
                    System.out.println(e);
                    e.printStackTrace();
                }
                break;
            case GET:
                try {
                    resp = client.execute(get);
                } catch (final ClientProtocolException e) {
                     
                } catch (final IOException e) {
                     
                }
                break;
        }

        if(null == resp) {
             
            throw new IllegalArgumentException();
        }
         
        return resp;
    }



    private enum sw {
        POST, GET
    }

    @Override
     
    public String finishUnzipped() {
        String resp = "failure";
        try {
            resp =  unzip(finish());
        } catch (final Exception e) {
             
        }
        return resp;
    }

     
    public static String unzip(  final HttpResponse response) throws Exception {
        final Header[] headers = response.getHeaders("Content-Encoding");
        if ((0 == headers.length) || !"gzip".equalsIgnoreCase(headers[0].getValue())) {
            return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        final GZIPInputStream in = new GZIPInputStream(response.getEntity()
                .getContent());
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in, HTTP.UTF_8));
        final StringBuilder buffer = new StringBuilder();
        String line;
        while (null != (line = reader.readLine())) {
            buffer.append(line);
        }
        in.close();
        return buffer.toString();
    }

}
