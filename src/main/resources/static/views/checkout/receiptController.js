angular.module('app').controller('receiptController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/market';

    $scope.loadReceipt = function () {
        $scope.orderId = $routeParams.id;
        $http({
            url: contextPath + '/api/v1/orders/' + $routeParams.id,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
            $scope.user = $scope.order.user;
            $scope.shippingInfo = $scope.order.orderShippingInfo;
            console.log($scope.order);
        });
    };

    $scope.loadReceipt();
});