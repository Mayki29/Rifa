$(document).ready(function() {
    if(localStorage.token == undefined){
        window.location.href = 'login.html';
    }

    if(sessionStorage.rifa == undefined){
        window.location.href = 'index.html';
    }
    
    verRifa();

});
  
function verRifa(){
    let sRifa = JSON.parse(sessionStorage.rifa);
    console.log(sRifa);

    document.getElementById("h1Id").innerText = sRifa.idRifa;
    document.getElementById("lblNombre").innerText = sRifa.nombres + ' ' + sRifa.apellidos;
    document.getElementById("lblNumero").innerText = sRifa.celular;
    document.getElementById("lblDireccion").innerText = sRifa.direccion;
    document.getElementById("lblMetodo").innerText = sRifa.metodoPago;
    document.getElementById("lblFecha").innerText = sRifa.fecha;
}
