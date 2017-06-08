(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ClickDialogController', ClickDialogController);

    ClickDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Click', 'Stand', 'SubCategoria', 'Exposicion'];

    function ClickDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Click, Stand, SubCategoria, Exposicion) {
        var vm = this;

        vm.click = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stands = Stand.query();
        vm.subcategorias = SubCategoria.query();
        vm.exposicions = Exposicion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.click.id !== null) {
                Click.update(vm.click, onSaveSuccess, onSaveError);
            } else {
                Click.save(vm.click, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:clickUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
