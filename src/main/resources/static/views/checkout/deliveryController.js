angular.module('app').controller('deliveryController', function ($scope, $q, $http, $localStorage, deliveryService) {
    const contextPath = 'http://localhost:8189/market';
    $scope.checkShippingInfo = false;
    $scope.checkDeliveryType = false;


    $scope.loadDeliveryView = function () {
        $scope.checkShippingInfo = null;
        findUserShippingInfo()
            .then(function(userShippingInfo) {
                if (userShippingInfo.user != null
                    && userShippingInfo.country != null
                    && userShippingInfo.city != null
                    && userShippingInfo.address != null
                    && userShippingInfo.zipCode != null) {
                    console.log(userShippingInfo);
                    $scope.checkShippingInfo = true;
                } else {
                    console.log('userShippingInfo is not valid or empty');
                    $scope.checkShippingInfo = false;
                }
            });
    };

    function findUserShippingInfo() {
        var d = $q.defer();
        $http({
            url: contextPath + '/api/v1/user-shipping',
            method: 'GET'
        }).then(function (response) {
            d.resolve(response.data);
        });
        return d.promise;
    };

    $scope.nextStep = function() {
        if ($scope.checkShippingInfo && $scope.checkDeliveryType) {
           $scope.nextStepPath = '#!/payment';
        } else {
           $scope.nextStepPath = null;
        }
    };

    $scope.changeDeliveryType = function(deliveryId) {
        deliveryService.setDeliveryTypeId(deliveryId);
        $scope.checkDeliveryType = true;
    };

    $scope.loadDeliveryView();
});