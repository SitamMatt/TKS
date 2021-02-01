function validateForm(){
    alert("Walidacja")
    const password = document.getElementById("form:password");
    if(password.value.length &lt; 8){
        alert("#{msg['password_too_short']}")
    }
    return password.value.length > 8
}