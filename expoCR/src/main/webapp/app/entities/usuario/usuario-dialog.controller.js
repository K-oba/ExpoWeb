(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('UsuarioDialogController', UsuarioDialogController);

    UsuarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Usuario', 'Timeline', 'Pregunta', 'Stand', 'Rol'];

    function UsuarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Usuario, Timeline, Pregunta, Stand, Rol) {
         var vm = this;
         vm.doNotMatch = null;
         vm.error = null;
         vm.errorUserExists = null;
         vm.errorEmailExists = null;

        vm.confirmPassword = "";
        vm.usuario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.timelines = Timeline.query();
        vm.preguntas = Pregunta.query();
        vm.stands = Stand.query({filter: 'usuario-is-null'});

        $q.all([vm.usuario.$promise, vm.stands.$promise]).then(function() {
            if (!vm.usuario.standId) {
                return $q.reject();
            }
            return Stand.get({id : vm.usuario.standId}).$promise;
        }).then(function(stand) {
            vm.stands.push(stand);
        });
        vm.rols = Rol.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if(vm.confirmPassword === vm.usuario.clave){
                vm.doNotMatch = null;
                if (vm.usuario.id !== null) {
                    Usuario.update(vm.usuario, onSaveSuccess, onSaveError);
                } else {
                    vm.usuario.rolId = 2;
                    Usuario.save(vm.usuario, onSaveSuccess, onSaveError);
                }
            }else{
                vm.doNotMatch = 'ERROR';
                vm.isSaving = false;
            }
        }

        function onSaveSuccess (result) {
//            $scope.$emit('expoCrApp:usuarioUpdate', result);
            $uibModalInstance.close(result);
               vm.isSaving = false;
               vm.success = 'OK';
        }

        function onSaveError (result) {
            vm.isSaving = false;
            if (result.status === 400 && result.data === 'login already in use') {
                vm.errorUserExists = 'ERROR';
            } else if (result.status === 400 && result.data === 'email address already in use') {
                vm.errorEmailExists = 'ERROR';
            } else {
                vm.error = 'ERROR';
            }
        }


    }
})();
