(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/home/home.html',
            })
            .when('/product/:id', {
                templateUrl: 'views/product/product.html',
                controller:'productController',
            })
            .when('/products', {
                templateUrl: 'views/product/products.html',
                controller:'productsController'
            })
            .when('/login', {
                templateUrl: 'views/login/login.html',
                controller:'loginController'
            })
            .when('/profile', {
                templateUrl: 'views/profile/profile.html',
                controller:'profileController'
            })
            .when('/orders', {
                templateUrl: 'views/profile/profile-orders.html',
                controller:'profileOrdersController'
            })
            .when('/reset-password', {
                templateUrl: 'views/profile/profile-reset-password.html',
                 controller:'profileResetPasswordController'
            })
            .when('/cart', {
                templateUrl: 'views/checkout/checkout-cart.html',
                controller:'cartController'
            })
            .when('/ch-login', {
                templateUrl: 'views/checkout/checkout-login.html',
                controller:'chLoginController'
            })
            .when('/delivery', {
                templateUrl: 'views/checkout/checkout-delivery.html',
                controller:'deliveryController'
            })
            .when('/payment', {
                templateUrl: 'views/checkout/checkout-payment.html',
                controller:'paymentController'
            })
            .when('/receipt/:id', {
                templateUrl: 'views/checkout/checkout-receipt.html',
                controller: 'receiptController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        if ($localStorage.cartId) {
        } else {
            const contextPath = 'http://localhost:8189/market';
            $http({
                url: contextPath + '/api/v1/cart/generate',
                method: 'GET'
            }).then(function (response) {
                $localStorage.cartId = response.data.str;
            });
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/market';

    if ($localStorage.currentUser) {
        $scope.navButtonInfo = $localStorage.currentUser.username;
        $scope.navButtonLink = 'profile';
    } else {
        $scope.navButtonInfo = 'login & register';
        $scope.navButtonLink = 'login';
    }
});