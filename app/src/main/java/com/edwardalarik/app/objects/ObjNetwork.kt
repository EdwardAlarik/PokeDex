package com.edwardalarik.app.objects

import android.app.Application
import android.os.Handler
import com.edwardalarik.app.BuildConfig
import com.edwardalarik.app.Log
import com.edwardalarik.app.R
import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.extensions.toCapital
import com.edwardalarik.app.api.extensions.toId
import com.edwardalarik.app.api.extensions.toTrueFalse
import com.edwardalarik.app.api.logic.retrofitCache
import com.edwardalarik.app.api.service.APIInterface
import com.edwardalarik.app.api.webmodel.Pokemon
import com.edwardalarik.app.api.webmodel.PokemonSpecies
import com.edwardalarik.app.api.webmodel.listPokemon
import com.edwardalarik.app.api.webmodel.listType
import com.edwardalarik.app.api.webmodel.listAbility
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.toString

class ObjNetwork(
    private val application: Application,
    private val executorService: ExecutorService,
    private val mainThreadHandler: Handler,
    private val objVariable: ObjVariable,
    private val objBD: ObjDB,
    private val objObserver: ObjObserver
) {
    init {
        Log.d(
            this::class.java.simpleName,
            "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}"
        )
    }

    var sslContextGlobal: SSLContext? = null
    var trustManagerGlobal: X509TrustManager? = null
    private var _apiInterface: APIInterface? = null
    var currentUrlApp = BuildConfig.url_app

    val apiInterface: APIInterface
        get() {
            if (_apiInterface == null) initApiInterface(BuildConfig.url_app)
            return _apiInterface!!
        }

    private fun initApiInterface(url: String, addInterceptor: Boolean = true) {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate: Certificate =
            application.resources.openRawResource(R.raw.certificate).use {
                certificateFactory.generateCertificate(it)
            }

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("cert", certificate)
        }

        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustManagerFactory.trustManagers, SecureRandom())
        }

        val trustManager = trustManagerFactory.trustManagers
            .first { it is X509TrustManager } as X509TrustManager

        sslContextGlobal = sslContext
        trustManagerGlobal = trustManager

        val clientBuilder = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG && addInterceptor) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            sslSocketFactory(sslContext.socketFactory, trustManager)
            cache(Cache(application.retrofitCache(), 200 * 1000000L))
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }

        _apiInterface = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
            .create(APIInterface::class.java)

        currentUrlApp = url
    }

    fun listAbility(offset: Int, limit: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}ability?offset=$offset&limit=$limit"
        )

        call.validaCodeResponseAsync("listado habilidades") { isOK, message, data ->
            if (isOK) {
                val model = Gson().fromJson(data, listAbility::class.java)
                val list = model.results

                list.forEach { it ->
                    objBD.frameDB.insert(
                        DB.TAB_ABILITY, listOf(
                            Pair(DB.COL_ID_ABILITY, it.url.toId()),
                            Pair(DB.COL_NAME, it.name.toCapital())
                        ),
                        true
                    )
                }
                onReady.invoke()
            } else {
                onReady.invoke()
            }
        }

        return call
    }

    fun listMove(offset: Int, limit: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}move?offset=$offset&limit=$limit"
        )

        call.validaCodeResponseAsync("listado movimientos") { isOK, message, data ->
            if (isOK) {
                val model = Gson().fromJson(data, listAbility::class.java)
                val list = model.results

                list.forEach { it ->
                    objBD.frameDB.insert(
                        DB.TAB_MOVE, listOf(
                            Pair(DB.COL_ID_MOVE, it.url.toId()),
                            Pair(DB.COL_NAME, it.name.toCapital())
                        ),
                        true
                    )
                }
                onReady.invoke()
            } else {
                onReady.invoke()
            }
        }

        return call
    }

    fun listType(offset: Int, limit: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}type?offset=$offset&limit=$limit"
        )

        call.validaCodeResponseAsync("listado tipos") { isOK, message, data ->
            if (isOK) {
                val model = Gson().fromJson(data, listType::class.java)
                val list = model.results

                list.forEach { it ->
                    objBD.frameDB.insert(
                        DB.TAB_TYPE, listOf(
                            Pair(DB.COL_ID_TYPE, it.url.toId()),
                            Pair(DB.COL_NAME, it.name.toCapital())
                        ),
                        true
                    )
                }
                onReady.invoke()
            } else {
                onReady.invoke()
            }
        }

        return call
    }

    fun listPokemon(offset: Int, limit: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}pokemon?offset=$offset&limit=$limit"
        )

        call.validaCodeResponseAsync("listado pokemon") { isOK, message, data ->
            if (isOK) {
                val model = Gson().fromJson(data, listPokemon::class.java)
                val list = model.results

                list.forEach { it ->
                    if (!objVariable.existPokemon(it.url.toId())) {

                        objBD.frameDB.insert(
                            DB.TAB_POKEMON, listOf(
                                Pair(DB.COL_ID_POKEMON, it.url.toId()),
                                Pair(DB.COL_ORDER, ""),
                                Pair(DB.COL_NAME, it.name.toCapital()),
                                Pair(DB.COL_HEIGHT, ""),
                                Pair(DB.COL_WEIGHT, ""),
                                Pair(DB.COL_BASE_EXPERIENCE, ""),
                                Pair(DB.COL_IS_DEFAULT, "0"),
                                Pair(DB.COL_LOCATION_AREA_ENCOUNTERS, "")
                            ),
                            true
                        )
                    }
                }
                onReady.invoke()
            } else {
                onReady.invoke()
            }
        }

        return call
    }

    fun dataPokemon(idPokemon: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}pokemon/$idPokemon"
        )

        call.validaCodeResponseAsync("Datos Pokemon") { isOK, message, data ->
            try {
                if (isOK) {
                    val model = Gson().fromJson(data, Pokemon::class.java)

                    objBD.frameDB.update(
                        DB.TAB_POKEMON, listOf(
                            Pair(DB.COL_ID_POKEMON, model.id),
                            Pair(DB.COL_ORDER, model.order),
                            Pair(DB.COL_NAME, model.name.toCapital()),
                            Pair(DB.COL_CRIES_LATEST, model.cries.latest),
                            Pair(DB.COL_CRIES_LEGACY, model.cries.legacy),
                            Pair(DB.COL_SPECIES, model.species.name.toCapital()),
                            Pair(DB.COL_HEIGHT, model.height),
                            Pair(DB.COL_WEIGHT, model.weight),
                            Pair(DB.COL_BASE_EXPERIENCE, model.base_experience),
                            Pair(DB.COL_IS_DEFAULT, model.is_default.toTrueFalse()),
                            Pair(DB.COL_LOCATION_AREA_ENCOUNTERS, model.location_area_encounters)
                        ), "${DB.COL_ID_POKEMON} = '$idPokemon'"
                    )

                    model.abilities.map { it ->
                        objBD.frameDB.insert(
                            DB.TAB_POKEMON_ABILITIES, listOf(
                                Pair(DB.COL_ID_POKEMON, idPokemon),
                                Pair(DB.COL_NAME, it.ability.name.toCapital()),
                                Pair(DB.COL_IS_HIDDEN, it.is_hidden.toTrueFalse()),
                                Pair(DB.COL_SLOT, it.slot)
                            ),
                            true
                        )
                    }

                    model.stats.map { it ->
                        objBD.frameDB.insert(
                            DB.TAB_POKEMON_STATS, listOf(
                                Pair(DB.COL_ID_POKEMON, idPokemon),
                                Pair(DB.COL_NAME, it.stat.name.toCapital()),
                                Pair(DB.COL_BASE_STAT, it.base_stat),
                                Pair(DB.COL_EFFORT, it.effort)
                            ),
                            true
                        )
                    }

                    model.types.map { it ->
                        objBD.frameDB.insert(
                            DB.TAB_POKEMON_TYPES, listOf(
                                Pair(DB.COL_ID_POKEMON, idPokemon),
                                Pair(DB.COL_NAME, it.type.name.toCapital()),
                                Pair(DB.COL_SLOT, it.slot)
                            ),
                            true
                        )
                    }

                    onReady.invoke()
                } else {
                    onReady.invoke()
                }
            } catch (e: Exception) {
                Log.e("Servicios", e.toString())
            }
        }

        return call
    }

    fun dataPokemonSpecies(idPokemon: Int, onReady: () -> Unit): Call<JsonElement> {
        val call = apiInterface.getByJson(
            url = "${BuildConfig.url_app}pokemon-species/$idPokemon"
        )

        call.validaCodeResponseAsync("Datos Pokemon Species") { isOK, message, data ->
            try {
                if (isOK) {
                    val model = Gson().fromJson(data, PokemonSpecies::class.java)

                    objBD.frameDB.update(
                        DB.TAB_POKEMON, listOf(
                            Pair(DB.COL_DESCRIPTION,
                                model.flavor_text_entries[0].flavor_text.replace("\n", " ")
                                    .replace("\u000c", " ")
                            )
                        ), "${DB.COL_ID_POKEMON} = '$idPokemon'"
                    )
                    onReady.invoke()
                } else {
                    onReady.invoke()
                }
            } catch (e: Exception) {
                Log.e("Servicios", e.toString())
            }
        }

        return call
    }

    private fun <T> Call<T>.validaCodeResponseAsync(
        descrp: String, onReady: (isOK: Boolean, message: String, body: T?) -> Unit
    ) {
        executorService.execute {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d(
                        this::class.java.simpleName,
                        "DEBUG, Proceso 'JSON OBJECT', ${response.raw().request.url}\n\n${response.errorBody() ?: response.body()}\n\n"
                    )

                    val url = response.raw().request.url
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {
                            Log.d(
                                this::class.java.simpleName,
                                "DEBUG, Proceso 'CALL_BY_T', Body correcto para $descrp"
                            )
                            onReady.invoke(true, "", body)
                        } else Log.d(
                            this::class.java.simpleName,
                            "DEBUG, Proceso 'CALL_BY_T', El body es null para $descrp"
                        )
                    } else {
                        val body = response.errorBody()?.string() ?: "Sin mensaje de error"
                        if (response.code() == 400) { // Error del servidor
                            //objBugs.insertBugs("$descrp, $url\n\n$body")
                        } else if (response.code() == 401) { // Credenciales incorrectas.
                            Log.d(
                                this::class.java.simpleName,
                                "DEBUG, Proceso 'SHARED_FLOW', Envia señal por datosUsuario"
                            )
                            //objBugs.insertBugs("$descrp, La sesión ha expirado ($descrp).")
                            Log.d(
                                this::class.java.simpleName,
                                "DEBUG, Proceso 'EXPIRE', Expira desde network asíncrono con $descrp"
                            )
                        } else {
                            //objBugs.insertBugs("$descrp, falla al enviar.\n\n$url\n\n$body")
                        }
                        onReady.invoke(false, body, null)
                    }
                }

                override fun onFailure(call: Call<T>, e: Throwable) {
                    //objBugs.insertBugs(e)
                    if (!call.isCanceled) {
                        onReady.invoke(false, e.message.toString(), null)
                    }
                }
            })
        }
    }
}