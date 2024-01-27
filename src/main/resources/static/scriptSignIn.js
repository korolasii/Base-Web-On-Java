function signIn() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    const requestData = new URLSearchParams();
    requestData.append('username', username);
    requestData.append('password', password);

    console.log("Sending request data:", requestData);

    axios.post('signInBack', requestData)
        .then(response => {
            window.location.href = "/welcome/" + response.data.username;
            console.log(response.data);
        })
        .catch(error => {
            console.error('Error during sign-in:', error);
        });
}