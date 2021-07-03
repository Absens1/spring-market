angular.module('app').controller('productsController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

    let filterByCategoriesId = [];
    $scope.filterByRangeValue = 10;
    $scope.min_price = 0;
    $scope.max_price = 500;

    $scope.isUserLoggedIn = function () {
        if ($localStorage.aprilMarketCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.loadPage = function (page) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                p: page,
                pageSize: $scope.filterByQuantityProducts,
                title: $scope.filterByTitle ? $scope.filterByTitle : null,
                min_price: $scope.filterByRangeValue > 0 ? $scope.filterByRangeValue : null,
                categoriesId : filterByCategoriesId.length > 0 ? filterByCategoriesId : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;

            let minPageIndex = page - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = page + 2;
            if (maxPageIndex > $scope.productsPage.totalPages) {
                maxPageIndex = $scope.productsPage.totalPages;
            }

            $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.loadCategories = function () {
        $http({
            url: contextPath + '/api/v1/categories',
            method: 'GET'
        }).then(function (response) {
            $scope.categories = response.data;
        });
    };

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add',
            method: 'GET',
            params: {
                cartId: $localStorage.cartId,
                p: productId
            }
        }).then(function (response) {
            console.log("Product with id="+ productId + " was added to cart");
        });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.changeCategoryFilter = function (categoryId) {
        let index = filterByCategoriesId.indexOf(categoryId);
        if (index > -1) {
            filterByCategoriesId.splice(index, 1);
        } else {
            filterByCategoriesId.push(categoryId);
        }
        $scope.loadPage(1);
    }

    $scope.loadPage(1);
    $scope.loadCategories();
});