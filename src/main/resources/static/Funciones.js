
    function actualizarCampos() {
        console.log("Actualizando campos...");
        var select = document.getElementById("inputCatProducto");
        var precioInput = document.getElementById("precio");
        var codigoInput = document.getElementById("codigo");
        var stockInput = document.getElementById("stock");

        var precio = select.options[select.selectedIndex].getAttribute("data-precio");
        var stock = select.options[select.selectedIndex].getAttribute("data-stock");
        var codigo = select.options[select.selectedIndex].getAttribute("data-codigo");

        precioInput.value = precio || "";
        stockInput.value = stock || "";
        codigoInput.value = codigo || "";

    }

    document.getElementById("inputCatProducto").addEventListener("change", actualizarCampos);
    
    
    
    function actualizarCamposCliente() {
        console.log("Actualizando campos...");
        var select = document.getElementById("inputCliente");
        var clienteInput = document.getElementById("nomcliente");
        var dniInput = document.getElementById("dni");
        var telofonoInput = document.getElementById("telefono");
        var codigoInput = document.getElementById("codigoCliente");

		var codigo = select.options[select.selectedIndex].getAttribute("data-codigo");
        var cliente = select.options[select.selectedIndex].getAttribute("data-cliente");
        var dni = select.options[select.selectedIndex].getAttribute("data-dni");
        var telefono = select.options[select.selectedIndex].getAttribute("data-telefono");

		codigoInput.value = codigo || "";
        clienteInput.value = cliente || "";
        dniInput.value = dni || "";
        telofonoInput.value = telefono || "";

    }

    document.getElementById("inputCliente").addEventListener("change", actualizarCamposCliente);
    

// Función para eliminar Producto
function eliminarPro(codproducto, estado) {
    let mensaje;
    let confirmarMensaje;

    if (estado === 1) {
        mensaje = "¿Estás seguro de eliminar el producto?";
        confirmarMensaje = "¡Tu producto ha sido eliminado!";
    } else if (estado === 0) {
        mensaje = "¿Estás seguro de recuperar el producto?";
        confirmarMensaje = "¡Tu producto ha sido recuperado!";
    }

    swal({
        title: mensaje,
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
    .then((OK) => {
        if (OK) {
            location.href = "/eliminarProducto/" + codproducto;
            swal(confirmarMensaje, {
                icon: "success",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarProducto?estado=" + estado;
                }
            });
        } else {
            swal("¡Tu producto está a salvo!", {
                icon: "info",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarProducto?estado=" + estado;
                }
            });
        }
    });
}



// Función para eliminar Cliente
function eliminarCli(idcliente,estado) {
    let mensaje;
    let confirmarMensaje;
    
     if (estado === 1) {
        mensaje = "¿Estás seguro de eliminar el cliente?";
        confirmarMensaje = "¡El cliente ha sido eliminado!";
    } else if (estado === 0) {
        mensaje = "¿Estás seguro de recuperar el Cliente?";
        confirmarMensaje = "¡El Cliente ha sido recuperado!";
    }
    
    swal({
        title: mensaje,
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
    .then((OK) => {
        if (OK) {
            location.href = "/eliminarCliente/" + idcliente ,
            swal(confirmarMensaje, {
                icon: "success",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarCliente?estado=" + estado;
                }
            });
        } else {
            swal("¡El Cliente está a salvo!", {
                icon: "info",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarCliente?estado=" + estado;
                }
            });
        }
    });
}




// Función para eliminar Empleado
function eliminarEmp(idempleado, estado) {

	let mensaje;
    let confirmarMensaje;

    if (estado === 1) {
        mensaje = "¿Estás seguro de eliminar el producto?";
        confirmarMensaje = "¡Tu producto ha sido eliminado!";
    } else if (estado === 0) {
        mensaje = "¿Estás seguro de recuperar el producto?";
        confirmarMensaje = "¡Tu producto ha sido recuperado!";
    }
    swal({
        title:mensaje,
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
    .then((OK) => {
        if (OK) {
            location.href = "/eliminarEmpleado/" + idempleado ,
            swal(confirmarMensaje, {
                icon: "success",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarEmpleado?estado="+estado;
                }
            });
        } else {
            swal("¡El Empleado está a salvo!", {
                icon: "info",
            }).then((ok) => {
                if (ok) {
                    location.href = "/cargarEmpleado?estado="+estado;
                }
            });
        }
    });
}








