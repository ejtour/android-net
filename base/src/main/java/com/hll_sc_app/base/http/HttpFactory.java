package com.hll_sc_app.base.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hll_sc_app.base.BuildConfig;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.Md5Utils;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.hll_sc_app.citymall.util.TraceIDUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * httpFactory
 *
 * @author 朱英松
 * @date 2019/5/31
 */
public class HttpFactory {
    private static final int TIMEOUT = 10;
    private static final String CS = "android";
    private static final String SIGN_KEY = "813eae6fe94441fbb39d24f641440541";
    private static final String SOURCE = "shopmall-supplier";
    private static GsonConverterFactory factory = GsonConverterFactory.create();
    private static final List<String> MESSAGE_LIST = Arrays.asList("108001", "108002", "108003", "108004", "108010");

    private static OkHttpClient create() {
        return new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(new SignInterceptor())
            .addInterceptor(new HttpLoggingInterceptor(message -> LogUtil.d("okHttp", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            // Truncated UTF-8 sequence.
            return false;
        }
    }

    public synchronized static <T> T create(Class<T> tClass) {
        return new Retrofit.Builder()
            .baseUrl(HttpConfig.getHost())
            .client(getHttpClient())
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(tClass);
    }

    private static OkHttpClient getHttpClient() {
        return Instance.INSTANCE;
    }

    public synchronized static <T> T create(Class<T> tClass, String baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getHttpClient())
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(tClass);
    }

    public synchronized static <T> T createImgUpload(Class<T> tClass) {
        return new Retrofit.Builder()
            .baseUrl("http://file.hualala.com")
            .client(getHttpClient())
            .addConverterFactory(new StringConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(tClass);
    }

    private static class Instance {
        static final OkHttpClient INSTANCE = create();
    }

    /**
     * 签名拦截
     */
    private static class SignInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            String pv = chain.request().header("pv");
            // 取出 body 字符串
            RequestBody requestBody = chain.request().body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String body = "{}";
            if (isPlaintext(buffer)) {
                body = buffer.readString(charset);
            }
            // 请求接口地址替换
            String url = null;
            if (!TextUtils.isEmpty(pv)) {
                if (TextUtils.equals("99999", pv)) {
                    // 小流量请求接口
                    url = HttpConfig.getVipHost() + "/shopmall/urlRouter";
                } else if (MESSAGE_LIST.contains(pv)) {
                    // 消息部分的接口
                    url = HttpConfig.getMessageHost() + HttpConfig.URL;
                } else {
                    url = chain.request().url().toString();
                }
            }
            // 拼接签名前字符串
            Request.Builder builder = chain.request().newBuilder()
                .addHeader("accessToken", UserConfig.accessToken())
                .addHeader("traceID", TraceIDUtils.getTraceID())
                .addHeader("cs", CS)
                .addHeader("source", SOURCE)
                .addHeader("cv", SystemUtils.getVersionName(App.INSTANCE))
                .addHeader("sign", Md5Utils.getMD5(SIGN_KEY + "_" + pv + "_" + body))
                .addHeader("groupID", UserConfig.getGroupID());
            if (!TextUtils.isEmpty(BuildConfig.ODM_ID)) {
                builder.addHeader("odmId", BuildConfig.ODM_ID);
            }
            return chain.proceed(TextUtils.isEmpty(url) ? builder.build() : builder.url(url).build());
        }
    }

    public static class StringConverterFactory extends Converter.Factory {

        public StringConverterFactory create() {
            return new StringConverterFactory();
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                Annotation[] annotations,
                                                                Retrofit retrofit) {
            return new StringResponseConvert();
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations,
                                                              Annotation[] methodAnnotations,
                                                              Retrofit retrofit) {
            return new StringRequestBodyConverter();
        }
    }

    private static class StringResponseConvert implements Converter<ResponseBody, String> {

        @Override
        public String convert(@NonNull ResponseBody value) throws IOException {
            try {
                return value.string();
            } finally {
                value.close();
            }
        }
    }

    public static class StringRequestBodyConverter implements Converter<String, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        @Override
        public RequestBody convert(@NonNull String value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            writer.write(value);
            writer.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }


}
