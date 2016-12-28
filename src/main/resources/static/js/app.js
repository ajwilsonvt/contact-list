//Angular 1
//ngRoute enables routes to handle HTTP requests
angular.module('Contact List', ['ngRoute'])
    .config(function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl : 'views/home.html'
        }).when('/instructions', {
            templateUrl : 'views/instructions.html'
        }).otherwise({
            redirectTo: '/'
        })
    });