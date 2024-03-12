package siwenyu.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class AliOSSUtils {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    private static final String ACCESS_KEY_ID = "bbb";


    private static final String ACCESS_KEY_SECRET = "bbb";
    // 填写Bucket名称，例如examplebucket。
    private static final String BUCKET_NAME = "bbb";

    /**
     * 设置每个分片的大小(单位bytes)
     */
    private final static long partSize = 10 * 1024 * 1024;
    /**
     * 获取CPU核心线程数
     */
    private final static int cores =  Runtime.getRuntime().availableProcessors();
    /**
     * 设置执行分片上传任务的线程池
     */
    public static ExecutorService threadPoolTaskExecutor = null;
    static{

         //自定义一个线程池
        threadPoolTaskExecutor = new ThreadPoolExecutor(
                cores*4, // coreSize
                cores*8, // maxSize
                60, // 60s
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(cores*4),  // 有界队列
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public String multipartUpload( String objectName,MultipartFile multipartFile) {

        OSS ossClient = new OSSClientBuilder().build(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String url = null;
        // 创建InitiateMultipartUploadRequest对象。
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(BUCKET_NAME, objectName);
        // 初始化分片。
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
        // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();
        //获取文件大小（单位bytes）
        long fileLength = multipartFile.getSize();
        // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags = Collections.synchronizedList(new ArrayList<>());
        //根据文件大小、分片大小,计算分片的个数
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        /*
         * 创建多线程上传的线程池和同步计数器
         */
        CountDownLatch partCountDownLatch = new CountDownLatch(partCount);
        // 遍历分片上传。
        InputStream instream = null;

        try {
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                instream = multipartFile.getInputStream();
                // 跳过已经上传的分片。
                instream.skip(startPos);
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(BUCKET_NAME);
                uploadPartRequest.setKey(objectName);
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(instream);
                // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
                uploadPartRequest.setPartSize(curPartSize);
                // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
                uploadPartRequest.setPartNumber(i + 1);

                /*
                 * 创建线程池上传分片
                 */
                threadPoolTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                        UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                        // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
                        partETags.add(uploadPartResult.getPartETag());
                        //上传完毕,修改同步计数器
                        partCountDownLatch.countDown();
                    }
                });
            }
            // 创建CompleteMultipartUploadRequest对象。

            partCountDownLatch.await();
            // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(BUCKET_NAME, objectName, uploadId, partETags);
            // 完成上传
            CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            url = "https://"+BUCKET_NAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
        return url;
    }

}