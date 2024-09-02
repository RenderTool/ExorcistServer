package com.exorcist.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

public class TencentCosUtil {

    private static String secretId = "AKIDyux03xqhNtKk3HRhzGbD4DgBnKxjSRWi";
    private static String secretKey = "T4kVH712Kxr7UuGgGxmcYd7b2X5qjuz3";
    private static String bucketName = "exorcist-1301761261";
    private static String region = "ap-shanghai";

    private static URL getObjectUrl(String key) {

        // getObjectUrl 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));

        COSClient cosClient = new COSClient(cred, clientConfig);
        // 设置生成的 url 的协议名
        clientConfig.setHttpProtocol(HttpProtocol.https);

        URL url= cosClient.getObjectUrl(bucketName, key);

        System.out.println(url);

        cosClient.shutdown();

        return url;

    }

    @SneakyThrows
    public static String putFile(String key, MultipartFile file) {

        String url="";

        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));

        COSClient cosClient = new COSClient(cred, clientConfig);

        // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流

        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        objectMetadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);
        try {

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());

            //获取下载地址
            url= getObjectUrl(key).toString();
            cosClient.shutdown();

        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        return url;
    }
}
