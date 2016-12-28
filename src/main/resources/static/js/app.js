//Angular 1
//ngRoute enables routes to handle HTTP requests
angular.module('Contact List', ['ngRoute'])
    .config(function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl : 'templates/home.html'
        }).when('/instructions', {
            templateUrl : 'templates/instructions.html'
        }).otherwise({
            redirectTo: '/'
        })
    });