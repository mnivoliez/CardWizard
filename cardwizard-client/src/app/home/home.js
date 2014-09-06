var homeModule = angular.module('HomeModule', []);

homeModule.controller('HomeCtrl', ['$scope', 'allCard', function ($scope, allCard) {
  $scope.cards = allCard.data;

}]);