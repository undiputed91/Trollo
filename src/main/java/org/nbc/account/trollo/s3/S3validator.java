package org.nbc.account.trollo.s3;

import com.amazonaws.services.s3.AmazonS3;

public class S3validator {

    public static void validate(AmazonS3 amazonS3, String bucket, String fileName) {

    }

    private static boolean isExistFile(AmazonS3 amazonS3, String bucket, String filename) {
        return amazonS3.doesObjectExist(bucket, filename);
    }

}
