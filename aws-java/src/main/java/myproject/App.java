package myproject;

import com.pulumi.Pulumi;
import com.pulumi.asset.FileAsset;
import com.pulumi.aws.s3.Bucket;
import com.pulumi.aws.s3.BucketObject;
import com.pulumi.aws.s3.BucketObjectArgs;
import com.pulumi.aws.s3.BucketArgs;
import com.pulumi.aws.s3.inputs.BucketWebsiteArgs;
import com.pulumi.core.Output;

public class App {
    public static void main(String[] args) {
        Pulumi.run(ctx -> {

            // Create an AWS resource (S3 Bucket)
           // Create an AWS resource (S3 Bucket)
           var bucket = new Bucket("my-bucket", BucketArgs.builder()
                .website(BucketWebsiteArgs.builder()
                    .indexDocument("index.html")
                    .build()
                )
                .build()
            );

            // Export the name of the bucket
            ctx.export("bucketEndpoint", Output.format("http://%s", bucket.websiteEndpoint()));

            // Create an S3 Bucket object
            new BucketObject("index.html", BucketObjectArgs.builder()
                .bucket(bucket.bucket())
                .source(new FileAsset("index.html"))
                .acl("public-read")
                .contentType("text/html")
                .build()
            );
        });
    }
}
