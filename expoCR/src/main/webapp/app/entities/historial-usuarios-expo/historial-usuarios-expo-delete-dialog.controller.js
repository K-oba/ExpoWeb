(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('HistorialUsuariosExpoDeleteController',HistorialUsuariosExpoDeleteController);

    HistorialUsuariosExpoDeleteController.$inject = ['$uibModalInstance', 'entity', 'HistorialUsuariosExpo'];

    function HistorialUsuariosExpoDeleteController($uibModalInstance, entity, HistorialUsuariosExpo) {
        var vm = this;

        vm.historialUsuariosExpo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HistorialUsuariosExpo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
