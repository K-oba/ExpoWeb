(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BeaconDialogController', BeaconDialogController);

    BeaconDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Beacon', 'Exposicion', 'Stand', 'SubCategoria'];

    function BeaconDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Beacon, Exposicion, Stand, SubCategoria) {
        var vm = this;

        vm.beacon = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exposicions = Exposicion.query();
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
            if (vm.beacon.id !== null) {
                Beacon.update(vm.beacon, onSaveSuccess, onSaveError);
            } else {
                Beacon.save(vm.beacon, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:beaconUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
