function confirmAction(){
    var res = confirm("Kliknij OK aby potwierdzić operację.");
    if (res == true){
        return true
    }
    else {
        alert("Operacja została anulowana.")
        return false
    }
}