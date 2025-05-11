// Check if client is logged in
if (!localStorage.getItem("email")) {
    // If not logged in, to redirect to login page.
    window.location.replace("http://localhost:8080/login");
}


const currentEmailSpan = document.getElementById("current-email");
const passwordForm = document.getElementById("password-form");
const hiddenEmailInput = document.getElementById("hidden-email-input");



// display the account that the password is being changed, and populate the hidden input field
const currentEmail = localStorage.getItem("email");
currentEmailSpan.textContent = currentEmail;
hiddenEmailInput.value = currentEmail;



const isValidPassword = (password) => {
    const specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;
    const noWhitespaceRegex = /^\S*$/;
    if (specialCharRegex.test(password) && noWhitespaceRegex.test(password) && password.length >= 12) {
        return true;
    }
    return false;
}


const isPasswordsMatch = (pass1, pass2) => {
    if (pass1 === pass2) {
        return true;
    }
    return false;  
}


passwordForm.addEventListener("submit", (e) => {
    let newPassword = document.getElementById("new-password").value;
    let confirmPassword = document.getElementById("confirm-password").value;

    if (!isPasswordsMatch(newPassword, confirmPassword)) {
        alert("Passwords do not match!");
        e.preventDefault();
        return;
    }

    if (!isValidPassword(newPassword)) {
        alert("Password does not meet the criteria!");
        e.preventDefault();
        return;
    }
});


