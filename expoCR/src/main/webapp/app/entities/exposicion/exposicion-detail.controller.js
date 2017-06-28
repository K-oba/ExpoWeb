(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionDetailController', ExposicionDetailController);

    ExposicionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exposicion', 'Distrito', 'Categoria', 'Charla', 'Amenidades', 'Beacon', 'Timeline', 'Click', 'Pregunta', 'Usuario'];

    function ExposicionDetailController($scope, $rootScope, $stateParams, previousState, entity, Exposicion, Distrito, Categoria, Charla, Amenidades, Beacon, Timeline, Click, Pregunta, Usuario) {
        var vm = this;

        vm.exposicion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:exposicionUpdate', function(event, result) {
            vm.exposicion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
