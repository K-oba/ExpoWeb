(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('HistorialUsuariosExpoDialogController', HistorialUsuariosExpoDialogController);

    HistorialUsuariosExpoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HistorialUsuariosExpo'];

    function HistorialUsuariosExpoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HistorialUsuariosExpo) {
        var vm = this;

        vm.historialUsuariosExpo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.historialUsuariosExpo.id !== null) {
                HistorialUsuariosExpo.update(vm.historialUsuariosExpo, onSaveSuccess, onSaveError);
            } else {
                HistorialUsuariosExpo.save(vm.historialUsuariosExpo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:historialUsuariosExpoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
