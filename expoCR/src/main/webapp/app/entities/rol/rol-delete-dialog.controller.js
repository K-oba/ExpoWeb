(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('RolDeleteController',RolDeleteController);

    RolDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rol'];

    function RolDeleteController($uibModalInstance, entity, Rol) {
        var vm = this;

        vm.rol = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rol.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
