package com.baseWeb.baseWeb;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequestExample {

    public static void main(String[] args) throws Exception {
        // URL вашего эндпоинта
        String url = "http://ваш_сервер/ваш_путь/signIn";

        // Пример данных, которые вы хотите отправить
        String requestBody = "{\"username\":\"your_username\",\"password\":\"your_password\"}";

        // Преобразование данных в массив байтов
        byte[] postData = requestBody.getBytes(StandardCharsets.UTF_8);

        // Установка соединения
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Отправка данных
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(postData);
        }

        // Чтение ответа
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
}
