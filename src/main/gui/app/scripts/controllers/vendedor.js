function vendedorController($scope,$http,$window,$location) {


     $scope.listado = {
         numberOfElements: 0,
         number: 0,
         totalElements: 0
     };
     $scope.maxSize = 100;
     $scope.vendedor = {};
     $scope.codigo = {};
     $scope.ipp = 20;
     $scope.pageNumber = 1;

     $scope.obtenerListaVendedor = function(){
         var url = "/vendedores?page="+($scope.pageNumber-1);
         if($scope.search != "" && $scope.search != undefined){
             var search = "&search="+$scope.search;
             url = "/vendedor/search?page="+($scope.pageNumber-1)+search;
         }
         $http.get(url)
         .success(function(data, status, headers, config) {
             $scope.listado=data;
             $scope.pageNumber = $scope.listado.number+1;
         });
     };

     $scope.buscar = function(){
         $scope.obtenerListaVendedor();
     };

     $scope.agregar = function(){
         $scope.vendedor.vendedorId = null;
         $scope.save(function(){
             $scope.vendedor = {};
         });
     };

     $scope.modificar = function(){
         $scope.save(function(){
             $scope.vendedor = {};
         });
     };

     $scope.editar = function(vendedor){
         $scope.vendedor = vendedor;
     };

     $scope.save = function(callback){
         $http.put("/vendedor/agregar",$scope.vendedor)
         .success(function(data, status, headers, config) {
             $scope.vendedor=data;
             $scope.obtenerListaVendedor();
             if(callback != undefined){
                 callback();
             }
         });
     };

     $scope.eliminar = function(vendedor){

         if(confirm("Esta seguro que quiere elimiar el vendedor?")){

             $http.get("/vendedor/delete/"+vendedor.vendedorId)
             .success(function(data, status, headers, config) {
                 $scope.obtenerListaVendedor();
                 $scope.obtenerListaCodigo(vendedor);
             });
         }
     };

     $scope.init = function(){
         $scope.obtenerListaVendedor();

     };

     $scope.isModificable = function(){
         return $scope.vendedor.vendedorId == undefined;
     };

     $scope.cerrarSession = function(){
         $http.post('/logout', {}).success(function() {
             $location.path("/index.html");
         }).error(function(data) {
         });
     };
     $scope.init();

 }