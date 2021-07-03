angular.module('app').controller('productController', function ($scope, $http, $localStorage, $routeParams, $window) {

    const contextPath = 'http://localhost:8189/market';
    $scope.productQuantity = 1;

    $scope.getProduct = function () {
    $window.scrollTo(0, 0);
        $http({
            url: contextPath + '/api/v1/products/' + $routeParams.id,
            method: 'GET'
        }).then(function (response) {
            $scope.product = response.data;
            console.log($scope.product);
        });
    };

    $scope.addToCart = function (quantity) {
        $http({
            url: contextPath + '/api/v1/cart/add',
            method: 'GET',
            params: {
                cartId: 'cart',
                p: $routeParams.id,
                q: $scope.productQuantity
            }
        }).then(function (response) {
            console.log("Product with id="+ productId + " x" + quantity + " was added to cart");
        });
    }

    $scope.getProduct();
});