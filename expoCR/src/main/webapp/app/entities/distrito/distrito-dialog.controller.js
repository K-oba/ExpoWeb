(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('DistritoDialogController', DistritoDialogController);

    DistritoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Distrito', 'Canton', 'Exposicion'];

    function DistritoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Distrito, Canton, Exposicion) {
        var vm = this;

        vm.distrito = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cantons = Canton.query();
        vm.exposicions = Exposicion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.distrito.id !== null) {
                Distrito.update(vm.distrito, onSaveSuccess, onSaveError);
            } else {
                Distrito.save(vm.distrito, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:distritoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
