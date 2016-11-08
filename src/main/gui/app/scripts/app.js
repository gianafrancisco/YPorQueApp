'use strict';

/**
 * @ngdoc overview
 * @name guiApp
 * @description
 * # guiApp
 *
 * Main module of the application.
 */
 /*
angular
  .module('guiApp', [
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
  */
angular.module('mainApp', ['ngRoute','ui.bootstrap']).
    config(function($routeProvider, $httpProvider) {

             $routeProvider.
               when('/articulo', {
                 templateUrl: 'views/articulo.html',
                 controller: 'ArticuloController'
               }).
              when('/vender', {
                templateUrl: 'views/vender.html',
                controller: 'VenderController'
              }).
              when('/venta', {
                templateUrl: 'views/venta.html',
                controller: 'VentaController'
              }).
              when('/retiro', {
                  templateUrl: 'views/retiro.html',
                  controller: 'RetiroController'
              }).
              when('/caja', {
                  templateUrl: 'views/caja.html',
                  controller: 'CajaController'
              })
            .otherwise({
                 redirectTo: '/caja'
               });
           $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
           }
)
.controller('MainController',mainController)
.controller('VentaController',ventaController)
.controller('VenderController',venderController)
.controller('RetiroController',retiroController)
.controller('CajaController',cajaController)
.controller('ArticuloController',articuloController);
