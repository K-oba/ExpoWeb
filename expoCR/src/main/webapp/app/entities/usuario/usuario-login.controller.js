/**
 * Created by Jimmi on 25/06/2017.
 */
(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('UsuarioLoginController', UsuarioLoginController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuario', 'Rol'];

    function UsuarioLoginController($scope, $rootScope, $stateParams, previousState, entity, Usuario, Rol) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;
        vm.isVisble = false;

        console.log('am alive');
        // if(vm.usuario.standId == null){
        //     vm.isVisble = false;
        // }else{
        //     vm.isVisble = true;
        // }

        // var unsubscribe = $rootScope.$on('expoCrApp:usuarioUpdate', function(event, result) {
        //     vm.usuario = result;
        // });
        // $scope.$on('$destroy', unsubscribe);
    }
})();
