package com.pophory.pophoryserver.infrastructure.s3;


import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AwsS3Config awsS3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    // 파일 업로드
    public void upload(InputStream file, String fileName, ObjectMetadata objectMetadata) {
        awsS3Config.amazonS3Client().putObject(bucket, fileName, file, objectMetadata);
    }

    // 파일 삭제
    public void delete(String fileName) {
        String key = URLDecoder.decode(fileName.split("/")[3], StandardCharsets.UTF_8);
        awsS3Config.amazonS3Client().deleteObject(bucket, key);
    }

    public String getImageUrl(String filePath) {
        return awsS3Config.amazonS3Client().getUrl(bucket, filePath).toString();
    }
}
