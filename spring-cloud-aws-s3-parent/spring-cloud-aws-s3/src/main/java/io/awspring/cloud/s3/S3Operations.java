/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.awspring.cloud.s3;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import org.springframework.lang.Nullable;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

public interface S3Operations {

	/**
	 * Creates a bucket in S3.
	 *
	 * @param bucketName - the bucket name
	 * @return created bucket location {@link CreateBucketResponse#location()}
	 */
	String createBucket(String bucketName);

	/**
	 * Deletes a S3 bucket.
	 *
	 * @param bucketName - the bucket name
	 */
	void deleteBucket(String bucketName);

	/**
	 * Deletes an object from S3 bucket.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 */
	void deleteObject(String bucketName, String key);

	/**
	 * Deletes an object from S3 bucket.
	 *
	 * @param s3Url - the S3 url s3://bucket/key
	 */
	void deleteObject(String s3Url);

	/**
	 * Stores a Java object in a S3 bucket. Uses {@link S3ObjectConverter} for serialization.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @param object - the Java object to serialize and store
	 * @return created {@link S3Resource}.
	 */
	S3Resource store(String bucketName, String key, Object object);

	/**
	 * Reads a Java object from a S3 bucket. Uses {@link S3ObjectConverter} for deserialization.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @param clazz - the class of the read object
	 * @param <T> - the type of the read object
	 * @return an object
	 */
	<T> T read(String bucketName, String key, Class<T> clazz);

	/**
	 * Uploads data from an input stream to a S3 bucket.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @param inputStream - the input stream
	 * @param objectMetadata - the object metadata
	 * @return created {@link S3Resource}
	 */
	S3Resource upload(String bucketName, String key, InputStream inputStream, @Nullable ObjectMetadata objectMetadata);

	/**
	 * Uploads data from an input stream to a S3 bucket.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @param inputStream - the input stream
	 * @return created {@link S3Resource}
	 */
	default S3Resource upload(String bucketName, String key, InputStream inputStream) {
		return upload(bucketName, key, inputStream, null);
	}

	/**
	 * Downloads object from S3.
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @return downloaded object represented as {@link S3Resource}
	 */
	S3Resource download(String bucketName, String key);

	/**
	 * Get PreSigned URL for fetching an S3 Object
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @param signatureDuration -
	 * @return downloaded object represented as {@link S3Resource}
	 */
	URL getPreSignedUrlForRead(String bucketName, String key, Duration signatureDuration);

	/**
	 * Get PreSigned URL for putting an S3 Object
	 *
	 * @param bucketName - the bucket name
	 * @param key - the object key
	 * @return downloaded object represented as {@link S3Resource}
	 */
	URL getPreSignedUrlForPut(String bucketName, String key, Duration signatureDuration,
			@Nullable ObjectMetadata objectMetadata);
}
