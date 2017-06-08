(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('RolDialogController', RolDialogController);

    RolDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rol', 'Usuario'];

    function RolDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rol, Usuario) {
        var vm = this;

        vm.rol = entity;
        vm.clear = clear;
        vm.save = save;
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rol.id !== null) {
                Rol.update(vm.rol, onSaveSuccess, onSaveError);
            } else {
                Rol.save(vm.rol, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:rolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
