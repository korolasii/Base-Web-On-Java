package com.baseWeb.baseWeb;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ControllerStart {

    private static final String USERS_FILE_PATH = "base.json";

    @Autowired
    private RegisteredUsers registeredUsers;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/saveUserData")
    @ResponseBody
    public Map<String, String> saveUserData(@RequestBody UserData userData) {
        try {
            List<UserData> existingUsers = loadUsersFromFile();
            existingUsers.add(userData);
            saveUsersToFile(existingUsers);

            Map<String, String> response = new HashMap<>();
            response.put("username", userData.getUsername());
            return response;
        } catch (IOException e) {
            e.printStackTrace();

            return Collections.singletonMap("error", "Ошибка при регистрации!");
        }
    }

    @GetMapping("/signIn")
    public String signInPage() {
        return "SignIn";
    }

    @PostMapping("/signInBack")
    @ResponseBody
    public ResponseEntity<Map<String, String>> signIn(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        try {
            List<UserData> existingUsers = loadUsersFromFile();
            Optional<UserData> matchedUser = existingUsers.stream()
                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                    .findFirst();

            if (matchedUser.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("username", matchedUser.get().getUsername());
                response.put("email", matchedUser.get().getEmail());
                response.put("password", matchedUser.get().getPassword());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Неверные учетные данные"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Ошибка при обработке запроса"));
        }
    }



    @GetMapping("/welcome/{username}")
    public String welcome(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }

    private List<UserData> loadUsersFromFile() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(USERS_FILE_PATH)), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        RegisteredUsers registeredUsers = gson.fromJson(json, RegisteredUsers.class);
        return registeredUsers != null ? registeredUsers.getUsers() : new ArrayList<>();
    }


    private void saveUsersToFile(List<UserData> users) throws IOException {
        Gson gson = new Gson();
        RegisteredUsers registeredUsers = new RegisteredUsers(users);
        String json = gson.toJson(registeredUsers);

        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            writer.write(json);
        }
    }
}