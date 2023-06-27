package com.teachingaura.util

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.HttpMethod
import com.google.cloud.storage.Storage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@Component
class SignedUrlUtil @Inject constructor(val storage: Storage) {

    @Value("\${gcp.bucketName}")
    private lateinit var bucketName: String

    private var timeout: Long = 2

    fun generateSignedUrlToUpload(objectName: String): String {

        // Define Resource
        val blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build()

        // Generate Signed URL
//        val extensionHeaders: MutableMap<String, String> = HashMap()
//        extensionHeaders["Content-Type"] = "image/webp"

        val url: URL = storage.signUrl(
            blobInfo,
            timeout,
            TimeUnit.HOURS,
            Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
            Storage.SignUrlOption.withV4Signature()
        )

        return url.toString()
    }

    fun generateSignedUrlToDownload(objectName: String): String {

        // Define resource
        val blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build()

        val url = storage.signUrl(blobInfo, timeout, TimeUnit.HOURS, Storage.SignUrlOption.withV4Signature())
        return url.toString()
    }
}