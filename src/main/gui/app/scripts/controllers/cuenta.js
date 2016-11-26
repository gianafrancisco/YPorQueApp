function cuentaController($scope,$http,$window,$location) {

     $scope.listado = {
         numberOfElements: 0,
         number: 0,
         totalElements: 0
     };
     $scope.maxSize = 100;
     $scope.cuenta = {};
     $scope.codigo = {};
     $scope.ipp = 20;
     $scope.pageNumber = 1;
     $scope.descripcion = "";
     $scope.entrega = 0;

     $scope.obtenerLista = function(){
         var url = "/cuentas?page="+($scope.pageNumber-1);
         if($scope.search != "" && $scope.search != undefined){
             var search = "&search="+$scope.search;
             url = "/cuentas?page="+($scope.pageNumber-1)+search;
         }
         $http.get(url)
         .success(function(data, status, headers, config) {
             $scope.listado=data;
             $scope.pageNumber = $scope.listado.number+1;
         });
     };

     $scope.buscar = function(){
         $scope.obtenerLista();
     };

     $scope.agregar = function(){
         $scope.cuenta.id = null;
         $scope.save(function(){
             $scope.cuenta = {};
         });
     };

     $scope.modificar = function(){
         $scope.save(function(){
             $scope.cuenta = {};
         });
     };

     $scope.editar = function(cuenta){
         $scope.cuenta = cuenta;
     };

     $scope.save = function(callback){
        if($scope.cuenta.id == null){
             $http.post("/cuentas",$scope.cuenta)
             .success(function(data, status, headers, config) {
                 $scope.cuenta = data;
                 $scope.obtenerLista();
                 if(callback != undefined){
                     callback();
                 }
             });
        }else{
            $http.put("/cuentas",$scope.cuenta)
             .success(function(data, status, headers, config) {
                 $scope.cuenta = data;
                 $scope.obtenerLista();
                 if(callback != undefined){
                     callback();
                 }
             });
        }
     };

     $scope.eliminar = function(cuenta){

         if(confirm("Esta seguro que quiere elimiar la cuenta?")){

             $http.delete("/cuentas/"+cuenta.id)
             .success(function(data, status, headers, config) {
                 $scope.obtenerLista();
             });
         }
     };

     $scope.init = function(){
         $scope.obtenerLista();

     };

     $scope.isModificable = function(){
         return $scope.cuenta.id == undefined;
     };

     $scope.agregarEntrega = function(cuenta, desc, entrega){
        var entrega = {
                descripcion: desc,
                monto: entrega
             };
        if(confirm("Esta seguro de registrar un ingreso a la cuenta corriente?")){
             $http.post(
             "/cuentas/"+cuenta.id+"/movimientos",
             entrega
             )
             .success(function(data, status, headers, config) {
                 console.log(data);
                 $scope.descripcion = "";
                 $scope.entrega = 0;
                 $scope.obtenerLista();
             });
        }
     };

     $scope.init();

 }