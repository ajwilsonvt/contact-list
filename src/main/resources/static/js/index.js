// Angular 1
//to handle a request at /, ngRoute would need to be injected in
//angular.module('REST app', ['ngRoute'])
angular.module('REST app', [])
    //this application, named REST app, has an empty config
    //.config();
    .controller('home', function($http) {
        var self = this;
        $http.get('/contacts/3').then(function(response) {
            console.log(response);
            self.test = response.data;
        })
    });