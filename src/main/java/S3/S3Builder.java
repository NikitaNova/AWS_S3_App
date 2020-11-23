package S3;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.List;

public class S3Builder {
    S3Client s3Client;
    Region region;

    public S3Builder(){
        region = Region.US_EAST_1;
        s3Client = S3Client.builder()
                .region(region)
                .build();
    }

    public void listBuckets(){
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach((x)-> System.out.println(x.name()));
    }

    public void uploadObjectsToBucket(Byte data, String bucketName){

    }

}
