var musicAppModule = angular.module('musicApp', []);
musicAppModule.controller('TuneViewController', function($scope) {
    $scope.viewTitle = "H";
});
musicAppModule.controller('TuneListController',
        function ($scope, $http) {
            $scope.URL = './resources/tunes/hex?limit=5';
            $scope.loadList = function (url) {
                $scope.URL = url || $scope.URL;
                $http.get($scope.URL).success(function (jsonData) {
                    $scope.listTitle = 'Låtlista';
                    $scope.links = jsonData.links;
                    $scope.prev = {
                        icon: './layout/images/resultset_previous_disabled.png'
                    };
                    $scope.next = {
                        icon: './layout/images/resultset_next_disabled.png'
                    };
                    for (var i = 0, len = $scope.links.length; i < len; i++) {
                        if ($scope.links[i].rel === 'previous') {
                            $scope.prev.URL = decodeURIComponent($scope.links[i].uri);
                            $scope.prev.icon = './layout/images/resultset_previous.png';
                        }
                        if ($scope.links[i].rel === 'next') {
                            $scope.next.URL = decodeURIComponent($scope.links[i].uri);
                            $scope.next.icon = './layout/images/resultset_next.png';
                        }
                    }
                    $scope.prev.clicked = function () {
                        if ($scope.prev.URL !== null && $scope.prev.URL !== undefined) {
                            $scope.loadList($scope.prev.URL);
                        }
                    };
                    $scope.next.clicked = function () {
                        if ($scope.next.URL !== null && $scope.next.URL !== undefined) {
                            $scope.loadList($scope.next.URL);
                        }
                    };
                    $scope.tunes = jsonData.tunes;
                    for (var i = 0, len = $scope.tunes.length; i < len; i++) {
                        $scope.tunes[i].options = [];
                        for (var j = 0, len = $scope.tunes[i].links.length; j < len; j++) {
                            if ($scope.tunes[i].links[j].rel === 'view-first-line') {
                                $scope.tunes[i].firstLineURL = decodeURIComponent($scope.tunes[i].links[j].uri);
                            }
                            if ($scope.tunes[i].links[j].rel === 'view-gif') {
                                $scope.tunes[i].gifURL = decodeURIComponent($scope.tunes[i].links[j].uri);
                                $scope.tunes[i].options.push({name: 'Visa GIF', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                            if ($scope.tunes[i].links[j].rel === 'download-abc') {
                                $scope.tunes[i].options.push({name: 'Ladda hem ABC', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                            if ($scope.tunes[i].links[j].rel === 'download-pdf') {
                                $scope.tunes[i].options.push({name: 'Ladda hem PDF', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                            if ($scope.tunes[i].links[j].rel === 'view-abc') {
                                $scope.tunes[i].options.push({name: 'Visa ABC', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                            if ($scope.tunes[i].links[j].rel === 'audio-midi') {
                                $scope.tunes[i].options.push({name: 'Ladda hem MIDI', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                            if ($scope.tunes[i].links[j].rel === 'edit') {
                                $scope.tunes[i].options.push({name: 'Redigera', url: decodeURIComponent($scope.tunes[i].links[j].uri)});
                            }
                        }
                    }
                });
            };
            $scope.loadList();
        });
