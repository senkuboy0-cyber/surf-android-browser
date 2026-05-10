package com.surf.browser.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MozillaApiService {
    
    @GET("addons/search/")
    suspend fun searchExtensions(
        @Query("q") query: String,
        @Query("sort") sort: String = "popular",
        @Query("page") page: Int = 1
    ): Response<ExtensionResponse>
    
    @GET("addons/categorized/")
    suspend fun getCategorizedExtensions(): Response<CategorizedExtensionsResponse>
    
    @GET("addons/addon/{extensionId}/")
    suspend fun getExtensionDetails(
        @Path("extensionId") extensionId: String
    ): Response<ExtensionDetailsResponse>
    
    @GET("addons/addon/{extensionId}/reviews/")
    suspend fun getExtensionReviews(
        @Path("extensionId") extensionId: String,
        @Query("page") page: Int = 1
    ): Response<ExtensionReviewsResponse>
    
    @GET("addons/addon/{extensionId}/versions/")
    suspend fun getExtensionVersions(
        @Path("extensionId") extensionId: String
    ): Response<ExtensionVersionsResponse>
}

@JsonClass(generateAdapter = true)
data class ExtensionResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Extension>
)

@JsonClass(generateAdapter = true)
data class Extension(
    val id: String,
    val slug: String,
    val type: String,
    @Json(name = "applicable_types") val applicableTypes: List<String>,
    val guid: String,
    val name: String,
    val current_version: ExtensionVersion?,
    val summary: String,
    val description: String,
    val authors: List<Author>,
    val categories: List<Category>,
    val tags: List<String>,
    val rating: Rating?,
    val download_count: Int,
    val created: String,
    val modified: String,
    val icon_url: String?,
    val preview: Preview?,
    val weekly_downloads: Int
)

@JsonClass(generateAdapter = true)
data class ExtensionVersion(
    val version: String,
    val file: ExtensionFile,
    val created: String,
    val modified: String,
    val compatibility: Compatibility
)

@JsonClass(generateAdapter = true)
data class ExtensionFile(
    val size: Int,
    val hash: String,
    val url: String,
    val id: String
)

@JsonClass(generateAdapter = true)
data class Compatibility(
    @Json(name = "min_version") val minVersion: String,
    @Json(name = "max_version") val maxVersion: String,
    val strict_min: Boolean,
    val strict_max: Boolean
)

@JsonClass(generateAdapter = true)
data class Author(
    val id: Int,
    val name: String,
    val slug: String,
    val url: String?
)

@JsonClass(generateAdapter = true)
data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Rating(
    val average: Double,
    val count: Int
)

@JsonClass(generateAdapter = true)
data class Preview(
    val icons: List<PreviewIcon>,
    val add_on: PreviewAddon
)

@JsonClass(generateAdapter = true)
data class PreviewIcon(
    val size: Int,
    val url: String
)

@JsonClass(generateAdapter = true)
data class PreviewAddon(
    val name: String,
    val slug: String,
    val summary: String
)

@JsonClass(generateAdapter = true)
data class CategorizedExtensionsResponse(
    val results: Map<String, List<Extension>>
)

@JsonClass(generateAdapter = true)
data class ExtensionDetailsResponse(
    val guid: String,
    val slug: String,
    val name: String,
    val current_version: ExtensionVersion,
    val summary: String,
    val description: String,
    val authors: List<Author>,
    val categories: List<Category>,
    val tags: List<String>,
    val rating: Rating?,
    val download_count: Int,
    val created: String,
    val modified: String,
    val icon_url: String?,
    val preview: Preview?,
    val weekly_downloads: Int,
    val permissions: List<String>
)

@JsonClass(generateAdapter = true)
data class ExtensionReviewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Review>
)

@JsonClass(generateAdapter = true)
data class Review(
    val id: Int,
    val rating: Int,
    val comment: String,
    val created: String,
    val modified: String,
    val user: ReviewUser
)

@JsonClass(generateAdapter = true)
data class ReviewUser(
    val id: Int,
    val name: String,
    val url: String?
)

@JsonClass(generateAdapter = true)
data class ExtensionVersionsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ExtensionVersion>
)