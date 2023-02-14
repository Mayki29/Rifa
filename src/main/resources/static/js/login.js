$(document).ready(function() {
    //on ready
  });
  
async function iniciarSesion(){  
  
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.password = document.getElementById('txtPassword').value;
  
    const request = await fetch('api/login', {
          method: 'POST',
          headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
              
          },
          body: JSON.stringify(datos)
      });

      const respuesta = await request.text();
      console.log(respuesta);
      if(respuesta == 'Fail' || respuesta == "Fail"|| respuesta == null){
        alert("Las credenciales son incorrectas. Intenta otra vez");
      }else{
        localStorage.token = respuesta;
        localStorage.nombre = datos.nombre;
        window.location.href = 'index.html';
      }
  
  }