(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CantonDetailController', CantonDetailController);

    CantonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Canton', 'Provincia', 'Distrito'];

    function CantonDetailController($scope, $rootScope, $stateParams, previousState, entity, Canton, Provincia, Distrito) {
        var vm = this;

        vm.canton = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:cantonUpdate', function(event, result) {
            vm.canton = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
