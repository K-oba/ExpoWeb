(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('UsuarioDetailController', UsuarioDetailController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuario', 'Timeline', 'Pregunta', 'Stand', 'Rol'];

    function UsuarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Usuario, Timeline, Pregunta, Stand, Rol) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;
        vm.isVisble = false;

        if(vm.usuario.standId == null){
            vm.isVisble = false;
        }else{
            vm.isVisble = true;
        }

        var unsubscribe = $rootScope.$on('expoCrApp:usuarioUpdate', function(event, result) {
            vm.usuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
