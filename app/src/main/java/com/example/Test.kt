package com.example

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

//дата класс для создания тела запроса
data class SignInRequestBody(
    val email: String,
    val password: String
)

//дата класс для десириализации ответа сервера
data class SignInResponseBody(
    val token: String
)

//оздаем переменную контентТайп для передачи в тело запроса(явно определяем тип контента для сервера)
val contentType = "application/json; charset=utf-8".toMediaType()

fun main() {
    //создаем перехватчик запросов, который отображает инф. в логах
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    //создаем парсер для преобразования строки в дсон и обратно
    val gson = Gson()

    //создаем окНттр клиент
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // создаем экземпляр тела запроса с необходимыми данными
    val requestBody = SignInRequestBody(
        email = "admin@google.com",
        password = "123"
    )

    //преобразовываем экземпляр класса в Json файл
    val requestBodyString = gson.toJson(requestBody)

    //создаем строку тела запроса и помещаем туда КонтентТайп(чтобы сервер точно могу определить тип)
    val okHttpRequestBody = requestBodyString.toRequestBody(contentType)

    //создаем шаблон запроса или экземпляр класса Request
    val request = Request.Builder()
        .post(okHttpRequestBody)//тк метод запроса post вызываем соответствующий метод в шаблоне и передаем строку тела запроса
        .url("http://127.0.0.1:12345/sign-in")//передаем полную строку запроса, в конце указываем имя запроса(залогиниться)
        .build()

    //создаем вызов\Call созданного выше запроса
    val call = client.newCall(request)

    //делаем синхронный вызов, т.е текущий поток будет заблокирован пока запрос не выполнится
    //сохраняем полученный ответ в переменную
    //проверяем успешно ли прошел запрос
    val response = call.execute()
    if (response.isSuccessful) { //если запрос прошел успешно извлекаем тело и приводим к типу стринг
        val responseBodyString =
            response.body!!.string()//в строке содержится запрос из одного поля(токен типа стринг)
        //создаем экземпляр класа ответа, в поля которого будет содержаться строка полученного токена
        //передаем строку запроса и явно указываем класс
        val signInResponseBody = gson.fromJson(responseBodyString, SignInResponseBody::class.java)
        print("TOKEN: ${signInResponseBody.token}")
    } else {
        //если запрос не прошел, выбрасываем исключение
        throw java.lang.IllegalStateException("Что-то пошло не так")
    }
}