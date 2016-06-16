package utils;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ***********************************************************
 * author: alex
 * time: 16/5/26 上午9:25
 * name: OKHttp最简单的封装
 * overview:
 * usage:
 * *************************************************************
 */
public class OKHttpUtils {

    private static OKHttpUtils mOKHttpUtils;
    private static OkHttpClient mOkHttpClient;

    private OKHttpUtils(Context context) {
        mOkHttpClient = getOkHttpClient();

        //开启响应的缓存
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        int cacheSize1 = 10 * 1024 * 1024; //10M
        int cacheSize = 10 << 20; //10M
        Log.i("tag", cacheSize + "::" + cacheSize1);
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        mOkHttpClient.setCache(cache);

        mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
    }

    /**
     * 工具类的单例设计
     *
     * @param context
     * @return
     */
    public static OKHttpUtils getOKHttpUtils(Context context) {
        if (mOKHttpUtils == null) {
            synchronized (OKHttpUtils.class) {
                if (mOKHttpUtils == null) {
                    mOKHttpUtils = new OKHttpUtils(context);
                }
            }

        }
        return mOKHttpUtils;
    }

    /**
     * OKHttpClient
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    /*********************************************************************
     * Get同步请求方式
     *********************************************************************/
    /**
     * 构建Get请求的Request
     *
     * @param url 请求的url
     * @param tag 给每一个请求标记tag，方便取消
     * @return
     */
    private Request buildGetRequest(String url, Object tag) {
        Request.Builder builder = new Request.Builder();
        if (tag != null) {
            builder.tag(tag);
        }
        return builder.url(url).build();
    }

    /**
     * 构建Get请求的Response对象
     *
     * @param url 请求的url
     * @param tag 请求标记tag
     * @return
     * @throws IOException
     */
    private Response buildGetResponse(String url, Object tag) throws IOException {
        Request request = buildGetRequest(url, tag);
        if (request != null) {
            return mOkHttpClient.newCall(request).execute();
        }
        return null;
    }

    /**
     * 得到Get同步方式的返回的数据
     *
     * @param url
     * @param object
     * @param context
     * @return
     * @throws IOException
     */
    public static String buildGetString(String url, Object object, Context context) throws IOException {
        Response response = getOKHttpUtils(context).buildGetResponse(url, object);
        if (response != null && response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

    public static byte[] buildGetByte(String url, Object object, Context context) throws IOException {
        Response response = getOKHttpUtils(context).buildGetResponse(url, object);
        if (response != null && response.isSuccessful()) {
            return response.body().bytes();
        }
        return null;
    }

    /*********************************************************************
     * 异步Get的方式
     *********************************************************************/
    public static void buildGetAsync(Context context, String url, Object tag, Callback callback) {
        Request request = getOKHttpUtils(context).buildGetRequest(url, tag);
        if (request != null) {
            mOkHttpClient.newCall(request).enqueue(callback);
        }
    }

    /*********************************************************************
     * Post方式
     *********************************************************************/

    /**
     * 构建Post的请求
     *
     * @param url
     * @param body
     * @param tag
     * @return
     */
    private Request buildPostRequest(String url, RequestBody body, Object tag) {
        Request.Builder builder = new Request.Builder();
        if (tag != null) {
            builder.tag(tag);
        }
        return builder.url(url).post(body).build();
    }

    /**
     * 构建服务端返回的数据
     *
     * @param url
     * @param body
     * @param tag
     * @return
     * @throws IOException
     */
    private String buildPostRequestBody(String url, RequestBody body, Object tag) throws IOException {
        Request request = buildPostRequest(url, body, tag);
        if (request != null) {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return response.body().string();
            }
        }
        return null;
    }

    /**
     * 提交keyValue值
     *
     * @param context
     * @param map
     * @param url
     * @param tag
     * @return
     * @throws IOException
     */
    public static String postKeyValue(Context context, Map<String, String> map, String url, Object tag) throws IOException {
        //1,构建RequestBody
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        //2,调用buildPostRequestBody()放回服务端数据
        return getOKHttpUtils(context).buildPostRequestBody(url, requestBody, tag);
    }

    /**
     * 提交Json字符串
     *
     * @param context
     * @param json
     * @param url
     * @param tag
     * @return
     * @throws IOException
     */
    public static String postJson(Context context, String json, String url, Object tag) throws IOException {
        String TYPE_JSON = "mapp/json;charset=utf-8";
        MediaType mediaType = MediaType.parse(TYPE_JSON);
        RequestBody requestBody = RequestBody.create(mediaType, json);
        return getOKHttpUtils(context).buildPostRequestBody(url, requestBody, tag);
    }


    /*********************************************************************
     * 取消tag标记
     *********************************************************************/
    public static void cancelTag(Object tag) {
        mOkHttpClient.cancel(tag);
    }


}
