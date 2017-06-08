(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ClickDetailController', ClickDetailController);

    ClickDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Click', 'Stand', 'SubCategoria', 'Exposicion'];

    function ClickDetailController($scope, $rootScope, $stateParams, previousState, entity, Click, Stand, SubCategoria, Exposicion) {
        var vm = this;

        vm.click = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:clickUpdate', function(event, result) {
            vm.click = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
