angular.module('app').controller('chLoginController', function ($scope, $http, $localStorage, $location, $window) {
    const contextPath = 'http://localhost:8189/market';

    function checkUserLoggedIn() {
        if ($localStorage.currentUser) {
            $scope.userLoggedIn = true;
        } else {
            $scope.userLoggedIn = false;
        }
    };

    $scope.mergeCarts = function () {
        $http({
            url: contextPath + '/api/v1/cart/merge',
            method: 'GET',
            params: {
                'cartId': $localStorage.cartId
            }
        }).then(function (response) {
        });
    }

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.mergeCarts();

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $window.location.href = '#!/delivery';
                    window.location.reload(); // todo delete if binding localStorage.currentUser and index view on change
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToReg = function () {
        $http.post(contextPath + '/reg', $scope.regUser)
            .then(function successCallback(response) {
                if (response.data.token) {
                    console.log($scope.regUser);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.regUser.username, token: response.data.token};

                    $scope.mergeCarts();

                    $scope.regUser.username = null;
                    $scope.regUser.password = null;

                    $window.location.href = '#!/delivery';
                    $window.location.reload(); // todo delete if binding localStorage.currentUser and index view on change
                }
            }, function errorCallback(response) {
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

    checkUserLoggedIn();
});