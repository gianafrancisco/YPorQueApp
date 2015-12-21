var articuloApp = angular.module('articuloApp', ['ui.bootstrap']);

articuloApp.controller('ArticuloController', function ($scope,$http) {


    $scope.listado = {};
    $scope.listadoCodigo = {};
    $scope.articulo = {};
    $scope.codigo = {};

    $scope.obtenerListaArticulo = function(){
        $http.get("/articulos",$scope.articulo)
        .success(function(data, status, headers, config) {
            $scope.listado=data;
        });
    };

    $scope.obtenerListaCodigo = function(articulo){
        if(articulo.articuloId != 'undefined'){
            $http.get("/articulo/"+articulo.articuloId+"/items")
            .success(function(data, status, headers, config) {
                $scope.listadoCodigo=data;
            });
         }
    };

    $scope.agregarArticulo = function(){
        $scope.articulo.articuloId = null;
        $scope.save();
    };

    $scope.modificarArticulo = function(){
        $scope.save();
    };

    $scope.agregarCodigo = function(){
        $scope.codigo.itemId = null;
        $scope.saveItem();
    };

    $scope.modificarCodigo = function(){
        $scope.saveItem();
    };

    $scope.modificar = function(articulo){
        $scope.articulo = articulo;
        $scope.obtenerListaCodigo($scope.articulo);
    };

    $scope.save = function(){
        $http.put("/articulo/agregar",$scope.articulo)
        .success(function(data, status, headers, config) {
            $scope.articulo=data;
            $scope.obtenerListaArticulo();
        });
    };

    $scope.eliminar = function(articulo){
        $http.get("/articulo/delete/"+articulo.articuloId)
        .success(function(data, status, headers, config) {
            $scope.obtenerListaArticulo();
            $scope.obtenerListaCodigo(articulo);
        });
    };

    $scope.eliminarCodigo = function(articulo,item){
        $http.get("/articulo/"+articulo.articuloId+"/item/delete/"+item.itemId)
        .success(function(data, status, headers, config) {
            $scope.obtenerListaCodigo(articulo);
        });
    };

    $scope.saveItem = function(){
        $http.put("/articulo/"+$scope.articulo.articuloId+"/agregar",$scope.codigo)
        .success(function(data, status, headers, config) {
            $scope.codigo=data;
            $scope.obtenerListaCodigo($scope.articulo);
        });
    };

    $scope.init = function(){
        $scope.obtenerListaArticulo();

    };

    $scope.init();

});