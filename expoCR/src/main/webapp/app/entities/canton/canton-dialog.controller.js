(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CantonDialogController', CantonDialogController);

    CantonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Canton', 'Provincia', 'Distrito'];

    function CantonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Canton, Provincia, Distrito) {
        var vm = this;

        vm.canton = entity;
        vm.clear = clear;
        vm.save = save;
        vm.provincias = Provincia.query();
        vm.distritos = Distrito.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.canton.id !== null) {
                Canton.update(vm.canton, onSaveSuccess, onSaveError);
            } else {
                Canton.save(vm.canton, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:cantonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
