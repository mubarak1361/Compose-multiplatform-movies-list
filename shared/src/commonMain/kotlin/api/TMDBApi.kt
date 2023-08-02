package api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.MoviesWrapper

class TMDBApi {

    private val httpClient = HttpClient {
        defaultRequest {
            url {
                headers.append("Authorization", "Bearer $API_KEY")
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getAllMovies(region: String): MoviesWrapper {
        return httpClient.get("$BASE_URL/discover/movie") {
            url {
                parameters.append("region", region)
            }
        }.body()
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
        const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ZDA0MzdiZDE5MmRjOTM2MWZjNTU2ZDNkNjUxZjIyMiIsInN1YiI6IjYyYjk2YmYxMzU2YTcxMDA1MWM5NTQ2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wMswLhFwxbt-FpIwX9IQsr_gwyiMB36N36IPFgcJJ74"
    }
}