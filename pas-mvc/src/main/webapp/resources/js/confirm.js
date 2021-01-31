function confirmAction(){
    var res = confirm("Click OK to confirm action.");
    if (res == true){
        return true
    }
    else {
        alert("Operacja anulowana")
        return false
    }
}