(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('DistritoDetailController', DistritoDetailController);

    DistritoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Distrito', 'Canton', 'Exposicion'];

    function DistritoDetailController($scope, $rootScope, $stateParams, previousState, entity, Distrito, Canton, Exposicion) {
        var vm = this;

        vm.distrito = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:distritoUpdate', function(event, result) {
            vm.distrito = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
