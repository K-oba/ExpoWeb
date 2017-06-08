(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDialogController', BrouchureDialogController);

    BrouchureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Brouchure', 'Stand', 'SubCategoria'];

    function BrouchureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Brouchure, Stand, SubCategoria) {
        var vm = this;

        vm.brouchure = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stands = Stand.query();
        vm.subcategorias = SubCategoria.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.brouchure.id !== null) {
                Brouchure.update(vm.brouchure, onSaveSuccess, onSaveError);
            } else {
                Brouchure.save(vm.brouchure, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:brouchureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
