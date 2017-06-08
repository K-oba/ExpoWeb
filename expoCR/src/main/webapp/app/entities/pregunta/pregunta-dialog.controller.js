(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('PreguntaDialogController', PreguntaDialogController);

    PreguntaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pregunta', 'Usuario', 'Charla', 'Exposicion'];

    function PreguntaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pregunta, Usuario, Charla, Exposicion) {
        var vm = this;

        vm.pregunta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.usuarios = Usuario.query();
        vm.charlas = Charla.query();
        vm.exposicions = Exposicion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pregunta.id !== null) {
                Pregunta.update(vm.pregunta, onSaveSuccess, onSaveError);
            } else {
                Pregunta.save(vm.pregunta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:preguntaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
