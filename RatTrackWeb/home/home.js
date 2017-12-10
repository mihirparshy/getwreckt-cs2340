'use strict';

angular.module('ratTrack.home', ['ngRoute', 'firebase'])

// Declared route
.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'home/home.html',
        controller: 'HomeCtrl'
    });
}])

// Home controller
.controller('HomeCtrl', ['$scope', '$location','$firebaseAuth', function($scope, $firebaseAuth) {
    var firebaseObj = new Firebase("https://cs2340getwreckt.firebaseio.com");
    var loginObj = $firebaseAuth(firebaseObj);

    //  On successful authentication, we get a success callback and
    //  on an unsuccessful authentication, we get an error callback.

    $scope.user = {};
    $scope.SignIn = function(event) {
        event.preventDefault();  // To prevent form refresh
        var username = $scope.user.email;
        var password = $scope.user.password;

        loginObj.$authWithPassword('password', {
                email: username,
                password: password
            })
            .then(function(user) {
                // Success callback
                console.log('Authentication successful');
                $location.path('/welcome');
            }, function(error) {
                // Failure callback
                console.log('Authentication failure');
            });
    }

}]);
