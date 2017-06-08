(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('PreguntaDetailController', PreguntaDetailController);

    PreguntaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pregunta', 'Usuario', 'Charla', 'Exposicion'];

    function PreguntaDetailController($scope, $rootScope, $stateParams, previousState, entity, Pregunta, Usuario, Charla, Exposicion) {
        var vm = this;

        vm.pregunta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:preguntaUpdate', function(event, result) {
            vm.pregunta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
