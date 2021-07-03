angular.module('app').controller('paymentController', function ($scope, $http, $localStorage, $window, deliveryService) {
    const contextPath = 'http://localhost:8189/market';
    var check = false;
    $scope.loadDelivery = function () {
        $http({
            url: contextPath + '/api/v1/delivery',
            method: 'GET'
        }).then(function (response) {
            $scope.deliveryList = response.data;
        });
    };

    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                deliveryTypeId: deliveryService.getDeliveryTypeId(),
                cardNumber: $scope.cart.number
            }
        }).then(function (response) {
            $window.location.href = '#!/receipt/' + response.data.id;
        });
    };

    $scope.changeDeliveryType = function(deliveryId) {
        deliveryService.setDeliveryTypeId(deliveryId);
        check = true;
    };

    $scope.loadDelivery();
});