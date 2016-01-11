function venderController($scope,$http,$window,$location,$rootScope) {
   $scope.listado = {
       numberOfElements: 0,
       number: 0,
       totalElements: 0
   };
   $scope.maxSize = 100;
   $scope.listadoCodigo = {};
   $scope.articulo = {};
   $scope.codigo = {};
   $scope.ipp = 20;
   $scope.pageNumber = 1;
   $scope.vendedores = {};
   $scope.vendedor = {};
   $scope.carrito = [];
   $scope.listDevolucion = [];
   $scope.pagos = [{ id: 1, value: "Efectivo"},{ id: 2, value: "Tarjeta"}];
   $scope.formaPago = $scope.pagos[0];
   $scope.montoTotal = 0.0;
   $scope.mensajeConfirmacionVenta = "";

   $scope.obtenerListaArticulo = function(){
       var url = "/articulos?page="+($scope.pageNumber-1);
       if($scope.search != "" && $scope.search != undefined){
           var search = "&search="+$scope.search;
           url = "/articulo/search?page="+($scope.pageNumber-1)+search;
       }
       $http.get(url)
       .success(function(data, status, headers, config) {
           $scope.listado=data;
           $scope.pageNumber = $scope.listado.number+1;
       });
   };

   $scope.buscarArticuloDevolucion = function(){
       var url = "/venta/devolucion?codigoDevolucion="+$scope.codigoDevolucion;
       $http.get(url)
       .success(function(data, status, headers, config) {
           $scope.listadoDevolucion=data;
           $scope.listadoDevolucion.forEach(function(current){
                current.fecha = undefined;
            });
       });
   };

   $scope.buscarArticulo = function(){
       $scope.obtenerListaArticulo();
   };

   $scope.obtenerListaVendedores = function(){
       var url = "/vendedores?page=0&size=1000";
       $http.get(url)
       .success(function(data, status, headers, config) {
           $scope.vendedores=data;
           if($scope.vendedores.content.length > 0){
               $scope.vendedor = $scope.vendedores.content[0];
           }
       });
   };

   $scope.agregarCarrito = function(articulo){
       var existe = false;
       $scope.carrito.forEach(function(current,index,array){
           if(current.articulo.codigo == articulo.codigo ){
               if(current.articulo.cantidadStock > current.cantidad){
                   current.cantidad++;
               }
               existe = true;
           }
       });
       if(!existe && articulo.cantidadStock > 0){
           $scope.carrito.push({
               cantidad: 1,
               articulo: articulo,
               vendedor: $scope.vendedor,
               formaPago: $scope.formaPago.value,
               nroCupon: $scope.numeroCupon
           });
       }
       $scope.calcularTotal();
   }

   $scope.agregarDevolucion = function(venta){
       var existe = false;
       $scope.listDevolucion.forEach(function(current,index,array){
           if(current.venta.ventaId == venta.ventaId ){
               existe = true;
           }
       });
       if(!existe){
           $scope.listDevolucion.push({
               cantidad: 1,
               venta: venta,
               vendedor: $scope.vendedor,
               formaPago: $scope.formaPago.value,
               nroCupon: $scope.numeroCupon
           });
       }
       $scope.calcularTotal();
   }
   $scope.removerCarrito = function(articulo){
       var eliminar = -1;
       $scope.carrito.forEach(function(current,index,array){
           if(current.articulo.codigo == articulo.codigo){
               current.cantidad--;
               if(current.cantidad == 0){
                   eliminar = index;
               }
           }
       });
       if(eliminar >= 0){
           $scope.carrito.splice(eliminar,1);
       }
       $scope.calcularTotal();
   }

  $scope.removerDevolucion = function(venta){
      var eliminar = -1;
      $scope.listDevolucion.forEach(function(current,index,array){
          if(current.venta.ventaId == venta.ventaId){
              current.cantidad--;
              if(current.cantidad == 0){
                  eliminar = index;
              }
          }
      });
      if(eliminar >= 0){
          $scope.listDevolucion.splice(eliminar,1);
      }
      $scope.calcularTotal();
  }

   $scope.calcularTotal = function(){
       var total = 0;
       $scope.carrito.forEach(function(current){
           total+=current.cantidad*current.articulo.precio;
       });

       $scope.listDevolucion.forEach(function(current){
           total-=current.cantidad*current.venta.precio;
       });

       $scope.montoTotal = total;
   };

   $scope.datosVendedor=function(){
       return $scope.vendedor.nombre+" "+$scope.vendedor.apellido;
   };

   $scope.actualizarCarrito = function(){
       $scope.carrito.forEach(function(current){
           current.vendedor = $scope.vendedor;
           current.formaPago = $scope.formaPago.value;
           current.nroCupon = $scope.numeroCupon;
       });

      $scope.listDevolucion.forEach(function(current){
          current.vendedor = $scope.vendedor;
          current.formaPago = $scope.formaPago.value;
          current.nroCupon = $scope.numeroCupon;
      });

   };

   $scope.vender = function(){

       if(confirm("Esta seguro de realizar la venta?")){

           $scope.actualizarCarrito();

           $http.put("/venta/confirmar",{
                articulos: $scope.carrito,
                devoluciones: $scope.listDevolucion
           })
           .success(function(data, status, headers, config) {
               $scope.carrito = [];
               $scope.listDevolucion = [];
               $scope.calcularTotal();
               $scope.buscarArticulo();
               $scope.numeroCupon = "";
               $scope.mensajeConfirmacionVenta = data.codigoDevolucion;
               $scope.listadoDevolucion = [];
               $('#confirmacionVenta').modal('show');
           });
       }

   };

   $scope.cancelar = function(){

       if(confirm("Esta seguro de cancelar la venta?")){
           $scope.carrito = [];
           $scope.listDevolucion = [];
           $scope.calcularTotal();
           $scope.numeroCupon = "";
       }


   };

   $scope.notaCredito = function(){
        var monto = new Number($scope.montoTotal);
        if(monto < 0){
            monto = -1*monto;
            var notaCredito = {
                articuloId: 6,
                codigo: "N.CREDITO",
                descripcion: "Nota de credito por la diferencia",
                factor1: 1,
                factor2: 1,
                precioLista: monto,
                precio: monto,
                cantidadStock: 1,
                cantidad: 1
            };
            $scope.agregarCarrito(notaCredito);
        }
   };

   $scope.init = function(){
       $scope.obtenerListaVendedores();
   };

   $scope.devolucion = function(){
        $('#dialogDevolucion').modal('show');
   };

   $scope.devolucionCerrar = function(){
       $('#dialogDevolucion').modal('close');
   };

   $scope.init();

}