(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ProvinciaDialogController', ProvinciaDialogController);

    ProvinciaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Provincia', 'Canton'];

    function ProvinciaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Provincia, Canton) {
        var vm = this;

        vm.provincia = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cantons = Canton.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.provincia.id !== null) {
                Provincia.update(vm.provincia, onSaveSuccess, onSaveError);
            } else {
                Provincia.save(vm.provincia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:provinciaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
