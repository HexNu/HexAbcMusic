var app = angular.module('musicApp', []);

app.directive('focusMe', function ($timeout) {    
    return {    
        link: function (scope, element, attrs, model) {                
            $timeout(function () {
                element[0].focus();
            });
        }
    };
});

app.controller('UserController', function ($rootScope, $scope, $timeout) {
    $rootScope.user = {};
    $scope.loginString = null;
    $rootScope.login = function () {
        $rootScope.user.name = $scope.loginString;
    };
    $rootScope.setUserNameFocus = function ($timeout) {
        $('user-name-input').focus();
    };
    $rootScope.logout = function () {
        $rootScope.user.name = null;
        $scope.loginString = null;
    };
});
app.controller('NavController', function ($scope, $http) {

});



