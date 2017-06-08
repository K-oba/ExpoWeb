(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('AmenidadesDeleteController',AmenidadesDeleteController);

    AmenidadesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Amenidades'];

    function AmenidadesDeleteController($uibModalInstance, entity, Amenidades) {
        var vm = this;

        vm.amenidades = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Amenidades.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
