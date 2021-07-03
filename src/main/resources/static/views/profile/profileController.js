angular.module('app').controller('profileController', function ($scope, $http, $localStorage, $routeParams, $window) {

    const contextPath = 'http://localhost:8189/market';

    if (!$localStorage.currentUser) {
        $window.location.href = '#!/login';
    }

    $scope.getUserInfo = function () {
        $http({
            url: contextPath + '/api/v1/user',
            method: 'GET'
        }).then(function (response) {
            $scope.user = response.data;
        });
    };

    $scope.getUserShippingInfo = function () {
        $http({
            url: contextPath + '/api/v1/user-shipping',
            method: 'GET'
        }).then(function (response) {
            $scope.userShippingInfo = response.data;
            console.log(response.data);
        });
    };

    $scope.changeUserInfo = function () {
        $http({
            url: contextPath + '/api/v1/user',
            method: 'PUT',
            params: {
                firstName: $scope.user.firstName,
                lastName: $scope.user.lastName,
            }
        }).then(function (response) {
           console.log(response.data);
        });
    };

    $scope.changeUserShippingInfo = function () {
        $http.put(contextPath + '/api/v1/user-shipping', $scope.userShippingInfo)
            .then(function (response) {
            console.log(response.data);
        });
    };

    $scope.getOrdersCount = function () {
        $http({
            url: contextPath + '/api/v1/orders/count',
            method: 'GET'
        }).then(function (response) {
            $scope.ordersCount = response.data;
        });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        window.location.reload(); // todo delete if binding localStorage.currentUser and index view on change
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.getUserInfo();
    $scope.getUserShippingInfo();
    $scope.getOrdersCount();
});