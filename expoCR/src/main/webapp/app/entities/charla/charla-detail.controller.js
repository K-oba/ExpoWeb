(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CharlaDetailController', CharlaDetailController);

    CharlaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Charla', 'Exposicion', 'Pregunta'];

    function CharlaDetailController($scope, $rootScope, $stateParams, previousState, entity, Charla, Exposicion, Pregunta) {
        var vm = this;

        vm.charla = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:charlaUpdate', function(event, result) {
            vm.charla = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
