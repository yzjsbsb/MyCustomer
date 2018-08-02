package cn.com.hxx.fakewaterfall.Module.Http.Utils;

import android.accounts.NetworkErrorException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by apple on 2018/7/31.
 */

public class HttpURLConnectionUtils {

    public static String get(String path){
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            //设置字符集
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //从主机读取数据的超时时间
            httpURLConnection.setReadTimeout(5000);
            //连接主机的超时时间
            httpURLConnection.setConnectTimeout(10000);
            int responseCode = httpURLConnection.getResponseCode(); //调用getResponseCode就不需要调用connect了
            if (responseCode == 200){
                InputStream inputStream = httpURLConnection.getInputStream();
                return getStringFromInputStream(inputStream);
            }else {
                throw new NetworkErrorException("RESPONSE_CODE IS" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    public static Bitmap downLoadPic(String path){
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            //设置字符集
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //从主机读取数据的超时时间
            httpURLConnection.setReadTimeout(5000);
            //连接主机的超时时间
            httpURLConnection.setConnectTimeout(10000);
            int responseCode = httpURLConnection.getResponseCode(); //调用getResponseCode就不需要调用connect了
            if (responseCode == 200){
                InputStream inputStream = httpURLConnection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            }else {
                throw new NetworkErrorException("RESPONSE_CODE IS" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();// 关闭连接
            }
        }
        return null;
    }



    public static String post(String path, String data){
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(10000);

            // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
            OutputStream outputStream = httpURLConnection.getOutputStream();// 获得一个输出流,向服务器写数据
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200){
                InputStream inputStream = httpURLConnection.getInputStream();
                return getStringFromInputStream(inputStream);
            }else {
                throw new NetworkErrorException("RESPONSE_CODE IS" + responseCode);
            }

        }catch (Exception e){

        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
        return null;
    }



    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }

}
