$(document).ready(function() {
  if(localStorage.token == undefined || localStorage.token == "" || localStorage.token == ''){
    window.location.href = 'login.html';
  }else{
      
    //$('#rifas').DataTable();
    cargarRifas();
    document.getElementById("userName").innerText = localStorage.nombre;
  }
});

function getHeaders(){
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  };
}

async function cargarRifas(){  
  const request = await fetch('api/rifas', {  
    method: 'GET',
    headers: getHeaders(),
  });
  const usuarios = await request.json();



  let listadoHtml = '';
  let contador = 0;

  for (let usuario of usuarios){
    let btnVerRifa = '<a onclick="verRifaPorId('+usuario.idRifa+')" class="btn btn-primary"><i class="fas fa-eye"></i></a>';
    let botonConfirmar = usuario.confirmacion == 'Confirmada' ? '<a onclick="confirmarVenta('+usuario.idRifa+')" class="btn btn-success disabled"><i class="fas fa-check-circle"></i></a>' : '<a onclick="confirmarVenta('+usuario.idRifa+')" class="btn btn-success"><i class="fas fa-check-circle"></i></a>';
    let telefonoTexto = usuario.celular == null ? '-' : usuario.celular;
    let colorT = usuario.confirmacion == 'Confirmada' ? '<tr style="color: rgb(0, 170, 0)">' : '<tr style="color: red">';
    let usuarioHtml = colorT+'<td>'
    + usuario.idRifa+'</td><td>' 
    + usuario.nombres + '</td><td>'
    + usuario.apellidos+'</td><td>'
    + usuario.metodoPago+'</td><td>'
    + usuario.fecha+'</td><td>'
    + telefonoTexto+'</td><td>'
    + usuario.direccion+'</td><td>'
    + usuario.confirmacion+'</td><td>'
    + botonConfirmar + btnVerRifa +'</td></tr>';

    listadoHtml += usuarioHtml;
    contador += 1;
  }

  console.log(usuarios);

  let porcentaje = contador*100/15;

  document.querySelector('#rifas tbody').innerHTML = listadoHtml;
  document.getElementById("idContador").innerText = contador;
  document.getElementById("idBarra").setAttribute('style','width: ' + porcentaje + '%');
}



async function confirmarVenta(id){

  if(!confirm('¿Desea confirmar esta venta?')){
    return;
  }

  const request = await fetch('api/confirmar', {  // lo que hace el await es que se espere el resultado del codigo
    method: 'PUT',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token,
      'idRifa':id
    }
  });
  const confirmacion = await request.text();


  if(confirmacion == "Confirmada"){
    alert("Se confirmo correctamente la rifa: "+id);
  }else{
    alert("Hubo un error");
  }
  cargarRifas();
}

function cerrarSesion(){
  localStorage.removeItem('token');
  window.location.href = 'login.html';
}


async function verRifaPorId(id){

  const request = await fetch('api/rifa/'+id, {  // lo que hace el await es que se espere el resultado del codigo
    method: 'GET',
    headers: getHeaders(),
  });
  const rifa = await request.json();

  sessionStorage.rifa = JSON.stringify(rifa);
  window.location.href = 'visualizarifa.html';
}


function changeTypeInput(){
  let selectValue = document.getElementById('stColumna').value;
  if(selectValue == 'fecha'){
    document.getElementById('txtSearch').setAttribute('type','date');
  }else{
    document.getElementById('txtSearch').setAttribute('type','search');
  }
  
}

async function searchRifas(){
  let datos = {};
    datos.data = document.getElementById('txtSearch').value;
    datos.option = document.getElementById('stColumna').value;

  if(datos.data == ""){
    cargarRifas();
    return;
  }
  
  
  const request = await fetch('api/rifa/search', {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(datos)
    });

  const respuesta = await request.json();



  let listadoHtml = '';

  for (let usuario of respuesta){
    let btnVerRifa = '<a onclick="verRifaPorId('+usuario.idRifa+')" class="btn btn-primary"><i class="fas fa-eye"></i></a>';
    let botonConfirmar = usuario.confirmacion == 'Confirmada' ? '<a onclick="confirmarVenta('+usuario.idRifa+')" class="btn btn-success disabled"><i class="fas fa-check-circle"></i></a>' : '<a onclick="confirmarVenta('+usuario.idRifa+')" class="btn btn-success"><i class="fas fa-check-circle"></i></a>';
    let telefonoTexto = usuario.celular == null ? '-' : usuario.celular;
    let colorT = usuario.confirmacion == 'Confirmada' ? '<tr style="color: rgb(0, 170, 0)">' : '<tr style="color: red">';
    let usuarioHtml = colorT+'<td>'
    + usuario.idRifa+'</td><td>' 
    + usuario.nombres + '</td><td>'
    + usuario.apellidos+'</td><td>'
    + usuario.metodoPago+'</td><td>'
    + usuario.fecha+'</td><td>'
    + telefonoTexto+'</td><td>'
    + usuario.direccion+'</td><td>'
    + usuario.confirmacion+'</td><td>'
    + botonConfirmar + btnVerRifa +'</td></tr>';

    listadoHtml += usuarioHtml;
  }

  document.querySelector('#rifas tbody').innerHTML = listadoHtml;
}

