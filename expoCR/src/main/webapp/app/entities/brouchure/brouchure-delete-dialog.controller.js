(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDeleteController',BrouchureDeleteController);

    BrouchureDeleteController.$inject = ['$uibModalInstance', 'entity', 'Brouchure'];

    function BrouchureDeleteController($uibModalInstance, entity, Brouchure) {
        var vm = this;

        vm.brouchure = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Brouchure.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }

        function readURL(input) {
          if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
               $('#blah')
                   .attr('src', e.target.result);
           };

           reader.readAsDataURL(input.files[0]);
       }
     }
    }
})();
