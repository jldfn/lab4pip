'use strict';

var app = angular.module('app4pip', ['ngMaterial', 'ngMessages', 'ngRoute', 'ngCookies']); // 'material.svgAssetsCache',

app.config(function ($routeProvider) {
    $routeProvider
        .when('/plot/:username', {
            templateUrl: 'plot.html'
        })
        .when('/authentication', {
            templateUrl: 'login.html'
        })
        .otherwise({
                redirectTo: '/authentication'
            });
});
