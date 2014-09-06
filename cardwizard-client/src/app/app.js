var cardwizardApp = angular.module('cardwizardApp', ['ui.router', 'pascalprecht.translate', 'ui.bootstrap', 'HomeModule']);

cardwizardApp.config(function ($locationProvider, $stateProvider, $urlRouterProvider) {

  //change URL without #
  $locationProvider.html5Mode(true);

  // Default location to home
  $urlRouterProvider.otherwise("/");

  $stateProvider
      .state("home",
      {
        url: "/",
        controller: 'HomeCtrl',
        templateUrl: 'app/home/home.tpl.html',
        resolve: {
          allCard: ['$http', function ($http) {
            return $http.get('AllCards.json');
          }
          ]
        }});
});

cardwizardApp.controller("AppCtrl", [ function ($scope) {

}]);



