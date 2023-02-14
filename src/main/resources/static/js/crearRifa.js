$(document).ready(function() {
    if(localStorage.token == undefined){
        window.location.href = 'login.html';
    }
  });
  
  function getHeaders(){
    return {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token
    };
  }
  
  async function registrarRifa(){  
    
    if(!confirm("¿Estas seguro de registrar?")){
        return
    }

    btnCarga()

    let datosRifa ={};
    datosRifa.nombres = document.getElementById("txtNombres").value;
    datosRifa.apellidos = document.getElementById("txtApellidos").value;
    datosRifa.metodoPago = document.getElementById("selMetodo").value;
    datosRifa.celular = document.getElementById("txtCelular").value;
    datosRifa.direccion = document.getElementById("txtDireccion").value;



    const request = await fetch('api/crear', {  
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(datosRifa)
    });
    try{
      const rifasLista = await request.json();
      sessionStorage.rifa = JSON.stringify(rifasLista);
      

      window.location.href = 'visualizarifa.html';
    }catch(error){
      alert("Ocurrio un error, se cargara la pagina otra vez y vuelva a intentarlo");
      location.reload();
    }
    

    
  }
  
  
  
  async function eliminarUsuario(id){
  
    if(!confirm('¿Desea eliminar este usuario?')){
      return;
    }
    const request = await fetch('api/usuarios/' + id, {  // lo que hace el await es que se espere el resultado del codigo
      method: 'DELETE',
      headers: getHeaders(),
    });
  
    location.reload()
    
  }

  function btnCarga(){
    let spin = '<div class="spinner-border text-light" role="status">'+
                '<span class="sr-only">Loading...</span></div>';
    
    document.getElementById("btnFinalizar").innerHTML = spin;
    document.getElementById("btnFinalizar").setAttribute('disabled','');
    document.getElementById("btnCancelar").setAttribute('disabled','');
    document.getElementById("btnCerrar").setAttribute('disabled','');

    
  }