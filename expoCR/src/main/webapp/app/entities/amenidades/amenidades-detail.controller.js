(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('AmenidadesDetailController', AmenidadesDetailController);

    AmenidadesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Amenidades', 'Exposicion'];

    function AmenidadesDetailController($scope, $rootScope, $stateParams, previousState, entity, Amenidades, Exposicion) {
        var vm = this;

        vm.amenidades = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:amenidadesUpdate', function(event, result) {
            vm.amenidades = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
