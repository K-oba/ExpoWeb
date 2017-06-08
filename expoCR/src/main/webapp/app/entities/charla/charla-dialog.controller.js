(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CharlaDialogController', CharlaDialogController);

    CharlaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Charla', 'Exposicion', 'Pregunta'];

    function CharlaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Charla, Exposicion, Pregunta) {
        var vm = this;

        vm.charla = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exposicions = Exposicion.query();
        vm.preguntas = Pregunta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.charla.id !== null) {
                Charla.update(vm.charla, onSaveSuccess, onSaveError);
            } else {
                Charla.save(vm.charla, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:charlaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
