var app = angular.module('musicApp', []);

app.controller('MainController', function ($scope, $rootScope) {
    $scope.search = {
        query: {
            links: []
        },
        terms: null
    };
    $scope.user = {
        userName: null,
        password: null,
        name: null,
        email: null,
        showHits: 10,
        getUserName: function () {
            return this.userName;
        },
        setUserName: function (userName) {
            this.userName = userName;
        },
        setPassword: function (password) {
            this.password = password;
        },
        getName: function () {
            return this.name !== null ? this.name : this.userName;
        },
        setName: function (name) {
            this.name = name;
        },
        getEmail: function () {
            return this.email || '';
        },
        setEmail: function (email) {
            this.email = email;
        },
        setShowHits: function (numberOfHits) {
            this.showHits = numberOfHits || 10;
        },
        isLoggedIn: function () {
            return this.userName !== null;
        },
        clear: function () {
            this.userName = null;
            this.password = null;
            this.name = null;
        }
    };
    $scope.tune = {
        list: []
    }
});
app.controller('LoginController', function ($scope) {
    $scope.userNameValue = null;
    $scope.userPasswordValue = null;
    $scope.login = function () {
        $scope.user.setUserName($scope.userNameValue);
        $scope.user.setPassword($scope.userPasswordValue);
    };
    $scope.logout = function () {
        $scope.user.clear();
        $scope.userNameValue = null;
        $scope.nameValue = null;
        $scope.userEmailValue = null;
        $scope.userPasswordValue = null;
    };
});
app.controller('TuneListController', function ($scope, $http) {
});
app.controller('SettingsController', function ($scope, $rootScope, $http) {
    $scope.updateSettings = function () {
        $scope.user.setName($scope.nameValue || null);
        $scope.user.setEmail($scope.userEmailValue || null);
        $scope.user.setShowHits($scope.userShowHits || null);
    };
});



