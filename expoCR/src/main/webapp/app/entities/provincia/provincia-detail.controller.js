(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ProvinciaDetailController', ProvinciaDetailController);

    ProvinciaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Provincia', 'Canton'];

    function ProvinciaDetailController($scope, $rootScope, $stateParams, previousState, entity, Provincia, Canton) {
        var vm = this;

        vm.provincia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:provinciaUpdate', function(event, result) {
            vm.provincia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
