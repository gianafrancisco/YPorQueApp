var app = angular.module('mainApp', ['ngRoute','ui.bootstrap']).
    config(function($routeProvider, $httpProvider) {

             $routeProvider.
               when('/articulo', {
                 templateUrl: 'articulo.html',
                 controller: 'ArticuloController'
               }).
              when('/vender', {
                templateUrl: 'vender.html',
                controller: 'VenderController'
              }).
              when('/venta', {
                templateUrl: 'venta.html',
                controller: 'VentaController'
              })
            .otherwise({
                 redirectTo: '/vender'
               });
           $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
           }
)
.controller('VentaController',ventaController)
.controller('VenderController',venderController)
.controller('ArticuloController',articuloController);
