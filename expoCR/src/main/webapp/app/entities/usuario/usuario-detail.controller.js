(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('UsuarioDetailController', UsuarioDetailController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuario', 'Timeline', 'Pregunta', 'Stand', 'Rol', 'Exposicion'];

    function UsuarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Usuario, Timeline, Pregunta, Stand, Rol, Exposicion) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;
        vm.isVisble = false;
        vm.exposicions = Exposicion.queryByUser({userId:1});
        console.log(vm.exposicions);
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
