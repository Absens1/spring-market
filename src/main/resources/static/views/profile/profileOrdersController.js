angular.module('app').controller('profileOrdersController', function ($scope, $http, $localStorage, $routeParams, $window) {

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

    $scope.getUserOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
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
    $scope.getUserOrders();
});