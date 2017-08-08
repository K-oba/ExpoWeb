(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('HistorialUsuariosExpoDetailController', HistorialUsuariosExpoDetailController);

    HistorialUsuariosExpoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HistorialUsuariosExpo'];

    function HistorialUsuariosExpoDetailController($scope, $rootScope, $stateParams, previousState, entity, HistorialUsuariosExpo) {
        var vm = this;

        vm.historialUsuariosExpo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:historialUsuariosExpoUpdate', function(event, result) {
            vm.historialUsuariosExpo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
