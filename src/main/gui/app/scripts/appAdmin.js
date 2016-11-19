'use strict';

angular.module('mainAdminApp',
    [
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap'
    ]).
    config(function($routeProvider) {

             $routeProvider.
               when('/articulo', {
                 templateUrl: 'views/admin/articulo.html',
                 controller: 'ArticuloController',
                 controllerAs: 'articulo'
               }).
              when('/vendedor', {
                templateUrl: 'views/admin/vendedor.html',
                controller: 'VendedorController',
                controllerAs: 'vendedor'
              }).
              when('/venta', {
                templateUrl: 'views/admin/venta.html',
                controller: 'VentaController',
                controllerAs: 'venta'
              })
            .otherwise({
                 redirectTo: '/articulo'
               });
           //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
           }
)
.controller('VentaController',ventaController)
.controller('VendedorController',vendedorController)
.controller('ArticuloController',articuloController);
