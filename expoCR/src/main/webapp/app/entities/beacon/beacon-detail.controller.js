(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BeaconDetailController', BeaconDetailController);

    BeaconDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Beacon', 'Exposicion', 'Stand', 'SubCategoria'];

    function BeaconDetailController($scope, $rootScope, $stateParams, previousState, entity, Beacon, Exposicion, Stand, SubCategoria) {
        var vm = this;

        vm.beacon = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:beaconUpdate', function(event, result) {
            vm.beacon = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
