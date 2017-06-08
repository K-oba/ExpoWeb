(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('AmenidadesDialogController', AmenidadesDialogController);

    AmenidadesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Amenidades', 'Exposicion'];

    function AmenidadesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Amenidades, Exposicion) {
        var vm = this;

        vm.amenidades = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exposicions = Exposicion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.amenidades.id !== null) {
                Amenidades.update(vm.amenidades, onSaveSuccess, onSaveError);
            } else {
                Amenidades.save(vm.amenidades, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:amenidadesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
