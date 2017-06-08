(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CantonDeleteController',CantonDeleteController);

    CantonDeleteController.$inject = ['$uibModalInstance', 'entity', 'Canton'];

    function CantonDeleteController($uibModalInstance, entity, Canton) {
        var vm = this;

        vm.canton = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Canton.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
