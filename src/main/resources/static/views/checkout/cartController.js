angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

    if ($localStorage.currentUser) {
        $scope.nextStepPath = 'delivery';
    } else {
        $scope.nextStepPath = 'ch-login';
    }

    $scope.loadCart = function (page) {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET',
            params: {
                cartId: $localStorage.cartId
            }
        }).then(function (response) {
            $scope.cartDto = response.data;
            $scope.cartSum = $scope.cartDto.sum;
        });
    };

    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'GET',
            params: {
                cartId: $localStorage.cartId
            }
        }).then(function (response) {
            console.log("clear cart!");
        });
    };

    $scope.remProduct = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/rem-product',
            method: 'DELETE',
            params: {
                cartId: $localStorage.cartId,
                p: productId
            }
        }).then(function (response) {
            $scope.cartDto = response.data;
            $scope.cartSum = $scope.cartDto.sum;
        });
    };

    $scope.changeProductQuantity = function (productId, quantity) {
        $http({
            url: contextPath + '/api/v1/cart/change',
            method: 'PUT',
            params: {
                cartId: $localStorage.cartId,
                p: productId,
                q: quantity
            }
        }).then(function (response) {
            $scope.cartSum = response.data;
        });
    }

    $scope.loadCart();
});