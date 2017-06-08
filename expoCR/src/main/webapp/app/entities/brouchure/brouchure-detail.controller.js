(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDetailController', BrouchureDetailController);

    BrouchureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Brouchure', 'Stand', 'SubCategoria'];

    function BrouchureDetailController($scope, $rootScope, $stateParams, previousState, entity, Brouchure, Stand, SubCategoria) {
        var vm = this;

        vm.brouchure = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:brouchureUpdate', function(event, result) {
            vm.brouchure = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
