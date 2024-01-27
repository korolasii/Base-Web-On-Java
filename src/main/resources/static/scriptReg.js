document.addEventListener("DOMContentLoaded", function () {
    var signUpButton = document.getElementById("signUpButton");

    signUpButton.addEventListener("click", function () {
        var email = document.getElementById("email").value;
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        var data = {
            email: email,
            username: username,
            password: password
        };

        var jsonData = JSON.stringify(data);

        axios.post('/saveUserData', JSON.stringify(data), {
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            console.log(response.data);
            window.location.href = "/welcome/" + response.data.username;
        })
        .catch(error => {
            console.error(error.response.data);
            alert("Ошибка при регистрации!");
        });
    });
});