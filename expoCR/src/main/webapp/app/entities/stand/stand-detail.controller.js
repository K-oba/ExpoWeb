(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('StandDetailController', StandDetailController);

    StandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stand', 'Usuario', 'Brouchure', 'Click', 'Beacon'];

    function StandDetailController($scope, $rootScope, $stateParams, previousState, entity, Stand, Usuario, Brouchure, Click, Beacon) {
        var vm = this;

        vm.stand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:standUpdate', function(event, result) {
            vm.stand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
