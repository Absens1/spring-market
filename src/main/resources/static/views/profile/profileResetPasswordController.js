angular.module('app').controller('profileResetPasswordController', function ($scope, $http, $localStorage, $routeParams, $window) {

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

    $scope.getOrdersCount = function () {
        $http({
            url: contextPath + '/api/v1/orders/count',
            method: 'GET'
        }).then(function (response) {
            $scope.ordersCount = response.data;
        });
    };

    $scope.changeUserPassword = function () {
        $http({
            url: contextPath + '/api/v1/user/change-password',
            method: 'PUT',
            params: {
                oldPassword: $scope.oldPassword,
                newPassword: $scope.newPassword,
                repeatNewPassword: $scope.repeatNewPassword,
            }
        }).then(function (response) {
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
    $scope.getOrdersCount();
});